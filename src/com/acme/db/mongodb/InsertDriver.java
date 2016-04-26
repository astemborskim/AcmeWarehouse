package com.acme.db.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDriver{
	
ArrayList<String> dbList = new ArrayList<String>();
ArrayList<String> colList = new ArrayList<String>();
ArrayList<String> invType = new ArrayList<String>(); // this will be a database table to persist and be managed
boolean check;
ResourceValidation rvInsert = new ResourceValidation();

MongoClient mongoClient;// = null;
MongoCollection<Document> collection; //= null;
MongoDatabase db;

public void insert(){
	
	try {
		//connect to local mongoDB
			mongoClient = MongoConnect.connectme();
		//get and display available databases
			dbList = rvInsert.availableDatabases(mongoClient);
					
			do{
				//Prompt user to pick a database
					System.out.print("Insert to Database #");
				//get user input
					String dbin = rvInsert.getInput();
				//validate input
				if(!rvInsert.databaseValidate(mongoClient, dbin, dbList)){
						System.out.println(dbin + " is not a valid input!");
						check = false;
					}
				else{
					//System.out.println(dbin + " is valid");
					//System.out.println("You chose " + rvInsert.getDatabaseChoice());
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
		
			
			boolean valid;
			do{			
				System.out.println("What type of inventory would you like to add?:");
				int menuSize = rvInsert.getInventoryTypes(mongoClient);
				System.out.printf("\t%-20s\n", "0. Exit"); // give an option to exit from inventory menu
				String input = rvInsert.getInput();
				valid = rvInsert.validateInvType(input, menuSize);
				
			}while(!valid);
			
			if(valid){
				System.out.println("Call for the proper inventory insert model");
			}
				
			
	} catch (Exception e1) {
			e1.printStackTrace();
	}	
	
	

	}
	
	
	
	
//	try{	 
//	//insert in to collection
//		System.out.println("Inserting " + rvInsert.getCollectionChoice() + " into " + rvInsert.getDatabaseChoice());
//		Document doc = new Document()
//				.append("item_num", "12345")
//				.append("type", "Music")
//				.append("format", "digital")
//				.append("quantity", "9034")
//				.append("info", (new Document()
//						.append("artist", "Weezer")
//						.append("album", "White Album")
//						.append("year", "2016")
//						.append("genre", "Alternative")));
//
////Alternative put method to insert
////		doc.put("item_number", "4564");
////		doc.put("type", "Furniture");
////		doc.put("name", "Leather Desk Chair");
////		doc.put("Color", "Black");
//			
//		System.out.println("ITEM: " + doc.toJson());
//		//collection.insertOne(doc);
//	}catch (Exception e){
//		System.err.println(e.getClass().getName() + " :" + e.getMessage());
//	}
//	finally{
//		mongoClient.close();
//	}
	
}