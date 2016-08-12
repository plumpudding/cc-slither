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
	private String playerName;
	private Direction direction;
	private Point newPoint;
	private Point oldHead;
	private Point tileBeforeHead;
	private boolean isDead;
	private Color color = new Color();
	private int speed = 1;

	@Override
	public boolean isAtPoint(Point p) {
		for (Point tile : tiles)
			if (tile.equals(p))
				return true;

		return false;
	}

	public void checkForCollison(Point point) {
		if (point.x == 0 || point.y == 0
				|| point.x == GameServer.getInstance().getController().getGameState().worldWidth
				|| point.y == GameServer.getInstance().getController().getGameState().worldWidth) {
			die();
		}
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

	public void die() {
		for (Point tile : tiles) {
			GameServer.getInstance().getController().additem(new Food(tile));
		}
		isDead = true;
	}

	private Point checkDirection(Direction d) {
		Direction od = Util.getOppositeDirection(d);

		if (oldHead.movePoint(d) == tileBeforeHead)
			return oldHead.movePoint(od);
		else
			return oldHead.movePoint(d);
	}

	public Snake(String name, Point point) {
		this.playerName = name;
		this.tiles.add(point);
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
		return playerName;
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
		s.die();
	}

	public void addItem(Item item) {
		this.activeItems.add(item);
	}

	public void addTile(Point tile) {
		this.tiles.add(tile);
	}

	public boolean isDead() {
		return isDead;
	}

}
