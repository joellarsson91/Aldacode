package travelplanner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class StationGraph {

	private Map<String, Station> stationMap = new HashMap<>();

	/**
	 * 
	 * @param route Tar in en lista med stationer för att bygga upp en rutt med stationer,
	 * tex rutten röda fruängen linje en väg.
	 * @param startTime Tar hänsyn till vilken mängd minuter efter midnatt första avgången går. 
	 */
	
	public void createRoute(List<Station> route, Integer startTime) {

		Iterator<Station> iter = route.iterator();
		Station stationFrom = iter.next(); // Lägg till första stationen
		int currentTime = startTime;
		while (iter.hasNext()) {
			Station stationTo = iter.next(); // Lägg till stationen den grenar till
			int distance = getDistance(stationFrom, stationTo); // Hämta avståndet till den stationen
			stationFrom.getDepartureMap().put(currentTime, new Departure(distance, stationTo)); // Skapa en ny avgång till
																							// den stationen med avstånd
			currentTime += distance;
			stationFrom = stationTo;
		}

	}
	
	/**
	 * 
	 * @param station1 Hittar fågelvägen från "station1" beräknat från hypotenusan av koordinaterna.
	 * @param station2 till "station 2" slut målet. Räknar ut heurustiken beroende på fågelavståndet till målet.
	 * getDistance räknar även ut avståndet mellan varje station och ger varje sträcka sin vikt. Räknar inte med
	 * potentiellt vägar som går i annan riktning än exakt raka vägen och tar heller inte i akt farten på fordonet,
	 * till exempel om det är buss, tåg, bil utan antar alltid att det är högsta farten.
	 * @return Mängden minuter fågelvägen mellan två stationer tar i minuter beräknat av koordinater där
	 * 1 koordinatenhet är 1 minut.
	 */

	public int getDistance(Station station1, Station station2) {

		int x = station1.getX() - station2.getX();
		int y = station1.getY() - station2.getY();

		double hypotenuse = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));

		return (int) Math.ceil(hypotenuse);

	}

}// class
