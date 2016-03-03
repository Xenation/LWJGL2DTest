package models;

import org.lwjgl.util.vector.Vector2f;

import render.Loader;

public class Sprite {
	
	private int texture;
	protected final Vector2f size;
	protected RawModel model;
	
	public Sprite(Loader loader, String fileName) {
		this.size = new Vector2f(0.1f, 0.1f);
		this.texture = loader.loadTexture(fileName);
		generateModel(loader);
	}
	
	public Sprite(Loader loader, Vector2f size, String fileName) {
		this.size = size;
		this.texture = loader.loadTexture(fileName);
		generateModel(loader);
	}
	
	private void generateModel(Loader loader) {
		float pos[] = {-this.size.x/2, this.size.y/2,
				this.size.x/2, this.size.y/2,
				-this.size.x/2, -this.size.y/2,
				this.size.x/2, -this.size.y/2};
		float tex[] = {0,0, 1,0, 0,1, 1,1};
		this.model = loader.loadToVAO(pos, tex);
	}
	
	public int getTexture() {
		return texture;
	}
	
	public RawModel getModel() {
		return this.model;
	}
	
	public Vector2f getSize() {
		return size;
	}
	
}
