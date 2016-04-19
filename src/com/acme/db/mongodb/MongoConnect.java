package com.acme.db.mongodb;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;

public class MongoConnect {
	
	public static MongoClient connectme(){
		Logger logger = Logger.getLogger("org.mongodb.driver");
		logger.setLevel(Level.WARNING);
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		return mongoClient;
	}
}