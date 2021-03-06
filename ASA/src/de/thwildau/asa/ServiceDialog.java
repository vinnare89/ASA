package de.thwildau.asa;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.DialogFragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceDialog extends DialogFragment {
	String title;

	/**
	 * Create a new instance of MyDialogFragment, providing "num" as an
	 * argument.
	 */
	static ServiceDialog newInstance(String title) {
		ServiceDialog f = new ServiceDialog();

		// Supply num input as an argument.
		Bundle args = new Bundle();
		args.putString("title", title);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		title = getArguments().getString("title");
		
		setStyle(DialogFragment.STYLE_NO_TITLE,android.R.style.Theme_Holo_Light_Dialog);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.service_dialog_layout, container,
				false);
		View image = v.findViewById(R.id.service_dialog_image);

		URL url;
		InputStream content = null;
		try {
			url = new URL("http://mosaic.cloudmade.de/img/roadsigns/DE/A31.png");
			content = (InputStream)url.getContent();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Drawable d = Drawable.createFromStream(content , "src"); 
		((ImageView) image).setImageDrawable(d);
		
		
		View tv = v.findViewById(R.id.service_dailog_text);
		((TextView) tv).setText("Type = " + title);
		// + getNameForNum(mNum));

		// Watch for button clicks.
		Button button = (Button) v.findViewById(R.id.service_dialog_ok_button);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// When button is clicked, call up to owning activity.
				dismiss();
			}
		});

		return v;
	}

}
