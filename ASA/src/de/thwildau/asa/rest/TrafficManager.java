package de.thwildau.asa.rest;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
*
* Class which makes Traffic Messages out of a server request
*
*/
public class TrafficManager {
	private static String baseUrl = "http://jphil.de:8080/ASA/TRAFFIC";

	public static List<Traffic> getAllRequests() {
		ArrayList<Traffic> list = new ArrayList<Traffic>();
		StringBuilder url = new StringBuilder();
		url.append(baseUrl);
		OSLCDataSet dataset = new OSLCDataSet(url.toString(), "user", "user",
				true);

		for (String path : dataset.getAllPaths()) {
			Log.e("allPath", "allPath: " + path);
			// System.out.println(path);
		}

		for (OSLCEntry entry : dataset) {
			Traffic traffic = new Traffic(entry);
			list.add(traffic);
		}
		System.out.println(list);
		return list;
	}

}
