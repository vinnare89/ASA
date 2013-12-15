package de.thwildau.asa;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class AsaActivity extends Activity {
	
	final Context context = this;
	
	int mStackLevel = 0;
	
    // Google Map
    private GoogleMap googleMap;
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
	        
	        testButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					showDialog();
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

}
