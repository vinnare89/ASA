package de.thwildau.asa;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class representing a single Entry of the Traffic List
 *
 */
public class TrafficListView extends ArrayAdapter<String> {
	private final Activity context;
	// attributes of the list
	private final String[] values;
	private final String[] streetSigns;

	public TrafficListView(Activity context, String[] values,
			String[] streetSigns) {
		super(context, R.layout.traffic_list_item_layout, values);

		this.context = context;
		this.values = values;
		this.streetSigns = streetSigns;

	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.traffic_list_item_layout,
				null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.traffic_txt);
		ImageView imageView = (ImageView) rowView
				.findViewById(R.id.traffic_img);
		// ImageView wcView = (ImageView) rowView.findViewById(R.id.toilette);
		// ImageView shopView = (ImageView) rowView.findViewById(R.id.shop);
		// ImageView ecView = (ImageView) rowView.findViewById(R.id.ec);
		// ImageView imbissView = (ImageView) rowView.findViewById(R.id.imbiss);
		// ImageView spielplatzView = (ImageView)
		// rowView.findViewById(R.id.spielplatz);
		// ImageView duscheView = (ImageView) rowView.findViewById(R.id.dusche);
		// Button reserveButton = (Button) rowView.findViewById(R.id.reserve);
		
		// set text
		txtTitle.setText(values[position]);
		// get icon depending on message
		try {
			URL url = new URL(streetSigns[position]);
			InputStream content = (InputStream) url.getContent();
			Drawable d = Drawable.createFromStream(content, "src");
			imageView.setImageDrawable(d);
		} catch (Exception e) {

		}
		return rowView;
	}

}
