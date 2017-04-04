package com.architecture.spark.model;

import java.io.Serializable;

public class Localization implements Serializable{

	private static final long serialVersionUID = -2734360439945071139L;

	private String latitude;
    
	private String longitud;
    
    private String date;

    public Localization() {
	}
    
	public Localization(String latitude, String longitud, String date) {
		super();
		this.latitude = latitude;
		this.longitud = longitud;
		this.date = date;
	}

	@Override
	public String toString() {
		return "{\"latitude\":"  +"\"" + latitude +"\"" + ", \"longitud\":" +"\"" + longitud +"\"" + ", \"date\":" +"\"" + date +"\"" + "}";
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
