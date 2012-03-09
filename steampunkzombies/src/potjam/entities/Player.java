package potjam.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import potjam.entities.weapons.FlintlockPistol;
import potjam.entities.weapons.Weapon;
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
		this.setDrawStandardHealth(false);
		this.setHealthBarWidth(100f);
		this.setHealthBarWidthMax(100f);
		this.setHealthBarHeight(10f);
		initAnimations();
		setAnimation(getAnimationByKey("standRight"), true);
		Camera.centerCamera(1152, 648, this.getMinX() + this.getWidth()/2, this.getMinY() + this.getHeight()/2);
		this.setActiveWeapon(new FlintlockPistol(this.getMinX(), this.getMinY(), this.getWidth(), this.getHeight(), this));
	}

	/**
	 * Input, Bewegungen und Kamera updaten.
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		this.getActiveWeapon().update(gc, delta);
		this.getUserInput(gc, delta);
		
		boolean hasMoved = true;
		if(!collidedWithWorld(this.getMoveSpeed()*delta, 0)) {
			this.move(delta);
		} else {
			hasMoved = false;
		}
		
		this.fall(delta);
		Camera.centerCamera(gc.getWidth(), gc.getHeight(), this.getMinX() + this.getWidth()/2, this.getMinY() + this.getHeight()/2);
		
		this.checkDeath();
	}

	public void draw(GameContainer gc, Graphics g) {
		super.draw(gc, g);
		this.getActiveWeapon().draw(gc, g);
		this.drawHealth(gc, g);
	}
	
	private void checkDeath() {
		if(this.isDead()) {
			this.setX(0);
			this.setY(0);
			this.setHitPoints(this.getHitPointsMax());
			this.setDead(false);
		}
	}
	
	private void drawHealth(GameContainer gc, Graphics g) {
		g.setColor(Color.white);
		g.drawString("Health: ", 15f - Camera.getShiftX(), 50f - Camera.getShiftY());
		
		g.setColor(Color.red);
		g.fillRect(90f - Camera.getShiftX(), 50f + this.getHealthBarHeight()/2f - Camera.getShiftY(), this.getHealthBarWidth(), this.getHealthBarHeight());
	}

	/**
	 * Input abfragen und verarbeiten.
	 * @param gc
	 */
	private void getUserInput(GameContainer gc, int delta) {
		//Mausabfrage - Links oder Rechts vom Spieler
		if(MouseInput.getMouseX() < this.getMinX() + this.getWidth()/2) {
			this.setLastMovingDirection(0);
		} else {
			this.setLastMovingDirection(1);
		}
		
		//Maustastenabfrage
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			this.getActiveWeapon().use(gc, delta);
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
		int width = 59;
		int height = 71;
		int animSpeed = 100;
		
		//Walk right
		SpriteSheet sheet = new SpriteSheet("ressources/Hero.png", width, height);
		Animation anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 8; i++) {
			anim.addFrame(sheet.getSprite(0, i), animSpeed);
		}
		addAnimation("walkRight", anim);
		
		//Walk left
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		for(int i = 0; i < 8; i++) {
			anim.addFrame(sheet.getSprite(0, i).getFlippedCopy(true, false), animSpeed);
		}
		addAnimation("walkLeft", anim);
		
		//Stand right
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(0, 3), animSpeed);
		addAnimation("standRight", anim);
		
		//Stand left
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(0, 3).getFlippedCopy(true, false), animSpeed);
		addAnimation("standLeft", anim);
		
		//Jump right
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(0, 0), animSpeed);
		addAnimation("jumpRight", anim);
		
		//Jump left
		sheet = new SpriteSheet("ressources/Hero.png", width, height);
		anim = new Animation();
		anim.setAutoUpdate(true);
		anim.addFrame(sheet.getSprite(0, 0).getFlippedCopy(true, false), animSpeed);
		addAnimation("jumpLeft", anim);
	}

}
