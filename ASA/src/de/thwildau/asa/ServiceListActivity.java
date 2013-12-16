package de.thwildau.asa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.thwildau.asa.rest.POI;
import de.thwildau.asa.rest.PoiManager;

public class ServiceListActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_list_layout);
		
		final ListView listview = (ListView) findViewById(R.id.service_list_view);
		List<POI> poiList = PoiManager.getAllRequests();
		ArrayList<String> stringList = new ArrayList<String>(); 
		for(POI p : poiList){
			stringList.add("\t" + p.getStrasse() + " " + p.getPlz() + " " + p.getOrt() + "\t\t" + p.getE5() + " �/l");
		}
//	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//	        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
//	        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
//	        "Android", "iPhone", "WindowsMobile" };
		String[] values = stringList.toArray(new String[0]);
		Integer[] images = new Integer[values.length];
		for(int i = 0; i< images.length; i++){
			String brand = poiList.get(i).getMarke();
			if(brand.equals("ARAL")){
				images[i] = R.drawable.aral;
			}else if(brand.equals("Total")){
				images[i] = R.drawable.total;
			}else if(brand.equals("Agip")){
				images[i] = R.drawable.agip;
			}else if(brand.equals("ESSO")){
				images[i] = R.drawable.esso;
			}else if(brand.equals("JET")){
				images[i] = R.drawable.jet;
			}else if(brand.equals("bft")){
				images[i] = R.drawable.bft;
			}else if(brand.equals("TAMOIL")){
				images[i] = R.drawable.tamoil;
			}else if(brand.equals("HEM")){
				images[i] = R.drawable.hem;
			}else if(brand.equals("star")){
				images[i] = R.drawable.star;
			}else if(brand.equals("Sprint")){
				images[i] = R.drawable.sprint;
			}else if(brand.equals("Shell")){
				images[i] = R.drawable.shell;
			}else if(brand.equals("SB")){
				images[i] = R.drawable.sb;
			}else{
				images[i] = R.drawable.not_known;
			}
		}

//	    final ArrayList<String> list = new ArrayList<String>();
//	    for (int i = 0; i < values.length; ++i) {
//	      list.add(values[i]);
//	    }
//	    final StableArrayAdapter adapter = new StableArrayAdapter(this,
//	        android.R.layout.simple_list_item_1, list);
		PoiListView adapter = new
                PoiListView(ServiceListActivity.this, values, images);
		
	    listview.setAdapter(adapter);
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
}
