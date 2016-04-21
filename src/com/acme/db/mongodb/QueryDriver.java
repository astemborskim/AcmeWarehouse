package com.acme.db.mongodb;

import java.util.ArrayList;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class QueryDriver {

	String qname;
	String qvalue;
	MongoClient mongoClient;;
	MongoDatabase db;
	MongoCollection<Document> collection;
	ResourceValidation rvQuery = new ResourceValidation();
	ArrayList<String> dbList = new ArrayList<String>();
	ArrayList<String> colList = new ArrayList<String>();
	boolean check;
	
	public void query(int input){	
		//Connect	
			mongoClient = MongoConnect.connectme();
		//Get and display available databases	
			dbList = rvQuery.availableDatabases(mongoClient); 
		do{
			//Prompt user to pick a database
				System.out.print("Query Database #");
			//get user input
				String dbin = rvQuery.getInput();
			//validate input
			if(!rvQuery.databaseValidate(mongoClient, dbin, dbList)){
					System.out.println(dbin + " is not a valid input!");
					check = false;
				}
			else{
				System.out.println(dbin + " is valid");
				System.out.println("You chose " + rvQuery.getDatabaseChoice());
			//Use chosen database
				db = mongoClient.getDatabase(rvQuery.getDatabaseChoice());
				check = true;
			}
		}while(!check);
		
		do{
			//List all collections from chosen database
				colList = rvQuery.availableCollections(mongoClient, db, rvQuery.getDatabaseChoice());
			//prompt for collection number
				System.out.print("Query Collection #");
			// get user input
				String cin = rvQuery.getInput();
			// validate collection choice
					if(!rvQuery.collectionValidate(mongoClient, db, rvQuery.getDatabaseChoice(), cin, colList)){
						System.out.println(cin + " is not a valid choice");
						check = false;
					}
					else{
						collection = db.getCollection(rvQuery.getCollectionChoice());
						check = true;
					}		
			}while (!check);
		
		switch(input){
		
		case 1:
			//Show All Retrieved Doc in specified collection
				System.out.println("----[Retrieve all Documents in Collection]----");
				for (Document doc: collection.find()){
					System.out.println(doc.toJson());
				}
				break;
		case 2:
			//Constraint to field input
				queryConstrained(rvQuery.getDatabaseChoice(), rvQuery.getCollectionChoice());
				break;
		}
		
			mongoClient.close();
	}
	

	public void queryConstrained(String dbName, String colName){
			System.out.println("----[Retrieve Documents from " + dbName + " in collection " + colName + ":" + " ]----");
				System.out.print("Document Key: ");
				qname = rvQuery.getInput();
				System.out.print("Document Value: ");
				qvalue = rvQuery.getInput();
				for (Document doc: collection.find(new Document (qname, qvalue))){
					System.out.println(doc.toJson());
				}
			mongoClient.close();
	}
	
//	public void generateKeyList(){ //Right now this prints out the document. not the keys
//		List<Document> keys = db.getCollection("items").find().into(new ArrayList<Document>());
//		for (Document key : keys){
//			System.out.println(key);
//		}
//	}
}
