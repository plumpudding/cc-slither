package at.computercamp.slither.server;

public class Item implements GameObject {

	private int activeDuration;
	private double inactiveDecayProbability;
	private Point position;
	public boolean isDestroyed;
	private long startTime;
	private boolean isActive = false;
	public String type = "dummy";
	private int sizeIncrement = 0;

	public boolean isAtPoint(Point p) {
		return p == position;
	}
	public Item(Point point) {
		this.position = point;
	}
	
	
	@Override
	public void tick() {
		activeDuration = 10;
		if(!isActive){
			if (Math.random() < inactiveDecayProbability){
				isDestroyed = true;
			}
		}else{
			if (System.currentTimeMillis() - startTime >= activeDuration * 1000) {
				isDestroyed = true;
			}
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
