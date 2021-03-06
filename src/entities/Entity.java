package entities;

import org.lwjgl.util.vector.Vector2f;

import models.Sprite;

public class Entity {
	
	protected Sprite sprite;
	protected Collider collider;
	protected Vector2f position;
	protected Vector2f scale;
	protected float rotation;
	protected float depth;
	
	public Entity() {
		this.sprite = null;
		this.position = new Vector2f(0, 0);
		this.scale = new Vector2f(1, 1);
		this.rotation = 0;
	}
	public Entity(Sprite sprite, Vector2f pos, float rot) {
		this.sprite = sprite;
		this.position = pos;
		this.scale = new Vector2f(1, 1);
		this.rotation = rot;
	}
	public Entity(Sprite sprite, Vector2f pos, float rot, int depth) {
		this.sprite = sprite;
		this.position = pos;
		this.scale = new Vector2f(1, 1);
		this.rotation = rot;
		this.depth = depth;
	}
	public Entity(Sprite sprite, Vector2f pos, Vector2f scale, float rot) {
		this.sprite = sprite;
		this.position = pos;
		this.scale = scale;
		this.rotation = rot;
	}
	public Entity(Sprite sprite, Vector2f pos, Vector2f scale, float rot, int depth) {
		this.sprite = sprite;
		this.position = pos;
		this.scale = scale;
		this.rotation = rot;
		this.depth = depth;
	}
	
	public void setCollider(Collider col) {
		this.collider = col;
	}
	
	public Collider getCollider() {
		return this.collider;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public void increasePosition(float dx, float dy) {
		this.position.x += dx;
		this.position.y += dy;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void increaseRotation(float d) {
		this.rotation += d;
	}
	
	public Vector2f getScale() {
		return this.scale;
	}
	
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	
	public void increaseScale(float dx, float dy) {
		this.scale.x += dx;
		this.scale.y += dy;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}
	
}
