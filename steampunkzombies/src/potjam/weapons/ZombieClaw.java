package potjam.weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ZombieClaw extends Weapon {

	public ZombieClaw(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.setReUseTime(1000);
	}

	@Override
	public void use(GameContainer gc, int delta) {
		System.out.println("Rawr");
		//reUseTimeCounter Reset
		super.use(gc, delta);
	}

	@Override
	public void draw(GameContainer gc, Graphics g) {
		
	}

}
