package potjam.weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import potjam.entities.CharacterEntity;

public class ZombieClaw extends Weapon {
	public ZombieClaw(float x, float y, float width, float height, CharacterEntity e) throws SlickException {
		super(x, y, width, height, e);
		this.setReUseTime(1000);
	}

	@Override
	public void use(GameContainer gc, int delta) {
		float interpX = this.getWidth();
		if(this.getUser().getLastMovingDirection() == 0)
			interpX = -interpX;
		
		if(this.collidedWithPlayer(interpX, 0)) {
			System.out.println("Rawr");
		}
		//reUseTimeCounter Reset
		super.use(gc, delta);
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		super.update(gc, delta);
		this.setLocation(this.getUser().getX(), this.getUser().getY());
	}

	@Override
	public void draw(GameContainer gc, Graphics g) {
		
	}

}
