
package at.computercamp.slither.server;

public class Kevin { // the game controller

	private GameState gs = new GameState();

	// tick tick tick...

	public void tick() {
		for (Snake snake : gs.snakes) {
			snake.tick();
			if (snake.isDead) {
				gs.snakes.remove(snake);
				snake = null;
			}
		}

		for (Item item : gs.inactiveItems) {
			item.tick();
			if (item.isDestroyed) {
				gs.inactiveItems.remove(item);
				item = null;
			}
		}

		// spawn items

	}

	public GameState getGameState() {
		return gs;
	}

	public GameObject getObjectAtPoint(Point point) {

		for (GameObject obj : GameServer.getInstance().getController().getGameState().inactiveItems)
			if (obj.isAtPoint(point))
				return obj;

		for (GameObject obj : GameServer.getInstance().getController().getGameState().snakes)
			if (obj.isAtPoint(point))
				return obj;

		return null;
	}

	// TODO do more stuff here
	public void handleClientAction(ClientAction action, Snake snake) {
		snake.setDirection(action.getDirection());
		snake.setIsFast(action.isFast());
	}

	public Point findEmptyPosition() {
		Point p = null;

		// TODO prevent endless loop
		while (true) {
			int x = (int) (Math.random() * gs.boardLength);
			int y = (int) (Math.random() * gs.boardHeight);
			p = new Point(x, y);
			if (getObjectAtPoint(p) == null) {
				return p;
			}
		}
	}

	public Point findEmptyPosition(Point nextTo) {
		Point p = null;

		p = new Point(nextTo.x, nextTo.y + 1);
		if (getObjectAtPoint(p) == null)
			return p;

		p = new Point(nextTo.x, nextTo.y - 1);
		if (getObjectAtPoint(p) == null)
			return p;

		p = new Point(nextTo.x + 1, nextTo.y);
		if (getObjectAtPoint(p) == null)
			return p;

		p = new Point(nextTo.x - 1, nextTo.y);
		if (getObjectAtPoint(p) == null)
			return p;

		return null;
	}

	public Snake addSnake(String name) {
		Point[] startTiles = new Point[3];

		startTiles[0] = findEmptyPosition();

		for (int i = 1; i < startTiles.length; i++) {
			startTiles[i] = findEmptyPosition(startTiles[i - 1]);
		}

		Snake snake = new Snake(name, findEmptyPosition());
		gs.snakes.add(snake);
		
		for (Point tile : startTiles) {
			snake.addTile(tile);
		}
		
		int length = (int) Math.round(Math.sqrt(gs.snakes.size()));
		if (gs.boardHeight < length) {
			gs.boardHeight = length;
			gs.boardLength = length;
		}


		return snake;
	}

	public void removeSnake(Snake snake) {
		gs.snakes.remove(snake);
	}

}
