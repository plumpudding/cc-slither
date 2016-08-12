package at.computercamp.slither.server;

/**
 * Created by feliumla16 on 10.08.2016.
 */
public class SlowItem extends Item {
	
	public SlowItem(Point point) {
		super(point);
	}

	@Override
	public void collide(Snake s) {
		super.collide(s);
		s.setIsSlow(true);
	}
}
