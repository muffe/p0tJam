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
	private Player player;
	public static ArrayList<Block> blockList;
	private Zombie zombie;
	
	/**
	 * Titel uebergen, initialisieren.
	 * @param title
	 */
	public PotJamMain(String title) {
		super(title);
	}

	/**
	 * Alle Initialisierungen hier
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		Camera.init();
		MouseInput.init();
		
		this.player = new Player(300, 300, 46, 75);
		this.zombie = new Zombie(300, 300, 46, 75);
		blockList = new ArrayList<Block>();
		blockList.add(new Block(0, 500, 1152, 500));
	}

	/**
	 * Alle Updates hier, nur Spiellogik und Userinput
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		MouseInput.update(gc);
		this.player.update(gc, delta);
		this.zombie.update(gc, delta);
		
		for(int i = 0; i < 10000000; i++);
	}

	/**
	 * Alles hier Zeichnen. Keine Logikupdates.
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//Camera - Muss als erstes gezeichnet werden
		g.setBackground(new Color(0,100,200));
		Camera.draw(g);
		this.player.draw(gc, g);
		this.zombie.draw(gc, g);
		
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
