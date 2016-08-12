package at.computercamp.slither.server;

public class Util {

	public static Direction getOppositeDirection(Direction d) {

		switch (d) {
		case North:
			return Direction.South;
		case East:
			return Direction.West;
		case South:
			return Direction.North;
		case West:
			return Direction.East;
		}

		return null;

	}
	
}
