package com.architecture.spark.model;

public class LocalizationSeller {

    private String identificationNumber;
    
    private String latitude;
    
	private String longitud;
    
    private String sentDate;

    @Override
	public String toString() {
		return "LocalizationSeller [identificationNumber=" + identificationNumber + ", latitude=" + latitude
				+ ", longitud=" + longitud + ", sentDate=" + sentDate + "]";
	}

	public LocalizationSeller(String identificationNumber, String latitude, String longitud, String sentDate) {
		super();
		this.identificationNumber = identificationNumber;
		this.latitude = latitude;
		this.longitud = longitud;
		this.sentDate = sentDate;
	}
    
	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
}
