package potjam.entities;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Gib extends CollisionEntity {
	private Image image;
	private float speed;
	private float rotationIncrement;
	private float fallSpeedMax;
	private float fallSpeedCurrent;
	private float fallSpeedIncrement;
	private float lifeTime;
	private float lifeTimeMax;
	private float compareX;
	private boolean dead;
	
	public Gib(float x, float y, float width, float height, Image img, float compareX) throws SlickException {
		super(x, y, width, height);
		this.image = img;
		this.rotationIncrement = 0.1f;
		this.fallSpeedMax = 1.0f;
		this.fallSpeedCurrent = 0.0f;
		this.fallSpeedIncrement = 0.001f;
		this.lifeTime = 0;
		this.lifeTimeMax = 5000;
		this.compareX = compareX;
		this.dead = false;
		
		this.calculateRandomMovement();
	}

	@Override
	public void update(GameContainer gc, int delta) {
		this.image.setRotation(this.rotationIncrement++);
		this.setX(this.getX() + this.speed*delta);
		this.setY(this.getY() + this.fallSpeedCurrent*delta - 0.5f*this.fallSpeedIncrement*delta*delta);
		
		if(this.fallSpeedCurrent < this.fallSpeedMax) {
			this.fallSpeedCurrent += this.fallSpeedIncrement*delta;
		}
		
		this.lifeTime += delta;
		if(this.lifeTime >= this.lifeTimeMax)
			this.dead = true;
	}

	@Override
	public void draw(GameContainer gc, Graphics g) {
		g.drawImage(image, this.getMinX(), this.getMinY());
	}
	
	private void calculateRandomMovement() {
		Random r = new Random();
		this.speed = (float) (0.3f + r.nextFloat()/3);
		this.fallSpeedCurrent = (float) -(r.nextDouble()/1.5);
		
		if(this.compareX > this.getMinX())
			this.speed = -this.speed;
	}
	
	public void calculateRandomMovement(float additionalPower) {
		this.calculateRandomMovement();
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public float getSpeedX() {
		return speed;
	}

	public void setSpeedX(float speedX) {
		this.speed = speedX;
	}

	public float getRotationIncrement() {
		return rotationIncrement;
	}

	public void setRotationIncrement(float rotationIncrement) {
		this.rotationIncrement = rotationIncrement;
	}

	public float getLifeTimeMax() {
		return lifeTimeMax;
	}

	public void setLifeTimeMax(float lifeTimeMax) {
		this.lifeTimeMax = lifeTimeMax;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
