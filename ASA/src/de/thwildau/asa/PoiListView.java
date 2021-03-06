package de.thwildau.asa;

import net.siemens.railautomation.oslc.http.request.RequestContext;
import net.siemens.railautomation.oslc.http.request.Requester;
import net.siemens.railautomation.oslc.http.response.ResponseContext;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * Class representing a single Entry of the Gas Station List (PoiListActivity)
 *
 */
public class PoiListView extends ArrayAdapter<String> {
	// attributes of the list
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
	private final String[] id;

	public PoiListView(Activity context, String[] web, Integer[] imageId,
			Boolean[] wcs, Boolean[] shop, Boolean[] ec, Boolean[] imbiss,
			Boolean[] spielplatz, Boolean[] dusche, Integer[] parkingAvailable,
			Integer[] parkingTotal, String[] ids) {
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
		this.id = ids;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final int _position = position;
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_item_layout, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		ImageView wcView = (ImageView) rowView.findViewById(R.id.toilette);
		ImageView shopView = (ImageView) rowView.findViewById(R.id.shop);
		ImageView ecView = (ImageView) rowView.findViewById(R.id.ec);
		ImageView imbissView = (ImageView) rowView.findViewById(R.id.imbiss);
		ImageView spielplatzView = (ImageView) rowView
				.findViewById(R.id.spielplatz);
		ImageView duscheView = (ImageView) rowView.findViewById(R.id.dusche);
		Button reserveButton = (Button) rowView.findViewById(R.id.reserve);
		// if there are no more parking places left ..
		if (parkingAvailable[position] == 0) {
			reserveButton.setClickable(false);
			reserveButton.setText("No Places Available");
		} else {
			reserveButton.setText("Reserve Parking ("
					+ parkingAvailable[position] + "/" + parkingTotal[position]
					+ ")");
		}
		txtTitle.setText(web[position]);
		imageView.setImageResource(imageId[position]);
		// determine whether the POI have an wc, shop, ... and depending on that show an icon or not
		if (!(wcs[position])) {
			wcView.setVisibility(View.INVISIBLE);
		}
		if (!(shop[position])) {
			shopView.setVisibility(View.INVISIBLE);
		}
		if (!(ec[position])) {
			ecView.setVisibility(View.INVISIBLE);
		}
		if (!(imbiss[position])) {
			imbissView.setVisibility(View.INVISIBLE);
		}
		if (!(spielplatz[position])) {
			spielplatzView.setVisibility(View.INVISIBLE);
		}
		if (!(dusche[position])) {
			duscheView.setVisibility(View.INVISIBLE);
		}
		reserveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View button) {
				// TODO Auto-generated method stub
				Requester _requester = new Requester();
				StringBuilder url = new StringBuilder();
				url.append("http://jphil.de:8080/ASA/PlaceBooker?action=take&mtsk_id=");
				url.append(id[_position]);
				RequestContext req = _requester.createRequestContext(null,
						url.toString());
				ResponseContext res = _requester.doRequest(req);
				System.out.println(res.getResponseBody());
				Button b = (Button) button;
				b.setText("Reserved Parking !");
				b.setClickable(false);
			}
		});

		return rowView;
	}
}
