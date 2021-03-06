package de.thwildau.asa.rest;

import java.util.List;

/**
 * 
 * Class representing a Traffic Message
 *
 */
public class Traffic {
	// attributes of a traffic message
	private String streetsign;
	private String event;
	private String street;
	private String description;
	private String age;
	
	public Traffic(OSLCEntry entry) {
		this(entry, "trafficmessages#entry#");
	}
	// constructor of a Traffic Message
	public Traffic(OSLCEntry entry, String baseUrl) {	
		System.out.println(entry.getProperty(baseUrl + "description"));
		setDescription((String) entry.getProperty(baseUrl + "description"));
		setStreetsign((String) entry.getProperty(baseUrl + "streetsign#image"));
		setEvent((String) entry.getProperty(baseUrl + "event#image").toString());
		setStreet((String) entry.getProperty(baseUrl + "street"));		
		setAge((String) entry.getProperty(baseUrl + "age"));
		
		
	}

	// getter and setter ...
	
	public String getStreetsign() {
		return streetsign;
	}

	public void setStreetsign(String streetsign) {
		this.streetsign = streetsign;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	


}
