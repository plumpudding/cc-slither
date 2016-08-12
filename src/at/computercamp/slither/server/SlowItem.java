package at.computercamp.slither.server;

/**
 * Created by feliumla16 on 10.08.2016.
 */
public class SlowItem extends Item {

	@Override
	protected void applyEffectToSnake(Snake snake) {
		long startTime = System.currentTimeMillis();
		snake.setIsSlow(true);
		if (startTime >= 10000) {
			snake.setIsSlow(false);
		}
	}
}
