package at.computercamp.slither.server;

import java.util.ArrayList;
import java.util.List;

public class Snake implements GameObject {

	private List<Point> tiles = new ArrayList<Point>();
	private List<Item> activeItems = new ArrayList<Item>();
	private static int moveInterval = 6;
	private static int moveIntervalFast = 3;
	private static int moveIntervalSlow = 10;
	private boolean isFast;
	private boolean isSlow;
	private int tickCounter;
	private String name;
	private Direction direction;
	private Point newPoint;
	private Point oldHead;
	private Point tileForHead;
	public boolean isDead;
 
	@Override
	public boolean isAtPoint(Point p) {
		for (Point tile:tiles)
			if (tile == p)
				return true;
		
		return false;
	}

	public void move() {
		oldHead = tiles.get(0);
		tileForHead = tiles.get(1);
		newPoint = null;
		switch(direction){
			case NORTH:
				newPoint = checkdirection(getNorthPoint(), getSouthPoint());
				break;
				
			case EAST:
				newPoint = checkdirection(getEastPoint(), getWesthPoint());
				break;
			
			case SOUTH:
				newPoint = checkdirection(getSouthPoint(), getNorthPoint());
				break;
			
			case WEST:
				newPoint = checkdirection(getWesthPoint(), getEastPoint());
				break;
		}
		
		tiles.remove(tiles.size() - 1);
		tiles.add(0, newPoint);
		
	}
	
	private Point checkdirection(Point newdirection, Point opersidedirection){
		if(newdirection == tileForHead){
			newPoint = opersidedirection;
		}else{
			newPoint = newdirection;
		}
		return newPoint;
	}
	
	
	private Point getNorthPoint(){
		newPoint.y = oldHead.y - 1;
		newPoint.x = oldHead.x;
		return newPoint;
		
	}
	private Point getEastPoint(){
		newPoint.y = oldHead.y;
		newPoint.x = oldHead.x + 1;
		return newPoint;
		
	}
	private Point getSouthPoint(){
		newPoint.y = oldHead.y + 1;
		newPoint.x = oldHead.x;
		return newPoint;
		
	}
	private Point getWesthPoint(){
		newPoint.y = oldHead.y;
		newPoint.x = oldHead.x - 1;
		return newPoint;
		
	}
	
	public Snake(String name) {
		this.name = name;
	}

	@Override
	public void tick() {
		for (Item item:activeItems) {
			item.tick();
			item.applyEffectToSnake(this);
		}
		
		tickCounter += 1;
		// 1 tick = 100ms

		if (isFast && tickCounter >= moveIntervalFast){
			move();
			tickCounter = 0;
		}if (isSlow && tickCounter >= moveIntervalSlow) {
			move();
			tickCounter = 0;
		}else if (tickCounter >= moveInterval){
			move();
			tickCounter = 0;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setIsFast(boolean isFast) {
		this.isFast = isFast;
	}

	public void setIsSlow(boolean isSlow){
		this.isSlow = isSlow;
	}

	@Override
	public void collide(Snake s) {
		//TODO
	}
	
}
