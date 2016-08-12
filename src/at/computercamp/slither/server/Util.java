package at.computercamp.slither.server;

public class Util {

	public static Direction getOppositeDirection(Direction d) {

		switch (d) {
		case NORTH:
			return Direction.SOUTH;
		case EAST:
			return Direction.WEST;
		case SOUTH:
			return Direction.NORTH;
		case WEST:
			return Direction.EAST;
		}

		return null;

	}
	
}
