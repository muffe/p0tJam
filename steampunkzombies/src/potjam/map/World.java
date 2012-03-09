package potjam.map;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import potjam.entities.Entity;
import potjam.entities.Player;
import potjam.entities.enemies.Zombie;
import potjam.entities.enemies.ZombieSpawner;

public class World {
	public static final int ZOMBIE_WIDTH = 48;
	public static final int ZOMBIE_HEIGHT = 73;
	
	private static Player player;
	private static ArrayList<Zombie> zombies;
	private static ArrayList<ZombieSpawner> zombieSpawner;
	private static ArrayList<Block> backgroundTiles;
	private static ArrayList<Block> tiles;
	private static ArrayList<Block> foregroundTiles;
	private static ArrayList<Entity> zones;				//Alle Zonen (Endzone, Triggerbereiche etc.) ohne Kollision
	private static HashMap<String, Image> textures;
	
	
	public static void init() throws SlickException { //Enthaelt spaeter Pfad zu .xml Map Datei
		player = new Player(100, 200, 59, 71); //x - y - breite - hoehe                        || HIER SPIELER POSITIONIEREN
		
		zombies = new ArrayList<Zombie>();
		zombieSpawner = new ArrayList<ZombieSpawner>();
		backgroundTiles = new ArrayList<Block>();
		tiles = new ArrayList<Block>();
		foregroundTiles = new ArrayList<Block>();
		zones = new ArrayList<Entity>();
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
		// Void
		textures.put("Void", new Image("ressources/map/Void.png"));
		// Earth & Grass
		textures.put("EarthBack", new Image("ressources/map/EarthBack.png"));
		textures.put("GrassOverlay", new Image("ressources/map/GrassOverlay.png"));
		textures.put("GrassOverlayLeft", new Image("ressources/map/GrassOverlayLeft.png"));
		textures.put("GrassOverlayRight", new Image("ressources/map/GrassOverlayRight.png"));
		textures.put("EarthShadowOverlay", new Image("ressources/map/EarthShadowOverlay.png"));
		textures.put("EarthTopBrick", new Image("ressources/map/EarthTopBrick.png"));
		// Brick
		textures.put("Brick", new Image("ressources/map/Brick.png"));
		textures.put("BrickBack", new Image("ressources/map/BrickBack.png"));
		// Rock
		textures.put("Rock", new Image("ressources/map/Rock.png"));
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

		//Bloecke
		float map_x = -1000; // dient dazu, mir Berechnungen beim Erstellen von Blöcken von links nach rechts zu ersparen
		
		createForeground(map_x,-400, 1000,1000, "Void");
		map_x += 1000;

		createStandardEarth(map_x,276, 1024,512);
		createEarthPlatform(400,100, 100, 32, false);
		createEarthPlatform(700,-80, 150, 32, false);
		map_x += 1024;
		Zombie z = new Zombie(map_x-60, 190, zombieWidth, zombieHeight);
		zombies.add(z);
		
		createForeground(map_x,0, 128,276, "Rock");
		createForeground(map_x,276, 128,512, "");
		map_x += 128;
		
		createStandardEarth(map_x,276, 128,512);
		map_x += 128;
		
		createStandardEarth(map_x,308, 128,470);
		createBackground(map_x,0, 128,308, "EarthBack");
		createEarthPlatform(map_x,-32, 256,32, true);
		map_x += 128;
		
		createStandardEarth(map_x,340, 128,438);
		createBackground(map_x,0, 128,340, "EarthBack");
		map_x += 128;
		
		createStandardEarth(map_x,372, 512,406);

		/*EndZone zone = new EndZone(500, 0, 300, 276);
		zones.add(zone);*/
	}
	// Erzeugt einen Vordergrundbereich
	private static void createForeground(float x, float y, float width, float height, String texture) throws SlickException {
		Block b = new Block(x, y, width, height);
		if (texture != "")
			b.setTexture(textures.get(texture));
		tiles.add(b);
	}
	// Erzeugt einen Hintergrundbereich
	private static void createBackground(float x, float y, float width, float height, String texture) throws SlickException {
		Block b = new Block(x, y, width, height);
		if (texture != "")
			b.setTexture(textures.get(texture));
		backgroundTiles.add(b);
	}
	// Erzeugt einen Nicht-Kollisions-Vordergrundbereich
	private static void createOverlay(float x, float y, float width, float height, String texture) throws SlickException {
		Block b = new Block(x, y, width, height);
		if (texture != "")
			b.setTexture(textures.get(texture));
		foregroundTiles.add(b);
	}
	// Setzt 4 Pixel höher als y an, um das Gras oben drauf zu zeichnen
	private static void createStandardEarth(float x, float y, float width, float height) throws SlickException {
		createForeground(x, y, width, height, "");
		createOverlay(x, y-4, width, 14, "GrassOverlay");
	}
	// Erdplattform
	// Macht in alle Richtungen Extras (46 links/rechts, 4 oben, 12 unten)
	private static void createEarthPlatform(float x, float y, float width, float height, boolean shadow) throws SlickException {
		createForeground(x, y, width, height, "");
		createOverlay(x, y-4, width, 15, "GrassOverlay");
		createOverlay(x-4, y, 15, height, "GrassOverlayLeft");
		createOverlay(x+width-12, y, 15, height, "GrassOverlayRight");
		if (shadow)
			createOverlay(x, y+height, width, 13, "EarthShadowOverlay");
	}

	public static void update(GameContainer gc, int delta) throws SlickException {
		player.update(gc, delta);
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).update(gc, delta);
		}
		
		for(int i = 0; i < zombieSpawner.size(); i++) {
			if(zombieSpawner.get(i).isEnabled()) {
				zombieSpawner.get(i).update(delta);
			}
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
		
		for(int i = 0; i < zones.size(); i++) {
			zones.get(i).update(gc, delta);
		}
	}
	
	public static void draw(GameContainer gc, Graphics g) {
		for(int i = 0; i < backgroundTiles.size(); i++) {
			backgroundTiles.get(i).draw(gc, g);
		}
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).draw(gc, g);
		}
		
		
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).draw(gc, g);
		}
		
		for(int i = 0; i < zones.size(); i++) {
			zones.get(i).draw(gc, g);
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
	
	public static ZombieSpawner getZombieSpawnerByIndex(int index) {
		return zombieSpawner.get(index);
	}
	
	public static void addZombieSpawner(ZombieSpawner zs) {
		zombieSpawner.add(zs);
	}
	
	public static int getZombieSpawnerListSize() {
		return zombieSpawner.size();
	}
	
	public static void removeZombieSpawnerByIndex(int index) {
		zombieSpawner.remove(index);
	}
	
	public static void removeZombieSpawner(ZombieSpawner zs) {
		zombieSpawner.remove(zs);
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
