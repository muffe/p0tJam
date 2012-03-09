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
		player = new Player(100, 200, 46, 75); //x - y - breite - hoehe                        || HIER SPIELER POSITIONIEREN
		
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
		textures.put("EarthTopGrass", new Image("ressources/map/EarthTopGrass.png"));
		textures.put("EarthBack", new Image("ressources/map/EarthBack.png"));
		textures.put("EarthPlatformCornerLeftOverlay", new Image("ressources/map/EarthPlatformCornerLeftOverlay.png"));
		textures.put("EarthPlatformCornerRightOverlay", new Image("ressources/map/EarthPlatformCornerRightOverlay.png"));
		textures.put("EarthPlatformSingle", new Image("ressources/map/EarthPlatformSingle.png"));
		textures.put("EarthPlatformSingleCornerLeft", new Image("ressources/map/EarthPlatformSingleCornerLeft.png"));
		textures.put("EarthPlatformSingleCornerRight", new Image("ressources/map/EarthPlatformSingleCornerRight.png"));
		textures.put("EarthShadowOverlay", new Image("ressources/map/EarthShadowOverlay.png"));
		textures.put("EarthTopBrick", new Image("ressources/map/EarthTopBrick.png"));
		// Brick
		textures.put("Brick", new Image("ressources/map/Brick.png"));
		textures.put("BrickBack", new Image("ressources/map/BrickBack.png"));
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
		Zombie z = new Zombie(300, 100, ZOMBIE_WIDTH, ZOMBIE_HEIGHT);
		zombies.add(z);
		
		/*z = new Zombie(700, 200, zombieWidth, zombieHeight);
		zombies.add(z);*/

		EndZone zone = new EndZone(500, 0, 300, 276);
		zones.add(zone);

		//Bloecke
		Block b = new Block(-1000,-400, 1000, 1000);
		b.setTexture(textures.get("Void"));
		tiles.add(b);
		createStandardEarth(0,276, 1000, 500);
		createEarthSinglePlatform(400, 162, 100);
		
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
	// Setzt 38 Pixel höher als y an, um das Gras oben drauf zu zeichnen
	private static void createStandardEarth(float x, float y, float width, float height) throws SlickException {
		createBackground(x, y-38, width, 38, "GrassOverlay");
		createForeground(x, y, width, 38, "EarthTopGrass");
		createForeground(x, y+38, width, height-38, "");
	}
	// Einzeilige Erdplattform mit Breite width
	// Macht in alle Richtungen Extras (46 links/rechts, 38 oben/unten)
	// Minimumbreite 2*46
	private static void createEarthSinglePlatform(float x, float y, float width) throws SlickException {
		if (width > 92)
			createForeground(x+46, y, width-92, 38, "EarthPlatformSingle");
		createForeground(x, y, 46, 38, "EarthPlatformSingleCornerLeft");
		createForeground(x+width-46, y, 46, 38, "EarthPlatformSingleCornerRight");
		createBackground(x, y-38, width, 38, "GrassOverlay");
		createBackground(x-46, y, 46, 38, "EarthPlatformCornerLeftOverlay");
		createBackground(x+width, y, 46, 38, "EarthPlatformCornerRightOverlay");
		createBackground(x, y+38, width, 38, "EarthShadowOverlay");
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
