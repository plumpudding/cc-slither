package at.computercamp.slither.server;

/**
 * Created by feliumla16 on 10.08.2016.
 */
public class SpeedItem extends Item {

	@Override
	protected void applyEffectToSnake(Snake snake) {
		snake.setIsFast(true);
	}
}
