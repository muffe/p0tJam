package potjam.weapons;

import org.newdawn.slick.GameContainer;

import potjam.entities.CharacterEntity;
import potjam.entities.CollisionEntity;

public abstract class Weapon extends CollisionEntity {
	private CharacterEntity user;
	private float damage;
	private float reUseTime;
	private float reUseTimeCounter;
	private int ammunition;
	
	public Weapon(float x, float y, float width, float height, CharacterEntity e) {
		super(x, y, width, height);
		this.user = e;
		this.damage = 10.0f;
		this.reUseTime = 1000.0f;
		this.reUseTimeCounter = 0.0f;
		this.ammunition = -1;
	}
	
	public void use(GameContainer gc, int delta) {
		this.setReUseTimeCounter(0.0f);
	}
	
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

	public CharacterEntity getUser() {
		return user;
	}

	public void setUser(CharacterEntity user) {
		this.user = user;
	}
}
