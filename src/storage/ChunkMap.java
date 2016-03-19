package storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Tile;
import models.TileSprite;

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
		Chunk chk = getAt((int) Math.floor(til.x/8f), (int) Math.floor(til.y/8f));
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
		Chunk chk = getAt((int) Math.floor(x/8f), (int) Math.floor(y/8f));
		if (chk != null)
			chk.removeAt(x, y);
	}
	
	//// GETTERS \\\\
	public Map<Vector2i, Chunk> getMap() {
		return chunkMap;
	}
	
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
	
	public Tile getLeftTile(Tile tile) {
		Chunk chk = getAt((int) Math.floor(tile.x/8f), (int) Math.floor(tile.y/8f));
		for (TileSprite spr : chunkMap.get(chk.getPosition()).getMap().keySet()) {
			for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(spr)) {
				if (tile.x == til.x - 1) {
					return til;
				}
			}
		}
		return null;
	}
	
	public Tile getRightTile(Tile tile) {
		Chunk chk = getAt((int) Math.floor(tile.x/8f), (int) Math.floor(tile.y/8f));
		for (TileSprite spr : chunkMap.get(chk.getPosition()).getMap().keySet()) {
			for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(spr)) {
				if (tile.x == til.x + 1) {
					return til;
				}
			}
		}
		return null;
	}
	
	public Tile getTopTile(Tile tile) {
		Chunk chk = getAt((int) Math.floor(tile.x/8f), (int) Math.floor(tile.y/8f));
		for (TileSprite spr : chunkMap.get(chk.getPosition()).getMap().keySet()) {
			for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(spr)) {
				if (tile.y == til.y + 1) {
					return til;
				}
			}
		}
		return null;
	}
	
	public Tile getBottomTile(Tile tile) {
		Chunk chk = getAt((int) Math.floor(tile.x/8f), (int) Math.floor(tile.y/8f));
		for (TileSprite spr : chunkMap.get(chk.getPosition()).getMap().keySet()) {
			for (Tile til : chunkMap.get(chk.getPosition()).getMap().get(spr)) {
				if (tile.y == til.y - 1) {
					return til;
				}
			}
		}
		return null;
	}
	
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
	
	//// GENERATORS \\\\
	public void initializeChunks(int x, int y, int above, int side) {
		Vector2i topLeft = Chunk.toChunkPosition(x-side, y+above);
		Vector2i botLeft = Chunk.toChunkPosition(x-side, y-above);
		Vector2i botRight = Chunk.toChunkPosition(x+side, y-above);
		
		for (int cY = botLeft.y; cY <= topLeft.y; cY++) {
			for (int cX = botLeft.x; cX <= botRight.x; cX++) {
				if (getAt(cX, cY) == null) {
					add(new Chunk(cX, cY));
					System.out.println("Generating X"+cX+"  Y"+cY);
				}
			}
		}
	}
	
	public void generateFlatTiles(TileSprite tilSpr, int y) {
		for (Chunk chk : chunkMap.values()) {
			if (chk.getPosition().y < Chunk.toChunkPosition(y) && chk.isGenerating) {
				for (int x = chk.getPosition().x*8; x < chk.getPosition().x*8+8; x++) {
					for (int w = chk.getPosition().y*8; w < chk.getPosition().y*8+8; w++) {
						addTile(new Tile(tilSpr, x, w));
					}
				}
				chk.isGenerating = false;
			} else if (chk.getPosition().y == Chunk.toChunkPosition(y) && chk.isGenerating) {
				for (int x = chk.getPosition().x*8; x < chk.getPosition().x*8+8; x++) {
					for (int w = chk.getPosition().y*8; w < y; w++) {
						addTile(new Tile(tilSpr, x, w));
					}
				}
				chk.isGenerating = false;
			}
		}
	}
	
}
