package models;

import org.lwjgl.util.vector.Vector2f;

import render.Loader;

public class Quad {
	
	protected final Vector2f size;
	protected RawModel model;
	
	public Quad(Loader loader) {
		this.size = new Vector2f(0.1f, 0.1f);
		generateModel(loader);
	}
	
	public Quad(Loader loader, Vector2f size) {
		this.size = size;
		generateModel(loader);
	}
	
	private void generateModel(Loader loader) {
		float pos[] = {0, this.size.y,
				this.size.x, this.size.y,
				0, 0,
				this.size.x, 0};
		this.model = loader.loadToVAO(pos);
	}
	
	public RawModel getModel() {
		return this.model;
	}
	
	public Vector2f getSize() {
		return size;
	}
	
}
