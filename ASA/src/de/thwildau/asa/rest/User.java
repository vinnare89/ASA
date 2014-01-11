package de.thwildau.asa.rest;

/**
 * 
 * Class representing a User
 *
 */
public class User {
	// user attributes
	private Boolean result;
	private String fullName;
	private String gender;
	
	public User(OSLCEntry entry) {
		this(entry, "resultSet#entry#");
	}
	// constructor of a user
	public User(OSLCEntry entry, String baseUrl) {
		setResult(Boolean.parseBoolean((String) entry.getProperty(baseUrl + "result")));
		setFullName((String) entry.getProperty(baseUrl + "fullName"));
		setGender((String) entry.getProperty(baseUrl + "gender"));
	}
	
	// getter and setter .,..
	
	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
