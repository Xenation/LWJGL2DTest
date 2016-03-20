package storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Tile;
import models.TileSprite;

public class Chunk {
	
	public boolean isGenerating = true;
	
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
			if (getTileAt(tile.x, tile.y) == null) {
				if (tiles.containsKey(tile.getType().getSprite())) {
					tiles.get(tile.getType().getSprite()).add(tile);
				} else {
					List<Tile> newList = new ArrayList<Tile>();
					newList.add(tile);
					tiles.put(tile.getType().getSprite(), newList);
				}
			}
		}
	}
	
	public void addAll(List<Tile> tiles) {
		for (Tile tile : tiles) {
			add(tile);
		}
	}
	
	public void remove(Tile tile) {
		if (tiles.containsKey(tile.getType().getSprite())) {
			tiles.get(tile.getType().getSprite()).remove(tile);
		}
	}
	
	public void removeSprite(TileSprite spr) {
		tiles.remove(spr);
	}
	
	public void removeAt(int x, int y) {
		TileSprite sprRef = null;
		Tile toRemove = null;
		for (TileSprite spr : tiles.keySet()) {
			for (Tile til : tiles.get(spr)) {
				if (til.x == x && til.y == y) {
					toRemove = til;
					sprRef = spr;
				}
			}
		}
		if (sprRef != null && toRemove != null)
			tiles.get(sprRef).remove(toRemove);
	}
	
	public Map<TileSprite, List<Tile>> getMap() {
		return tiles;
	}
	
	public List<Tile> get(TileSprite spr) {
		return tiles.get(spr);
	}
	
	public Tile getTileAt(int x, int y) {
		for (TileSprite spr : tiles.keySet()) {
			for (Tile til : tiles.get(spr)) {
				if (til.x == x && til.y == y) {
					return til;
				}
			}
		}
		return null;
	}
	
	public Vector2i getPosition() {
		return position;
	}
	
	public static Vector2i toChunkPosition(Vector2i worldPos) {
		return new Vector2i(toChunkPosition(worldPos.x), toChunkPosition(worldPos.y));
	}
	public static Vector2i toChunkPosition(int x, int y) {
		return new Vector2i(toChunkPosition(x), toChunkPosition(y));
	}
	public static int toChunkPosition(int i) {
		int n;
		if (i < 0) {
			n = i/8-1;
		} else {
			n = i/8;
		}
		return n;
	}
	
}
