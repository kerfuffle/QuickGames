package net.kerfuffle.lanwar;

import static org.lwjgl.opengl.GL11.*;
import static net.kerfuffle.Utilities.Util.*;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import net.kerfuffle.Utilities.Quad;
import net.kerfuffle.Utilities.RGB;

public class Player implements Entity{

	int id;
	
	float x, y;
	private float lastX, lastY;
	float speed, bulletSpeed = 2;
	Quad box;
	ArrayList <Quad> bullets = new ArrayList<Quad>();
	boolean cameraFollow = false;
	public boolean isDead = false;
	enum Orientation {UP, DOWN, LEFT, RIGHT};
	Orientation orientation;
	ToolBar tools;
	
	private int up, down, left, right, shoot;
	
	public Player(float x, float y, float w, float h, RGB color)
	{
		this.x = x;
		this.y = y;
		box = new Quad(x, y, w, h, color);
		tools = new ToolBar(this);
	}
	
	
	public void draw()
	{
		box.draw();
		tools.draw();
	}
	
	private boolean focusCamera(float x, float y)
	{
		if (lastX < x)
		{
			lastX ++;
			glTranslatef(-1, 0 ,0);
		}
		if (lastX > x)
		{
			lastX --;
			glTranslatef(1, 0, 0);
		}
		if (lastY < y)
		{
			lastY ++;
			glTranslatef(0, -1, 0);
		}
		if (lastY > y)
		{
			lastY --;
			glTranslatef(0, 1, 0);
		}
		if (lastX == x && lastY == y)
		{
			System.out.println("gucc");
			return true;
		}
		return false;
	}
	
	public void tp(float x, float y)
	{
		lastX = this.x;
		lastY = this.y;
		setPos(x, y);
		while(!focusCamera(x, y));
	}
	
	public void shoot()
	{
		for (int i = 0; i < bullets.size(); i++)
		{
			if (orientation == Orientation.UP)
			{
				bullets.get(i).y += bulletSpeed;
			}
			if (orientation == Orientation.DOWN)
			{
				bullets.get(i).y -= bulletSpeed;
			}
			if (orientation == Orientation.LEFT)
			{
				bullets.get(i).x -= bulletSpeed;
			}
			if (orientation == Orientation.RIGHT)
			{
				bullets.get(i).x += bulletSpeed;
			}
			
			if (hit(box, bullets.get(i)))
			{
				bullets.remove(i);
				continue;
			}
			
			bullets.get(i).draw();
		}
	}
	
	
	public void move()
	{
		if (Keyboard.isKeyDown(up))
		{
			if (cameraFollow)
			{
				glTranslatef(0, -speed, 0);
			}
			setPos(x, y+speed);
			orientation = Orientation.UP;
		}
		if (Keyboard.isKeyDown(down))
		{
			if (cameraFollow)
			{
				glTranslatef(0, speed, 0);
			}
			setPos(x, y-speed);
			orientation = Orientation.DOWN;
		}
		if (Keyboard.isKeyDown(left))
		{
			if (cameraFollow)
			{
				glTranslatef(speed, 0, 0);
			}
			setPos(x-speed, y);
			orientation = Orientation.LEFT;
		}
		if (Keyboard.isKeyDown(right))
		{
			if (cameraFollow)
			{
				glTranslatef(-speed, 0, 0);
			}
			setPos(x+speed, y);
			orientation = Orientation.RIGHT;
		}
		if (checkKey(shoot))
		{
			float bx = -1, by = -1;
			if (orientation == Orientation.UP)
			{
				bx = (x+(box.w/2));
				by = y + box.h + 80;
			}
			if (orientation == Orientation.DOWN)
			{
				bx = (x+(box.w/2));
				by = y - 80;
			}
			if (orientation == Orientation.LEFT)
			{
				bx = x - 80;
				by = (y+(box.h/2));
			}
			if (orientation == Orientation.RIGHT)
			{
				bx = x + box.w + 80;
				by = (y+(box.h/2));
			}
			
			Quad bullet = new Quad(bx, by, 10, 10, randomColor());
			bullets.add(bullet);
		}
		
	}
	
	public boolean isDead()
	{
		return isDead;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setPos(float x, float y)
	{
		this.x = x;
		box.x = x;
		this.y = y;
		box.y = y;
	}
	public void setKeys(int up, int down, int left, int right, int shoot)
	{
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.shoot = shoot;
	}
	public float getW()
	{
		return box.w;
	}
	public float getH()
	{
		return box.h;
	}
	
	public String toString()
	{
		return null;
	}
	
}
