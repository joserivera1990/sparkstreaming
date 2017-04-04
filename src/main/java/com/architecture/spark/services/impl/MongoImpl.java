package com.architecture.spark.services.impl;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.BSONObject;

import com.architecture.spark.model.LocalizationSeller;
import com.architecture.spark.model.Register;
import com.architecture.spark.model.Localization;
import com.architecture.spark.services.Mongo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoImpl implements Mongo, Serializable {

	private static final long serialVersionUID = -10492075101647135L;
     
	public void save(String localizationSent) throws UnknownHostException {
    	Gson gson = new Gson();
    	if (localizationSent != null && !localizationSent.isEmpty()) {
			MongoClient mongo = new MongoClient("localhost", 27017);
			
			LocalizationSeller localizationSeller = gson.fromJson(localizationSent, LocalizationSeller.class);
			
			DB db = mongo.getDB("local");
			DBCollection col = db.getCollection("SellerLocalization");
			DBObject object = col.findOne(buildQuery(localizationSeller.getIdentificationNumber()));
			
			if (object != null) {
				BSONObject bson = (BSONObject) object.get("localization");
				String stringLocalization =  bson.toString();
		        Type listType = new TypeToken<List<Localization>>(){}.getType();
		        List<Localization> listLocalization = gson.fromJson(stringLocalization, listType);
		        listLocalization = addLocalization(listLocalization, localizationSeller);		        
				col.remove(buildQuery(localizationSeller.getIdentificationNumber()));
				Register register = new Register(localizationSeller.getIdentificationNumber(), listLocalization);
				DBObject dbObject = (DBObject) JSON.parse(register.toString());
				col.insert(dbObject);
			} else { 
				Register register = new Register(localizationSeller.getIdentificationNumber(), createLocalization(localizationSeller));
				DBObject dbObject = (DBObject) JSON.parse(register.toString());
				col.insert(dbObject);
			}
			
		}	
	} 
    
	public MongoClient instanceMongo() throws UnknownHostException {
		return new MongoClient("localhost", 27017);
	}
	
	private List<Localization> addLocalization(List<Localization> listLocalization, LocalizationSeller localizationSeller) {
		listLocalization.add(new Localization(localizationSeller.getLatitude(), localizationSeller.getLongitud(), localizationSeller.getSentDate()));
		return listLocalization;
	}
	
	private static List<Localization> createLocalization(LocalizationSeller localizationSeller) {
		List<Localization> listLocalization = new ArrayList<>();
		listLocalization.add(new Localization(localizationSeller.getLatitude(), localizationSeller.getLongitud(), localizationSeller.getSentDate()));
		return listLocalization;
	}
	
    private static DBObject buildQuery(String identificationNumber) {
    	BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();	
		docBuilder.append("_id", identificationNumber);
		return docBuilder.get();
    }	
	
}
