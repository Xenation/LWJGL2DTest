package entities;

import org.lwjgl.util.vector.Vector2f;

import models.Sprite;
import storage.ChunkMap;

public class Collider {
	
	private float x, y, w, h;
	private Vector2f entPos;

	public Collider(Vector2f pos) {
		this.x = -1;
		this.y = -1;
		this.w = 2;
		this.h = 2;
		this.entPos = pos;
	}
	public Collider(Vector2f pos, float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.entPos = pos;
	}
	
	public void fitSprite(Sprite spr) {
		this.x = -spr.getSize().x/2;
		this.y = -spr.getSize().y/2;
		this.w = spr.getSize().x;
		this.h = spr.getSize().y;
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
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getW() {
		return w;
	}
	
	public float getH() {
		return h;
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
			float tilTop = til.y + Tile.TILE_SIZE;
			float tilBottom = til.y;
			float tilRight = til.x + Tile.TILE_SIZE;
			float tilLeft = til.x;
			
			float curTop = getWY() + h;
			float curBottom = getWY();
			float curRight = getWX() + w;
			float curLeft = getWX();
			
			if (curRight <= tilLeft) {
				// We are left of tile
				Tile left = chkMap.getLeftTile(til);
				if (left != null && (curBottom >= tilTop || curTop <= tilBottom)) { // There is a left tile
					slide.y = 0;
				} else {
					slide.x = 0;
				}
			}
			if (curLeft >= tilRight) {
				// We are right of tile
				Tile right = chkMap.getRightTile(til);
				if (right != null && (curBottom >= tilTop || curTop <= tilBottom)) { // There is a right tile
					slide.y = 0;
				} else {
					slide.x = 0;
				}
			}
			if (curBottom >= tilTop) {
				// We are top of tile
				Tile top = chkMap.getTopTile(til);
				if (top != null && (curLeft >= tilRight || curRight <= tilLeft)) { // There is a top tile
					slide.x = 0;
				} else {
					slide.y = 0;
				}
			}
			if (curTop <= tilBottom) {
				// We are bottom of tile
				Tile bottom = chkMap.getBottomTile(til);
				if (bottom != null && (curLeft >= tilRight || curRight <= tilLeft)) { // There is a bottom tile
					slide.x = 0;
				} else {
					slide.y = 0;
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
	
	public float getWX() {
		return this.entPos.x + this.x;
	}
	public float getWY() {
		return this.entPos.y + this.y;
	}
	
}
