package potjam.map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import potjam.entities.Entity;

public abstract class MapEntity extends Entity {
	/**
	 * Speichert Textur.
	 */
	private Image texture;
	private boolean repeat;
	
	/**
	 * Maﬂe und Position vom Entity
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws SlickException 
	 */
	public MapEntity(float x, float y, float width, float height) throws SlickException {
		super(x, y, width, height);
		texture = new Image("ressources/map/Earth.png");
		repeat = true;
	}
	
	/**
	 * Zeichnen der Textur.
	 */
	@Override
	public void draw(GameContainer gc, Graphics g) {
		if(this.texture != null) {
			if(repeat) {
				g.fillRect(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight(), this.texture, 0.0f, 0.0f);
			} else {
				g.drawImage(texture, this.getMinX(), this.getMinY());
			}
			
			//Debug
			//g.draw(this);
		} else {
			g.draw(this);
		}
	}

	/**
	 * 
	 * @return Textur
	 */
	public Image getTexture() {
		return this.texture;
	}

	/**
	 * Textur setzen.
	 * @param path
	 * @throws SlickException
	 */
	public void setTexture(String path) throws SlickException {
		this.texture = new Image(path);
	}
	
	public void setTexture(Image image) {
		this.texture = image;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
}
