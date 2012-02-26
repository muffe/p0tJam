package potjam.entities;

import potjam.main.PotJamMain;

public abstract class CollisionEntity extends Entity {

	public CollisionEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	
	/**
	 * Kollision mit allen MapEntity Objekten der ArrayList.
	 * 
	 * TODO: Bei Implementation der Map mit richtiger ArrayList ersetzen!
	 * 
	 * @param interpX
	 * @param interpY
	 * @return
	 */
	public boolean collidedWithWorld(float interpX, float interpY) {
		boolean collided = false;
		this.setX(this.getX() + interpX);
		this.setY(this.getY() + interpY);
		
		for(int i = 0; i < PotJamMain.blockList.size(); i++) {
			if(this.intersects(PotJamMain.blockList.get(i)) || this.contains(PotJamMain.blockList.get(i)) || PotJamMain.blockList.get(i).contains(this)) {
				collided = true;
				break;
			}
		}
		
		this.setX(this.getX() - interpX);
		this.setY(this.getY() - interpY);
		
		return collided;
	}
	
	/**
	 * Kollision mit allen Zombie Objekten der ArrayList.
	 * 
	 * TODO: Bei Implementation der Map mit richtiger ArrayList ersetzen!
	 * 
	 * @param interpX
	 * @param interpY
	 * @return
	 */
	public boolean collidedWithCharacter(float interpX, float interpY) {
		boolean collided = false;
		
		this.setX(this.getX() + interpX);
		this.setY(this.getY() + interpY);
		
		for(int i = 0; i < PotJamMain.zombies.size(); i++) {
			if(this.intersects(PotJamMain.zombies.get(i)) || this.contains(PotJamMain.zombies.get(i)) || PotJamMain.zombies.get(i).contains(this)) {
				collided = true;
				break;
			}
		}
		
		this.setX(this.getX() - interpX);
		this.setY(this.getY() - interpY);
		
		return collided;
	}
	
	/**
	 * Kollision mit allen Spieler Objekten.
	 * 
	 * TODO: Bei Implementation der Map mit richtigen Objekten ersetzen!
	 * 
	 * @param interpX
	 * @param interpY
	 * @return
	 */
	public boolean collidedWithPlayer(float interpX, float interpY) {
		boolean collided = false;
		
		this.setX(this.getX() + interpX);
		this.setY(this.getY() + interpY);
		
		if(this.intersects(PotJamMain.player) || this.contains(PotJamMain.player) || PotJamMain.player.contains(this)) {
			collided = true;
		}
		
		this.setX(this.getX() - interpX);
		this.setY(this.getY() - interpY);
		
		return collided;
	}

}
