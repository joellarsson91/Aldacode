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
	 *                           minuter från midnatt.
	 * @return returnerar resterande antal minuter utöver mängd timmar klockan är
	 *         när man är framme.
	 */

	public int minutes(int finalTimeAtArrival) {
		int hours = hours(finalTimeAtArrival);
		int minutes = finalTimeAtArrival - (60 * hours);

		return minutes;
	}

	/**
	 * 
	 * @param finalArrivalTime Tar in slutgiltiga klockslaget i antal totala minuter
	 *                         från midnatt.
	 * @return returnerar den givna timslaget för vad klockan är när man är framme.
	 */

	public int hours(int finalArrivalTime) {
		int hours = finalArrivalTime / 60;

		return hours;
	}

	/**
	 * 
	 * @param start Startstationen man beräknar snabbaste vägen ifrån.
	 * @param end   Slutdestinationen man beräknar snabbaste vägen till.
	 * @param time  Från vilket klockslag man vill jämföra sin sökning med när tågen
	 *              går enligt dess tillsatta avgångar.
	 * 
	 *              Börjar med att skapa en prioritetskö där den lägger i första
	 *              startstationen som ett startnodsobjekt med dess
	 *              stationsegenskaper, heurustiktiden till målet samt nuvarande tid
	 *              som första tiden. Sedan skapar man en HashMap där varje station
	 *              erhåller ett heltal som ska föreställa snabbaste tiden till just
	 *              den noden. Detta heltal kommer sedan uppdateras enligt Dijkstra
	 *              tabellgenomsökning, där heurustiken för A* kommer vara
	 *              fågelvägen till målet som jämförs med vikten av tiden till
	 *              målet. På sättet sökalgoritmen är utformad får man endast svar
	 *              på när man kommer till slutstationen. Vill man även ha resvägen
	 *              så sparas det inte genom denna metod utan isåfall skulle man
	 *              behöva implementera mer än sjäva Integern med tiden, utan ett
	 *              objekt som sparade pathen hit, stationsnamn för stationsnamn.
	 *              För själva uträkningen hade fortfarande endast Integern använts,
	 *              men på sätt hade man även kunnat skriva ut vägen till mål,
	 *              station för station.
	 * 
	 *              Slutsvaret blir därför en utskrift av vad klockan är och när du
	 *              är framme med tanke från varifrån du åker och var slutmålet är.
	 *              Man får även reda på hur snabbt resan tar. Om ingen resväg,
	 *              hittas så svarar den att ingen resväg hittades, vilket kan bero
	 *              på att stationerna inte är kopplade på något sätt alternativt
	 *              att en sådan avgång inte går vid den här tiden på dygnet.
	 * 
	 *              Slutligen, en klar nackdel som hur man hittar bästa vägen på
	 *              detta sätt är att den inte tar hänsyn till hur lång tid ett byte
	 *              kan ta utan man räknar kallt med om man når en station vid en
	 *              viss tid och ens byte går samtidigt så hinner man alltid med
	 *              bytet. Visserligen händer det en del i verkligheten, till
	 *              exempel när man byter till gröna linjen från röda eller vice
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
			System.out.println("Ingen väg hittades till slutdestinationen");

		} else {

			System.out.print("Klockan är: " + hours(time) + " " + minutes(time) + " och om du åker från " + start
					+ " med nästa avgång är du är framme vid " + end + ": " + hours(finalTimeAtArrival) + " ");
			System.out.println(minutes(finalTimeAtArrival));
			System.out.println("Resan tar " + (finalTimeAtArrival - time) + " minuter");
		}

	}
}
