package at.computercamp.slither.server;

public class Item implements GameObject {

	private int activeDuration;
	private double inactiveDecayProbability;
	private Point location;
	public boolean isDestroyed;
	private long startTime;
	private boolean isActive = false;

	public boolean isAtPoint(Point p) {
		return p == location;
	}
	public Item(Point point) {
		this.location = point;
	}
	
	
	@Override
	public void tick() {
		activeDuration = 10;
		if (Math.random() < inactiveDecayProbability)
			isDestroyed = true;
		if (System.currentTimeMillis() - startTime >= activeDuration * 1000) {
			isDestroyed = true;
		}
	}

	public int getActiveDuration() {
		return activeDuration;
	}

	@Override
	public void collide(Snake s) {
		startTime = System.currentTimeMillis();
		isActive = true;
		s.addItem(this);
	}

	public boolean isActive() {
		return isActive;
	}
}
