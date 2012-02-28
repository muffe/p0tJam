package potjam.enemies;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import potjam.entities.CharacterEntity;
import potjam.main.PotJamMain;
import potjam.weapons.ZombieClaw;

public class Zombie extends CharacterEntity {
	private boolean attacking;
	private float attackAnimationTime;
	private float attackAnimationTimeCounter;
	private String moveState;
	private String attackState;
	private boolean lostHead;
	private Sound attackSound;
	
	public Zombie(float x, float y, float width, float height) throws SlickException {
		super(x, y, width, height);
		initAnimations();
		this.setSpeed(0.03f);
		this.setActiveWeapon(new ZombieClaw(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight(), this));
		this.attacking = false;
		this.attackAnimationTime = 1000.0f;
		this.attackAnimationTimeCounter = 0.0f;
		this.moveState = "Walk";
		this.attackState = "Attack";
		this.lostHead = false;
		this.attackSound = new Sound("ressources/sounds/Zombie_Attack.wav");
	}
	

	@Override
	public void update(GameContainer gc, int delta) {
		this.getActiveWeapon().update(gc, delta);
		this.attackAI(gc, delta);
		this.moveAI(delta);
		this.fall(delta);
		
		if(this.getHitPoints() <= 50) {
			this.moveState = "WalkHeadless";
			this.attackState = "AttackHeadless";
			if(!this.lostHead) {
				this.lostHead = true;
				
			}
		}
		
		if(this.isDead())
		{
			PotJamMain.zombies.remove(this);
		}
	}
	
	public void draw(GameContainer gc, Graphics g) {
		super.draw(gc, g);
	}

	private void attackAI(GameContainer gc, int delta) {
		//Spieler links oder rechts vom Zombie
		boolean facingPlayer = false;
		
		if(this.getLastMovingDirection() == 0) {
			if(PotJamMain.player.getMinX() < this.getMinX()) { //TODO: player bei Einbau der Map mit richtigem player Objekt ersetzen
				facingPlayer = true;
			}
		} else {
			if(PotJamMain.player.getMinX() > this.getMinX()) {
				facingPlayer = true;
			}
		}
		
		if((collidedWithPlayer(this.getMoveSpeed()*delta, 0) && facingPlayer) || this.attacking) {
			if(!this.attacking) {
				this.attackSound.play();
			}
			
			this.attacking = true;
			
			if(this.getLastMovingDirection() == 0) {
				this.setAnimation(this.getAnimationByKey("left"+this.attackState), true);
			} else {
				this.setAnimation(this.getAnimationByKey("right"+this.attackState), true);
			}
			
			if(this.attackAnimationTimeCounter >= this.attackAnimationTime) {
				this.getActiveWeapon().use(gc, delta);
				this.attackAnimationTimeCounter = 0.0f;
				this.attacking= false;
			} else {
				this.attackAnimationTimeCounter += delta;
			}
		} else {
			if(this.getLastMovingDirection() == 0) {
				this.setAnimation(this.getAnimationByKey("left"+this.moveState), true);
			} else {
				this.setAnimation(this.getAnimationByKey("right"+this.moveState), true);
			}
		}
	}


	private void moveAI(int delta) {
		if(!collidedWithWorld(this.getMoveSpeed()*delta, 0) && !this.endOfBlockReached(delta)) {
			if(!this.attacking)
				this.move(delta);
		} else {
			if(this.getLastMovingDirection() == 0) {
				this.setLastMovingDirection(1);
				this.setMoveSpeed(this.getSpeed());
				this.setAnimation(this.getAnimationByKey("right"+this.moveState), true);
			} else {
				this.setLastMovingDirection(0);
				this.setMoveSpeed(-this.getSpeed());
				this.setAnimation(this.getAnimationByKey("left"+this.moveState), true);
			}
		}
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
		int width = 48;
		int height = 73;
		int animSpeed = 250;
		String path = "ressources/ZombieSheet.png";
		
		//Walk right
		SpriteSheet sheet = new SpriteSheet(path, width, height);
		Animation anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 6; i++) {
			anim.addFrame(sheet.getSprite(i, 0), animSpeed);
		}
		addAnimation("rightWalk", anim);
		
		//Walk left
		sheet = new SpriteSheet(path, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 5; i > -1; i--) {
			anim.addFrame(sheet.getSprite(i, 2), animSpeed);
		}
		addAnimation("leftWalk", anim);
		
		//Attack right
		sheet = new SpriteSheet(path, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 5; i++) {
			anim.addFrame(sheet.getSprite(i, 1), animSpeed);
		}
		addAnimation("rightAttack", anim);
		
		//Attack left
		sheet = new SpriteSheet(path, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 5; i > 0; i--) {
			anim.addFrame(sheet.getSprite(i, 3), animSpeed);
		}
		addAnimation("leftAttack", anim);
		
		
		//Walk right Headless
		sheet = new SpriteSheet(path, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 6; i++) {
			anim.addFrame(sheet.getSprite(i, 4), animSpeed);
		}
		addAnimation("rightWalkHeadless", anim);
		
		//Walk left Headless
		sheet = new SpriteSheet(path, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 5; i > -1; i--) {
			anim.addFrame(sheet.getSprite(i, 6), animSpeed);
		}
		addAnimation("leftWalkHeadless", anim);
		
		//Attack right Headless
		sheet = new SpriteSheet(path, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 5; i++) {
			anim.addFrame(sheet.getSprite(i, 5), animSpeed);
		}
		addAnimation("rightAttackHeadless", anim);
		
		//Attack left Headless
		sheet = new SpriteSheet(path, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 5; i > 0; i--) {
			anim.addFrame(sheet.getSprite(i, 7), animSpeed);
		}
		addAnimation("leftAttackHeadless", anim);
	}
}
