package com.acme.db.mongodb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class ResourceValidation {
	
	ArrayList<String> list = new ArrayList<String>();
	private String dbChoice;
	private String collChoice;

public boolean databaseValidate(MongoClient mongoClient, String dbNum, ArrayList<String> dbList){
		System.out.println("dbList size is " + dbList.size());
		int dbN = Integer.parseInt(dbNum)-1;
		System.out.println("dbN = " + dbN);
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
}

public boolean collectionValidate(MongoClient mongoClient, MongoDatabase db, String database, String col, ArrayList<String> colList){
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
