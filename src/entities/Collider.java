package entities;

import org.lwjgl.util.vector.Vector2f;

import storage.ChunkMap;

public class Collider {
	
	private float x, y, w, h;
	private Entity entity;

	public Collider(Entity ent) {
		this.x = -1;
		this.y = -1;
		this.w = 2;
		this.h = 2;
		this.entity = ent;
	}
	public Collider(Entity ent, float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.entity = ent;
	}
	
	public void fitSprite() {
		this.x = -entity.getSprite().getSize().x/2;
		this.y = -entity.getSprite().getSize().y/2;
		this.w = entity.getSprite().getSize().x;
		this.h = entity.getSprite().getSize().y;
	}
	
	public void increaseWidthCentered(float w) {
		this.x -= w/2;
		this.w += w;
	}
	
	public void increaseHeightCentered(float h) {
		this.y -= h/2;
		this.h += h;
	}
	
	public void increaseWidth(float w) {
		this.w += w;
	}
	
	public void increaseHeight(float h) {
		this.h += h;
	}
	
	public void setWidth(float w) {
		this.w = w;
	}
	
	public void setHeight(float h) {
		this.h = h;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public boolean isColliding(Collider col) {
		if ((col.getWX() < this.getWX() + this.w) && (col.getWX() + col.w > this.getWX())
				&& (col.getWY() < this.getWY() + this.h) && (col.getWY() + col.h > this.getWY())) {
			return true;
		}
		return false;
	}
	
	public boolean isColliding(Collider col, float nx, float ny) {
		if ((col.getWX() < this.getWX() + nx + this.w) && (col.getWX() + col.w > this.getWX() + nx)
				&& (col.getWY() < this.getWY() + ny + this.h) && (col.getWY() + col.h > this.getWY() + ny)) {
			return true;
		}
		return false;
	}
	
	public boolean isColliding(Tile til, float nx, float ny) {
		if ((til.x < this.getWX() + nx + this.w) && (til.x + Tile.TILE_SIZE> this.getWX() + nx)
				&& (til.y < this.getWY() + ny + this.h) && (til.y + Tile.TILE_SIZE > this.getWY() + ny)) {
			return true;
		}
		return false;
	}
	
	public Vector2f collideSlide(Collider col, float nx, float ny) {
		Vector2f slide = new Vector2f(1, 1);
		if (isColliding(col, nx, ny)) {
			if (!isCollidingX(col))
				slide.x = 0;
			if (!isCollidingY(col))
				slide.y = 0;
		}
		return slide;
	}
	
	public Vector2f collideSlide(Tile til, ChunkMap chkMap, float nx, float ny) {
		Vector2f slide = new Vector2f(1, 1);
		if (isColliding(til, nx, ny)) {
			Tile left = chkMap.getLeftTile(til);
			Tile right = chkMap.getRightTile(til);
//			Tile top = chkMap.getTopTile(til);
//			Tile bottom = chkMap.getBottomTile(til);
			if (!isCollidingY(til)) {
				slide.y = 0;
			}
			if (!isCollidingX(til)) {
				if ((left != null && !isColliding(left, nx, ny)) && (right != null && !isColliding(right, nx, ny))) {
					slide.x = 0;
				}
				if (isCollidingY(til)) {
					slide.x = 0;
				}
			}
		}
		return slide;
	}
	
	private boolean isCollidingX(Collider col) {
		if ((col.getWX() < this.getWX() + this.w) && (col.getWX() + col.w > this.getWX())) {
			return true;
		}
		return false;
	}
	
	private boolean isCollidingY(Collider col) {
		if ((col.getWY() < this.getWY() + this.h) && (col.getWY() + col.h > this.getWY())) {
			return true;
		}
		return false;
	}
	
	private boolean isCollidingX(Tile til) {
		if ((til.x < this.getWX() + this.w) && (til.x + Tile.TILE_SIZE > this.getWX())) {
			return true;
		}
		return false;
	}
	
	private boolean isCollidingY(Tile til) {
		if ((til.y < this.getWY() + this.h) && (til.y + Tile.TILE_SIZE > this.getWY())) {
			return true;
		}
		return false;
	}
	
	public float getWX() {
		return this.entity.position.x + this.x;
	}
	public float getWY() {
		return this.entity.position.y + this.y;
	}
	
}
