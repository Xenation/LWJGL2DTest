package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import models.Sprite;

public class Player extends Entity {
	
	private Vector2f velocity = new Vector2f(0, 0);
	
	public Player(Sprite sprite, Vector2f pos, Vector2f scale, float rot) {
		super(sprite, pos, scale, rot);
	}
	
	public void move() {
		catchVelocity();
		this.increasePosition(velocity.x, velocity.y);
		resetVelocity();
	}
	
	private void catchVelocity() {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			this.velocity.y += 0.001f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			this.velocity.x -= 0.001f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.velocity.y -= 0.001f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.velocity.x += 0.001f;
		}
		
	}
	
	private void resetVelocity() {
		this.velocity.x = 0;
		this.velocity.y = 0;
	}
	
}
