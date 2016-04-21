package com.acme.db.mongodb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.hadoop.mapred.*;
import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
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
	
	public void query(int input){	
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
				queryConstrained(rv.getDatabaseChoice(), rv.getCollectionChoice());
				break;
		}
		
			mongoClient.close();
	}
	
	public void generateKeyList(){
		//db.getCollection("items").mapReduce(mapFunction, reduceFunction).filter(queryFilter);
	}
	
	public void queryConstrained(String dbName, String colName){
			System.out.println("----[Retrieve Documents from " + dbName + " in collection " + colName + ":" + " ]----");
				System.out.print("Document Key: ");
				qname = getInput();
				System.out.print("Document Value: ");
				qvalue = getInput();
				for (Document doc: collection.find(new Document (qname, qvalue))){
					System.out.println(doc.toJson());
				}
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
