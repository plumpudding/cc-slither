package at.computercamp.slither.server;

public class Food extends Item {
	
	public Food(Point point) {
		super(point);
		this.type = "Food";
	}

	@Override
	public void collide(Snake s) {
		s.addTile();
	}
}