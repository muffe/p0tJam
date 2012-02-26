package potjam.weapons;

import org.newdawn.slick.GameContainer;

import potjam.entities.Entity;
import potjam.entities.Player;

public abstract class Weapon extends Entity {
	private float damage;
	private float reUseTime;
	private float reUseTimeCounter;
	private int ammunition;
	
	public Weapon(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.damage = 10.0f;
		this.reUseTime = 1000.0f;
		this.reUseTimeCounter = 0.0f;
		this.ammunition = -1;
	}
	
	public abstract void use(GameContainer gc, Player player);
	
	@Override
	public void update(GameContainer gc, int delta) {
		updateReUseCounter(delta);
	}
	
	private void updateReUseCounter(int delta) {
		if(this.reUseTimeCounter < this.reUseTime) {
			this.reUseTimeCounter += delta;
		}
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public int getAmmunition() {
		return ammunition;
	}

	public void setAmmunition(int ammunition) {
		this.ammunition = ammunition;
	}

	public float getReUseTime() {
		return reUseTime;
	}

	public void setReUseTime(float reUseTime) {
		this.reUseTime = reUseTime;
	}

	public float getReUseTimeCounter() {
		return reUseTimeCounter;
	}

	public void setReUseTimeCounter(float reUseTimeCounter) {
		this.reUseTimeCounter = reUseTimeCounter;
	}
}
