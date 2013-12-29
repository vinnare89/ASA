package de.thwildau.asa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StartActivity extends Activity implements OnTouchListener {
	
	TextView loginHeaderTextView;
	EditText loginUsernameEditText; // username input
	EditText loginPasswordEditText; // password input
	Button loginSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        loginHeaderTextView = (TextView) findViewById(R.id.login_header);
		loginUsernameEditText = (EditText) findViewById(R.id.login_username);
		loginPasswordEditText = (EditText) findViewById(R.id.login_password);
		loginSubmitButton = (Button) findViewById(R.id.login_submit);

		Typeface header_typeface = Typeface.createFromAsset(getAssets(),
				"fonts/MOIRE-BOLD.TTF");
		loginHeaderTextView.setTypeface(header_typeface);
		
		loginSubmitButton.setOnTouchListener(this);
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
        	Util.vibrate(getSystemService(Context.VIBRATOR_SERVICE));
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP){
        	Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
			startActivity(intent);
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }
    
}
