package de.thwildau.asa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import de.thwildau.asa.rest.POI;
import de.thwildau.asa.rest.PoiManager;
/**
 * Activity which will be started after pressing on the LKW-Button at the Main Menu
 * 
 * Contains the main logic of the application
 *
 */
public class AsaActivity extends Activity implements OnClickListener {

	final Context context = this;
	
	public static AsaActivity singleAsaActivity = null;

	int mStackLevel = 0;

	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	// Google Map
	private GoogleMap googleMap;
	// location Client
	LocationClient mLocationClient;
	// current Location
	Location mCurrentLocation;
	//Location request
	LocationRequest mLocationRequest;
	// Location Array List
	ArrayList<Location> list = new ArrayList<Location>();
	//String  for messages --- not used
	private static String msg;

	private static final int MILLISECONDS_PER_SECOND = 1000; // Milliseconds per
																// second
	public static final int UPDATE_INTERVAL_IN_SECONDS = 300;// Update frequency
																// in seconds
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS; // Update frequency in milliseconds
	private static final int FASTEST_INTERVAL_IN_SECONDS = 60; // The fastest
																// update
																// frequency, in
																// seconds
	// A fast frequency ceiling in milliseconds
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;
	
	// parameters for travel time tracking
	private Handler mHandler = new Handler();
	long mStartTime = 0L;
	long asaStartTime = 21600000;
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	// GUI elements
	TextView asaHeaderTextView;
	TextView asaTimeTextView;
	ToggleButton asaTimeToggleButton;
	ProgressBar asaTimeProgressBar;
	public static Spinner asaDistanceSpinner;
	public static Spinner asaFuelSpinner;
	Button asaTrafficButton;
	Button asaServiceButton;
	Button asaLogoutButton;
	
	
	// Google Maps GUI elements 
	MarkerOptions markerX;
	Marker MyLocation;
	Marker Current;
	Marker NextM;
	MarkerOptions MyLocMO;
	PolylineOptions rectLine;
	boolean StartCase = true;
	boolean StartCaseRoute = true;
	boolean RoutetoTarget = false;
	double TargetLon = 13.401747;
	double TargetLat = 52.518728;
	CameraPosition cameraPosition;
    String TargetDesc;
    

	// location data
	public static double currentLocationLatitude = 0.0;
	public static double currentLocationLongitude = 0.0;
	public static List<POI> poiList = new ArrayList<POI>();

	private LocationManager mLocationManager;
	private Handler mHandlerLOc;
	private boolean mGeocoderAvailable;
	private boolean mUseBoth;

	// A fast frequency ceiling in milliseconds
	// Keys for maintaining UI states after rotation.
	private static final String KEY_BOTH = "use_both";
	// UI handler codes.
	private static final int UPDATE_ADDRESS = 1;
	private static final int UPDATE_LATLNG = 2;

