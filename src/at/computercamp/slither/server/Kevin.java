package at.computercamp.slither.server;

public class Kevin { //the game controller
	
	private GameState gs = new GameState();
	
	//tick tick tick...
	
	public void tick() {
		for (Snake snake:gs.snakes)
			snake.tick();
		
		for (Item item:gs.inactiveItems) {
			item.tick();
			if (item.isDestroyed) {
				gs.inactiveItems.remove(item);
				item = null;
			}
		}
	}

	public GameState getGameState() {
		return gs;
	}

	//TODO do more stuff here
	public void handleClientAction(ClientAction action, Snake snake) {
		snake.setDirection(action.getDirection());
		snake.setIsFast(action.isFast());
	}
	
	public Snake addSnake(String name) {
		Snake snake = new Snake(name);
		gs.snakes.add(snake);
		return snake;
	}

}
