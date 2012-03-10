package potjam.entities.enemies;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;

import potjam.entities.CharacterEntity;
import potjam.entities.Gib;
import potjam.entities.weapons.ZombieClaw;
import potjam.main.PotJamMain;
import potjam.map.World;

public class Zombie extends CharacterEntity {
	private boolean attacking;
	private float attackAnimationTime;
	private float attackAnimationTimeCounter;
	private String moveState;
	private String attackState;
	private boolean lostHead;
	private Sound sound;
	private ArrayList<Gib> gibs;
	private float deathTime;
	private float deathTimeCounter;
	private float detectionRange;
	
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
		this.sound = new Sound("ressources/sounds/Zombie_Attack.wav");
		this.gibs = new ArrayList<Gib>();
		this.deathTime = 3000.0f;
		this.deathTimeCounter = 0.0f;
		this.detectionRange = 100f;
		this.setDrawStandardHealth(false);
		this.setMoveSpeed(-this.getSpeed());
	}
	

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if(!this.isDead()) {
			this.getActiveWeapon().update(gc, delta);
			this.attackAI(gc, delta);
			this.moveAI(delta);
			this.fall(delta);
		}
		
		this.takeDamage(gc, delta);
	}

	public void draw(GameContainer gc, Graphics g) {
		super.draw(gc, g);
		
		for(int i = 0; i < this.gibs.size(); i++) {
			this.gibs.get(i).draw(gc, g);
		}
	}
	
	private void takeDamage(GameContainer gc, int delta) throws SlickException {
		if(this.getHitPoints() <= 50) {
			this.moveState = "WalkHeadless";
			this.attackState = "AttackHeadless";
			if(!this.lostHead) {
				this.lostHead = true;
				this.gibs.add(new Gib(this.getMinX(), this.getMinY(), 24.0f, 24.0f,
						new SpriteSheet("ressources/Zombie_Gibs.png", 24, 24).getSprite(0, 0),World.getPlayer().getMinX()));
				this.gibs.add(new Gib(this.getMinX(), this.getMinY(), 24.0f, 24.0f,
						new SpriteSheet("ressources/Zombie_Gibs.png", 24, 24).getSprite(1, 0), World.getPlayer().getMinX()));
				sound.play();
			}
			
			for(int i = 0; i < gibs.size(); i++) {
				this.gibs.get(i).update(gc, delta);
				if(this.gibs.get(i).isDead())
					this.gibs.remove(i);
			}
		}
		
		if(this.isDead()) {
			if(this.deathTimeCounter >= this.deathTime) {
				//PotJamMain.zombies.remove(this);
			} else {
				if(this.deathTimeCounter == 0.0f) {
					if(this.getLastMovingDirection() == 0) {
						this.setAnimation(this.getAnimationByKey("leftDeathHeadless"), false);
					} else {
						this.setAnimation(this.getAnimationByKey("rightDeathHeadless"), false);
					}
					
					this.setCollidable(false);
					this.getAnimation().setLooping(false);
					this.getAnimation().start();
				}
				this.getAnimation().update(delta);
				this.deathTimeCounter += delta;
			}
		}
	}

	private void attackAI(GameContainer gc, int delta) {
		//Spieler links oder rechts vom Zombie
		boolean facingPlayer = false;
		
		if(this.getLastMovingDirection() == 0) {
			if(World.getPlayer().getMinX() < this.getMinX()) {
				facingPlayer = true;
			}
		} else {
			if(World.getPlayer().getMinX() > this.getMinX()) {
				facingPlayer = true;
			}
		}
		
		if((collidedWithPlayer(this.getMoveSpeed()*delta, 0) && facingPlayer) || this.attacking) {
			if(!this.attacking) {
				this.sound.play();
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
		if(Math.abs(this.getMinX() - World.getPlayer().getMinX()) < this.detectionRange &&
				Math.abs(this.getMinY() - World.getPlayer().getMinY()) < this.detectionRange && !this.attacking) {
			if(World.getPlayer().getMinX() < this.getMinX()) {
				this.setLastMovingDirection(0);
				this.setMoveSpeed(-this.getSpeed());
				this.setAnimation(this.getAnimationByKey("left"+this.moveState), true);
			} else {
				this.setLastMovingDirection(1);
				this.setMoveSpeed(this.getSpeed());
				this.setAnimation(this.getAnimationByKey("right"+this.moveState), true);
			}
		}
		
		if(!collidedWithWorld(this.getMoveSpeed()*delta, 0) && !this.endOfBlockReached(delta)) {
			if(!this.attacking) {
				this.move(delta);
			}
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
		int widthDeath = 80;
		int heightDeath = 73;
		int animSpeed = 200;
		int animSpeedDeath = 125;
		String path = "ressources/ZombieSheet.png";
		String pathHeadless = "ressources/ZombieHeadlessSheet.png";
		
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
		for(int i = 0; i < 6; i++) {
			anim.addFrame(sheet.getSprite(i, 0).getFlippedCopy(true, false), animSpeed);
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
		for(int i = 0; i < 5; i++) {
			anim.addFrame(sheet.getSprite(i, 1).getFlippedCopy(true, false), animSpeed);
		}
		addAnimation("leftAttack", anim);
		
		
		//Walk right Headless
		sheet = new SpriteSheet(pathHeadless, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 6; i++) {
			anim.addFrame(sheet.getSprite(i, 0), animSpeed);
		}
		addAnimation("rightWalkHeadless", anim);
		
		//Walk left Headless
		sheet = new SpriteSheet(pathHeadless, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 6; i++) {
			anim.addFrame(sheet.getSprite(i, 0).getFlippedCopy(true, false), animSpeed);
		}
		addAnimation("leftWalkHeadless", anim);
		
		//Attack right Headless
		sheet = new SpriteSheet(pathHeadless, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 5; i++) {
			anim.addFrame(sheet.getSprite(i, 1), animSpeed);
		}
		addAnimation("rightAttackHeadless", anim);
		
		//Attack left Headless
		sheet = new SpriteSheet(pathHeadless, width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 5; i++) {
			anim.addFrame(sheet.getSprite(i, 1).getFlippedCopy(true, false), animSpeed);
		}
		addAnimation("leftAttackHeadless", anim);
		
		//Die right Headless
		sheet = new SpriteSheet(pathHeadless, widthDeath, heightDeath);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 7; i++) {
			anim.addFrame(sheet.getSprite(i, 2), animSpeedDeath);
		}
		addAnimation("rightDeathHeadless", anim);
		
		//Die left Headless
		sheet = new SpriteSheet(pathHeadless, widthDeath, heightDeath);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 7; i++) {
			anim.addFrame(sheet.getSprite(i, 2).getFlippedCopy(true, false), animSpeedDeath);
		}
		addAnimation("leftDeathHeadless", anim);
	}


	public float getDetectionRange() {
		return detectionRange;
	}


	public void setDetectionRange(float detectionRange) {
		this.detectionRange = detectionRange;
	}
}
