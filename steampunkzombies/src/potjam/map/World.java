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
		player = new Player(100, 200, 46, 75); //x - y - breite - hoehe                        || HIER SPIELER POSITIONIEREN
		
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
		/*int zombieWidth = 48;
		int zombieHeight = 73;
		
		Zombie z = new Zombie(300, 100, zombieWidth, zombieHeight);
		zombies.add(z);
		
		z = new Zombie(700, 200, zombieWidth, zombieHeight);
		zombies.add(z);*/


		//Bloecke
		createForeground(-1000,-400, 1000,1000, "Void");
		createStandardEarth(0,276, 1000,500);
		createEarthSinglePlatform(400,100, 100, false);
		createEarthSinglePlatform(700,-80, 150, false);
		createForeground(1000,0, 150,276, "Rock");
		createForeground(1000,276, 150,500, "");
		createStandardEarth(1150,276, 150,500);
		createStandardEarth(1300,314, 150,462);
		createStandardEarth(1450,352, 150,424);
		createStandardEarth(1600,390, 500,376);
		createBackground(1300,0, 150,314, "EarthBack");
		createBackground(1450,0, 150,276, "EarthBack");
		
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
	private static void createEarthSinglePlatform(float x, float y, float width, boolean shadow) throws SlickException {
		if (width > 92)
			createForeground(x+46, y, width-92, 38, "EarthPlatformSingle");
		createForeground(x, y, 46, 38, "EarthPlatformSingleCornerLeft");
		createForeground(x+width-46, y, 46, 38, "EarthPlatformSingleCornerRight");
		createBackground(x, y-38, width, 38, "GrassOverlay");
		createBackground(x-46, y, 46, 38, "EarthPlatformCornerLeftOverlay");
		createBackground(x+width, y, 46, 38, "EarthPlatformCornerRightOverlay");
		if (shadow)
			createBackground(x, y+38, width, 38, "EarthShadowOverlay");
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
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).draw(gc, g);
		}
		
		
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).draw(gc, g);
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
