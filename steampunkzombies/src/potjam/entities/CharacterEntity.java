package potjam.entities;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import potjam.entities.weapons.Weapon;

public abstract class CharacterEntity extends CollisionEntity {
	private HashMap<String, Animation> animationMap;
	
	/**
	 * Speichert auszufuehrende Animation
	 */
	private Animation animation;
	private Weapon activeWeapon;
	
	/**
	 * Gibt an, in welche Richtung der Charakter zuletzt geschaut hat. 0 = links, 1 = rechts.
	 */
	private int lastMovingDirection;
	private float hitPoints;
	private float speed;
	private float moveSpeed;
	private float fallSpeedMax;
	private float fallSpeedCurrent;
	private float fallSpeedIncrement;
	private float jumpHeight;
	private boolean jumping;
	private boolean dead;
	
	/**
	 * Maﬂe und Position.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public CharacterEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.animationMap = new HashMap<String, Animation>();
		
		lastMovingDirection = 0;
		this.speed = 0.3f;
		this.moveSpeed = 0.0f;
		this.fallSpeedMax = 1.0f;
		this.fallSpeedCurrent = 0.0f;
		this.fallSpeedIncrement = 0.002f;
		this.jumpHeight = 0.7f;
		this.jumping = false;
		this.hitPoints = 100;
	}
	
	/**
	 * Animation zeichnen.
	 */
	public void draw(GameContainer gc, Graphics g) {
		if(this.animation != null) {
			g.drawAnimation(this.animation, this.getMinX(), this.getMinY());
			
			//Debug
			//g.draw(this);
		} else {
			g.draw(this);
		}
	}
	
	/**
	 * Springen.
	 */
	public void jump() {
		if(!jumping) {
			this.fallSpeedCurrent = -this.jumpHeight;
			this.jumping = true;
		}
	}
	
	/**
	 * Bewegen.
	 * @param delta
	 * @return
	 */
	public void move(int delta) {
			this.setX(this.getX() + this.moveSpeed*delta);
	}
	
	/**
	 * Fallen.
	 * @param delta
	 */
	public void fall(int delta) {
		if(this.fallSpeedCurrent < this.fallSpeedMax) {
			this.fallSpeedCurrent += this.fallSpeedIncrement*delta;
		}
		
		if(!collidedWithWorld(0, this.fallSpeedCurrent*delta)) {
			this.setY(this.getY() + this.fallSpeedCurrent*delta - 0.5f*this.fallSpeedIncrement*delta*delta);
		} else {
			this.fallSpeedCurrent = 0.0f;
			this.jumping = false;
		}
	}
	
	/**
	 * Entity bekommt Schaden
	 * @param f
	 */
	
	public void hurt(float f) 
	{
		this.setHitPoints(this.getHitPoints() - f);
		if(this.getHitPoints() <= 0)
		{
			this.setDead(true);
		}
	}
	
	/**
	 * Animationen initialisieren.
	 * @throws SlickException
	 */
	public abstract void initAnimations() throws SlickException;
	
	/**
	 * Animationen ueber Key-String abfragen.
	 * @param key
	 * @return
	 */
	public Animation getAnimationByKey(String key) {
		return this.animationMap.get(key);
	}
	
	/**
	 * Animation hinzufuegen.
	 * @param key
	 * @param anim
	 */
	public void addAnimation(String key, Animation anim) {
		this.animationMap.put(key, anim);
	}

	/**
	 * 
	 * @return aktuelle Animation
	 */
	public Animation getAnimation() {
		return this.animation;
	}

	/**
	 * Animation setzen.
	 * @param animation
	 * @param autoUpdate
	 */
	public void setAnimation(Animation animation, boolean autoUpdate) {
		this.animation = animation;
		this.animation.setAutoUpdate(autoUpdate);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public int getLastMovingDirection() {
		return lastMovingDirection;
	}

	public void setLastMovingDirection(int lastMovingDirection) {
		this.lastMovingDirection = lastMovingDirection;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public float getFallSpeedCurrent() {
		return fallSpeedCurrent;
	}

	public void setFallSpeedCurrent(float fallSpeedCurrent) {
		this.fallSpeedCurrent = fallSpeedCurrent;
	}

	public float getFallSpeedIncrement() {
		return fallSpeedIncrement;
	}

	public void setFallSpeedIncrement(float fallSpeedIncrement) {
		this.fallSpeedIncrement = fallSpeedIncrement;
	}

	public Weapon getActiveWeapon() {
		return activeWeapon;
	}

	public void setActiveWeapon(Weapon activeWeapon) {
		this.activeWeapon = activeWeapon;
	}

	public float getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(float f) {
		this.hitPoints = f;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}
