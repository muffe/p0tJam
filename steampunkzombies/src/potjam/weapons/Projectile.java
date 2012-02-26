package potjam.weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import potjam.entities.CollisionEntity;
import potjam.shared.Point;

public class Projectile extends CollisionEntity {
	private float speed;
	private float speedX;
	private float speedY;
	private float lifeTime;
	private float lifeTimeCounter;
	
	public Projectile(float x, float y, float width, float height, float speed) {
		super(x, y, width, height);
		this.speed = speed;
		speedX = 0;
		speedY = 0;
		lifeTime = 1000.0f;
	}

	@Override
	public void update(GameContainer gc, int delta) {
		move(delta);
		checkDeath(delta);
	}

	@Override
	public void draw(GameContainer gc, Graphics g) {
		g.fill(this);
	}
	
	private void move(int delta) {
		if(!this.collidedWithWorld(speedX*delta, speedY*delta) && !this.collidedWithCharacter(speedX*delta, speedY*delta)) {
			this.setX(this.getX() + speedX * delta);
			this.setY(this.getY() + speedY * delta);
		} else {
			this.lifeTimeCounter = this.lifeTime;
		}
	}
	
	public boolean isDead() {
		if(this.lifeTimeCounter >= this.lifeTime)
			return true;
		else
			return false;
	}
	
	private void checkDeath(int delta) {
		if(this.lifeTimeCounter < this.lifeTime) {
			this.lifeTimeCounter += delta;
		}
	}
	
	public void calculateSpeed(Point start, Point target) {
		float distX = Math.abs(start.getX() - target.getX());
		float distY = Math.abs(start.getY() - target.getY());
		float length = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		length = length / speed;
		
		speedX = distX / length;
		speedY = distY / length;
		
		if(start.getX() > target.getX()) {
			speedX = -speedX;
		}
		
		if(start.getY() > target.getY()) {
			speedY = -speedY;
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(float lifeTime) {
		this.lifeTime = lifeTime;
	}

	public float getLifeTimeCounter() {
		return lifeTimeCounter;
	}

	public void setLifeTimeCounter(float lifeTimeCounter) {
		this.lifeTimeCounter = lifeTimeCounter;
	}

}