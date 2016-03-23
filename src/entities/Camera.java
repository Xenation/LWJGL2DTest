package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import render.DisplayManager;
import render.Renderer;
import storage.ChunkMap;

public class Camera {
	
	private Vector2f position;
	private float rotation;
	
	private Entity follow;
	private TileType tilType;
	
	public Camera() {
		this.position = new Vector2f(0, 0);
		this.rotation = 0;
		this.tilType = TileType.Dirt;
	}
	
	public Camera(Vector2f position, float rotation) {
		this.position = position;
		this.rotation = rotation;
		this.tilType = TileType.Dirt;
	}
	
	public void move(ChunkMap chkMap) {
		
		chkMap.initializeChunks((int) position.x, (int) position.y, (int) Renderer.UNITS_Y/2+8, (int) Renderer.UNITS_Y);
//		chkMap.generateFlatTiles(tileSpr, -8);
		chkMap.generateWave(-4);
		
		if (follow != null) {
			this.position.set(follow.getPosition());
		} else {
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
		
		Display.setTitle("FPS"+(int)(1/DisplayManager.deltaTime()));
		
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) {
			this.tilType = TileType.Dirt;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {
			this.tilType = TileType.Stone;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
			this.tilType = TileType.Grass;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3)) {
			this.tilType = TileType.Log;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
			this.tilType = TileType.Leaves;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) {
			this.tilType = TileType.TallGrass;
		}
		
		if (tilType != null && Mouse.isButtonDown(0)) {
			float posX = this.position.x + (Mouse.getX() - Display.getWidth()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
			float posY = this.position.y + (Mouse.getY() - Display.getHeight()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
//			Display.setTitle("X"+Math.floor(posX)+"    Y"+Math.floor(posY));
			chkMap.setTile(new Tile(tilType, (int) Math.floor(posX), (int) Math.floor(posY)));
		}
		
		if (Mouse.isButtonDown(1)) {
			float posX = this.position.x + (Mouse.getX() - Display.getWidth()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
			float posY = this.position.y + (Mouse.getY() - Display.getHeight()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
//			Display.setTitle("X"+Math.floor(posX)+"    Y"+Math.floor(posY));
			chkMap.removeTile((int) Math.floor(posX), (int) Math.floor(posY));
		}
		
	}
	
	public void setFollow(Entity ent) {
		this.follow = ent;
	}
	
	public void setTileType(TileType type) {
		this.tilType = type;
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
