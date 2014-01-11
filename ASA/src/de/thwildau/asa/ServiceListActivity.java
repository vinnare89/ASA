package de.thwildau.asa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import de.thwildau.asa.rest.POI;
import de.thwildau.asa.rest.PoiManager;

/**
 * 
 * Activity which shows the list of gas station (POIs)
 * 
 */
public class ServiceListActivity extends Activity implements OnItemClickListener{
	// list of POIs
	List<POI> poiList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_list_layout);

		final ListView listview = (ListView) findViewById(R.id.service_list_view);
		// get all POIs from server
		poiList = PoiManager.getAllRequests();
		//initialize the lists attributes
		ArrayList<String> stringList = new ArrayList<String>();
		ArrayList<Boolean> wcList = new ArrayList<Boolean>();
		ArrayList<Boolean> shopList = new ArrayList<Boolean>();
		ArrayList<Boolean> ecList = new ArrayList<Boolean>();
		ArrayList<Boolean> imbissList = new ArrayList<Boolean>();
		ArrayList<Boolean> spielplatzList = new ArrayList<Boolean>();
		ArrayList<Boolean> duscheList = new ArrayList<Boolean>();
		ArrayList<Integer> parkingTotalList = new ArrayList<Integer>();
		ArrayList<Integer> parkingAvailableList = new ArrayList<Integer>();
		ArrayList<String> idList = new ArrayList<String>();
		// for every POI ...
		for (POI p : poiList) {
			// set up attributes for single view
			stringList.add("\t" + p.getStrasse() + " " +  p.getHausNr() + " " + p.getPlz() + " "
					+ p.getOrt() + "\t\t" + p.getSprit() + " €/l\t\t" + p.getEntfernung() + " km\t\t");
			wcList.add(p.isWc());
			shopList.add(p.isShop());
			ecList.add(p.isEc());
			imbissList.add(p.isImbiss());
			spielplatzList.add(p.isSpielplatz());
			duscheList.add(p.isDusche());
			parkingAvailableList.add(p.getParkingAvailable());
			parkingTotalList.add(p.getParkingTotal());
			idList.add(p.getMtsk_id());
		}
		// String[] values = new String[] { "Android", "iPhone",
		// "WindowsMobile",
		// "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		// "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
		// "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
		// "Android", "iPhone", "WindowsMobile" };
		
		// transform dynamic lists to static ones
		String[] values = stringList.toArray(new String[0]);
		Boolean[] wcs = wcList.toArray(new Boolean[0]);
		Boolean[] shops = shopList.toArray(new Boolean[0]);
		Boolean[] ecs = ecList.toArray(new Boolean[0]);
		Boolean[] imbisss = imbissList.toArray(new Boolean[0]);
		Boolean[] spielatzs = spielplatzList.toArray(new Boolean[0]);
		Boolean[] dusches = duscheList.toArray(new Boolean[0]);
		Integer[] parkingTotals = parkingTotalList.toArray(new Integer[0]);
		Integer[] parkingAvailables = parkingAvailableList.toArray(new Integer[0]);
		String[] ids = idList.toArray(new String[0]);
		Integer[] images = new Integer[values.length];
		// depending on the vendor set an image
		for (int i = 0; i < images.length; i++) {
			try {
				String brand = poiList.get(i).getMarke();
				if (brand.equals("ARAL")) {
					images[i] = R.drawable.aral;
				} else if (brand.equals("Total")) {
					images[i] = R.drawable.total;
				} else if (brand.equals("Agip")) {
					images[i] = R.drawable.agip;
				} else if (brand.equals("ESSO")) {
					images[i] = R.drawable.esso;
				} else if (brand.equals("JET")) {
					images[i] = R.drawable.jet;
				} else if (brand.equals("bft")) {
					images[i] = R.drawable.bft;
				} else if (brand.equals("TAMOIL")) {
					images[i] = R.drawable.tamoil;
				} else if (brand.equals("HEM")) {
					images[i] = R.drawable.hem;
				} else if (brand.equals("star")) {
					images[i] = R.drawable.star;
				} else if (brand.equals("Sprint")) {
					images[i] = R.drawable.sprint;
				} else if (brand.equals("Shell")) {
					images[i] = R.drawable.shell;
				} else if (brand.equals("SB")) {
					images[i] = R.drawable.sb;
				} else {
					images[i] = R.drawable.not_known;
				}
			} catch (NullPointerException e) {
				images[i] = R.drawable.not_known;
			}
			poiList.get(i).setDrawableId(images[i]);
		}

		// final ArrayList<String> list = new ArrayList<String>();
		// for (int i = 0; i < values.length; ++i) {
		// list.add(values[i]);
		// }
		// final StableArrayAdapter adapter = new StableArrayAdapter(this,
		// android.R.layout.simple_list_item_1, list);
		
		// hand over static list to adapter of the list view
		PoiListView adapter = new PoiListView(ServiceListActivity.this, values,
				images, wcs, shops, ecs, imbisss, spielatzs, dusches, parkingAvailables, parkingTotals, ids);

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                        List<String> objects) {
                super(context, textViewResourceId, objects);
                for (int i = 0; i < objects.size(); ++i) {
                        mIdMap.put(objects.get(i), i);
                }
        }

        @Override
        public long getItemId(int position) {
                String item = getItem(position);
                return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
                return true;
        }

}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int postition, long id) {
		AsaActivity.poiList = this.poiList;
		Log.e("Clicked on Item", poiList.get(postition).getLatitude()+ " ---- " + poiList.get(postition).getLongitude());
		AsaActivity.singleAsaActivity.RoutetoTarget(Double.parseDouble(poiList.get(postition).getLatitude()), Double.parseDouble(poiList.get(postition).getLongitude()));
		AsaActivity.singleAsaActivity.getRoutetoTarget(new LatLng(AsaActivity.currentLocationLatitude,AsaActivity.currentLocationLongitude), "Tankstelle");
		finish();
	}
}
