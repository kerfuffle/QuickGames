package net.kerfuffle.lanwar;

import static net.kerfuffle.Utilities.Util.*;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

public class ToolBar {
	
	Quad toolTiles[] = new Quad[8];
	
	Texture tex = loadTex("toolTile");
	Texture highlightTex = loadTex("toolTileHighlight");
	
	Player player;
	float originX, originY;

	public ToolBar(Player player)
	{
		this.player = player;
		
		originX = player.x - Main.WIDTH/2 + player.getW()/2;
		originY = player.y - Main.HEIGHT/2 + player.getH()/2;
		
		for (int i = 0; i < toolTiles.length; i++)
		{
			toolTiles[i] = new Quad(originX + 125 + ((i+1)*20), originY + 15, 75, 75, new RGB(1, 1, 1));
			toolTiles[i].tex = tex;
		}

	}
	
	private void updatePos()
	{
		originX = player.x - Main.WIDTH/2 + player.getW()/2;
		originY = player.y - Main.HEIGHT/2 + player.getH()/2;
		
		for (int i = 0; i < toolTiles.length; i++)
		{
			toolTiles[i].x = originX + 125 +(i+1)*(20 + toolTiles[i].w);
			toolTiles[i].y = originY + 15;
		}
	}
	
	public void draw()
	{
		updatePos();
		
		color(new RGB(0, 1, 0));
		lineH(originX, originY+100, Main.WIDTH);
		
		for (int i = 0; i < toolTiles.length; i++)
		{
			if (onHover(toolTiles[i].x, toolTiles[i].y, toolTiles[i].w, toolTiles[i].h, originX + Mouse.getX(), originY + Mouse.getY()))
			{
				toolTiles[i].tex = highlightTex;
			}
			else
			{
				toolTiles[i].tex = tex;
			}
			
			toolTiles[i].draw();
		}
	}
	
}
