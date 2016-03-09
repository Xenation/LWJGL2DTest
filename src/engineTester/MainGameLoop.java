package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import entities.*;
import models.*;
import render.*;

public class MainGameLoop {
	
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		Camera camera = new Camera(new Vector2f(0.5f, 0), 0);
		
		LayerMap layerMap = new LayerMap();
		layerMap.add(new Layer());
		layerMap.add(new Layer());
		
		Entity ent = new Entity(new Sprite(loader, new Vector2f(2, 2), "test"), new Vector2f(2, 0), 0);
		ent.setCollider(new Collider(ent));
		ent.getCollider().fitSprite();
		
		Entity ent1 = new Entity(ent.getSprite(), new Vector2f(-2, 0), 0);
		ent1.setCollider(new Collider(ent1));
		ent1.getCollider().fitSprite();
		
		Player player = new Player(new Sprite(loader, new Vector2f(1f, 2f), "player"), new Vector2f(0, 0), new Vector2f(1, 1), 0);
		player.setCollider(new Collider(player));;
		player.getCollider().fitSprite();
		player.getCollider().increaseWidthCentered(-0.05f);
		player.getCollider().increaseHeight(-0.05f);
		camera.setFollow(player);
		
		Entity bck = new Entity(new Sprite(loader, new Vector2f(20, 20), "background"), new Vector2f(0, 0), 0, -2);
		
		Entity grd = new Entity(new Sprite(loader, new Vector2f(4, 1), "ground"), new Vector2f(0, -4), 0);
		grd.setCollider(new Collider(grd));
		grd.getCollider().fitSprite();
		
		layerMap.getLayer(0).add(ent);
		layerMap.getLayer(0).add(ent1);
		layerMap.getLayer(0).add(grd);
		layerMap.getLayer(0).add(player);
		layerMap.getLayer(1).add(bck);
		
		ChunkMap chkMap = new ChunkMap();
		chkMap.add(new Chunk());
		chkMap.add(new Chunk(-1, 0));
		chkMap.add(new Chunk(0, -1));
		chkMap.add(new Chunk(-1, -1));
		
		TileSprite tilSpr = new TileSprite(loader, "test");
		chkMap.getAt(0, 0).add(new Tile(tilSpr, 5, 5));
		chkMap.getAt(0, 0).add(new Tile(tilSpr, 5, 4));
		chkMap.getAt(0, 0).add(new Tile(tilSpr, 4, 4));
		
		chkMap.getAt(0, -1).add(new Tile(tilSpr, 0, -4));
		chkMap.getAt(0, -1).add(new Tile(tilSpr, 1, -4));
		chkMap.getAt(0, -1).add(new Tile(tilSpr, 2, -4));
		
		chkMap.getAt(-1, -1).add(new Tile(tilSpr, -1, -4));
		chkMap.getAt(-1, -1).add(new Tile(tilSpr, -2, -4));
		chkMap.getAt(-1, -1).add(new Tile(tilSpr, -3, -4));
		
		while (!Display.isCloseRequested()) {
			// Game Loop
			player.move(layerMap.getLayer(0), chkMap);
			camera.move();
			
			renderer.prepare();
			renderer.renderLayers(layerMap, camera);
			renderer.renderChunks(chkMap, camera);
			
			DisplayManager.updateDisplay();
		}
		
		// Clean Up
		renderer.cleanUp();
		
		DisplayManager.closeDisplay();
		
	}
	
}
