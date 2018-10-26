//package exo2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapred.jobcontrol.Job;
import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import data.dataCreation;
import exo2.mapRed;


public class Main {

	
	public static void main(String[] args) throws IOException, InterruptedException{
		int I = 20;
		//System.out.println(System.getProperty("java.runtime.version"));	
		
		/*Configuration conf = new Configuration();
	    int numNodes=1;
	    conf.setInt("pagerank.numnodes", numNodes);*/
 
	    /*Job job = new Job(conf, "PageRankJob");//.getInstance
	    job.setJarByClass(main.class);
	    job.setMapperClass(PageRankMapper.class);
	    job.setReducerClass(PageRankReducer.class);*/
		//dataCreation.clean();
	    dataCreation.mongoDB();
	    dataCreation.completDB();
		
		
		
		
		for(int i = 1 ; i <= I ; i++ ){
			System.out.println("****************************************************************");
			System.out.println("****************************************************************");
			System.out.println("****************************************************************");
			System.out.print("VALEUR DE I = " );
			System.out.println(i);
			
			MongoDatabase database = dataCreation.getDB();
			MongoCollection<Document> urls = dataCreation.getUrls();
			
			mapRed MymapRed = new mapRed(database);
			mapRed.mapReduce(urls);
		}
		
		System.out.println("Fin du Programme!!!");
	
	
	
	}
	
	
	
}
