package de.thwildau.asa.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.thwildau.asa.AsaActivity;

import android.util.Log;

/**
 *
 * Class which makes POIs out of a server request
 *
 */
public class PoiManager {
	
//	private static String baseUrl = "http://jphil.de:8080/ASA/POI?lat=52.5401&long=12.9776&dist=25&sprit=e5";
	private static String baseUrl = "http://jphil.de:8080/ASA/POI";
	
	public static List<POI> getAllRequests() {
		ArrayList<POI> list = new ArrayList<POI>();
		StringBuilder url = new StringBuilder();
		url.append(baseUrl);
		url.append("?lat=" + AsaActivity.currentLocationLatitude);
		url.append("&long=" + AsaActivity.currentLocationLongitude);
		String dist = String.valueOf(AsaActivity.asaDistanceSpinner.getSelectedItem());
		url.append("&dist=" + dist.substring(0, dist.length()-3));
		String sprit = String.valueOf(AsaActivity.asaFuelSpinner.getSelectedItem());
		url.append("&sprit=" + sprit);
		OSLCDataSet dataset = new OSLCDataSet(url.toString(), "user", "user", true);
		
		for (String path : dataset.getAllPaths()) {
			Log.e("allPath", "allPath: " + path );
//			System.out.println(path);
		}
		
		for (OSLCEntry entry : dataset) {
			POI poi = new POI(entry);
			list.add(poi);
		}
		System.out.println(list); 
		Collections.sort(list);
		return list; 
	}

}
