package models;

import render.Loader;
import tiles.Tile;

public class TileSprite {
	
	private int texture;
	protected RawModel model;
	
	public TileSprite(Loader loader, String fileName) {
		this.texture = loader.loadTexture(fileName);
		generateModel(loader);
	}
	
	private void generateModel(Loader loader) {
		float pos[] = {0, Tile.TILE_SIZE,
				Tile.TILE_SIZE, Tile.TILE_SIZE,
				0, 0,
				Tile.TILE_SIZE, 0};
		float tex[] = {0,0, 1,0, 0,1, 1,1};
		this.model = loader.loadToVAO(pos, tex);
	}
	
	public int getTexture() {
		return texture;
	}
	
	public RawModel getModel() {
		return this.model;
	}
	
}
