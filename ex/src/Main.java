import data.dataCreation;
import exo2.mapRed;

public class Main {

	public static void main(String[] args) {
        dataCreation mongoDB = dataCreation.getInstance();
        mongoDB.getUrls().drop();
	    mongoDB.completDB();
		
		for(int i = 1 ; i <= 20 ; i++ ){
			System.out.println("****************************************************************");
			System.out.println("****************************************************************");
			System.out.println("****************************************************************");
			System.out.print("VALEUR DE I = " );
			System.out.println(i);
			
			mapRed MymapRed = new mapRed(mongoDB);
			MymapRed.mapReduce();
		}
		
		System.out.println("Fin du Programme!!!");

	}
	
	
	
}
