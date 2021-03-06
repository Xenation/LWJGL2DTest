package storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TileSprite;
import tiles.Tile;
import tiles.TileType;

public class ChunkMap {
	
	private Map<Vector2i, Chunk> chunkMap = new HashMap<Vector2i, Chunk>();
	
	//// ADDERS \\\\
	public void add(Chunk chk) {
		if (!chunkMap.containsKey(chk.getPosition())) {
			chunkMap.put(chk.getPosition(), chk);
		}
	}
	
	public void addAll(List<Chunk> chks) {
		for (Chunk chk : chks) {
			add(chk);
		}
	}
	
	public void addTile(Tile til) {
		Chunk chk = getAt(Chunk.toChunkPosition(til.x), Chunk.toChunkPosition(til.y));
		if (chk != null)
			chk.add(til);
	}
	
	//// REMOVERS \\\\
	public void remove(Chunk chk) {
		chunkMap.remove(chk.getPosition(), chk);
	}
	
	public void removeAt(Vector2i pos) {
		chunkMap.remove(pos);
	}
	
	public void removeTile(int x, int y) {
		Chunk chk = getAt(Chunk.toChunkPosition(x), Chunk.toChunkPosition(y));
		if (chk != null)
			chk.removeAt(x, y);
	}
	
	//// SETTERS \\\\
	public void setTile(Tile til) {
		Chunk chk = getAt(Chunk.toChunkPosition(til.x), Chunk.toChunkPosition(til.y));
		if (chk != null)
			chk.set(til);
	}
	
	//// GETTERS \\\\
	public Map<Vector2i, Chunk> getMap() {
		return chunkMap;
	}
	
	// Chunk
	public Chunk getAt(Vector2i pos) {
		for (Vector2i vec : chunkMap.keySet()) {
			if (vec.equals(pos)) {
				return chunkMap.get(vec);
			}
		}
		return null;
	}
	public Chunk getAt(int x, int y) {
		for (Vector2i vec : chunkMap.keySet()) {
			if (vec.equals(x, y)) {
				return chunkMap.get(vec);
			}
		}
		return null;
	}
	
	// Tile
	public Tile getTileAt(int x, int y) {
		Chunk chk = getAt(Chunk.toChunkPosition(x, y));
		for (TileType typ : chk.getTypes()) {
			for (Tile til : chk.get(typ)) {
				if (til.x == x && til.y == y) {
					return til;
				}
			}
		}
		return null;
	}
	
