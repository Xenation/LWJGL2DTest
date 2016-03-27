package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import render.DisplayManager;
import render.Renderer;
import storage.ChunkMap;
import tiles.SupportedTile;
import tiles.Tile;
import tiles.TileType;
import toolbox.Maths;

public class Camera {
	
	private Vector2f position;
	private float rotation;
	
	private Entity follow;
	private float followSpeed;
	private TileType tilType;
	
	public Camera() {
		this.position = new Vector2f(0, 0);
		this.rotation = 0;
		this.tilType = TileType.Dirt;
		this.followSpeed = 4;
	}
	
	public Camera(Vector2f position, float rotation) {
		this.position = position;
		this.rotation = rotation;
		this.tilType = TileType.Dirt;
		this.followSpeed = 4;
	}
	
	public void move(ChunkMap chkMap) {
		
		chkMap.initializeChunks((int) position.x, (int) position.y, (int) Renderer.UNITS_Y/2+8, (int) Renderer.UNITS_Y);
//		chkMap.generateFlatTiles(tileSpr, -8);
		chkMap.generateWave(-4);
		
		if (follow != null) {
			if (this.followSpeed != 0) {
				this.position.x = Maths.lerp(this.position.x, follow.position.x, DisplayManager.deltaTime() * followSpeed);
				this.position.y = Maths.lerp(this.position.y, follow.position.y, DisplayManager.deltaTime() * followSpeed);
			} else {
				this.position.set(follow.getPosition());
			}
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
			if (tilType != TileType.TallGrass) {
				chkMap.setTile(new Tile(tilType, Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY())));
			} else {
				chkMap.setTile(new SupportedTile(tilType, Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY())));
			}
		}
		if (Mouse.isButtonDown(1)) {
			chkMap.removeTile(Maths.fastFloor(getMouseWorldX()), Maths.fastFloor(getMouseWorldY()));
		}
		
	}
	
	public void setFollow(Entity ent) {
		this.follow = ent;
	}
	
	public void setFollowSpeed(float speed) {
		this.followSpeed = speed;
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
	
	public float getMouseWorldX() {
		return this.position.x + (Mouse.getX() - Display.getWidth()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
	}
	
	public float getMouseWorldY() {
		return this.position.y + (Mouse.getY() - Display.getHeight()/2) / ((float) Display.getHeight() / Renderer.UNITS_Y);
	}
	
}
