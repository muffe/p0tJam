package potjam.entities;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import potjam.shared.Camera;
import potjam.shared.MouseInput;

public class Player extends CharacterEntity {
	
	/**
	 * Position und Dimension.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws SlickException
	 */
	public Player(float x, float y, float width, float height) throws SlickException {
		super(x, y, width, height);
		initAnimations();
		setAnimation(getAnimationByKey("standRight"), true);
		Camera.centerCamera(1152, 648, this.getMinX() + this.getWidth()/2, this.getMinY() + this.getHeight()/2);
	}

	/**
	 * Input, Bewegungen und Kamera updaten.
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		this.getUserInput(gc);
		
		boolean hasMoved = this.move(delta);
		this.fall(delta);
		
		if(hasMoved) {
			Camera.moveCamera(this.getMoveSpeed()*delta, 0.0f);
		}
		Camera.moveCamera(0.0f, this.getFallSpeedCurrent()*delta);
	}
	
	public void draw(GameContainer gc, Graphics g) {
		super.draw(gc, g);
	}

	/**
	 * Input abfragen und verarbeiten.
	 * @param gc
	 */
	private void getUserInput(GameContainer gc) {
		//Mausabfrage - Links oder Rechts vom Spieler
		if(MouseInput.getMouseX() < this.getMinX() + this.getWidth()/2) {
			this.setLastMovingDirection(0);
		} else {
			this.setLastMovingDirection(1);
		}
		
		//Tastenabfrage - Links
		if(gc.getInput().isKeyDown(Input.KEY_A)) {
			this.setMoveSpeed(-this.getSpeed());
			
			if(!this.isJumping() && this.getLastMovingDirection() == 0) {
				this.setAnimation(this.getAnimationByKey("walkLeft"), true);
			} else {
				this.setAnimation(this.getAnimationByKey("walkRight"), true);
			}
		//Tastenabfrage - Rechts
		} else if(gc.getInput().isKeyDown(Input.KEY_D)) {
			this.setMoveSpeed(this.getSpeed());

			if(!this.isJumping() && this.getLastMovingDirection() == 1) {
				this.setAnimation(this.getAnimationByKey("walkRight"), true);
			} else {
				this.setAnimation(this.getAnimationByKey("walkLeft"), true);
			}
		//Keine Taste gedrueckt
		} else {
			this.setMoveSpeed(0);
			
			if(this.getLastMovingDirection() == 0) {
				if(!this.isJumping()) {
					this.setAnimation(this.getAnimationByKey("standLeft"), true);
				}
			} else {
				if(!this.isJumping()) {
					this.setAnimation(this.getAnimationByKey("standRight"), true);
				}
			}
		}
		
		//Springen
		if(gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			this.jump();
		}
		
		//Animation im Sprung aendern
		if(this.isJumping()) {
			if(this.getLastMovingDirection() == 0) {
				this.setAnimation(this.getAnimationByKey("jumpLeft"), true);
			} else {
				this.setAnimation(this.getAnimationByKey("jumpRight"), true);
			}
		}
	}

	/**
	 * Animationen initialisieren.
	 */
	@Override
	public void initAnimations() throws SlickException {
		int width = 43;
		int height = 75;
		
		//Walk right
		SpriteSheet sheet = new SpriteSheet("ressources/Hero.png", width, height);
		Animation anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(0, 0), 200);
		anim.addFrame(sheet.getSprite(1, 0), 200);
		addAnimation("walkRight", anim);
		
		//Walk left
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(2, 0), 200);
		anim.addFrame(sheet.getSprite(3, 0), 200);
		addAnimation("walkLeft", anim);
		
		//Stand right
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(0, 0), 200);
		addAnimation("standRight", anim);
		
		//Stand left
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(3, 0), 200);
		addAnimation("standLeft", anim);
		
		//Jump right
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(0, 1), 200);
		addAnimation("jumpRight", anim);
		
		//Jump left
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(1, 1), 200);
		addAnimation("jumpLeft", anim);
	}

}
