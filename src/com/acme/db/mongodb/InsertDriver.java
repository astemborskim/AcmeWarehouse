package com.acme.db.mongodb;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDriver{

	public void insert(){
	
	ResourceValidation rvInsert = new ResourceValidation();
		
	MongoClient mongoClient = null;
	//Use items collection	
	MongoCollection<Document> collection = null;
	
	try {
		//connect to local mongoDB
			mongoClient = MongoConnect.connectme();
		//get and display available databases
			rvInsert.availableDatabases(mongoClient);
					
		//get user database choice
			rvInsert.getDatabaseChoice();
			
			MongoDatabase db = mongoClient.getDatabase("backstock");//Use inventory database
			collection = db.getCollection("misc");
	} catch (Exception e1) {
			e1.printStackTrace();
	}
	
	try{	 
	//insert in to collection
		System.out.println("Inserting " + "dbName" + " into " + "collection");
		Document doc = new Document();
		doc.put("item_number", "4564");
		doc.put("type", "Furniture");
		doc.put("name", "Leather Desk Chair");
		doc.put("Color", "Black");
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