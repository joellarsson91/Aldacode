package travelplanner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test {

	public static void main(String[] args) {

		StationGraph test = new StationGraph();

		Station fruängen = new Station("Fruängen", 1, 1);
		Station västertorp = new Station("Västertorp", 2, 3);
		Station hägerstensåsen = new Station("Hägerstensåsen", 3, 5);
		Station telefonplan = new Station("Telefonplan", 3, 6);
		Station midsommarkransen = new Station("Midsommarkransen", 4, 7);
		Station liljeholmen = new Station("Liljeholmen", 5, 9);
		Station hornstull = new Station("Hornstull", 6, 10);
		Station zinkensdamm = new Station("Zinkensdamm", 7, 11);
		Station mariatorget = new Station("Mariatorget", 7, 12);
		Station slussen = new Station("Slussen", 9, 13);
		Station gamlaStan = new Station("Gamla Stan", 9, 14);
		Station tCentralen = new Station ("T-Centralen", 10, 14);
		Station ostermalmstorg = new Station ("Östermalmstorg", 11, 15);
		//Tar mycket minne med alla avgångar
		Station axelsberg = new Station("Axelsberg", -6, -3);
		Station ornsberg = new Station("Örnsberg", -5, -2);
		Station aspudden = new Station("Aspudden", -2, 0);
//		Station liljeholmen = new Station("Liljeholmen", 5, 9);
//		Station hornstull = new Station("Hornstull", 6, 10);
//		Station zinkensdamm = new Station("Zinkensdamm", 7, 11);
//		Station mariatorget = new Station("Mariatorget", 7, 12);
//		Station slussen = new Station("Slussen", 9, 13);
//		Station gamlaStan = new Station("Gamla Stan", 9, 14);
//		Station tCentralen = new Station ("T-Centralen", 10, 14);
//		Station ostermalmstorg = new Station ("Östermalmstorg", 11, 15);
		Station karlaplan = new Station("Karlaplan", 10, 16);
		Station gardet = new Station("Gärdet", 11, 18);
		Station ropsten = new Station("Ropsten", 20, 20);
		
		List<Station> redLine13 = new ArrayList<>();
		redLine13.add(fruängen);
		redLine13.add(västertorp);
		redLine13.add(hägerstensåsen);
		redLine13.add(telefonplan);
		redLine13.add(midsommarkransen);
		redLine13.add(liljeholmen);
		redLine13.add(hornstull);
		redLine13.add(zinkensdamm);
		redLine13.add(mariatorget);
		redLine13.add(slussen);
		redLine13.add(gamlaStan);
		redLine13.add(tCentralen);
		redLine13.add(ostermalmstorg);
		
		List<Station> redLine12 = new ArrayList<>();
		redLine12.add(axelsberg);
		redLine12.add(ornsberg);
		redLine12.add(aspudden);
		redLine12.add(liljeholmen);
		redLine12.add(hornstull);
		redLine12.add(zinkensdamm);
		redLine12.add(mariatorget);
		redLine12.add(slussen);
		redLine12.add(gamlaStan);
		redLine12.add(tCentralen);
		redLine12.add(ostermalmstorg);
		redLine12.add(karlaplan);
		redLine12.add(gardet);
		redLine12.add(ropsten);

		PathSearcher searchPath = new PathSearcher(test);
		//Skapa  rutt 13
		for(int i = 0;i<1440;i+=10) { 
			test.createRoute(redLine13, i);
		}

		searchPath.getFastestPath(västertorp, slussen, 501);
		//Totalt antal minuter vad klockan är från när man kan åka räknat från midnatt som tredje parameter.
		
		
		for(int i = 0;i<1440;i+=10) { 
			test.createRoute(redLine12, i);
		}		
		
		searchPath.getFastestPath(ornsberg, ropsten, 501);
		searchPath.getFastestPath(fruängen, ropsten, 502);
	}

}
