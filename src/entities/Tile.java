package entities;

import models.TileSprite;

public class Tile {
	
	public static final float TILE_SIZE = 1;
	
	private TileSprite sprite;
	public final int x;
	public final int y;
	
	public Tile() {
		this.sprite = null;
		this.x = 0;
		this.y = 0;
	}
	public Tile(TileSprite spr, int x, int y) {
		this.sprite = spr;
		this.x = x;
		this.y = y;
	}
	
	public TileSprite getSprite() {
		return sprite;
	}
	
}
