package potjam.main;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import potjam.entities.Gib;
import potjam.entities.Player;
import potjam.entities.enemies.Zombie;
import potjam.map.Block;
import potjam.map.World;
import potjam.shared.Camera;
import potjam.shared.MouseInput;

public class PotJamMain extends BasicGame {
	private Block crosshair;
	
	/**
	 * Titel uebergen, initialisieren.
	 * @param title
	 */
	public PotJamMain() {
		super("Working Title");
	}

	//TODO: Bei Implementation der Map, ArrayList fuer Bloecke, Spieler und Zombies mit richtigen ersetzen!
	
	/**
	 * Alle Initialisierungen hier
	 */
	@Override
	public void init(GameContainer gc) throws SlickException {
		Camera.init();
		MouseInput.init();
		World.init();
		
		crosshair = new Block(0, 0, 32, 32);
		crosshair.setTexture("ressources/Fadenkreuz2.png");
	}

	/**
	 * Alle Updates hier, nur Spiellogik und Userinput
	 */
	@Override
	public void update(GameContainer gc, int delta) {
		MouseInput.update(gc);
		
		try {
			World.update(gc, delta);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		crosshair.setX(MouseInput.getMouseX() - 16);
		crosshair.setY(MouseInput.getMouseY() - 16);
		
		if(gc.getInput().isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}

	/**
	 * Alles hier Zeichnen. Keine Logikupdates.
	 */
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setBackground(new Color(30, 5, 5));
		
		//Camera - Muss als erstes gezeichnet werden
		Camera.draw(g);
		
		World.draw(gc, g);
		
		crosshair.draw(gc, g);
	}
	
	/**
	 * Main Methode zum Erzeugen eines Fensters.
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		PotJamMain game = new PotJamMain();
		AppGameContainer app = new AppGameContainer(game, 1152, 648, false);
		app.setMouseGrabbed(true);
		app.start();
	}

}
