package com.architecture.spark.model;

import java.util.List;

public class Register {

	private String _id;
	private List<Localization> localization;
	
	@Override
	public String toString() {
		return "{\"_id\":" + "\""+ _id +"\""+ ", \"localization\":"  + localization + "}";
	}
	public Register(String _id, List<Localization> localization) {
		super();
		this._id = _id;
		this.localization = localization;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<Localization> getLocalization() {
		return localization;
	}
	public void setLocalization(List<Localization> localization) {
		this.localization = localization;
	}

}
