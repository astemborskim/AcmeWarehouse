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
	MongoClient mongoClient = null;
	MongoDatabase db = null;
	MongoCollection<Document> collection = null;;;
	ArrayList<String> list = new ArrayList<String>();
	
	public void queryAll(){
		//Connection
			mongoClient = MongoConnect.connectme();
		//Use inventory database
			db = mongoClient.getDatabase("inventory");
		//Use items collection	
			collection = db.getCollection("items");
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
			
		//List available databases
			MongoIterable<String> dbs = mongoClient.listDatabaseNames();
			int index = 0;
			for (String dbName : dbs){
				mongoClient.getDB(dbName);
				list.add(index, dbName);
				System.out.printf("\t%-10s\n", index+1 +". " + list.get(index));
				index++;
			}
			
			System.out.print("Database #: ");
			qdb = getInput();
			//Check if DB is valid
			if (dbValidate(qdb)){
				System.out.print("Collection: ");
				qcol = getInput();
				//check if collection is valid
				if (colValidate(qdb, qcol)){
					System.out.print("Document Field: ");
					qname = getInput();
					System.out.print("Field Value:: ");
					qvalue = getInput();
					for (Document doc: collection.find()){
						System.out.println(doc.toJson());
					}
				}
			}
			mongoClient.close();
	}
	
	public boolean dbValidate(String db){
		MongoIterable<String> dbs = mongoClient.listDatabaseNames();
	    
		for (String dbName : dbs){
			mongoClient.getDB(dbName);
			System.out.println("Database Name: " + dbName);
		
		//MongoCursor<String> dbsCursor = mongoClient.listDatabaseNames().iterator();
			if(dbName.equals(db)) {
				System.out.println("Database " + dbName + " exists!");
	        	return true;
	    	}
			else {
				System.out.println(db + " does not exist!");
				return false;
			}
	    } 
		return false;
}
	
	public boolean colValidate(String database, String col){
		mongoClient.getDB(database);
		MongoIterable<String> cols = db.listCollectionNames();
		
		for (String colName : cols){
			System.out.println("Collection name: " + colName);
		}
		
		return false;
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
