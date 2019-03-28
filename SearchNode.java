package travelplanner;

public class SearchNode implements Comparable<SearchNode> {

	private int timeHere;
	private int distanceToGoal;
	private Station station;
	
	public SearchNode(int timeHere, int distanceToGoal, Station station) {
		this.timeHere = timeHere;
		this.distanceToGoal = distanceToGoal;
		this.station = station;
	}
	
	public int getTimeHere() {
		return timeHere;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	
	public Station getStation() {
		return this.station;
	}

	public void setTimeHere(int timeHere) {
		this.timeHere = timeHere;
	}


	public int getDistanceToGoal() {
		return distanceToGoal;
	}


	public void setDistanceToGoal(int distanceToGoal) {
		this.distanceToGoal = distanceToGoal;
	}


	public int getEarliestArrivalTime() {
		return timeHere + distanceToGoal;
	}

	@Override
	public int compareTo(SearchNode object) {
		
		return Integer.compare(getEarliestArrivalTime(), object.getEarliestArrivalTime());
	}
	
	
	
}
