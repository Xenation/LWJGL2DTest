package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TileSprite;
import toolbox.Vector2i;

public class Chunk {
	
	private Map<TileSprite, List<Tile>> tiles = new HashMap<TileSprite, List<Tile>>();
	private Vector2i position;
	
	public Chunk() {
		this.position = new Vector2i();
	}
	public Chunk(int x, int y) {
		this.position = new Vector2i(x, y);
	}
	
	public void add(Tile tile) {
		if (tile.x < position.x*8 + 8 && tile.x >= position.x*8
				&& tile.y < position.y*8 + 8 && tile.y >= position.y*8) {
			if (tiles.containsKey(tile.getSprite())) {
				tiles.get(tile.getSprite()).add(tile);
			} else {
				List<Tile> newList = new ArrayList<Tile>();
				newList.add(tile);
				tiles.put(tile.getSprite(), newList);
			}
		}
	}
	
	public void addAll(List<Tile> tiles) {
		for (Tile tile : tiles) {
			add(tile);
		}
	}
	
	public void remove(Tile tile) {
		if (tiles.containsKey(tile.getSprite())) {
			tiles.get(tile.getSprite()).remove(tile);
		}
	}
	
	public void removeSprite(TileSprite spr) {
		tiles.remove(spr);
	}
	
	public Map<TileSprite, List<Tile>> getMap() {
		return tiles;
	}
	
	public List<Tile> get(TileSprite spr) {
		return tiles.get(spr);
	}
	public Vector2i getPosition() {
		return position;
	}
	
}
