package com.acme.db.mongodb;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.client.MongoCollection;


public class MapReduce{	
	
	void mapDB(MongoCollection<Document> collection){
		String map ="function () {"+"emit('size', {count:1});"+"}";
		String reduce = "function (key, values) { "+" total = 0; "+" for (var i in values) { "+" total += values[i].count; "+" } "+" return {count:total} }";
		MapReduceCommand cmd = new MapReduceCommand(collection, map, reduce,null, MapReduceCommand.OutputType.INLINE, null);
		MapReduceOutput out = collection.mapReduce(cmd);
			for (DBObject o : out.results()) {
				System.out.println(o.toString());
			}
			System.out.println("Done");
	}
	
	
}
