package de.thwildau.asa;

import android.app.Activity;
import android.os.Vibrator;

public class Util extends Activity {
	private static Vibrator v = null;
	
	public static void vibrate(Object o){
		if(v == null){
			v = (Vibrator) o;
		} else {
			v.vibrate(90);
		}		
	}
}
