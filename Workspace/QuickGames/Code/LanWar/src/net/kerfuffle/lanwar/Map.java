package net.kerfuffle.lanwar;

import java.util.ArrayList;

public interface Map {
	
	float originX = 0, originY = 0;
	
	public void init();
	
	public void update();
	
	public void addEntity(Entity e);
	
	public void removeEntity(int i);
	
	public ArrayList<Entity> getEntities();
	
	public String toString();
	
}
