package potjam.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import potjam.entities.CharacterEntity;

public class Zombie extends CharacterEntity {
	
	public Zombie(float x, float y, float width, float height) throws SlickException {
		super(x, y, width, height);
		initAnimations();
		this.setSpeed(0.03f);
	}
	

	@Override
	public void update(GameContainer gc, int delta) {
		if(endOfBlockReached(delta)) {
			if(this.getLastMovingDirection() == 0) {
				this.setLastMovingDirection(1);
				this.setMoveSpeed(this.getSpeed());
				this.setAnimation(this.getAnimationByKey("walkRight"), true);
			} else {
				this.setLastMovingDirection(0);
				this.setMoveSpeed(-this.getSpeed());
				this.setAnimation(this.getAnimationByKey("walkLeft"), true);
			}
		}
		
		this.move(delta);
		this.fall(delta);
	}

	private boolean endOfBlockReached(int delta) {
		boolean endOfBlockReached = false;
		float interpX = this.getWidth()*1.0f;
		float interpY = this.getHeight()/2.0f;
		
		if(this.getLastMovingDirection() == 0) {
			interpX = -interpX;
		}
		
		endOfBlockReached = !this.collidedWithWorld(interpX, interpY);
		
		return endOfBlockReached;
	}


	@Override
	public void initAnimations() throws SlickException {
		int width = 46;
		int height = 75;
		int animSpeed = 250;
		
		//Walk right
		SpriteSheet sheet = new SpriteSheet("ressources/ZombieSheet.png", width, height);
		Animation anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 6; i++) {
			anim.addFrame(sheet.getSprite(i, 0), animSpeed);
		}
		addAnimation("walkRight", anim);
		
		//Walk left
		sheet = new SpriteSheet("ressources/ZombieSheet.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 5; i > -1; i--) {
			anim.addFrame(sheet.getSprite(i, 1), animSpeed);
		}
		addAnimation("walkLeft", anim);
	}
}
