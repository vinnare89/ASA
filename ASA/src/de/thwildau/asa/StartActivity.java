package de.thwildau.asa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.thwildau.asa.rest.User;
import de.thwildau.asa.rest.UserManager;

/**
 * First Activity that will be shown after starting the ASA-App
 * 
 * The user have to enter the credentials to go further
 *
 */
public class StartActivity extends Activity implements OnTouchListener {
	// gui elements
	TextView loginHeaderTextView;
	EditText loginUsernameEditText; // username input
	EditText loginPasswordEditText; // password input
	Button loginSubmitButton;
	
	// user data
	public static User _user;
	public static String userName = "";
	public static String userPw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // allow connections without multi-threading
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        System.setProperty("org.xml.sax.driver", "org.xmlpull.v1.sax2.Driver");
        
        loginHeaderTextView = (TextView) findViewById(R.id.login_header);
		loginUsernameEditText = (EditText) findViewById(R.id.login_username);
		loginPasswordEditText = (EditText) findViewById(R.id.login_password);
		loginSubmitButton = (Button) findViewById(R.id.login_submit);

		Typeface header_typeface = Typeface.createFromAsset(getAssets(),
				"fonts/MOIRE-BOLD.TTF");
		loginHeaderTextView.setTypeface(header_typeface);
		loginUsernameEditText.setText("max");
		loginPasswordEditText.setText("xam");
		
		loginSubmitButton.setOnTouchListener(this);
    }
    /**
     * react on button
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN ) {
        	Util.vibrate(getSystemService(Context.VIBRATOR_SERVICE));
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP){
        	// if user credentials are correct, go further and start Main Menu
        	if(login()){
        		Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
    			startActivity(intent);
        	}else{
        		Toast.makeText(getApplicationContext(), "Wrong Credentials !", Toast.LENGTH_LONG).show();
        	}
        	
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }
    /**
     * check credentials
     * @return result of credential check
     */
    private boolean login(){
    	userName = loginUsernameEditText.getText().toString();
    	userPw = loginPasswordEditText.getText().toString();
    	System.out.println("LOGIN ------> " + userName + " --> " + userPw);
    	this._user = UserManager.getAllRequests().get(0);
    	Log.e("LOGIN", _user.getFullName() + " --> " + _user.getResult());
    	return _user.getResult();
//    	return false;
    }
    
}