	private static final int TEN_SECONDS = 10000;
	private static final int TEN_METERS = 10;
	private static final int TWO_MINUTES = 1000 * 60 * 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asa_layout);
		
		if(singleAsaActivity == null){
			singleAsaActivity = this;
		}

		asaHeaderTextView = (TextView) findViewById(R.id.asa_header);
		asaTimeProgressBar = (ProgressBar) findViewById(R.id.asa_control_time_progressbar);
		asaTimeTextView = (TextView) findViewById(R.id.asa_control_time_text_view);
		asaTimeToggleButton = (ToggleButton) findViewById(R.id.asa_control_time_toggle_button);
		asaDistanceSpinner = (Spinner) findViewById(R.id.distance_spinner);
		asaFuelSpinner = (Spinner) findViewById(R.id.fuel_spinner);

		asaTrafficButton = (Button) findViewById(R.id.asa_button_traffic);
		asaServiceButton = (Button) findViewById(R.id.asa_button_service);
		asaLogoutButton = (Button) findViewById(R.id.asa_logout_button);
		asaLogoutButton.setOnClickListener(this);

		Typeface header_typeface = Typeface.createFromAsset(getAssets(),
				"fonts/MOIRE-BOLD.TTF");
		asaHeaderTextView.setTypeface(header_typeface);
		// set driver for XML-parsing
		System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
		asaTimeProgressBar.setProgress(100);
		
		// button for travel time tracking
		asaTimeToggleButton
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							// The toggle is enabled
							// if (mStartTime == 0L) {
							mStartTime = System.currentTimeMillis();
							mHandler.removeCallbacks(mUpdateTimeTask);
							mHandler.postDelayed(mUpdateTimeTask, 100);
							// }
						} else {
							// The toggle is disabled
							mHandler.removeCallbacks(mUpdateTimeTask);
						}
					}
				});
		// button to get traffic list
		asaTrafficButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						TrafficListActivity.class);
				startActivity(intent);
			}
		});
		// button to get Gas Station (POI) list
		asaServiceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ServiceListActivity.class);
				startActivity(intent);
			}
		});

		// Position + Routenberechnung
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// Restore apps state (if exists) after rotation.
		if (savedInstanceState != null) {
			mUseBoth = savedInstanceState.getBoolean(KEY_BOTH);
		} else {
			mUseBoth = false;
		}
		mUseBoth = true;
		mGeocoderAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& Geocoder.isPresent();

		// Handler for updating text fields on the UI like the lat/long and
		// address.
		mHandlerLOc = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case UPDATE_ADDRESS:
					Log.e("Location", (String) msg.obj);
				case UPDATE_LATLNG:
					Log.e("Location", (String) msg.obj);
					break;
				}
			}
		};
		// Get a reference to the LocationManager object.
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		

	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	// Restores UI states after rotation.
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(KEY_BOTH, mUseBoth);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();		//initilize Google Map
		setupMyLocation();	//get current Location
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	// Stop receiving location updates whenever the Activity becomes invisible.
	@Override
	protected void onStop() {
		super.onStop();
		mLocationManager.removeUpdates(listener);		//close location Manager
	}

	void showDialog() {
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		DialogFragment newFragment = ServiceDialog.newInstance("Tankstelle");
		newFragment.show(ft, "dialog");
	}

	// ben�tigt f�r die Routenberechnung
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {
		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			// If the result code is Activity.RESULT_OK, try to connect again
			switch (resultCode) {
			case Activity.RESULT_OK:
				// Try the request again
				break;
			}
		}
	}

	private List<POI> getAllPoi() {
		PoiManager manager = new PoiManager();
		return manager.getAllRequests();
	}

	OnClickListener mStartListener = new OnClickListener() {
		public void onClick(View v) {
			if (mStartTime == 0L) {
				mStartTime = System.currentTimeMillis();
				mHandler.removeCallbacks(mUpdateTimeTask);
				mHandler.postDelayed(mUpdateTimeTask, 100);
			}
		}
	};
	
	/**
	 * function the manage the traffic time tracking
	 */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			final long start = mStartTime;
			long millis = SystemClock.uptimeMillis() - start;
