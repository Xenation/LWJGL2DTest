package engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import entities.*;
import models.*;
import render.*;
import storage.ChunkMap;
import storage.Layer;
import storage.LayerMap;

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
		player.getCollider().increaseHeight(-0.001f);
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
		
		TileSprite tilSpr = new TileSprite(loader, "tile_dirt");
		camera.setTile(tilSpr);
		
		while (!Display.isCloseRequested()) {
			// Game Loop
			player.move(layerMap.getLayer(0), chkMap);
			camera.move(chkMap);
			
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
