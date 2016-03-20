package entities;

import models.TileSprite;
import render.Loader;

public enum TileType {
	Dirt("dirt", "tile_dirt"),
	Grass("grass", "tile_grass"),
	Stone("stone", "tile_stone");
	
	private String name;
	private TileSprite sprite;
	
	private TileType(String name, String sprFile) {
		this.name = name;
		this.sprite = new TileSprite(Loader.DEF_LOADER, sprFile);
	}
	
	public String getName() {
		return this.name;
	}
	
	public TileSprite getSprite() {
		return this.sprite;
	}
	
}
