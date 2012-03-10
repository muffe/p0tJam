package potjam.entities.enemies;

import org.newdawn.slick.SlickException;

import potjam.map.World;

public class ZombieSpawner {
	private int x;
	private int y;
	private int spawnRate;
	private boolean enabled;
	private int rateCounter;
	private int maxAmount;
	private int currentAmount;
	
	public ZombieSpawner(int x, int y, int spawnRate, boolean enabled) {
		this.x = x;
		this.y = y;
		this.spawnRate = spawnRate;
		this.enabled = enabled;
		this.currentAmount = 0;
		this.maxAmount = 5;
	}
	
	public void update(int delta) throws SlickException {
		if(this.rateCounter < this.spawnRate) {
			this.rateCounter += delta;
		} else {
			if(this.currentAmount < this.maxAmount) {
				Zombie zombie = new Zombie(x, y, World.ZOMBIE_WIDTH, World.ZOMBIE_HEIGHT);
				World.addZombie(zombie);
				this.rateCounter = 0;
				this.currentAmount++;
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpawnRate() {
		return spawnRate;
	}

	public void setSpawnRate(int spawnRate) {
		this.spawnRate = spawnRate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getCounter() {
		return rateCounter;
	}

	public void setCounter(int counter) {
		this.rateCounter = counter;
	}
}
