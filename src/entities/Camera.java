package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class Camera {
	
	private Vector2f position;
	private float rotation;
	
	public Camera() {
		this.position = new Vector2f(0, 0);
		this.rotation = 0;
	}
	
	public Camera(Vector2f position, float rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public void move() {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
			this.position.y += 0.02f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) {
			this.position.y -= 0.02f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
			this.position.x -= 0.02f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
			this.position.x += 0.02f;
		}
		
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
}
