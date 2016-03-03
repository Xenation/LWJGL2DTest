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
		EntityRenderer renderer = new EntityRenderer();
		
		Camera camera = new Camera(new Vector2f(0.5f, 0), 0);
		
		Entity ent = new Entity(new Sprite(loader, "test"), new Vector2f(0, 0), new Vector2f(1, 1), 0);
		
		Player player = new Player(new Sprite(loader, new Vector2f(0.2f, 0.4f), "perso3.2d"), new Vector2f(0, 0), new Vector2f(1, 1), 0);
		
		while (!Display.isCloseRequested()) {
			// Game Loop
			player.move();
			player.increaseRotation(0.1f);
			
			renderer.render(ent, camera);
			renderer.render(player, camera);
			
			DisplayManager.updateDisplay();
		}
		
		// Clean Up
		renderer.cleanUp();
		
		DisplayManager.closeDisplay();
		
	}
	
}
