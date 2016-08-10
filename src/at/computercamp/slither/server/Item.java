package at.computercamp.slither.server;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Item implements GameObject {

	private int activeDuration;
	private double inactiveDuration;
	private Point location;
	public boolean isDestroyed;
	
	public boolean isAtPoint(Point p) {
		return p == location;
	}

	@Override
	public void tick() {
		if(Math.random() < inactiveDuration)
			isDestroyed = true;
	}

	public int getActiveDuration() {
		return activeDuration;
	}

	@Override
	public void collide(Snake s) {
		//TODO
		//s.addItem(this);
	}
	
	public void activate() {
		//TODO
	}

	protected void applyEffectToSnake(Snake snake){
		throw new NotImplementedException();
	}
	
}
