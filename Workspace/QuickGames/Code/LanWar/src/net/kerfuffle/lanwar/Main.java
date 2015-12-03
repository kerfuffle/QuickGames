package net.kerfuffle.lanwar;

import static net.kerfuffle.Utilities.Util.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.input.Keyboard.*;

import java.util.ArrayList;

import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Main {

	static final int WIDTH = 1200, HEIGHT = 700;
	static final int boundsWIDTH = 5000, boundsHEIGHT = 5000;
	
	static ArrayList <Player> players = new ArrayList<Player>();
	static ArrayList <Zombie> zombies = new ArrayList<Zombie>();
	
	public static void main(String args[])
	{
		setDisplay(WIDTH, HEIGHT, "LanWar");
		
		DefaultMap map = new DefaultMap();
		
		Quad bounds = new Quad(0, 0, boundsWIDTH, boundsHEIGHT, new RGB(0, 0, 0));
		
		Player p = new Player (100, 100, 50, 50, randomColor());
		p.setPos(WIDTH/2-p.getW()/2, HEIGHT/2-p.getH()/2);
		p.speed = 5;
		p.cameraFollow = true;
		p.setKeys(KEY_W, KEY_S, KEY_A, KEY_D, KEY_SPACE);
		
		Quad plat = new Quad(100, 100, 500, 20, new RGB(1, 0, 0));
		
		map.init();
		map.addEntity(p);
		
		for (int i = 0; i < 2; i++)
		{
			map.addEntity(new Zombie(i*50, i*50, map));
		}
		
		
		while (!Display.isCloseRequested())
		{
			glClear(GL_COLOR_BUFFER_BIT);
			
			/*bounds.draw();
			enforceBoundaries();
			
			plat.draw();
			
			zomb.checkHits();
			zomb.move();
			zomb.draw();
			
			for (int i = 0; i < players.size(); i++)
			{
				players.get(i).draw();
				players.get(i).move();
				players.get(i).shoot();
				
				if (checkKey(KEY_P))
				{
					players.get(i).box.color = randomColor();
				}
			}*/
			
			
			
			if (checkKey(KEY_P))
			{
				map.refreshStars();
				Player pe = (Player) map.entities.get(0);
				p.tp(900, 404);
			}
			
			map.update();
			
			glColor3f(0, 1, 0);
			circle(50, 50, 100);
			
			updateAndSync(60);
		}
	}
	
	
	public static void enforceBoundaries()
	{
		for (int a = 0; a < players.size(); a++)
		{
			float x = players.get(a).x;
			float y = players.get(a).y;
			float w = players.get(a).getW();
			float h = players.get(a).getH();
			float speed = players.get(a).speed;
			
			if (x <= 0 && y <= 0)
			{
				glTranslatef(-speed, -speed, 0);
				players.get(a).setPos(x+speed, y+speed);
			}
			if (x >= 0 && y >= boundsHEIGHT)
			{
				glTranslatef(-speed, speed, 0);
				players.get(a).setPos(x+speed, y-speed);
			}
			if (x >= boundsWIDTH && y >= boundsHEIGHT)
			{
				
				players.get(a).setPos(x-speed, y-speed);
			}
			if (x >= boundsWIDTH && y <= 0)
			{
				players.get(a).setPos(x-speed, y+speed);
			}
			
			if (x <= 0)
			{
				glTranslatef(-speed, 0, 0);
				players.get(a).setPos(x+speed, y);
			}
			if (x+w >= boundsWIDTH)
			{
				glTranslatef(speed, 0, 0);
				players.get(a).setPos(x-speed, y);
			}
			if (y <= 0)
			{
				glTranslatef(0, -speed, 0);
				players.get(a).setPos(x, y+speed);
			}
			if (y+h >= boundsHEIGHT)
			{
				glTranslatef(0, speed, 0);
				players.get(a).setPos(x, y-speed);
			}
			System.out.format("%f, %f\n", x, y);
		}
	}
}
