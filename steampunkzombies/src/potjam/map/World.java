package potjam.map;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import potjam.entities.Player;
import potjam.entities.enemies.Zombie;

public class World {
	private static Player player;
	private static ArrayList<Zombie> zombies;
	private static ArrayList<Block> backgroundTiles;
	private static ArrayList<Block> tiles;
	private static ArrayList<Block> foregroundTiles;
	private static HashMap<String, Image> textures;
	
	
	public static void init() throws SlickException { //Enthaelt spaeter Pfad zu .xml Map Datei
		player = new Player(100, 100, 46, 75); //x - y - breite - hoehe                        || HIER SPIELER POSITIONIEREN
		
		zombies = new ArrayList<Zombie>();
		backgroundTiles = new ArrayList<Block>();
		tiles = new ArrayList<Block>();
		foregroundTiles = new ArrayList<Block>();
		textures = new HashMap<String, Image>();
		
		loadTextures();
		loadMap();
	}

	/**
	 * Hier Texturen in die HashMap legen.
	 * 
	 * Beispiel:
	 * textures.put("identifier", new Image("ressources/.../texturename.png"));
	 * 
	 * Alle Bloecke haben standardmaessig die MapFront.png Textur.
	 * 
	 * @throws SlickException 
	 * 
	 */
	private static void loadTextures() throws SlickException {
		
	}
	
	/**
	 * Hier die Map bauen. Beispiel zur Erstellung und Texturierung eines Blocks:
	 * 
	 * Texturen wie folgt auslesen und einem Block zuweisen:
	 * Block b = new Block(x, y, w, h);
	 * b.setTexture(textures.get("identifier"));
	 * tiles.add(b);
	 * 
	 * Nur Bloecke aus der "tiles" ArrayList werden in die Kollision miteinbezogen!
	 * 
	 * 
	 * Beispiel zur Erstellung eines Zombies:
	 * Zombie z = new Zombie(x, y, w, h);
	 * zombies.add(z);
	 * 
	 * @throws SlickException 
	 * 
	 */
	private static void loadMap() throws SlickException {
		//Zombies - Breite und Hoehe bitte immer gleich lassen
		int zombieWidth = 48;
		int zombieHeight = 73;
		
		Zombie z = new Zombie(300, 300, zombieWidth, zombieHeight);
		zombies.add(z);
		
		z = new Zombie(700, 200, zombieWidth, zombieHeight);
		zombies.add(z);
		
		
		//Bloecke
		Block b = new Block(-1152, 500, 2304, 500);
		tiles.add(b);
		
		b = new Block(550, 450, 50, 50);
		tiles.add(b);
		
		b = new Block(550, 150, 50, 50);
		tiles.add(b);
		
	}

	public static void update(GameContainer gc, int delta) throws SlickException {
		player.update(gc, delta);
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).update(gc, delta);
		}
		
		for(int i = 0; i < backgroundTiles.size(); i++) {
			backgroundTiles.get(i).update(gc, delta);
		}
		
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).update(gc, delta);
		}
		
		for(int i = 0; i < foregroundTiles.size(); i++) {
			foregroundTiles.get(i).update(gc, delta);
		}
	}
	
	public static void draw(GameContainer gc, Graphics g) {
		for(int i = 0; i < backgroundTiles.size(); i++) {
			backgroundTiles.get(i).draw(gc, g);
		}
		
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).draw(gc, g);
		}
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).draw(gc, g);
		}
		
		player.draw(gc, g);
		
		for(int i = 0; i < foregroundTiles.size(); i++) {
			foregroundTiles.get(i).draw(gc, g);
		}
	}
	
	public static Zombie getZombieByIndex(int index) {
		return zombies.get(index);
	}
	
	public static void addZombie(Zombie zombie) {
		zombies.add(zombie);
	}
	
	public static int getZombieListSize() {
		return zombies.size();
	}
	
	public static void removeZombieByIndex(int index) {
		zombies.remove(index);
	}
	
	public static void removeZombie(Zombie zombie) {
		zombies.remove(zombie);
	}
	
	public static Block getTileByIndex(int index) {
		return tiles.get(index);
	}
	
	public static void addTile(Block block) {
		tiles.add(block);
	}
	
	public static int getTileListSize() {
		return tiles.size();
	}
	
	public static Player getPlayer() {
		return player;
	}
}
