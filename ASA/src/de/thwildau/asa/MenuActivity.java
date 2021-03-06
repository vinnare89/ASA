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
/**
 * Activity which handles the Main Menu
 * Currently just the LKW-Button contains functionality
 *
 */
public class MenuActivity extends Activity implements OnTouchListener, OnClickListener {
	// gui elements
	TextView mainMenuHeaderTextView;
	Button lkwButton;
	Button pkwButton;
	Button busButton;
	Button logoutButton;


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
		logoutButton = (Button) findViewById(R.id.main_menu_logout_button);
		
		lkwButton.setOnTouchListener(this);
		pkwButton.setOnTouchListener(this);
		busButton.setOnTouchListener(this);
		logoutButton.setOnClickListener(this);
		// welcome the user
		StringBuilder welcomeText = new StringBuilder();
		welcomeText.append("Welcome ");
		if("m".equals(StartActivity._user.getGender())){
			welcomeText.append("Mr. ");
		} else {
			welcomeText.append("Mrs. ");
		}
		welcomeText.append(StartActivity._user.getFullName() + " !");
		Toast.makeText(getApplicationContext(), welcomeText.toString(), Toast.LENGTH_LONG).show();
	}
	
	@Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
        	Util.vibrate(getSystemService(Context.VIBRATOR_SERVICE));
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP){
        	// determine pressed button
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

	@Override
	public void onClick(View arg0) {
		Intent i = new Intent(getApplicationContext(), StartActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
