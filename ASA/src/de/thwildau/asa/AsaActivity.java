package de.thwildau.asa;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class AsaActivity extends Activity {
	
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

}
