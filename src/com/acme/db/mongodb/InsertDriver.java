package com.acme.db.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDriver{
	
ArrayList<String> dbList = new ArrayList<String>();
ArrayList<String> colList = new ArrayList<String>();
boolean check;
ResourceValidation rvInsert = new ResourceValidation();

MongoClient mongoClient;// = null;
MongoCollection<Document> collection; //= null;
MongoDatabase db;

public void insert(){

		System.out.println("I AM THE INSERTE DRIVER ... you called insert()");
	
	try {
		//connect to local mongoDB
			mongoClient = MongoConnect.connectme();
		//get and display available databases
			rvInsert.availableDatabases(mongoClient);
					
			do{
				//Prompt user to pick a database
					System.out.print("Query Database #");
				//get user input
					String dbin = rvInsert.getInput();
				//validate input
				if(!rvInsert.databaseValidate(mongoClient, dbin, dbList)){
						System.out.println(dbin + " is not a valid input!");
						check = false;
					}
				else{
					System.out.println(dbin + " is valid");
					System.out.println("You chose " + rvInsert.getDatabaseChoice());
				//Use chosen database
					db = mongoClient.getDatabase(rvInsert.getDatabaseChoice());
					check = true;
				}
			}while(!check);
			
			do{
				//List all collections from chosen database
					colList = rvInsert.availableCollections(mongoClient, db, rvInsert.getDatabaseChoice());
				//prompt for collection number
					System.out.print("Query Collection #");
				// get user input
					String cin = rvInsert.getInput();
				// validate collection choice
						if(!rvInsert.collectionValidate(mongoClient, db, rvInsert.getDatabaseChoice(), cin, colList)){
							System.out.println(cin + " is not a valid choice");
							check = false;
						}
						else{
							collection = db.getCollection(rvInsert.getCollectionChoice());
							check = true;
						}		
				}while (!check);
				
			
			db = mongoClient.getDatabase(rvInsert.getDatabaseChoice());//Use inventory database
			collection = db.getCollection(rvInsert.getCollectionChoice());
			
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