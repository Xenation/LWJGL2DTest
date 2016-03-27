package tiles;

import storage.ChunkMap;

public class SupportedTile extends Tile {
	
	public SupportedTile(TileType typ, int x, int y) {
		super(typ, x, y);
	}
	
	public boolean update(ChunkMap chkMap) {
		Tile botTile = chkMap.getTileAt(this.x, this.y - 1);
		if (botTile == null || !botTile.getType().isSolid()) {
			return false;
		}
		return true;
	}
	
}
