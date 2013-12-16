package de.thwildau.asa.rest;

import android.util.Log;

public class POI {
	
	private String strasse;
	
	private String mtsk_id;
	
	private String entfernung;
	
	private String breitengrad;
	
	private String laengengrad;
	
	private String name;
	
	private String ort;
	
	private String plz;
	
	private String datum;
	
	private String marke;
	
	private String hausNr;
	
	private String e5;
	
	
	public POI(OSLCEntry entry){
		this(entry, "pois#entry#");
	}
	
	public POI(OSLCEntry entry, String baseUrl) {
		
		
		setStrasse((String)entry.getProperty(baseUrl + "strasse"));
		setMtsk_id((String)entry.getProperty(baseUrl + "mtsk_id"));
		setEntfernung((String)entry.getProperty(baseUrl + "entfernung"));
		setBreitengrad((String)entry.getProperty(baseUrl + "breitengrad"));
		setLaengengrad((String)entry.getProperty(baseUrl + "laengengrad"));
		setName((String)entry.getProperty(baseUrl + "name"));
		setOrt((String)entry.getProperty(baseUrl + "ort"));
		setPlz((String)entry.getProperty(baseUrl + "plz"));
		setDatum((String)entry.getProperty(baseUrl + "datum"));
		setMarke((String)entry.getProperty(baseUrl + "marke"));
		setHausNr((String)entry.getProperty(baseUrl + "hausnr"));
		setE5((String)entry.getProperty(baseUrl + "e5"));
		
		System.out.println(getName() + " --> " + getStrasse() + " " + getHausNr());
		
		
	/*	setLink((String)entry.getProperty("id"));
		setId((String)entry.getProperty(baseUrl + "dc:title"));
		setHeadline((String)entry.getProperty(baseUrl + "Headline"));
		setToolVersion((String)entry.getProperty(baseUrl + "ToolVersion"));
		setSubmitDate((String)entry.getProperty(baseUrl + "SubmitDate"));
		setSubmitter((String)entry.getProperty(baseUrl + "Submitter"));
		setRequestType((String)entry.getProperty(baseUrl + "RequestType"));
		setCustomerName((String)entry.getProperty(baseUrl + "CustomerName"));
		setCustomerDepartment((String)entry.getProperty(baseUrl + "CustomerDepartment"));
		setCustomerMail((String)entry.getProperty(baseUrl + "CustomerMail"));
		setCustomer((String)entry.getProperty(baseUrl + "Customer"));
		setOwner((String)entry.getProperty(baseUrl + "CurrentOwner"));
		setDescription((String)entry.getProperty(baseUrl + "ProblemDescription"));
		setToolName((String)entry.getProperty(baseUrl + "Tool#Name"));
		
		System.out.println(getDescription());*/
		
	}

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

	public String getEntfernung() {
		return entfernung;
	}

	public void setEntfernung(String entfernung) {
		this.entfernung = entfernung;
	}

	public String getBreitengrad() {
		return breitengrad;
	}

	public void setBreitengrad(String breitengrad) {
		this.breitengrad = breitengrad;
	}

	public String getLaengengrad() {
		return laengengrad;
	}

	public void setLaengengrad(String laengengrad) {
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

	public String getE5() {
		return e5;
	}

	public void setE5(String e5) {
		this.e5 = e5;
	}

}