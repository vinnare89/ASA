package de.thwildau.asa;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.thwildau.asa.GMapV2Direction;
import de.thwildau.asa.rest.POI;
import de.thwildau.asa.rest.PoiManager;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class AsaActivity extends Activity {
	
	final Context context = this;
	
	int mStackLevel = 0;
	
	private final static int  CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    // Google Map
    private GoogleMap googleMap;
    LocationClient mLocationClient;
    Location mCurrentLocation;
    LocationRequest mLocationRequest;
    ArrayList<Location> list = new ArrayList<Location>();
    private static String msg;
    
    private static final int MILLISECONDS_PER_SECOND = 1000; // Milliseconds per second
    public static final int UPDATE_INTERVAL_IN_SECONDS = 300;// Update frequency in seconds
    private static final long UPDATE_INTERVAL =  MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;  // Update frequency in milliseconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 60;  // The fastest update frequency, in seconds
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    
	TextView asaHeaderTextView;
	Button testButton;
	Button testButton2;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.asa_layout);
	        
	        asaHeaderTextView = (TextView) findViewById(R.id.asa_header);
	        testButton = (Button) findViewById(R.id.button1);
	        testButton2 = (Button) findViewById(R.id.button2);
	        
	        Typeface header_typeface = Typeface.createFromAsset(getAssets(),
					"fonts/MOIRE-BOLD.TTF");
	        asaHeaderTextView.setTypeface(header_typeface);
	        
	        System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
	        
	        testButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
//					showDialog();
					getAllPoi();
				}
			});
	        
	        testButton2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), ServiceListActivity.class);
					startActivity(intent);
				}
			});
	        
	 }

	 
	    /**
	     * function to load map. If map is not created it will create it for you
	     * */
	    private void initilizeMap() {
	        if (googleMap == null) {
	            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	 
	            // check if map is created successfully or not
	            if (googleMap == null) {
	                Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
	            }
	            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
	            googleMap.setMyLocationEnabled(true);
	            // latitude and longitude
	            double longitude = 13.512783;
	            double latitude = 52.342052;
	            double longitude1 = 13.40292;
	            double latitude1 = 52.50452;
	            // create marker
	            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
	            MarkerOptions marker1 = new MarkerOptions().position(new LatLng(latitude1, longitude1)).title("Hello Maps");

	            // Changing marker icon
	            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
	            marker1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
	            // adding marker to the Map
	            googleMap.addMarker(marker);
	            googleMap.addMarker(marker1);
	            //set cameraview 
	            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(10).build();
	            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	            //create LatLng-Points 
	            LatLng fromPosition = new LatLng(latitude, longitude);
	            LatLng toPosition = new LatLng(latitude1,longitude1);
	            //create GMapV2Direction Object
	            GMapV2Direction md = new GMapV2Direction();
	            //get Document with Route Data between 2 Point and the mode (Driving, Walking)
	            Document doc = md.getDocument(fromPosition, toPosition, GMapV2Direction.MODE_DRIVING);
	            //getDirections from the Document, saved in an ArrayList
	            ArrayList<LatLng> directionPoint = md.getDirection(doc);
	            //Create RouteLine
	            PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
	            //Add Route points to the Route
	            for(int i = 0 ; i < directionPoint.size() ; i++) {          
	            	rectLine.add(directionPoint.get(i));
	            }
	            Log.e("Distance", md.getDistanceText(doc));
	            Log.e("Duration", md.getDurationText(doc));
	            //add Route to the Map
	            googleMap.addPolyline(rectLine);
	            
	            //Jetzt folgt ein Versuch die eigene Position zuverl�ssig aus dem Ger�t zu bekommen, da google.Map.getMyLocation  nur null zur�ck gibt
	            
//	            mLocationClient.connect();
//	            mLocationRequest = LocationRequest.create();
//	            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);    // Use high accuracy
////	            mLocationRequest.setInterval(UPDATE_INTERVAL);  // Setting the update interval to  5mins
////	            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);  // Set the fastest update interval to 1 min
//	            LocationListener locationListener = new MyLocationListener();
//	            mLocationClient.requestLocationUpdates(mLocationRequest,locationListener);
	            
//	            if(mLocationClient!=null)
//	            {
//	                double dLatitude = mLocationClient.getLatitude();
//	                double dLongitude = mLocationClient..getLongitude();
//	                Log.i("APPLICATION"," : "+dLatitude);
//	                Log.i("APPLICATION"," : "+dLongitude);
//	                googleMap.addMarker(new MarkerOptions().position(new LatLng(dLatitude, dLongitude)).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
//	                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLatitude, dLongitude), 8));
	//
//	            }
//	            else
//	            {
//	            	Log.i("APPLICATION"," unable ");
//	                Toast.makeText(this, "Unable to fetch the current location", Toast.LENGTH_SHORT);
//	            }            
	        }
	    }
	    
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	    }
	    
	    void showDialog() {
	        // DialogFragment.show() will take care of adding the fragment
	        // in a transaction.  We also want to remove any currently showing
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

	    //Geh�rt auch zum Versuch der eigenen Position
	    private final class MyLocationListener implements LocationListener {
	        @Override
	        public void onLocationChanged(Location location) {
	            // Report to the UI that the location was updated
	            mCurrentLocation =location;
	            Context context = getApplicationContext();

	            msg = Double.toString(location.getLatitude()) + "," +  Double.toString(location.getLongitude());
	            list.add(mCurrentLocation);
	            
	            Toast.makeText(context, msg,Toast.LENGTH_LONG).show();

	         }

	           }
	   //ben�tigt f�r die Routenberechnung
	   @Override
	   protected void onActivityResult(
	           int requestCode, int resultCode, Intent data) {
	       // Decide what to do based on the original request code
	       switch (requestCode) {

	           case CONNECTION_FAILURE_RESOLUTION_REQUEST :
	           /*
	            * If the result code is Activity.RESULT_OK, try
	            * to connect again
	            */
	               switch (resultCode) {
	                   case Activity.RESULT_OK :
	                   /*
	                    * Try the request again
	                    */

	                   break;
	               }

	       }

	   }
	   
	   private List<POI> getAllPoi(){
		   PoiManager manager = new PoiManager();
		   return manager.getAllRequests();
	   }
}
