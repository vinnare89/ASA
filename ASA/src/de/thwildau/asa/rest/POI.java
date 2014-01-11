package de.thwildau.asa.rest;

import de.thwildau.asa.R;
import android.util.Log;
/**
 * Class representing a POI
 * 
 * Containing all known attributes of a POI
 *
 */
public class POI implements Comparable<POI> {

	private String strasse;

	private String mtsk_id;

	private double entfernung;

	private String breitengrad;

	private String laengengrad;

	private String name;

	private String ort;

	private String plz;

	private String datum;

	private String marke;

	private String hausNr;

	private String sprit;

	private boolean wc;
	private boolean shop;
	private boolean ec;
	private boolean imbiss;
	private boolean spielplatz;
	private boolean dusche;

	private int parkingTotal;
	private int parkingAvailable;
	
	private int drawableId = R.drawable.not_known;

	public POI(OSLCEntry entry) {
		this(entry, "pois#entry#");
	}
	/**
	 * Contructor of an POI
	 * @param entry
	 * @param baseUrl
	 */
	public POI(OSLCEntry entry, String baseUrl) {

		setStrasse((String) entry.getProperty(baseUrl + "strasse"));
		setMtsk_id((String) entry.getProperty(baseUrl + "mtsk_id"));
		setEntfernung(Double.parseDouble((String) entry.getProperty(baseUrl
				+ "entfernung")));
		setLatitude((String) entry.getProperty(baseUrl + "breitengrad"));
		setLongitude((String) entry.getProperty(baseUrl + "laengengrad"));
		setName((String) entry.getProperty(baseUrl + "name"));
		setOrt((String) entry.getProperty(baseUrl + "ort"));
		setPlz((String) entry.getProperty(baseUrl + "plz"));
		setDatum((String) entry.getProperty(baseUrl + "datum"));
		setMarke((String) entry.getProperty(baseUrl + "marke"));
		setHausNr((String) entry.getProperty(baseUrl + "hausnr"));
		setSprit((String) entry.getProperty(baseUrl + "sprit"));

		setWc(Boolean.parseBoolean((String) entry.getProperty(baseUrl + "wc")));
		setShop(Boolean.parseBoolean((String) entry.getProperty(baseUrl
				+ "shop")));
		setEc(Boolean.parseBoolean((String) entry.getProperty(baseUrl + "ec")));
		setImbiss(Boolean.parseBoolean((String) entry.getProperty(baseUrl
				+ "imbiss")));
		setSpielplatz(Boolean.parseBoolean((String) entry.getProperty(baseUrl
				+ "spielplatz")));
		setDusche(Boolean.parseBoolean((String) entry.getProperty(baseUrl
				+ "dusche")));

		setParkingTotal(Integer.parseInt((String) entry.getProperty(baseUrl
				+ "parkingPlaces#total")));
		setParkingAvailable(Integer.parseInt((String) entry.getProperty(baseUrl
				+ "parkingPlaces#available")));
	}
	
	// getter and setter ...
	
	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getMtsk_id() {
		return mtsk_id;
	}

	public void setMtsk_id(String mtsk_id) {
		this.mtsk_id = mtsk_id;
	}

	public Double getEntfernung() {
		return entfernung;
	}

	public void setEntfernung(Double entfernung) {
		this.entfernung = entfernung;
	}

	public String getLatitude() {
		return breitengrad;
	}

	public void setLatitude(String breitengrad) {
		this.breitengrad = breitengrad;
	}

	public String getLongitude() {
		return laengengrad;
	}

	public void setLongitude(String laengengrad) {
		this.laengengrad = laengengrad;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	public String getHausNr() {
		return hausNr;
	}

	public void setHausNr(String hausNr) {
		this.hausNr = hausNr;
	}

	public String getSprit() {
		return sprit;
	}

	public void setSprit(String sprit) {
		this.sprit = sprit;
	}

	public boolean isWc() {
		return wc;
	}

	public void setWc(boolean wc) {
		this.wc = wc;
	}

	public boolean isShop() {
		return shop;
	}

	public void setShop(boolean shop) {
		this.shop = shop;
	}

	public boolean isEc() {
		return ec;
	}

	public void setEc(boolean ec) {
		this.ec = ec;
	}

	public boolean isImbiss() {
		return imbiss;
	}

	public void setImbiss(boolean imbiss) {
		this.imbiss = imbiss;
	}

	public boolean isSpielplatz() {
		return spielplatz;
	}

	public void setSpielplatz(boolean spielplatz) {
		this.spielplatz = spielplatz;
	}

	public boolean isDusche() {
		return dusche;
	}

	public void setDusche(boolean dusche) {
		this.dusche = dusche;
	}

	public int getParkingTotal() {
		return parkingTotal;
	}

	public void setParkingTotal(int parkingTotal) {
		this.parkingTotal = parkingTotal;
	}

	public int getParkingAvailable() {
		return parkingAvailable;
	}

	public void setParkingAvailable(int parkingAvailable) {
		this.parkingAvailable = parkingAvailable;
	}

	public int getDrawableId() {
		return drawableId;
	}

	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	
	/**
	 * function to make POIs compareable
	 */
	@Override
	public int compareTo(POI another) {
		// TODO Auto-generated method stub
		double result = this.getEntfernung() - another.getEntfernung();
		if (result < 0.0) {
			return -1;
		} else {
			return 1;
		}
	}

}
