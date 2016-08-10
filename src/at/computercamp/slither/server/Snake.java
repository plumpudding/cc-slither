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
	private Point newPoint;
	private Point oldHead;
	private Point tileForHead;
	public boolean isDead = false;

	@Override
	public boolean isAtPoint(Point p) {
		for (Point tile : tiles)
			if (tile == p)
				return true;

		return false;
	}

	public void move() {
		oldHead = tiles.get(0);
		tileForHead = tiles.get(1);

		switch (direction) {
		case NORTH:
			newPoint = checkDirection(getNorthPoint(), getSouthPoint());
			break;

		case EAST:
			newPoint = checkDirection(getEastPoint(), getWestPoint());
			break;

		case SOUTH:
			newPoint = checkDirection(getSouthPoint(), getNorthPoint());
			break;

		case WEST:
			newPoint = checkDirection(getWestPoint(), getEastPoint());
			break;
		}

		GameObject collider = GameServer.getInstance().getController().getObjectAtPoint(newPoint);

		if (collider != null)
			collider.collide(this);
		else {
			tiles.remove(tiles.size() - 1);
			tiles.add(0, newPoint);
		}

	}

	private Point checkDirection(Point newDirection, Point oppositeDirection) {
		if (newDirection == tileForHead) {
			newPoint = oppositeDirection;
		} else {
			newPoint = newDirection;
		}
		return newPoint;
	}

	private Point getNorthPoint() {
		return new Point(oldHead.x, oldHead.y - 1);
	}

	private Point getEastPoint() {
		return new Point(oldHead.x + 1, oldHead.y);
	}

	private Point getSouthPoint() {
		return new Point(oldHead.x, oldHead.y + 1);
	}

	private Point getWestPoint() {
		return new Point(oldHead.x - 1, oldHead.y);
	}

	public Snake(String name) {
		this.name = name;
	}

	@Override
	public void tick() {
		for (Item item : activeItems)
			item.tick();

		tickCounter += 1;
		// 1 tick = 100ms

		if (isFast && tickCounter >= moveIntervalFast) {
			move();
			tickCounter = 0;
		} else if (tickCounter >= moveInterval) {
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

	@Override
	public void collide(Snake s) {
		s.die();
	}

	public void die() {
		this.isDead = true;
	}

	public void addItem(Item item) {
		activeItems.add(item);
		item.activate();
	}

	public void addTile(Point tile) {
		tiles.add(tile);
	}

}
