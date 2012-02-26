package potjam.main;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import potjam.enemies.Zombie;
import potjam.entities.Player;
import potjam.map.Block;
import potjam.shared.Camera;
import potjam.shared.MouseInput;

public class PotJamMain extends BasicGame {
	public static Player player;
	public static ArrayList<Block> blockList;
	public static ArrayList<Zombie> zombies;
	
	/**
	 * Titel uebergen, initialisieren.
	 * @param title
	 */
	public PotJamMain(String title) {
		super(title);
	}

	//TODO: Bei Implementation der Map, ArrayList fuer Bloecke, Spieler und Zombies mit richtigen ersetzen!
	
	/**
	 * Alle Initialisierungen hier
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		Camera.init();
		MouseInput.init();
		
		this.player = new Player(100, 300, 46, 75);
		this.zombies = new ArrayList<Zombie>();
		
		try {
			this.zombies.add(new Zombie(300, 300, 48, 73));
			this.zombies.add(new Zombie(700, 200, 48, 73));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		blockList = new ArrayList<Block>();
		blockList.add(new Block(0, 500, 1152, 500));
		blockList.add(new Block(550, 450, 50, 50));
	}

	/**
	 * Alle Updates hier, nur Spiellogik und Userinput
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		MouseInput.update(gc);
		this.player.update(gc, delta);
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).update(gc, delta);
		}
	}

	/**
	 * Alles hier Zeichnen. Keine Logikupdates.
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setBackground(new Color(0,100,200));
		
		//Camera - Muss als erstes gezeichnet werden
		Camera.draw(g);
		
		for(int i = 0; i < zombies.size(); i++) {
			zombies.get(i).draw(gc, g);
		}
		
		this.player.draw(gc, g);
		
		for(int i = 0; i < blockList.size(); i++) {
			blockList.get(i).draw(gc, g);
		}
	}
	
	/**
	 * Main Methode zum Erzeugen eines Fensters.
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		PotJamMain game = new PotJamMain("Working Title");
		AppGameContainer app = new AppGameContainer(game, 1152, 648, false);
		app.start();
	}

}
