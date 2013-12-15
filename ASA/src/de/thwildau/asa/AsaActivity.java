package de.thwildau.asa;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class AsaActivity extends Activity {
	
    // Google Map
    private GoogleMap googleMap;
	TextView asaHeaderTextView;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.asa_layout);
	        
	        asaHeaderTextView = (TextView) findViewById(R.id.asa_header);
	        
	        Typeface header_typeface = Typeface.createFromAsset(getAssets(),
					"fonts/MOIRE-BOLD.TTF");
	        asaHeaderTextView.setTypeface(header_typeface);
	        
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
	        }
	    }
	    
	    @Override
	    protected void onResume() {
	        super.onResume();
	        initilizeMap();
	    }

}
