package potjam.shared;

import org.newdawn.slick.Graphics;

public class Camera {
	/**
	 * X Verschiebung
	 */
	private static float shiftX;
	
	/**
	 * Y Verschiebung
	 */
	private static float shiftY;
	
	/**
	 * Verschieben aktiviert
	 */
	private static boolean shiftEnabled;
	
	/**
	 * Startwerte setzen
	 */
	public static void init() {
		shiftX = 0;
		shiftY = 0;
		shiftEnabled = true;
	}
	
	/**
	 * Kamera bewegen
	 * @param x
	 * @param y
	 */
	public static void moveCamera(float x, float y) {
		shiftX -= x;
		shiftY -= y;
	}
	
	/**
	 * Kamera auf Koordinaten posX und posY zentrieren.
	 * @param windowWidth
	 * @param windowHeight
	 * @param posX
	 * @param posY
	 */
	public static void centerCamera(float windowWidth, float windowHeight, float posX, float posY) {
		float tempX = Math.abs(posX - windowWidth/2);
		float tempY = Math.abs(posY - windowHeight/2);
		
		if(posX >= windowWidth/2)
			tempX = -tempX;
		
		if(posY > windowHeight/2)
			tempY = -tempY;
		
		shiftX = tempX;
		shiftY = tempY;
	}
	
	/**
	 * Kameraverschiebung zeichnen
	 * @param g
	 */
	public static void draw(Graphics g) {
		g.translate(shiftX, shiftY);
	}

	public static boolean isShiftEnabled() {
		return shiftEnabled;
	}

	public static void setShiftEnabled(boolean shiftEnabled) {
		Camera.shiftEnabled = shiftEnabled;
	}

	public static float getShiftX() {
		return shiftX;
	}

	public static float getShiftY() {
		return shiftY;
	}
}
