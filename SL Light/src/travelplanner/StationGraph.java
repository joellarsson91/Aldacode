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
	 * @param route Tar in en lista med stationer f�r att bygga upp en rutt med stationer,
	 * tex rutten r�da fru�ngen linje en v�g.
	 * @param startTime Tar h�nsyn till vilken m�ngd minuter efter midnatt f�rsta avg�ngen g�r. 
	 */
	
	public void createRoute(List<Station> route, Integer startTime) {

		Iterator<Station> iter = route.iterator();
		Station stationFrom = iter.next(); // L�gg till f�rsta stationen
		int currentTime = startTime;
		while (iter.hasNext()) {
			Station stationTo = iter.next(); // L�gg till stationen den grenar till
			int distance = getDistance(stationFrom, stationTo); // H�mta avst�ndet till den stationen
			stationFrom.getDepartureMap().put(currentTime, new Departure(distance, stationTo)); // Skapa en ny avg�ng till
																							// den stationen med avst�nd
			currentTime += distance;
			stationFrom = stationTo;
		}

	}
	
	/**
	 * 
	 * @param station1 Hittar f�gelv�gen fr�n "station1" ber�knat fr�n hypotenusan av koordinaterna.
	 * @param station2 till "station 2" slut m�let. R�knar ut heurustiken beroende p� f�gelavst�ndet till m�let.
	 * getDistance r�knar �ven ut avst�ndet mellan varje station och ger varje str�cka sin vikt. R�knar inte med
	 * potentiellt v�gar som g�r i annan riktning �n exakt raka v�gen och tar heller inte i akt farten p� fordonet,
	 * till exempel om det �r buss, t�g, bil utan antar alltid att det �r h�gsta farten.
	 * @return M�ngden minuter f�gelv�gen mellan tv� stationer tar i minuter ber�knat av koordinater d�r
	 * 1 koordinatenhet �r 1 minut.
	 */

	public int getDistance(Station station1, Station station2) {

		int x = station1.getX() - station2.getX();
		int y = station1.getY() - station2.getY();

		double hypotenuse = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));

		return (int) Math.ceil(hypotenuse);

	}

}// class
