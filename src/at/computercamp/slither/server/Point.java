package at.computercamp.slither.server;

public class Point {

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	int x;
	int y;
	
	public Point getSouthPoint(){
		return new Point(this.x, this.y + 1);
	}

	public Point getNorthPoint(){
		return new Point(this.x, this.y - 1);
	}
	
	public Point getEastPoint(){
		return new Point(this.x + 1, this.y);
	}
	
	public Point getWestPoint(){
		return new Point(this.x - 1, this.y);
	}
	
	public Point movePoint(Direction d) {

		switch(d) {
			case NORTH:
				return this.getNorthPoint();
			case EAST:
				return this.getEastPoint();
			case SOUTH:
				return this.getSouthPoint();
			case WEST:
				return this.getWestPoint();
		}
		
		return null;

	}
}
