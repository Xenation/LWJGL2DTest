package storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tiles.Tile;
import tiles.TileType;

public class Chunk {
	
	public boolean isGenerating = true;
	
	private Map<TileType, List<Tile>> tiles = new HashMap<TileType, List<Tile>>();
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
				if (tiles.containsKey(tile.getType())) {
					tiles.get(tile.getType()).add(tile);
				} else {
					List<Tile> newList = new ArrayList<Tile>();
					newList.add(tile);
					tiles.put(tile.getType(), newList);
				}
			}
		}
	}
	
	public void addAll(List<Tile> tiles) {
		for (Tile tile : tiles) {
			add(tile);
		}
	}
	
	public void set(Tile tile) {
		if (tile.x < position.x*8 + 8 && tile.x >= position.x*8
				&& tile.y < position.y*8 + 8 && tile.y >= position.y*8) {
			Tile existing = getTileAt(tile.x, tile.y);
			if (existing == null) {
				if (tiles.containsKey(tile.getType())) {
					tiles.get(tile.getType()).add(tile);
				} else {
					List<Tile> newList = new ArrayList<Tile>();
					newList.add(tile);
					tiles.put(tile.getType(), newList);
				}
			} else {
				tiles.get(existing.getType()).remove(existing);
				if (existing.getType() == tile.getType()) {
					tiles.get(tile.getType()).add(tile);
				} else {
					if (tiles.containsKey(tile.getType())) {
						tiles.get(tile.getType()).add(tile);
					} else {
						List<Tile> newList = new ArrayList<Tile>();
						newList.add(tile);
						tiles.put(tile.getType(), newList);
					}
				}
			}
		}
	}
	
	public void remove(Tile tile) {
		if (tiles.containsKey(tile.getType())) {
			tiles.get(tile.getType()).remove(tile);
		}
	}
	
	public void removeType(TileType typ) {
		tiles.remove(typ);
	}
	
	public void removeAt(int x, int y) {
		TileType typRef = null;
		Tile toRemove = null;
		for (TileType typ : tiles.keySet()) {
			for (Tile til : tiles.get(typ)) {
				if (til.x == x && til.y == y) {
					toRemove = til;
					typRef = typ;
				}
			}
		}
		if (typRef != null && toRemove != null)
			tiles.get(typRef).remove(toRemove);
	}
	
	public Map<TileType, List<Tile>> getMap() {
		return tiles;
	}
	
	public Set<TileType> getTypes() {
		return tiles.keySet();
	}
	
	public List<Tile> get(TileType typ) {
		return tiles.get(typ);
	}
	
	public Tile getTileAt(int x, int y) {
		for (TileType typ : tiles.keySet()) {
			for (Tile til : tiles.get(typ)) {
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
		if (i < 0 && i % 8 != 0) {
			n = i/8-1;
		} else {
			n = i/8;
		}
		return n;
	}
	
}
