package exo2;


import java.io.IOException;
import java.util.*;

import org.jsoup.nodes.Element;

import com.mongodb.DBCollection;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.client.MapReduceIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

//import org.apache.commons.*;
import org.apache.hadoop.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import data.dataCreation;

//avec spark
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.Document;

public class mapRed {
	
	
	private static MongoDatabase database;
	private final static double d=0.85; //damping factor
	private static HashMap<String, Float> pageRank;
	
	public mapRed(MongoDatabase database) {
        this.database = database;
    }

	
/*	
	public static int getNumberOfOutBoundLink(String pageOrigine,String pageDestination) {
		ArrayList <Map.Entry <String,String>> graph=data.dataCreation.graph;
		int linkNumber=0;
		for(int i=0; i<= graph.size();i++) {
			if((graph.get(i).getValue()==pageDestination && graph.get(i).getKey()==pageOrigine) || (graph.get(i).getValue()==pageOrigine && graph.get(i).getKey()==pageDestination)) {
				linkNumber+=1;
			}
		}
		return linkNumber;
	}
	
	public HashMap<String, Float> createRankList() {
		ArrayList <Map.Entry <String,String>> graph=data.dataCreation.graph;
		//ArrayList <Map.Entry <String, float>> pageRank= new ArrayList <Map.Entry <String,float>>;
		HashMap<String, Float> pageRank= new HashMap<String,Float>();
		for(int i=0; i<= graph.size();i++) {
			String key=graph.get(i).getKey();
			if ((pageRank.containsKey(key))){
				pageRank.put(key, (float)1.0);
			}
		}
		this.pageRank=pageRank;
		return pageRank;
	}
	
	public static void setPageRank(String page, float pagerank) {
		mapRed.pageRank.remove(page, pagerank);
	}
	
	public static float getPageRank(String page) {
		return mapRed.pageRank.get(page);
	}
	
	public static ArrayList<String> getOriginePage(String page) {
		ArrayList<String> tab= new ArrayList<String>();
		ArrayList <Map.Entry <String,String>> graph=data.dataCreation.graph;
		for(int i=0; i<= graph.size();i++) {
			if (graph.get(i).getValue()==page) {
				tab.add(graph.get(i).getKey());
			}else if (graph.get(i).getKey()==page){
				tab.add(graph.get(i).getValue());
			}
		}
		return tab;
	}
	
	public static ArrayList<String> getAllPage(){
		ArrayList<String> pages = new ArrayList<String>();
		String page;
		ArrayList <Map.Entry <String,String>> graph=data.dataCreation.graph;
		for(int i=0; i<= graph.size();i++) {
			page =graph.get(i).getValue();
			if (!pages.contains(page)){
				pages.add(page);
			}
			page =graph.get(i).getKey();
			if (!pages.contains(page)){
				pages.add(page);
			}
		}
		return pages;
	}
	
*/
	

	public static void mapReduce(MongoCollection<Document> urls)throws IOException, InterruptedException {
		String map="function map() { \n" +//this.PageName,this.PageRank,this.linkedPages
				//"for (each pageO in pagesO) { \n" +
				"this.linkedPages.forEach(pageO =>{ \n" +
				"	emit(pageO, this.PageRank/this.linkedPages.lenght); \n" +
				"}); \n" +
				"emit(this.PageName,this.linkedPages); \n" +
				"};";
		
		String reduce="function reduce(this.PageName, ranklist) { \n" +//
				"dampingfactor = 0.85 ; \n" +
				"pagerank = 0; \n" +
				"ranklist.forEach(rank =>{" +
				//"for each rank in ranklist {\n" +
				"	if (!isNaN(rank)){ \n"+
				"		pagerank+=rank; \n"+
				"	} \n" +
					"else{ \n"+
						"linkedPages=rank; \n" +
					"} \n" +
				"}); \n"+
				"pagerank=1 - dampingfactor + (dampingfactor*pagerank); \n"+
				"emit(key[this.PageName,pagerank],linkedPages); \n" +
				"};";
		
		
		
		MapReduceIterable<Document> result = urls.mapReduce(map, reduce);

		System.out.println(result.toString());
		 //printResult(result);
		
	}
	

	public static void printResult(MapReduceIterable<Document> result) {
		for (Iterator<Document> i = result.iterator(); i.hasNext();)
		{	
			Document item = i.next();
			System.out.println(i);
		}

	}
	
	

}
