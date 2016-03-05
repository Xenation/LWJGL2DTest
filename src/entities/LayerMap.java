package entities;

import java.util.ArrayList;

public class LayerMap {
	
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	
	public void add(Layer lay) {
		this.layers.add(lay);
	}
	
	public void set(int index, Layer lay) {
		this.layers.set(index, lay);
	}
	
	public void moveUp(int index) {
		if (index > 0 && index < this.layers.size()) {
			Layer toMove = this.layers.get(index);
			this.layers.set(index, this.layers.get(index-1));
			this.layers.set(index-1, toMove);
		}
	}
	
	public void moveDown(int index) {
		if (index >= 0 && index < this.layers.size()-1) {
			Layer toMove = this.layers.get(index);
			this.layers.set(index, this.layers.get(index+1));
			this.layers.set(index+1, toMove);
		}
	}
	
	public ArrayList<Layer> getLayersList() {
		return this.layers;
	}
	
	public int getLayersCount() {
		return this.layers.size();
	}
	
	public Layer getLayer(int index) {
		return this.layers.get(index);
	}
	
}
