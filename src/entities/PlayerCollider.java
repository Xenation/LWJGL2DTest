package entities;

import org.lwjgl.util.vector.Vector2f;

import storage.ChunkMap;
import tiles.Tile;

public class PlayerCollider extends Collider {
	
	private Player player;
	
	public PlayerCollider(Player player) {
		super(player.position);
		this.player = player;
	}
	
	public Vector2f collideSlide(Tile til, ChunkMap chkMap, float nx, float ny) {
		Vector2f slide = new Vector2f(1, 1);
		if (isColliding(til, nx, ny)) {
			float tilTop = til.y + Tile.TILE_SIZE;
			float tilBottom = til.y;
			float tilRight = til.x + Tile.TILE_SIZE;
			float tilLeft = til.x;
			
			float curTop = getWY() + h;
			float curBottom = getWY();
			float curRight = getWX() + w;
			float curLeft = getWX();
			
			if (curRight <= tilLeft) {
				// We are left of tile
				Tile left = chkMap.getLeftTile(til);
				if (left != null && left.getType().isSolid() && (curBottom >= tilTop || curTop <= tilBottom)) { // There is a left tile
					slide.y = 0;
				} else {
					Tile top = chkMap.getTopTile(til);
					if (!player.isInAir() && (top == null || !top.getType().isSolid()) && (curBottom >= tilBottom && curBottom <= tilTop)) { // There is no top tile
						this.entPos.y += Tile.TILE_SIZE + 0.01f; // Step Up
					} else {
						slide.x = 0;
					}
				}
			}
			if (curLeft >= tilRight) {
				// We are right of tile
				Tile right = chkMap.getRightTile(til);
				if (right != null && right.getType().isSolid() && (curBottom >= tilTop || curTop <= tilBottom)) { // There is a right tile
					slide.y = 0;
				} else {
					Tile top = chkMap.getTopTile(til);
					if (!player.isInAir() && (top == null || !top.getType().isSolid()) && (curBottom >= tilBottom && curBottom <= tilTop)) { // There is no top tile
						this.entPos.y += Tile.TILE_SIZE + 0.01f; // Step Up
					} else {
						slide.x = 0;
					}
				}
			}
			if (curBottom >= tilTop) {
				// We are top of tile
				Tile top = chkMap.getTopTile(til);
				if (top != null && top.getType().isSolid() && (curLeft >= tilRight || curRight <= tilLeft)) { // There is a top tile
					slide.x = 0;
				} else {
					slide.y = 0;
				}
			}
			if (curTop <= tilBottom) {
				// We are bottom of tile
				Tile bottom = chkMap.getBottomTile(til);
				if (bottom != null && bottom.getType().isSolid() && (curLeft >= tilRight || curRight <= tilLeft)) { // There is a bottom tile
					slide.x = 0;
				} else {
					slide.y = 0;
				}
			}
		}
		return slide;
	}
	
}
