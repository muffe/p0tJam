package potjam.entities.weapons;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import potjam.entities.CharacterEntity;
import potjam.entities.Player;
import potjam.main.PotJamMain;
import potjam.map.World;
import potjam.shared.MouseInput;
import potjam.shared.Point;

public class FlintlockPistol extends Weapon {
	private ArrayList<Projectile> projectiles;
	private float speed;
	private float projectileSize;
	private Sound gunShot;

	public FlintlockPistol(float x, float y, float width, float height, CharacterEntity e) throws SlickException {
		super(x, y, width, height, e);
		projectiles = new ArrayList<Projectile>();
		speed = 2.0f;
		projectileSize = 3;
		this.setReUseTime(500.0f);
		this.setDamage(50.0f);
		gunShot = new Sound("ressources/sounds/FlintlockPistol_Shot.wav");
	}

	@Override
	public void use(GameContainer gc, int delta) {
		if(this.getReUseTimeCounter() >= this.getReUseTime()) {
			float midX = World.getPlayer().getMinX() + World.getPlayer().getWidth()/2 - projectileSize/2;
			float midY = World.getPlayer().getMinY() + World.getPlayer().getHeight()/2 - projectileSize/2;
			Projectile p = new Projectile(midX, midY, projectileSize, projectileSize, speed, this);
			p.calculateSpeed(new Point(midX, midY), new Point(MouseInput.getMouseX(), MouseInput.getMouseY()));
			this.projectiles.add(p);
			
			gunShot.play();
			//reUseTimeCounter Reset
			super.use(gc, delta);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) {
		//Update reUse Timer
		super.update(gc, delta);
		
		//Update Projectiles
		for(int i = 0; i < this.projectiles.size(); i++) {
			this.projectiles.get(i).update(gc, delta);
			
			if(this.projectiles.get(i).isDead()) {
				this.projectiles.remove(i);
			}
		}
	}

	@Override
	public void draw(GameContainer gc, Graphics g) {
		//Draw Projectiles
		for(int i = 0; i < this.projectiles.size(); i++) {
			this.projectiles.get(i).draw(gc, g);
		}
	}
	
/*	public void addProjectile(Projectile proj) {
		this.projectiles.add(proj);
	}
	
	public Projectile getProjectileByIndex(int index) {
		return this.projectiles.get(index);
	}
	
	public void removeProjectileByIndex(int index) {
		this.projectiles.remove(index);
	} */
}
