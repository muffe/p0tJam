package potjam.map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import potjam.entities.Entity;
import potjam.shared.Camera;

public class EndZone extends Entity {
	private int counter;
	private int timeMax;
	private boolean enabled;
	private boolean endReached;
	
	public EndZone(float x, float y, float width, float height) {
		super(x, y, width, height);
		this.counter = 0;
		this.timeMax = 1000;
		this.enabled = true;
		this.endReached = false;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if(this.intersects(World.getPlayer()) || this.contains(World.getPlayer())) {
			if(this.counter < this.timeMax) {
				this.counter += delta;
			} else {
				this.endReached = true;
				this.counter = 0;
			}
		}
		
		if(endReached) {
			if(this.counter < this.timeMax*3) {
				this.counter += delta;
			} else {
				this.endReached = false;
				this.counter = 0;
			}
		}
	}

	@Override
	public void draw(GameContainer gc, Graphics g) {
		g.setColor(Color.white);
		
		if(endReached) {
			g.drawString("Level geschafft!", gc.getWidth()/2 - 75 - Camera.getShiftX(), gc.getHeight()/3 - Camera.getShiftY());
		}
		
		//Debug
		//g.draw(this);
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getTimeMax() {
		return timeMax;
	}

	public void setTimeMax(int timeMax) {
		this.timeMax = timeMax;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEndReached() {
		return endReached;
	}

	public void setEndReached(boolean endReached) {
		this.endReached = endReached;
	}

}
