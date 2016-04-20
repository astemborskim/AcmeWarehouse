package com.acme.db.mongodb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class QueryDriver {

	String qdb;
	String qcol;
	String qname;
	String qvalue;
	MongoClient mongoClient;;
	MongoDatabase db;
	MongoCollection<Document> collection;
	ResourceValidation rv = new ResourceValidation();
	ArrayList<String> dbList = new ArrayList<String>();
	ArrayList<String> colList = new ArrayList<String>();
	boolean check;
	
	public void queryAll(){	
		//Connect	
			mongoClient = MongoConnect.connectme();
		//Get available databases	
			dbList = rv.availableDatabases(mongoClient);
		do{
		//Prompt user to pick a database
			System.out.print("Query Database #");
		//get user input
			String dbin = getInput();
		//validate input
		if(!rv.databaseValidate(mongoClient, dbin, dbList)){
				System.out.println(dbin + " is not a valid input!");
				check = false;
			}
		else{
			System.out.println(dbin + " is valid");
			System.out.println("You chose " + rv.getDatabaseChoice());
			//Use inventory database
			db = mongoClient.getDatabase(rv.getDatabaseChoice());
			check = true;
		}
		}while(!check);
		
		do{
		//List all collections from chosen database
			colList = rv.availableCollections(mongoClient, db, rv.getDatabaseChoice());
		//prompt for collection number
			System.out.print("Query Collection #");
		// get user input
			String cin = getInput();
		// validate collection choice
				if(!rv.collectionValidate(mongoClient, db, rv.getDatabaseChoice(), cin, colList)){
					System.out.println(cin + " is not a valid choice");
					check = false;
				}
				else{
					collection = db.getCollection(rv.getCollectionChoice());
					check = true;
				}		
			}while (!check);
		//Show All Retrieved Docs
				System.out.println("----[Retrieve all Documents in Collection]----");
				for (Document doc: collection.find()){
					System.out.println(doc.toJson());
				}
			mongoClient.close();
	}
	
	public void queryConstrained(){
		//Connection
			mongoClient = MongoConnect.connectme();
		//Use inventory database
			db = mongoClient.getDatabase("inventory"); //qdb
		//Use items collection	
			collection = db.getCollection("items"); //qcol
		//Show All Retrieved Docs
			System.out.println("----[Retrieve Documents from " + "qdb" + " in collection " + "qcol:" + " ]----");
			System.out.println("Available Databases:");
			
//		//List available databases
			rv.availableDatabases(mongoClient);
			
			System.out.print("Database #: ");
			qdb = getInput();
			
//			//Check if DB is valid
//			if (rv.databaseValidate(mongoClient, qdb)){
//				//List available collections
//				rv.availableCollections(mongoClient, db, qdb);
//
//				
//				System.out.print("Collection: ");
//				qcol = getInput();
//				//check if collection is valid
//				if (rv.collectionValidate(mongoClient, db , qdb, qcol)){
//					System.out.print("Document Field: ");
//					qname = getInput();
//					System.out.print("Field Value:: ");
//					qvalue = getInput();
//					for (Document doc: collection.find(new Document (qname, qvalue))){
//						System.out.println(doc.toJson());
//					}
//				}
//			}
			mongoClient.close();
	}

	public String getInput(){
		String s=null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			s = br.readLine();
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return s;
	}
}