//			int seconds = (int) (millis / 1000) % 60;
//			int minutes = (int) ((millis / (1000 * 60)) % 60);
//			int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
			// int seconds = (int) (millis / 1000);
			// int minutes = seconds / 60;
			// seconds = seconds % 60;
			
			asaStartTime -= 1000;

			asaTimeTextView.setText(timeFormat.format(new Date(asaStartTime)));
			double progress = (asaStartTime/21600000.0)*100;
			asaTimeProgressBar.setProgress((int)progress);

			// if (seconds < 10) {
			// asaTimeTextView.setText("" + minutes + ":0" + seconds);
			// } else {
			// asaTimeTextView.setText("" + minutes + ":" + seconds);
			// }

			mHandler.postAtTime(this, start	+ millis + 1000);
		}
	};

	// Set up fine and/or coarse location providers depending on whether the
	// fine provider or
	// both providers button is pressed.
	private void setupMyLocation() {
		Location gpsLocation = null;
		Location networkLocation = null;
		mLocationManager.removeUpdates(listener);
		// Request updates from both fine (gps) and coarse (network) providers.
		gpsLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER,
				R.string.not_support_gps);
		networkLocation = requestUpdatesFromProvider(
				LocationManager.NETWORK_PROVIDER, R.string.not_support_network);
		// If both providers return last known locations, compare the two and
		// use the better
		// one to update the UI. If only one provider returns a location, use
		// it.
		if (gpsLocation != null && networkLocation != null) {
			updateUILocation(getBetterLocation(gpsLocation, networkLocation));
		} else if (gpsLocation != null) {
			updateUILocation(gpsLocation);
		} else if (networkLocation != null) {
			updateUILocation(networkLocation);
		}
	}

	/**
	 * Method to register location updates with a desired location provider. If
	 * the requested provider is not available on the device, the app displays a
	 * Toast with a message referenced by a resource id.
	 * 
	 * @param provider
	 *            Name of the requested provider.
	 * @param errorResId
	 *            Resource id for the string message to be displayed if the
	 *            provider does not exist on the device.
	 * @return A previously returned {@link android.location.Location} from the
	 *         requested provider, if exists.
	 */
	private Location requestUpdatesFromProvider(final String provider,
			final int errorResId) {
		Location location = null;
		if (mLocationManager.isProviderEnabled(provider)) {
			mLocationManager.requestLocationUpdates(provider, TEN_SECONDS,
					TEN_METERS, listener);
			location = mLocationManager.getLastKnownLocation(provider);
		} else {
			Toast.makeText(this, errorResId, Toast.LENGTH_LONG).show();
		}
		return location;
	}

	private void doReverseGeocoding(Location location) {
		// Since the geocoding API is synchronous and may take a while. You
		// don't want to lock
		// up the UI thread. Invoking reverse geocoding in an AsyncTask.
		(new ReverseGeocodingTask(this)).execute(new Location[] { location });
	}

	private void updateUILocation(Location location) {
		// We're sending the update to a handler which then updates the UI with
		// the new location.
		Message.obtain(mHandlerLOc, UPDATE_LATLNG,
				location.getLatitude() + ", " + location.getLongitude())
				.sendToTarget();

		// Bypass reverse-geocoding only if the Geocoder service is available on
		// the device.
		if (mGeocoderAvailable)
			doReverseGeocoding(location);
	}

	//location listener is used to get always the current location
	private final LocationListener listener = new LocationListener() {
		   
		@Override
		public void onLocationChanged(Location location) {
			// A new location update is received.
			updateUILocation(location);		//update UI location
			double lat = location.getLatitude();	
			double lon = location.getLongitude();
			LatLng coordinate = new LatLng(lat, lon);
			//create new marker with the current position
    	   MyLocMO = new MarkerOptions().position(coordinate).title("You").snippet("your currrent position").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
           //set marker to Google Map
    	   MyLocation = googleMap.addMarker(MyLocMO);
    	   //set view to current position 
           cameraPosition = new CameraPosition.Builder().target(coordinate).zoom(10).build();
 	        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
 	        //if a route was set, update the route
           if(RoutetoTarget){
	           if(StartCase){
		           StartCase = false;
	        	   getRoutetoTarget(coordinate, TargetDesc);
	           }else{
	        	   getRoutetoTarget(coordinate, TargetDesc);
	           }
           }
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	};

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix. Code taken from
	 * http://developer.android.com/guide/topics/location
	 * /obtaining-user-location.html
	 * 
	 * @param newLocation
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 * @return The better Location object based on recency and accuracy.
	 */
	protected Location getBetterLocation(Location newLocation,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return newLocation;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved.
		if (isSignificantlyNewer) {
			return newLocation;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return currentBestLocation;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(newLocation.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return newLocation;
		} else if (isNewer && !isLessAccurate) {
			return newLocation;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return newLocation;
		}
		return currentBestLocation;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	// AsyncTask encapsulating the reverse-geocoding API. Since the geocoder API
	// is blocked,
	// we do not want to invoke it from the UI thread.
	private class ReverseGeocodingTask extends AsyncTask<Location, Void, Void> {
		Context mContext;

		public ReverseGeocodingTask(Context context) {
			super();
			mContext = context;
		}

		@Override
		protected Void doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

			Location loc = params[0];
			currentLocationLatitude = loc.getLatitude();
			currentLocationLongitude = loc.getLongitude();
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(),
						loc.getLongitude(), 1);
			} catch (IOException e) {
				e.printStackTrace();
				// Update address field with the exception.
				Message.obtain(mHandlerLOc, UPDATE_ADDRESS, e.toString())
						.sendToTarget();
			}
			if (addresses != null && addresses.size() > 0) {
				Address address = addresses.get(0);
				// Format the first line of address (if available), city, and
				// country name.
				String addressText = String.format(
						"%s, %s, %s",
						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "", address.getLocality(),
						address.getCountryName());
				// Update address field on UI.
				Message.obtain(mHandlerLOc, UPDATE_ADDRESS, addressText)
						.sendToTarget();
			}
			return null;
		}
	};

	/**
	 * Method creates the Route from the current position to your target
	 * @param fromPosition		your current position
	 * @param TargetDesc		in most cases the selected gas station
	 */
	public void getRoutetoTarget(LatLng fromPosition, String TargetDesc) {
		// latitude and longitude

		LatLng currentPos = null;
		googleMap.clear();
		// create marker
		markerX = new MarkerOptions()
				.position(new LatLng(TargetLat, TargetLon)).title(TargetDesc)
				.draggable(true);

		// Changing marker icon
		markerX.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		// adding marker to the Map
		googleMap.addMarker(markerX);
		googleMap.addMarker(MyLocMO);
		// add all poi icons to map
		drawAllIconsOnMap();
		
		// create LatLng-Points
		LatLng toPosition = new LatLng(TargetLat, TargetLon);
		// create GMapV2Direction Object
		GMapV2Direction md = new GMapV2Direction();
		// get Document with Route Data between 2 Point and the mode (Driving,
		// Walking)
		Document doc = md.getDocument(fromPosition, toPosition,
				GMapV2Direction.MODE_DRIVING);
		// getDirections from the Document, saved in an ArrayList
		ArrayList<LatLng> directionPoint = md.getDirection(doc);
		// Create RouteLine
		PolylineOptions rectLine = new PolylineOptions().width(3).color(
				Color.RED);
		// Add Route points to the Route

		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
		}
		Log.e("Distance", md.getDistanceText(doc));
		Log.e("Duration", md.getDurationText(doc));
		// add Route to the Map
		rectLine = new PolylineOptions().width(3).color(Color.RED);
		for (int i = 0; i < directionPoint.size(); i++) {
			rectLine.add(directionPoint.get(i));
			if (i == 0) {
				currentPos = directionPoint.get(i);
			}
			if (i == 2) {
				float degree = 360 - (float) getBearing(directionPoint.get(i),
						currentPos);
				System.out.println("float: " + degree);
//				MarkerOptions current = new MarkerOptions()
//						.position(currentPos)
//						.title("current")
//						.snippet("nextPosition")
//						.icon(BitmapDescriptorFactory
//								.fromResource(R.drawable.ic_launcher));
//				Current = googleMap.addMarker(current);
//				MarkerOptions Next = new MarkerOptions()
//						.position(directionPoint.get(i))
//						.title("NExt")
//						.snippet("nextPosition")
//						.icon(BitmapDescriptorFactory
//								.fromResource(R.drawable.ic_launcher));
//				NextM = googleMap.addMarker(Next);
				// set cameraview
				cameraPosition = new CameraPosition.Builder()
						.target(currentPos).zoom(16).bearing(degree).build();
				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
			}
		}
		//draw the generated route to the google map
		googleMap.addPolyline(rectLine);
		//Prove current location and the target, stop route if target is reached
	   if((md.getDistanceValue(doc) <= 10) && (directionPoint.size() <= 2)){
		   //Ziel erreicht
		   RoutetoTarget = false;
		   googleMap.clear();
		   Toast.makeText(this, "ANGEKOMMEN!!!!!!!!!", Toast.LENGTH_LONG).show();
	   }
	}

	/**
	 * Method generates the bearing between two points 
	 * @param nextPos		next position in your route
	 * @param fromPos		current position
	 * @return				bearing between points
	 */
	public double getBearing(LatLng nextPos, LatLng fromPos) {
		double longDiff = nextPos.longitude - fromPos.longitude;
		double y = Math.sin(longDiff) * Math.cos(nextPos.latitude);
		double x = Math.cos(fromPos.latitude) * Math.sin(nextPos.latitude)
				- Math.sin(fromPos.latitude) * Math.cos(nextPos.latitude)
				* Math.cos(longDiff);
		double degree = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
		System.out.println("double: " + degree);
		return degree;
	}

	/**
	 * Method sets the Target to the Route
	 * @param lat		latitude
	 * @param lon		longitude
	 */
	public void RoutetoTarget(double lat, double lon) {
		Log.e("latlong undansdasd", lat + " ######## " + lon);
		TargetLat = lat;
		TargetLon = lon;
		RoutetoTarget = true;
	}
	
	/**
	 * Method draws the all icons to the map
	 */
	   public void drawAllIconsOnMap(){
		   //draw all icons on Map
		   Iterator<POI> iterator = poiList.iterator();
		   while (iterator.hasNext()) {
			   POI M = iterator.next();
			   StringBuilder infoText = new StringBuilder();
			   infoText.append("Preis: " + M.getSprit() + "�;");
			   infoText.append("Parking Places " + M.getParkingAvailable() + "/" + M.getParkingTotal());
			   if(M.isWc())
				   infoText.append("; WC");
			   if(M.isShop())
				   infoText.append("; Shop");
			   if(M.isEc())
				   infoText.append("; EC");
			   if(M.isImbiss())
				   infoText.append("; Diner");
			   if(M.isSpielplatz())
				   infoText.append("; Playground");
			   if(M.isDusche())
				   infoText.append("; Shower");
			   setIcontoMap(Double.parseDouble(M.getLatitude()), Double.parseDouble(M.getLongitude()), M.getMarke(),infoText.toString(), M.getDrawableId());
		   }
	   }
	   
	   /**
	    * Method sets an icon to the map 
	    * @param lat		latitude
	    * @param lon		longitude
	    * @param Desc		Description
	    * @param Detail		detailed description
	    * @param Icon		picture/icon
	    */
	   public void setIcontoMap(double lat, double lon, String Desc, String Detail, int Icon){
		   LatLng Position = new LatLng(lat, lon);
//		   Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), Icon), 40, 40, true);
//		   MarkerOptions current = new MarkerOptions().position(Position).title(Desc).snippet(Detail).icon(BitmapDescriptorFactory.fromBitmap(bitmap));
		   MarkerOptions current = new MarkerOptions().position(Position).title(Desc).snippet(Detail).icon(BitmapDescriptorFactory.fromResource(Icon));
//		   GroundOverlayOptions current = new GroundOverlayOptions().position(Position, 200f).image(BitmapDescriptorFactory.fromResource(Icon));
//		   googleMap.addGroundOverlay(current);
		   googleMap.addMarker(current);
		   //add zu icon liste
		   //poiList.add(current);
	   }

	@Override
	public void onClick(View arg0) {
		Intent i = new Intent(getApplicationContext(), StartActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
}
