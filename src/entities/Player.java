package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import models.Sprite;
import models.TileSprite;
import render.DisplayManager;

public class Player extends Entity {
	
	private static float GRAVITY = -5;
	
	private Vector2f velocity = new Vector2f(0, 0);
	private float speed = 2;
	private boolean isInAir = true;
	
	public Player(Sprite sprite, Vector2f pos, Vector2f scale, float rot) {
		super(sprite, pos, scale, rot);
	}
	
	public void move(Layer layer, ChunkMap chunks) {
		catchVelocity();
		velocity.y += GRAVITY * DisplayManager.deltaTime();
		float dx = velocity.x * DisplayManager.deltaTime() * speed;
		float dy = velocity.y * DisplayManager.deltaTime();
		
		// TODO fix collision order bug (grip on tile of same y)
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
		
		for (Chunk chk : chunks.getMap().values()) {
			for (TileSprite spr : chk.getMap().keySet()) {
				for (Tile til : chk.get(spr)) {
					slide = this.collider.collideSlide(til, dx, dy);
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
		
		if (dy < 0)
			isInAir = true;
		
		this.increasePosition(dx, dy);
		
		Display.setTitle(this.velocity.y+"");
	}
	
	private void catchVelocity() {
		
		float vx = 0;
		float vy = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			vx -= 1;
			if (this.scale.x > 0) {
				this.scale.x = -this.scale.x;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			vx += 1;
			if (this.scale.x < 0) {
				this.scale.x = -this.scale.x;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !isInAir) {
			vy = 7.5f;
			isInAir = true;
		}
		
		this.velocity.x = vx;
		this.velocity.y += vy;
	}
	
}
