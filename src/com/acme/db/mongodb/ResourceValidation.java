package com.acme.db.mongodb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class ResourceValidation {
	
	ArrayList<String> list = new ArrayList<String>();
	private String dbChoice;
	private String collChoice;
	private DBCursor curs;
	
public boolean databaseValidate(MongoClient mongoClient, String dbNum, ArrayList<String> dbList){
		try {
			//System.out.println("dbList size is " + dbList.size());
			int dbN = Integer.parseInt(dbNum)-1;
			//System.out.println("dbN = " + dbN);
			if (dbN>=0 && dbN<dbList.size()){
				String choosenDB = dbList.get(dbN);
				//System.out.println("You chose " + choosenDB);
				setDatabaseChoice(choosenDB);
				dbList.clear();
				return true;
			}
			else{
				return false;
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid menu number!");
			//e.printStackTrace();
			return false;
		}
}

public boolean collectionValidate(MongoClient mongoClient, MongoDatabase db, String database, String col, ArrayList<String> colList){
	try {
		mongoClient.getDB(database);
		MongoIterable<String> cols = db.listCollectionNames();
		int cNum = Integer.parseInt(col)-1;
		if (cNum>=0 && cNum<colList.size()){
			String chosenCol = colList.get(cNum);
			setCollectionChoice(chosenCol);
			colList.clear();
			return true;
		}
		else{
			return false;
		}
	} catch (NumberFormatException e) {
		System.out.println("Please enter a valid menu number!");
		return false;
		//e.printStackTrace();
	}
}

	public String getDatabaseChoice(){
		return dbChoice;
	}

	private void setDatabaseChoice(String db){
		dbChoice=db;
	}
	
	public String getCollectionChoice(){
		return collChoice;
	}
	
	private void setCollectionChoice(String col){
		collChoice = col;
	}
	
	public ArrayList<String> availableDatabases(MongoClient mongoClient){
		list.clear();
		//List available databases
		MongoIterable<String> dbs = mongoClient.listDatabaseNames();
		int index = 0;
		for (String dbName : dbs){
			//mongoClient.getDB(dbName);
			list.add(index, dbName);
			System.out.printf("\t%-10s\n", index+1 +". " + list.get(index));
			index++;
		}
			return list;
	}
	
	public ArrayList<String> availableCollections(MongoClient mongoClient, MongoDatabase db, String database){
		list.clear();
		MongoIterable<String> c = db.listCollectionNames();
		int cindex = 0;
		for (String cName : c){
			//mongoClient.getDB(database);
			list.add(cindex, cName);
			System.out.printf("\t%-10s\n", cindex+1 +". " + list.get(cindex));
			cindex++;
		}
		return list;
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
	//List valid inventory that can be added and return the menu size to the calling class
	public int getInventoryTypes(MongoClient mongoClient){
		MongoDatabase db = mongoClient.getDatabase("serverPersistant");
		MongoCollection<Document> invType = db.getCollection("inventoryType");
		int size=0;
		//get document containing valid inventory types and size of document options
		for(Document doc : invType.find()){ 
			Double sz = (Double) doc.get("size"); //size of the document (# of keys)
			size = sz.intValue(); //convert to integer
			//Print each inventory type
			for(int i = 1; i <= size; i++){
				String index = "" + i;
				System.out.printf("\t%-10s\n", index + ". " + doc.get(index));
			}
			
		}
		return size;
	}
	
	public boolean validateInvType(String invNum, int menuSize){
		
		try {
			int choice = Integer.parseInt(invNum);
			if(choice >= 0 && choice <= menuSize){
				return true;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Not a valid choice... must be integer!");
			//e.printStackTrace();
			return false;
		}
		return false;
	}
//	public boolean databaseValidate(MongoClient mongoClient, String db){
//	
//		
//		MongoIterable<String> dbs = mongoClient.listDatabaseNames();
//	    
//		for (String dbName : dbs){
//			mongoClient.getDB(dbName);
//			System.out.println("Database Name: " + dbName);
//		if(dbName.equals(db)) {
//				System.out.println("Database " + dbName + " exists!");
//				return true;
//	        		    	}
//			else {
//				System.out.println(db + " does not exist!");
//				return false;
//			}
//		
//	    } 
//		mongoClient.close();
//		return false;
//	}

	
	
//	for (String dbName : dbs){
//	mongoClient.getDB(dbName);
//	System.out.println("Database Name: " + dbName);
//if(dbName.equals(db)) {
//		System.out.println("Database " + dbName + " exists!");
//		return true;
//    		    	}
//	else {
//		System.out.println(db + " does not exist!");
//		return false;
//	}
//
//} 
	
//	for (String colName : cols){
//	//System.out.println("Collection name: " + colName);
//	if (colName.equals(col)){
//		System.out.println("Collection " + colName + "exists!");
//		return true;
//	}
//}
//System.out.println("Collection " + col + " does NOT exist!");
//return false;	
	
}
