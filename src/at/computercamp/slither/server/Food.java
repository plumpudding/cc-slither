package at.computercamp.slither.server;

public class Food extends Item {
	@Override
	public void collide(Snake s) {
		s.addTile();
	}
}