package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;

public class Layer {
	
	private Map<Sprite, List<Entity>> layerMap = new HashMap<Sprite, List<Entity>>();
	
	public void add(Entity ent) {
		if (layerMap.containsKey(ent.getSprite())) {
			layerMap.get(ent.sprite).add(ent);
		} else {
			List<Entity> newList = new ArrayList<Entity>();
			newList.add(ent);
			layerMap.put(ent.sprite, newList);
		}
	}
	
	public void addAll(List<Entity> ents) {
		for (Entity ent : ents) {
			add(ent);
		}
	}
	
	public void remove(Entity ent) {
		if (layerMap.containsKey(ent.sprite)) {
			layerMap.get(ent.sprite).remove(ent);
		}
	}
	
	public void removeSprite(Sprite spr) {
		layerMap.remove(spr);
	}
	
	public Map<Sprite, List<Entity>> getMap() {
		return this.layerMap;
	}
	
	public List<Entity> get(Sprite spr) {
		return this.layerMap.get(spr);
	}
	
}
