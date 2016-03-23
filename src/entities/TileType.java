package entities;

import models.TileSprite;
import render.Loader;

public enum TileType {
	Dirt("tile_dirt"),
	Grass("tile_grass"),
	Stone("tile_stone"),
	Log("tile_log"),
	Leaves("tile_leaves"),
	TallGrass("tile_tallgrass", false);
	
	private final TileSprite sprite;
	private final boolean isSolid;
	
	private TileType(String sprFile) {
		this.sprite = new TileSprite(Loader.DEF_LOADER, sprFile);
		this.isSolid = true;
	}
	
	private TileType(String sprFile, boolean solid) {
		this.sprite = new TileSprite(Loader.DEF_LOADER, sprFile);
		this.isSolid = solid;
	}
	
	public TileSprite getSprite() {
		return this.sprite;
	}
	
	public boolean isSolid() {
		return this.isSolid;
	}
	
}