	// Tile relative
	public Tile getLeftTile(Tile tile) {
		for (Chunk chk : chunkMap.values()) {
			for (TileType typ : chunkMap.get(chk.getPosition()).getTypes()) {
				for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(typ)) {
					if (tile.x == til.x + 1 && tile.y == til.y) {
						return til;
					}
				}
			}
		}
		return null;
	}
	public Tile getRightTile(Tile tile) {
		for (Chunk chk : chunkMap.values()) {
			for (TileType typ : chunkMap.get(chk.getPosition()).getTypes()) {
				for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(typ)) {
					if (tile.x == til.x - 1 && tile.y == til.y) {
						return til;
					}
				}
			}
		}
		return null;
	}
	public Tile getTopTile(Tile tile) {
		for (Chunk chk : chunkMap.values()) {
			for (TileType typ : chunkMap.get(chk.getPosition()).getTypes()) {
				for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(typ)) {
					if (tile.x == til.x && tile.y == til.y - 1) {
						return til;
					}
				}
			}
		}
		return null;
	}
	public Tile getBottomTile(Tile tile) {
		for (Chunk chk : chunkMap.values()) {
			for (TileType typ : chunkMap.get(chk.getPosition()).getTypes()) {
				for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(typ)) {
					if (tile.x == til.x && tile.y == til.y + 1) {
						return til;
					}
				}
			}
		}
		return null;
	}
	
	// Surroundings
	public Map<Vector2i, Chunk> getSurroundings(int x, int y, int above, int side) {
		Map<Vector2i, Chunk> surr = new HashMap<Vector2i, Chunk>();
		
		Vector2i topLeft = Chunk.toChunkPosition(x-side, y+above);
		Vector2i botLeft = Chunk.toChunkPosition(x-side, y-above);
		Vector2i botRight = Chunk.toChunkPosition(x+side, y-above);
		
		for (int cY = botLeft.y; cY <= topLeft.y; cY++) {
			for (int cX = botLeft.x; cX <= botRight.x; cX++) {
				Chunk chk = getAt(cX, cY);
				if (chk != null) {
					surr.put(chk.getPosition(), chk);
				}
			}
		}
		
		return surr;
	}
	public List<Chunk> getSurroundingChunks(int x, int y, int above, int side) {
		List<Chunk> surr = new ArrayList<Chunk>();
		
		Vector2i topLeft = Chunk.toChunkPosition(x-side, y+above);
		Vector2i botLeft = Chunk.toChunkPosition(x-side, y-above);
		Vector2i botRight = Chunk.toChunkPosition(x+side, y-above);
		
		for (int cY = botLeft.y; cY <= topLeft.y; cY++) {
			for (int cX = botLeft.x; cX <= botRight.x; cX++) {
				Chunk chk = getAt(cX, cY);
				if (chk != null) {
					surr.add(chk);
				}
			}
		}
		
		return surr;
	}
	
	//// GENERATORS \\\\
	public void initializeChunks(int x, int y, int above, int side) {
		Vector2i topLeft = Chunk.toChunkPosition(x-side, y+above);
		Vector2i botLeft = Chunk.toChunkPosition(x-side, y-above);
		Vector2i botRight = Chunk.toChunkPosition(x+side, y-above);
		
		for (int cY = botLeft.y; cY <= topLeft.y; cY++) {
			for (int cX = botLeft.x; cX <= botRight.x; cX++) {
				if (getAt(cX, cY) == null) {
					add(new Chunk(cX, cY));
					System.out.println("Generating  X"+cX+" Y"+cY);
				}
			}
		}
	}
	
	public void generateFlatTiles(TileSprite tilSpr, int y) {
		for (Chunk chk : chunkMap.values()) {
			if (chk.getPosition().y < Chunk.toChunkPosition(y) && chk.isGenerating) {
				for (int x = chk.getPosition().x*8; x < chk.getPosition().x*8+8; x++) {
					for (int w = chk.getPosition().y*8; w < chk.getPosition().y*8+8; w++) {
						addTile(new Tile(TileType.Dirt, x, w));
					}
				}
				chk.isGenerating = false;
			} else if (chk.getPosition().y == Chunk.toChunkPosition(y) && chk.isGenerating) {
				for (int x = chk.getPosition().x*8; x < chk.getPosition().x*8+8; x++) {
					for (int w = chk.getPosition().y*8; w < y; w++) {
						addTile(new Tile(TileType.Dirt, x, w));
					}
				}
				chk.isGenerating = false;
			}
		}
	}
	
	public void generateWave(int y) {
		for (Chunk chk : chunkMap.values()) {
			if (chk.getPosition().y <= Chunk.toChunkPosition(y)+4 && chk.isGenerating) {
				for (int x = chk.getPosition().x*8; x < chk.getPosition().x*8+8; x++) {
					for (int w = chk.getPosition().y*8; w < chk.getPosition().y*8+8; w++) {
						if (w < (int) (Math.sin(Math.toRadians(x*4))*10 + y - 3)) {
							addTile(new Tile(TileType.Stone, x, w));
						} else if (w < (int) (Math.sin(Math.toRadians(x*4))*10 + y)) {
							addTile(new Tile(TileType.Dirt, x, w));
						} else if (w == (int) (Math.sin(Math.toRadians(x*4))*10 + y)) {
							addTile(new Tile(TileType.Grass, x, w));
						}
					}
				}
				chk.isGenerating = false;
			}
		}
	}
	
	
	//// UPDATER \\\\
	public void updateTiles() {
		// Store tiles to delete
		List<Tile> toRemove = new ArrayList<Tile>();
		for (Chunk chk : chunkMap.values()) {
			for (TileType typ : chk.getTypes()) {
				for (Tile til : chk.get(typ)) {
					if (!til.update(this)) {
						toRemove.add(til);
					}
				}
			}
		}
		// Delete stored tiles
		for (Tile til : toRemove) {
			removeTile(til.x, til.y);
		}
	}
	
}
