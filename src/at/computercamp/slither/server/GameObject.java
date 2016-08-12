package at.computercamp.slither.server;

public interface GameObject {

	public boolean isAtPoint(Point p);

	public void tick();

	public void collide(Snake s);

}
