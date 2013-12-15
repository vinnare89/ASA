package de.thwildau.asa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity implements OnTouchListener {

	TextView mainMenuHeaderTextView;
	Button lkwButton;
	Button pkwButton;
	Button busButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_layout);

		mainMenuHeaderTextView = (TextView) findViewById(R.id.main_menu_header);

		Typeface header_typeface = Typeface.createFromAsset(getAssets(),
				"fonts/MOIRE-BOLD.TTF");
		mainMenuHeaderTextView.setTypeface(header_typeface);
		
		lkwButton = (Button) findViewById(R.id.main_menu_button_lkw);
		pkwButton = (Button) findViewById(R.id.main_menu_button_pkw);
		busButton = (Button) findViewById(R.id.main_menu_button_bus);
		
		lkwButton.setOnTouchListener(this);
		pkwButton.setOnTouchListener(this);
		busButton.setOnTouchListener(this);

		

	}
	
	@Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
        	Util.vibrate(getSystemService(Context.VIBRATOR_SERVICE));
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP){
        	Intent intent = null;		
    		switch (v.getId()) {
    		case R.id.main_menu_logout_button:
    			// Logout Button
    			break;
    		case R.id.main_menu_button_bus:
    			// Bus Button
    			break;
    		case R.id.main_menu_button_pkw:
    			// PKW Button
    			break;
    		case R.id.main_menu_button_lkw:
    			intent = new Intent(this, AsaActivity.class);
    			break;
    		case R.id.main_menu_button_activities:
    			// Activities Button
    			break;
    		case R.id.main_menu_button_alarm:
    			// Alarm Button
    			Toast.makeText(this, "Test", Toast.LENGTH_LONG).show();
    			break;
    		case R.id.main_menu_button_information:
    			// Information Button
    			break;
    		}
    		if (intent != null)
    			startActivity(intent);
        }

        return false;
    }
}
