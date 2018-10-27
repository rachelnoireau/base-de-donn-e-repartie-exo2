package exo2;


import com.mongodb.client.MapReduceIterable;
import data.dataCreation;
import org.bson.Document;

import java.util.Iterator;

public class mapRed {

    private static dataCreation database;

    public mapRed(dataCreation database) {
        mapRed.database = database;
    }

    public void mapReduce() {
        /*
        String map = "function() { \n" +
                "this.linkedPages.forEach(page => { \n" +
                "	emit(page, this.PageRank / this.linkedPages.length); \n" +
                "}); \n" +
                "emit(this.PageName, this.linkedPages); \n" +
                "};";
                */

        String map = "function() { \n" +
                "this.linkedPages.forEach(page => { \n" +
                "	emit(page, this.PageRank / this.linkedPages.length); \n" +
                "}); \n" +
                "emit(this.PageName, this.linkedPages); \n" +
                "};";

        String reduce = "function(key, ranklist) { \n" +//
                "let dampingfactor = 0.85 ; \n" +
                "let pagerank = 0; \n" +
                "ranklist.forEach(rank => {\n" +
                "	if (!isNaN(rank)){ \n" +
                "		pagerank+=rank; \n" +
                "	} \n" +
                "else { \n" +
                "this.linkedPages=rank; \n" +
                "} \n" +
                "}); \n" +
                "pagerank = 1 - dampingfactor + (dampingfactor * pagerank); \n" +
                "return pagerank \n" +
                //"return (key[this.PageName,pagerank],this.linkedPages); \n" +
                "};";

        MapReduceIterable<Document> result = database.getUrls().mapReduce(map, reduce);
        for (Document doc : result) System.out.println(doc);
        //printResult(result);
    }

    private static void printResult(MapReduceIterable<Document> result) {
        for (Iterator<Document> i = result.iterator(); i.hasNext(); ) {
            System.out.println(i);
        }

    }


}
