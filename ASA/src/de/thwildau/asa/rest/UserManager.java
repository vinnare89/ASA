package de.thwildau.asa.rest;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import de.thwildau.asa.StartActivity;
/**
 * 
 * Class to get user date out of a server request
 *
 */
public class UserManager {
	
	private static String baseUrl = "http://jphil.de:8080/ASA/LOGIN";
	
	public static List<User> getAllRequests() {
	
		ArrayList<User> list = new ArrayList<User>();
		StringBuilder url = new StringBuilder();
		url.append(baseUrl);
		url.append("?user=" + StartActivity.userName);
		url.append("&pass=" + StartActivity.userPw);
		Log.e("LOGIN URL",url.toString());
		OSLCDataSet dataset = new OSLCDataSet(url.toString(), "user", "user", true);
		
		for (String path : dataset.getAllPaths()) {
			Log.e("allPath", "allPath: " + path );
//			System.out.println(path);
		}
		
		for (OSLCEntry entry : dataset) {
			User user = new User(entry);
			list.add(user);
		}
		System.out.println(list); 
		return list; 
	}

}
