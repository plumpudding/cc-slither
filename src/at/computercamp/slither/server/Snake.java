package at.computercamp.slither.server;

import java.util.ArrayList;
import java.util.List;

public class Snake implements GameObject {

	private List<Point> tiles = new ArrayList<Point>();
	private List<Item> activeItems = new ArrayList<Item>();
	private static int moveInterval = 6;
	private static int moveIntervalFast = 3;
	private boolean isFast;
	private int tickCounter;
	private String name;
	private Direction direction;
	
	public Snake(String name, boolean isFast) {
		this.name = name;
		this.isFast = isFast;
	}
	
	public boolean isAtPoint(Point p) {
		for (Point tile:tiles)
			if (tile == p)
				return true;
		
		return false;
	}

	public void move() {
		// TODO move
	}

	@Override
	public void tick() {
		for (Item item:activeItems)
			item.tick();
		
		tickCounter += 1;
		// 1 tick = 100ms

		if (isFast && tickCounter >= moveIntervalFast){
			move();
			tickCounter = 0;
		} else if (tickCounter >= moveInterval){
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
	
}
