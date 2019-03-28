package travelplanner;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class PathSearcher {

	StationGraph stationGraph;

	public PathSearcher(StationGraph stationGraph) {

		this.stationGraph = stationGraph;

	}

	

	/**
	 * 
	 * @param finalTimeAtArrival Tar in slutgiltiga klockslaget i antal totala
	 *                           minuter fr�n midnatt.
	 * @return returnerar resterande antal minuter ut�ver m�ngd timmar klockan �r
	 *         n�r man �r framme.
	 */

	public int minutes(int finalTimeAtArrival) {
		int hours = hours(finalTimeAtArrival);
		int minutes = finalTimeAtArrival - (60 * hours);

		return minutes;
	}

	/**
	 * 
	 * @param finalArrivalTime Tar in slutgiltiga klockslaget i antal totala minuter
	 *                         fr�n midnatt.
	 * @return returnerar den givna timslaget f�r vad klockan �r n�r man �r framme.
	 */

	public int hours(int finalArrivalTime) {
		int hours = finalArrivalTime / 60;

		return hours;
	}

	/**
	 * 
	 * @param start Startstationen man ber�knar snabbaste v�gen ifr�n.
	 * @param end   Slutdestinationen man ber�knar snabbaste v�gen till.
	 * @param time  Fr�n vilket klockslag man vill j�mf�ra sin s�kning med n�r t�gen
	 *              g�r enligt dess tillsatta avg�ngar.
	 * 
	 *              B�rjar med att skapa en prioritetsk� d�r den l�gger i f�rsta
	 *              startstationen som ett startnodsobjekt med dess
	 *              stationsegenskaper, heurustiktiden till m�let samt nuvarande tid
	 *              som f�rsta tiden. Sedan skapar man en HashMap d�r varje station
	 *              erh�ller ett heltal som ska f�rest�lla snabbaste tiden till just
	 *              den noden. Detta heltal kommer sedan uppdateras enligt Dijkstra
	 *              tabellgenoms�kning, d�r heurustiken f�r A* kommer vara
	 *              f�gelv�gen till m�let som j�mf�rs med vikten av tiden till
	 *              m�let. P� s�ttet s�kalgoritmen �r utformad f�r man endast svar
	 *              p� n�r man kommer till slutstationen. Vill man �ven ha resv�gen
	 *              s� sparas det inte genom denna metod utan is�fall skulle man
	 *              beh�va implementera mer �n sj�va Integern med tiden, utan ett
	 *              objekt som sparade pathen hit, stationsnamn f�r stationsnamn.
	 *              F�r sj�lva utr�kningen hade fortfarande endast Integern anv�nts,
	 *              men p� s�tt hade man �ven kunnat skriva ut v�gen till m�l,
	 *              station f�r station.
	 * 
	 *              Slutsvaret blir d�rf�r en utskrift av vad klockan �r och n�r du
	 *              �r framme med tanke fr�n varifr�n du �ker och var slutm�let �r.
	 *              Man f�r �ven reda p� hur snabbt resan tar. Om ingen resv�g,
	 *              hittas s� svarar den att ingen resv�g hittades, vilket kan bero
	 *              p� att stationerna inte �r kopplade p� n�got s�tt alternativt
	 *              att en s�dan avg�ng inte g�r vid den h�r tiden p� dygnet.
	 * 
	 *              Slutligen, en klar nackdel som hur man hittar b�sta v�gen p�
	 *              detta s�tt �r att den inte tar h�nsyn till hur l�ng tid ett byte
	 *              kan ta utan man r�knar kallt med om man n�r en station vid en
	 *              viss tid och ens byte g�r samtidigt s� hinner man alltid med
	 *              bytet. Visserligen h�nder det en del i verkligheten, till
	 *              exempel n�r man byter till gr�na linjen fr�n r�da eller vice
	 *              versa i samma riktning i Slussen. Men knappast samtliga av
	 *              fallen.
	 */

	public void getFastestPath(Station start, Station end, int time) {

		PriorityQueue<SearchNode> queue = new PriorityQueue<>();
		int birdDistance = stationGraph.getDistance(start, end);
		SearchNode firstNode = new SearchNode(time, birdDistance, start);
		queue.add(firstNode);

		Map<Station, Integer> earliestArrivalTimeMap = new HashMap<>();
		earliestArrivalTimeMap.put(start, time);

		int earliestArrivalAtEnd = Integer.MAX_VALUE;
		while (!queue.isEmpty()) {

			SearchNode currentNode = queue.poll();
			if (currentNode.getEarliestArrivalTime() > earliestArrivalAtEnd) {
				break;
			}
			for (Map.Entry<Integer, Departure> d : currentNode.getStation().getDepartureMap().entrySet()) {
				if (d.getKey() < currentNode.getTimeHere()) {
					continue;
				}

				Departure departures = d.getValue();
				Station targetStation = departures.getDestination();
				int newEarliestArrivalTime = d.getKey() + departures.getTime();
				if (!earliestArrivalTimeMap.containsKey(targetStation)
						|| earliestArrivalTimeMap.get(targetStation) > newEarliestArrivalTime) {
					earliestArrivalTimeMap.put(targetStation, newEarliestArrivalTime);
					if (targetStation.equals(end)) {
						earliestArrivalAtEnd = newEarliestArrivalTime;
					} else {

						SearchNode newNode = new SearchNode(newEarliestArrivalTime,
								stationGraph.getDistance(targetStation, end), targetStation);
						queue.add(newNode);
					}
				}
			}

		}

		Integer finalTimeAtArrival = earliestArrivalTimeMap.get(end);
		if (finalTimeAtArrival == null) {
			System.out.println("Ingen v�g hittades till slutdestinationen");

		} else {

			System.out.print("Klockan �r: " + hours(time) + " " + minutes(time) + " och om du �ker fr�n " + start
					+ " med n�sta avg�ng �r du �r framme vid " + end + ": " + hours(finalTimeAtArrival) + " ");
			System.out.println(minutes(finalTimeAtArrival));
			System.out.println("Resan tar " + (finalTimeAtArrival - time) + " minuter");
		}

	}
}
