package de.thwildau.asa.rest;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class PoiManager {
	
	private static String baseUrl = "http://jphil.de:8080/ASA/POI?lat=52.5401&long=12.9776&dist=25&sprit=e5";
	
	public static List<POI> getAllRequests() {
		ArrayList<POI> list = new ArrayList<POI>();
		OSLCDataSet dataset = new OSLCDataSet(baseUrl, "user", "user", true);
		
		for (String path : dataset.getAllPaths()) {
			Log.e("allPath", "allPath: " + path );
//			System.out.println(path);
		}
		
		for (OSLCEntry entry : dataset) {
			POI poi = new POI(entry);
			list.add(poi);
		}
		System.out.println(list); 
		return list; 
	}

}
