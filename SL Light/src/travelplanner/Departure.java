package travelplanner;

public class Departure {

	private int time;
	private Station destination;


	public Departure(int time, Station destination) {
		this.time = time;
		this.destination = destination;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Station getDestination() {
		return destination;
	}

	public void setDestination(Station destination) {
		this.destination = destination;
	}

}
