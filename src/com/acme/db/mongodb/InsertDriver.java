package com.acme.db.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDriver{

	public void insertOne(String dBName, String collName){
	
	String databaseName = dBName;
	String collectionName = collName;
	MongoClient mongoClient = null;
	//Use items collection	
	MongoCollection<Document> collection = null;
	
	try {
		//connect to local mongoDB.. attach to 'datbaseName' and open 'collectionName'
			mongoClient = MongoConnect.connectme();
			MongoDatabase db = mongoClient.getDatabase("inventory");//Use inventory database
			collection = db.getCollection("items");
	} catch (Exception e1) {
			e1.printStackTrace();
	}
	
	try{	
		//$218.46 - Shocks - $184.98 = 403.44. 
	//insert in to collection
		System.out.println("Inserting " + databaseName + " into " + collectionName);
		Document doc = new Document();
		doc.put("item_number", "1236");
		doc.put("type", "CD");
		doc.put("name", "The Black Album");
		doc.put("Author", "Weezer");
		System.out.println("ITEM: " + doc.toJson());
		collection.insertOne(doc);
	}catch (Exception e){
	System.err.println(e.getClass().getName() + " :" + e.getMessage());
	}
	finally{
		mongoClient.close();
	}
	}
}