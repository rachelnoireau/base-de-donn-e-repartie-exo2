package data;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.*;


public class dataCreation {

    private MongoCollection<Document> urls;
    private static MongoDatabase database;
    private Block<Document> printBlock = document -> System.out.println(document.toJson());

    public static ArrayList<Map.Entry<String, String>> graph;

    private dataCreation() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase dataB = mongoClient.getDatabase("urlDB");
        database = dataB;
        urls = dataB.getCollection("urls");
    }


    public void creationData() {

        ArrayList<Map.Entry<String, String>> graph = new ArrayList<>();

        graph.add(new AbstractMap.SimpleEntry<>("A", "B"));
        graph.add(new AbstractMap.SimpleEntry<>("A", "C"));
        graph.add(new AbstractMap.SimpleEntry<>("B", "C"));
        graph.add(new AbstractMap.SimpleEntry<>("C", "A"));
        graph.add(new AbstractMap.SimpleEntry<>("D", "C"));

        dataCreation.graph = graph;
    }

    public void completDB() {

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
        docC.append("linkedPages", Arrays.asList("A", "B", "D"));

        Document docD = new Document();
        docD.put("PageName", "D");
        docD.put("PageRank", 1);
        docD.append("linkedPages", Arrays.asList());

        urls.insertOne(doc);
        urls.insertOne(docB);
        urls.insertOne(docC);
        urls.insertOne(docD);
    }


    public ArrayList<Map.Entry<String, String>> getGraph() {
        return this.graph;
    }


    private static class DatabaseHolder {
        private static final dataCreation INSTANCE = new dataCreation();
    }

    public static dataCreation getInstance() {
        return DatabaseHolder.INSTANCE;
    }

    public void clean() {
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
        urls.find().forEach(printBlock);
    }

    public MongoCollection<Document> getUrls() {
        return urls;
    }


}
