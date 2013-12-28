package de.thwildau.asa;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import de.thwildau.asa.rest.Traffic;
import de.thwildau.asa.rest.TrafficManager;

public class TrafficListActivity extends Activity implements
		OnItemClickListener {
	List<Traffic> trafficList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.traffic_list_layout);
		
		final ListView listview = (ListView) findViewById(R.id.traffic_list_view);
		
		trafficList = TrafficManager.getAllRequests();
		
		ArrayList<String> stringList = new ArrayList<String>();
		ArrayList<String> streetSignList = new ArrayList<String>();
		
		for(Traffic t : trafficList){
			stringList.add(t.getDescription());
			streetSignList.add(t.getStreetsign());
		}
		
		String[] values = stringList.toArray(new String[0]);
		String[] streetSigns = streetSignList.toArray(new String[0]); 
		
		TrafficListView adapter = new TrafficListView(TrafficListActivity.this, values, streetSigns);

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
}
