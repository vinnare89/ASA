package de.thwildau.asa.rest;

public class User {
	
	private Boolean result;
	private String fullName;
	private String gender;
	
	public User(OSLCEntry entry) {
		this(entry, "resultSet#entry#");
	}
	
	public User(OSLCEntry entry, String baseUrl) {
		setResult(Boolean.parseBoolean((String) entry.getProperty(baseUrl + "result")));
		setFullName((String) entry.getProperty(baseUrl + "fullName"));
		setGender((String) entry.getProperty(baseUrl + "gender"));
	}

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
