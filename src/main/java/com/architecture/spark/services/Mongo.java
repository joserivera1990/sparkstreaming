package com.architecture.spark.services;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

public interface Mongo {

	void save(String localization) throws UnknownHostException;

	MongoClient instanceMongo() throws UnknownHostException;
}
