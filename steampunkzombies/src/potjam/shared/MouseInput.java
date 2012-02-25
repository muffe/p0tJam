package potjam.shared;

import org.newdawn.slick.GameContainer;

public class MouseInput {
	private static float mouseX;
	private static float mouseY;
	
	public static void init() {
		mouseX = 0.0f;
		mouseY = 0.0f;
	}
	
	public static void update(GameContainer gc) {
		mouseX = (float)gc.getInput().getMouseX() - Camera.getShiftX();
		mouseY = (float)gc.getInput().getMouseY() - Camera.getShiftY();
	}

	public static float getMouseX() {
		return mouseX;
	}

	public static void setMouseX(float mouseX) {
		MouseInput.mouseX = mouseX;
	}

	public static float getMouseY() {
		return mouseY;
	}

	public static void setMouseY(float mouseY) {
		MouseInput.mouseY = mouseY;
	}
}
