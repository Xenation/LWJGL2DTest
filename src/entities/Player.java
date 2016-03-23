package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import models.Sprite;
import render.DisplayManager;
import render.Renderer;
import storage.Chunk;
import storage.ChunkMap;
import storage.Layer;

public class Player extends Entity {
	
	private static float GRAVITY = -9.8f;
	
	private Vector2f velocity = new Vector2f(0, 0);
	private float speed = 5;
	private boolean isInAir = true;
	private boolean isSprinting = false;
	
	public Player(Sprite sprite, Vector2f pos, Vector2f scale, float rot) {
		super(sprite, pos, scale, rot);
	}
	
	public void move(Layer layer, ChunkMap chunks) {
		catchVelocity();
		velocity.y += GRAVITY * DisplayManager.deltaTime();
		float dx = velocity.x * DisplayManager.deltaTime();
		float dy = velocity.y * DisplayManager.deltaTime();
		
		Vector2f slide = new Vector2f(1, 1);
		for (Sprite spr : layer.getMap().keySet()) {
			for (Entity ent : layer.getMap().get(spr)) {
				if (ent.collider != null && ent != this) {
					slide = this.collider.collideSlide(ent.collider, dx, dy);
					if (slide.y == 0) {
						if (dy < 0) {
							isInAir = false;
						}
						velocity.y = 0;
					}
					dx *= slide.x;
					dy *= slide.y;
				}
			}
		}
		
		for (Chunk chk : chunks.getSurroundingChunks((int) position.x, (int) position.y, (int) Renderer.UNITS_Y/2, (int) Renderer.UNITS_Y)) {
			for (TileType typ : chk.getTypes()) {
				for (Tile til : chk.get(typ)) {
					if (til.getType().isSolid()) {
						slide = this.collider.collideSlide(til, chunks, dx, dy);
						if (slide.y == 0) {
							if (dy < 0) {
								isInAir = false;
							}
							velocity.y = 0;
						}
						dx *= slide.x;
						dy *= slide.y;
					}
				}
			}
		}
		
		if (dy < 0)
			isInAir = true;
		
		this.increasePosition(dx, dy);
	}
	
	private void catchVelocity() {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			isSprinting = true;
		} else {
			isSprinting = false;
		}
		
		float vx = 0;
		float vy = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			if (isSprinting) vx -= speed * 2;
			else vx -= speed;
			if (this.scale.x > 0) {
				this.scale.x = -this.scale.x;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (isSprinting) vx += speed * 2;
			else vx += speed;
			if (this.scale.x < 0) {
				this.scale.x = -this.scale.x;
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_Z) && velocity.y < 5) {
			vy = 0.25f;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) {
			vy = 10;
			isInAir = true;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_MULTIPLY)) {
			this.position.x = 0;
			this.position.y = 0;
		}
		
		this.velocity.x = vx;
		this.velocity.y += vy;
	}
	
}
