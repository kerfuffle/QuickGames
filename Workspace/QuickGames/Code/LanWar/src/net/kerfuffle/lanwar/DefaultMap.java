package net.kerfuffle.lanwar;

import java.util.ArrayList;

import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

import static net.kerfuffle.Utilities.Util.*;

public class DefaultMap implements Map{

	class Star
	{
		float disX, disY;
		public Star(float disX, float disY)
		{
			this.disX = disX;
			this.disY = disY;
		}
	}
	
	float originX, originY = 0, width = 5000, height = 5000;
	RGB backgroundColor = new RGB(0, 0, 0);
	Quad background = new Quad(originX, originY, width, height, backgroundColor);
	
	ArrayList <Entity> entities = new ArrayList<Entity>();
	
	Star stars[][] = new Star[(int) width][(int) height];
	private float starSpacing = 100;
	
	private void configureStars()
	{
		for (int i = 0; i < width/starSpacing; i++)
		{
			for (int j = 0; j < height/starSpacing; j++)
			{
				float disX = (float) (Math.random()*100);
				float disY = (float) (Math.random()*100);
				stars[i][j] = new Star(disX, disY);
			}
		}
		System.out.println("done");
	}
	
	private void drawStars()
	{
		color(new RGB(1, 1, 1));
		for (int i = 0; i < width/starSpacing; i++)
		{
			for (int j = 0; j < height/starSpacing; j++)
			{
				point((i+starSpacing)*stars[i][j].disX, (j+starSpacing)*stars[i][j].disY);
			}
		}
	}
	
	public void init()
	{
		configureStars();
	}
	
	public void refreshStars()
	{
		configureStars();
	}
	
	private void draw() 
	{
		background.draw();
		drawStars();
		
		for (Entity e : entities)
		{
			e.draw();
		}
	}
	
	public void update()
	{
		ArrayList <Integer> removables = new ArrayList<Integer>();
		for (Entity e : entities)
		{
			e.move();
			
			if (e.isDead())
			{
				requestRemove(e.getId());
				//removeEntity(e.getId());
				break;
			}
		}
		
		for (int i = 0 ; i < removables.size(); i++)
		{
			//removeEntity(removables.get(i));
		}
		removables.clear();
		
		draw();
	}
	
	public void requestRemove(int id)
	{
		
	}
	
	public void addEntity(Entity e) 
	{
		e.setId(entities.size());
		entities.add(e);
	}

	public void removeEntity(int i)
	{
		entities.remove(i);
	}
	
	public ArrayList<Entity> getEntities()
	{
		return entities;
	}

	public String toString()
	{
		return null;
	}
}
