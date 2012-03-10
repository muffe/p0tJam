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
import potjam.shared.Camera;

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
		textures.put("Brick", new Image("ressources/map/Brick.png")); // unused
		textures.put("BrickBack", new Image("ressources/map/BrickBack.png")); // unused
		// Rock
		textures.put("Rock", new Image("ressources/map/Rock.png"));
		// Cave
		textures.put("Cave", new Image("ressources/map/Cave.png"));
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

		//Bloecke
		float map_x = -1000; // dient dazu, mir Berechnungen beim Erstellen von Blöcken von links nach rechts zu ersparen
		
		createForeground(map_x,-400, 1000,1000, "Void");
		map_x += 1000;

		createStandardEarth(map_x,276, 1024,512);
		createEarthPlatform(400,100, 100, 32, false);
		createEarthPlatform(700,-80, 150, 32, false);
		map_x += 1024;
		createZombie(map_x-60, 190);
		createZombie(map_x-100, 190);
		createZombie(map_x-125, 190);
		createZombie(map_x-150, 190);
		
		
		createForeground(map_x,0, 128,276, "Rock");
		createForeground(map_x,276, 128,512, "");
		map_x += 128;
		
		createEarthPlatform(map_x,100, 32,32, false);
		createStandardEarth(map_x,276, 128,512);
		map_x += 128;
		
		createStandardEarth(map_x,308, 128,470);
		createBackground(map_x-128,0, 256,308, "EarthBack");
		createEarthPlatform(map_x,-32, 896,32, false);
		map_x += 128;
		
		createStandardEarth(map_x,340, 128,438);
		createBackground(map_x,0, 128,340, "EarthBack");
		map_x += 128;
		
		createStandardEarth(map_x,372, 512,406);
		createBackground(map_x,0, 512,372, "EarthBack");
		map_x += 512;
		createZombieSpawner(map_x -400,298, 7000);
		createZombieSpawner(map_x -100,298, 7000);
		
		createForeground(map_x,372, 128,406, "");
		createForeground(map_x, 0, 128,372, "Rock");
		//createBackground(map_x,0, 128,96, "EarthBack");
		map_x += 128;
		
		createStandardEarth(map_x,200, 2048,588);
		createZombie(map_x+100, 100);
		createZombie(map_x+400, 100);
		createZombie(map_x+800, 100);
		createEarthPlatform(map_x+200,20, 196,32, false);
		createEarthPlatform(map_x+600,-120, 196,32, false);
		createEarthPlatform(map_x+1000,-200, 128,32, false);
		createEarthPlatform(map_x+1400,-300, 196,32, false);
		createEarthPlatform(map_x+1800,-300, 196,32, false);
		createZombieSpawner(map_x+1400,-374, 6000);
		createZombie(map_x+1100,-300);
		createZombie(map_x+1900,-400);
		map_x += 2048;
		
		createForeground(map_x, 200, 128,788, "");
		createForeground(map_x,-276, 128,276, "Rock");
		createZombieSpawner(map_x-128, 125, 5000);
		map_x += 128;
		
		createStandardEarth(map_x, 200, 3072, 1024);
		createBackground(map_x-128, -276, 3072+128, 476, "EarthBack");
		map_x += 128;
		
		createForeground(map_x+128, -56, 192, 256, "Rock");
		createForeground(map_x+256, -184,192, 384, "Rock");
		createForeground(map_x+386, -56, 192, 256, "Rock");
		createForeground(map_x+512, 72, 192, 128, "Rock");
		createZombieSpawner(map_x+768, 125, 5000);
		createZombieSpawner(map_x+1024, 125, 5000);
		createZombieSpawner(map_x+1280, 125, 5000);
		
		EndZone endZone = new EndZone(map_x+3072-386, 200-128, 386, 128);
		zones.add(endZone);
		
		Block flag = new Block(map_x+3072-256, 200-128, 32, 128);
		flag.setTexture("ressources/map/Flag.png");
		backgroundTiles.add(flag);
		map_x += 3072;
		
		createForeground(map_x-128, -500, 600, 1000, "Void");
		
		

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
		createOverlay(x, y-4, width, 16, "GrassOverlay");
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
	// Zombie erstellen
	// Man muss sicherstellen, dass 48x73 daneben frei ist
	private static void createZombie(float x, float y) throws SlickException {
		Zombie z = new Zombie(x, y, World.ZOMBIE_WIDTH, World.ZOMBIE_HEIGHT);
		zombies.add(z);
	}
	// Zombiespawner erstellen
	// Man muss sicherstellen, dass 48x73 daneben frei ist
	private static void createZombieSpawner(float x, float y, int spawnRate) throws SlickException {
		ZombieSpawner z = new ZombieSpawner((int) x, (int) y, spawnRate, true);
		zombieSpawner.add(z);
		Block b = new Block(x,y, World.ZOMBIE_WIDTH, World.ZOMBIE_HEIGHT);
		b.setTexture(textures.get("Cave"));
		backgroundTiles.add(b);
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
		player.getBackground().draw(gc, g);
		
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
