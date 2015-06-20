package ar.edu.tp.domain;

public class Travel {

	private Bike bike;
	private Location origin;
	private Location destination;
	private String time;

	public Travel(Bike bike, Location origin, Location destination, String time) {
		this.bike = bike;
		this.origin = origin;
		this.destination = destination;
		this.time = time;
	}

	public Bike getBike() {
		return bike;
	}

	public Location getDestination() {
		return destination;
	}

	public Location getOrigin() {
		return origin;
	}

	public String getTime() {
		return time;
	}
}