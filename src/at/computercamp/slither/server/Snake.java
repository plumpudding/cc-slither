package at.computercamp.slither.server;

import java.util.ArrayList;
import java.util.List;

public class Snake implements GameObject {

	private List<Point> tiles = new ArrayList<Point>();
	private List<Item> activeItems = new ArrayList<Item>();
	private int notUsedTiles = 0;
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
	private Point tileBeforeHead;
	public boolean isDead;

	@Override
	public boolean isAtPoint(Point p) {
		for (Point tile : tiles)
			if (tile.equals(p))
				return true;

		return false;
	}

	public void checkForCollison(Point point) {
		GameObject collider = GameServer.getInstance().getController().getObjectAtPoint(point);
		if (collider != null) {
			collider.collide(this);
		}
	}

	public void move() {
		oldHead = tiles.get(0);
		tileBeforeHead = tiles.get(1);
		newPoint = checkDirection(direction);
		checkForCollison(newPoint);
		if (notUsedTiles == 0) {
			tiles.remove(tiles.size() - 1);
		} else {
			notUsedTiles--;
		}
		tiles.add(0, newPoint);
	}

	private Point checkDirection(Direction d) {
		Direction od = DirectionHelper.getOppositeDirection(d);

		if (oldHead == tileBeforeHead)
			return oldHead.movePoint(od);
		else
			return oldHead.movePoint(d);
	}

	public Snake(String name) {
		this.name = name;
	}

	@Override
	public void tick() {
		for (Item item : activeItems) {
			item.tick();
		}

		tickCounter += 1;
		// 1 tick = 100ms

		if (isFast && tickCounter >= moveIntervalFast) {
			move();
			tickCounter = 0;
		}
		if (isSlow && tickCounter >= moveIntervalSlow) {
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

	public void setIsSlow(boolean isSlow) {
		this.isSlow = isSlow;
	}

	public void addTile() {
		notUsedTiles++;
	}

	@Override
	public void collide(Snake s) {
		s.isDead = true;
	}

	public void addItem(Item item) {
		activeItems.add(item);
	}

}
