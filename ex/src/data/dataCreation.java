package data;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.bson.Document;


public class dataCreation {
	
	private static MongoCollection<Document> urls;
	private static MongoDatabase database;
	//private Block<Document> printBlock = document -> System.out.println(document.toJson());
	
	public static ArrayList <Map.Entry <String,String> > graph;
	
	public static void mongoDB() {
		//String link="mongodb://localhost:27017";
		//MongoClient client = 
		//MongoClient mongoClient = new MongoClients(new MongoClientURI("mongodb://localhost:27017"));
		
		MongoClient mongoClient = MongoClients.create();//new MongoClients();
		MongoDatabase dataB = mongoClient.getDatabase("urlDB");
		database=dataB;
		urls= dataB.getCollection("urls");//.createCollection("urls");
		//urls= dataB.get
		System.out.println(urls);
		//urls = database.getCollection("spells");
	}
	
	
	public void creationData() {
		
		ArrayList <Map.Entry <String,String> > graph = new ArrayList <Entry <String,String> > (); 
		
		graph.add(new AbstractMap.SimpleEntry<String, String>("A","B")); 
		graph.add(new AbstractMap.SimpleEntry<String, String>("A","C")); 
		graph.add(new AbstractMap.SimpleEntry<String, String>("B","C")); 
		graph.add(new AbstractMap.SimpleEntry<String, String>("C","A")); 
		graph.add(new AbstractMap.SimpleEntry<String, String>("D","C")); 
		
		this.graph=graph;
	} 
	
	public static void completDB() {

		//urls.insertOne(arg0);
		/*BasicDBObject documentDetail = new BasicDBObject();
		documentDetail.put("PageName", "A");
		documentDetail.put("PageRank", 1);
		List<String> linkedpages = new ArrayList<>();
		linkedpages.add("C");
		documentDetail.put("linkedPages", linkedpages);*/
		
		Document doc = new Document();
		doc.put("PageName", "A");
		doc.put("PageRank", 1);
		doc.append("linkedPages", Arrays.asList("C"));
		
		Document docB = new Document();
		docB.put("PageName", "B");
		docB.put("PageRank", 1);
		docB.append("linkedPages", Arrays.asList("A"));
		
		Document docC = new Document();
		docC.put("PageName", "C");
		docC.put("PageRank", 1);
		docC.append("linkedPages", Arrays.asList("A","B","D"));
		
		Document docD = new Document();
		docD.put("PageName", "D");
		docD.put("PageRank", 1);
		docD.append("linkedPages", Arrays.asList());
		
		urls.insertOne(doc);
		urls.insertOne(docB);
		urls.insertOne(docC);
		urls.insertOne(docD);
	}
	
	
	
	public ArrayList <Map.Entry <String,String>> getGraph(){
		return this.graph;
	}
	
	
    private static class DatabaseHolder {
        private static final dataCreation INSTANCE = new dataCreation();
    }

    public static dataCreation getInstance() {
        return DatabaseHolder.INSTANCE;
    }

    public static void clean() {
        urls.drop();
    }

    public void insert(Document document) {
        //urls.insertOne(document);
    }

    public void insert(List<Document> documents) {
        documents.forEach(document -> {
            if (document != null) {
                //urls.insertOne(document);
            }
        });
    }

    public void printAll() {
        //urls.find().forEach(printBlock);
    }


	public static MongoDatabase getDB() {
		return database;
	}
	public static MongoCollection<Document> getUrls() {
		return urls;
	}

	
}
