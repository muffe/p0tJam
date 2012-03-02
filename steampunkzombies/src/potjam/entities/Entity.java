package potjam.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

public abstract class Entity extends Polygon {
	/**
	 * Erzeugt rechteckiges Polygon.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Entity(float x, float y, float width, float height) {
		this.addPoint(x, y);
		this.addPoint(x + width, y);
		this.addPoint(x + width, y + height);
		this.addPoint(x, y + height);
	}
	
	/**
	 * Alle Updates hier.
	 * @param gc
	 * @param delta
	 */
	public abstract void update(GameContainer gc, int delta) throws SlickException;
	
	/**
	 * Rendering hier.
	 * @param gc
	 * @param g
	 */
	public abstract void draw(GameContainer gc, Graphics g);
}
