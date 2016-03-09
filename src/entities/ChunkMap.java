package entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import toolbox.Vector2i;

public class ChunkMap {
	
	private Map<Vector2i, Chunk> chunkMap = new HashMap<Vector2i, Chunk>();
	
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
	
	public void remove(Chunk chk) {
		chunkMap.remove(chk.getPosition(), chk);
	}
	
	public void removeAt(Vector2i pos) {
		chunkMap.remove(pos);
	}
	
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
	
}
