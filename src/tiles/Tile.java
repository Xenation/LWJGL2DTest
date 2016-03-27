package tiles;

import storage.ChunkMap;

public class Tile implements TileUpdater {
	
	public static final float TILE_SIZE = 1;
	
	protected TileType type;
	public final int x;
	public final int y;
	
	public Tile() {
		this.type = TileType.Dirt;
		this.x = 0;
		this.y = 0;
	}
	public Tile(TileType typ, int x, int y) {
		this.type = typ;
		this.x = x;
		this.y = y;
	}
	
	public TileType getType() {
		return type;
	}
	
	public boolean update(ChunkMap chkMap) {
		return true;
	}
	
}
