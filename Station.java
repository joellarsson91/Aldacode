package travelplanner;

import java.util.Map;
import java.util.TreeMap;

//x and y is map coordinates
public class Station {

	private String name;
	private Map<Integer, Departure> departureMap = new TreeMap<>(); // Sorterad variant av HashMap för att hitta närmst
															// avgångstid att jämföra med snabbast
	private final int x;
	private final int y;

	public Station(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Integer, Departure> getDepartureMap() {
		return departureMap;
	}

	public void setDepartureMap(Map<Integer, Departure> departureMap) {
		this.departureMap = departureMap;
	}

	public String getStationName() {
		return name;
	}

	public int getX() {
		return x;

	}

	public int getY() {
		return y;
	}

	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof Station) {

			Station otherStation = (Station) other;

			return name.equals(otherStation.getStationName());
		}
		return false;

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
