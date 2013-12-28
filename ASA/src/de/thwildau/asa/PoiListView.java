package de.thwildau.asa;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PoiListView extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] web;
	private final Integer[] imageId;
	private final Boolean[] wcs;
	private final Boolean[] shop;
	private final Boolean[] ec;
	private final Boolean[] imbiss;
	private final Boolean[] spielplatz;	
	private final Boolean[] dusche;
	private final Integer[] parkingAvailable;
	private final Integer[] parkingTotal;

	public PoiListView(Activity context, String[] web, Integer[] imageId, Boolean[] wcs, Boolean[] shop, Boolean[] ec, Boolean[] imbiss, Boolean[] spielplatz, Boolean[] dusche, Integer[] parkingAvailable, Integer[] parkingTotal) {
		super(context, R.layout.list_item_layout, web);
		this.context = context;
		this.web = web;
		this.imageId = imageId;
		this.wcs = wcs;
		this.shop = shop;
		this.ec = ec;
		this.imbiss = imbiss;
		this.spielplatz = spielplatz;
		this.dusche = dusche;
		this.parkingAvailable = parkingAvailable;
		this.parkingTotal = parkingTotal;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_item_layout, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		ImageView wcView = (ImageView) rowView.findViewById(R.id.toilette);
		ImageView shopView = (ImageView) rowView.findViewById(R.id.shop);
		ImageView ecView = (ImageView) rowView.findViewById(R.id.ec);
		ImageView imbissView = (ImageView) rowView.findViewById(R.id.imbiss);
		ImageView spielplatzView = (ImageView) rowView.findViewById(R.id.spielplatz);
		ImageView duscheView = (ImageView) rowView.findViewById(R.id.dusche);
		Button reserveButton = (Button) rowView.findViewById(R.id.reserve);
		
		reserveButton.setText("Reserve Parking (" + parkingAvailable[position] + "/" + parkingTotal[position] + ")");
		
		txtTitle.setText(web[position]);
		imageView.setImageResource(imageId[position]);
		if(!(wcs[position])){
			wcView.setVisibility(View.INVISIBLE);
		}
		if(!(shop[position])){
			shopView.setVisibility(View.INVISIBLE);
		}
		if(!(ec[position])){
			ecView.setVisibility(View.INVISIBLE);
		}
		if(!(imbiss[position])){
			imbissView.setVisibility(View.INVISIBLE);
		}
		if(!(spielplatz[position])){
			spielplatzView.setVisibility(View.INVISIBLE);
		}
		if(!(dusche[position])){
			duscheView.setVisibility(View.INVISIBLE);
		}
		return rowView;
	}
}
