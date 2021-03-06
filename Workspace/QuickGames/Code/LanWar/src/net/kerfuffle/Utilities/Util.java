package net.kerfuffle.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class Util {
	
	/**
	 * Example
	 * Gets console input and returns it as a string.
	 * 
	 * String in = conIn();		<- Stores the input from the console in a string
	 */
	public static String conIn()
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			return in.readLine();
		}
		catch (IOException e)
		{
			return "[ERROR] Could not read line.\n";
		}
	}
	
	/**
	 * Example
	 * This converts an array of strings into a vector of strings.
	 * 
	 * String blurb[] = {'H', 'I'};
	 * Vector <String> vecBlurb = stringArrayToVec(blurb);
	 * 
	 */
	public static Vector<String> stringArrayToVec(String str[])
	{
		Vector<String> vec = new Vector <String>();
		for (int i = 0; i < str.length; i++)
		{
			vec.add(str[i]);
		}
		return vec;
	}
	
	/**
	 * Example
	 * This converts a vector of strings into an array of strings.
	 * 
	 * Vector <String> blurb = new Vector<String>();
	 * blurb.add("poop");
	 * blurb.add("poopagain");
	 * 
	 * String arrayBlurb[] = vecToStringArray(blurb);
	 */
	public static String[] vecToStringArray(Vector <String> vec)
	{
		String str[] = new String[vec.size()];
		for (int i = 0; i < vec.size(); i++)
		{
			str[i] = vec.get(i);
		}
		return str;
	}
	
	/**
	 * Example
	 * This converts a vector of chars into a string.
	 * 
	 * Vector <Char> blurb = new Vector<Char>();
	 * blurb.add('h');
	 * blurb.add('i');
	 * 
	 * String strBlurb = vecToString(blurb);
	 */
	public static String vecToString(Vector vec)
	{
		String out = null;
		for (int i = 0; i < vec.size(); i++)
		{
			out += vec.get(i);
		}
		return out;
	}
	
	/**
	 * Example
	 * This is a function to output a string into a file.
	 * 
	 * outFile("Hello TextFile!", "out.txt");
	 */
	public static void outFile(String str, String name)
	{	
		PrintWriter out = null;
		try
		{
			out = new PrintWriter (new FileWriter(name));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		for (int i = 0; i < str.length(); i++)
		{
			if (str.charAt(i) == '\n')
			{
				out.println();
			}
			else
			{
				out.print(str.charAt(i));
			}
		}
		out.close();
	}
	
	/**
	 * Example
	 * This is a function that reads a text file and exports it as a string.
	 * 
	 * String fileData = inFile("in.txt");
	 */
	public static String inFile(String name)
	{
		Vector<Character> c = new Vector<Character>();
		try
		{
			BufferedReader in = new BufferedReader(new FileReader(name));
			int r;
			while((r = in.read()) != -1)
			{
				c.add((char) r);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		String ret = vecToString(c);
		String fix[] = ret.split("null");
		
		return fix[1];
	}
	
	
	/****************************************************************************************************************************************
	 * OPENGL STUFF BELOW
	 */
	
	static boolean keyStates[] = new boolean [256];
	static boolean mouseStates[] = new boolean [2];
	
	public static void initGL()
	{
		glMatrixMode(GL_PROJECTION);

		glLoadIdentity();		
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);
		//glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glEnable(GL_ALPHA);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glClearColor(1, 1, 1, 1);

		glDisable(GL_DEPTH_TEST);
	}
	
	public static void setDisplay(int width, int height, String name)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode (width, height));
			Display.setTitle(name);
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}
		initGL();
	}
	
	public static void updateAndSync(int fps)
	{
		Display.update();
		Display.sync(fps);
	}
	
	public static void point(float x, float y)
	{
		glBegin(GL_POINTS);
		glVertex2f(x, y);
		glEnd();
	}
	
	public static void hollowCircle(float x, float y, float r)
	{
		glBegin(GL_LINE_LOOP);
		for (int i = 0; i < 100; i++)
		{
			float angle = (float) (2*Math.PI*i/100);
			float px = (float) ((r * Math.cos(angle)));
			float py = (float) ((r * Math.sin(angle)));
			glVertex2f(x + px, y + py);
		}
		glEnd();
	}
	
	public static void circle(float x, float y, float r)
	{
		glBegin(GL_TRIANGLE_FAN);
		glVertex2f(x, y);
		for (float i = 1; i < 361; i += 0.2)
		{
			float x2 = (float) (x+Math.cos(i)*r);
			float y2 = (float) (y+Math.sin(i)*r);
			glVertex2f(x2, y2);
		}
		glEnd();
	}
	
	
	
	public static boolean hit(Quad pow, Quad p)
	{
		if ((p.x > pow.x && p.x < pow.x + pow.w) || (p.x + p.w > pow.x && p.x + p.w < pow.x + pow.w))	//check X
		{
			if ((p.y > pow.y && p.y < pow.y + pow.h) || (p.y + p.h > pow.y && p.y + p.h < pow.y + pow.h))	//check Y
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean hitRight(Quad p, Quad b)
	{
		if (b.x <= p.x+p.w && b.x >= p.x &&(b.y <= p.y+p.h && b.y+b.h >= p.y))
		{
			return true;
		}
		return false;
	}
	public static boolean hitLeft(Quad p, Quad b)
	{
		if (b.x+b.w >= p.x && b.x+b.w <= p.x &&(b.y <= p.y+p.h && b.y+b.h >= p.y))
		{
			return true;
		}
		return false;
	}
	public static boolean hitUp(Quad p, Quad b)
	{
		if (b.y+b.h >= p.y && b.y+b.h <= p.y && (b.x <= p.x+p.w && b.x >= p.x))
		{
			return true;
		}
		return false;
	}
	public static boolean hitDown(Quad p, Quad b)
	{
		if (b.y <= p.y+p.h && b.y >= p.y &&((b.x <= p.x+p.w && b.x >= p.x)))
		{
			return true;
		}
		return false;
	}
	
	public static RGB randomColor()
	{
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new RGB(r, g, b);
	}
	
	public static void color(RGB color)
	{
		glColor3f(color.R(), color.G(), color.B());
	}
	
	public static void lineH(float x, float y, float w)
	{
		glBegin(GL_LINES);
		glVertex2f(x, y);
		glVertex2f(x + w, y);
		glEnd();
	}
	
	public static void quad(float x, float y, float w, float h)
	{
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x, y + h);
		glVertex2f(x + w, y + h);
		glVertex2f(x + w, y);
		glEnd();
	}
	public static void quad(Coord c, Size s)
	{
		quad(c.getX(), c.getY(), s.getWidth(), s.getHeight());
	}
	
	public static void quadTex(float x, float y, float w, float h, Texture tex)
	{
		tex.bind();
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2f(1, 1);		
		glVertex2f(x, y);
		glTexCoord2f(1, 0); 	
		glVertex2f(x, y + h);
		glTexCoord2f(0, 0);		
		glVertex2f(x + w, y + h);
		glTexCoord2f(0, 1);		
		glVertex2f(x + w, y);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		//tex.release();
	}
	
	public static void shiftX(int x)
	{
		glTranslatef(x, 0, 0);
	}
	
	public static void shiftY (int y)
	{
		glTranslatef(0, y, 0);
	}
	
	public static boolean checkKey(int i)
	{
		if (Keyboard.isKeyDown(i) != keyStates[i])
		{
			return keyStates[i] = !keyStates[i];
		}
		else
		{
			return false;
		}
	}
	
	public static boolean checkMouse(int i)
	{
		if (Mouse.isButtonDown(i) != mouseStates[i])
		{
			return mouseStates[i] = !mouseStates[i];
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onHover(float x, float y, float w, float h, float mx, float my)
	{
		if ((mx >= x && mx <= x + w) && (my >= y && my <= y + h))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onRClick(int x, int y, int w, int h)
	{
		if ((Mouse.getX() >= x && Mouse.getX() <= x + w) && (Mouse.getY() >= y && Mouse.getY() <= y + h) && checkMouse(1))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onClickandDrag(int x, int y, int w, int h)
	{
		if ((Mouse.getX() >= x && Mouse.getX() <= x + w) && (Mouse.getY() >= y && Mouse.getY() <= y + h) && Mouse.isButtonDown(0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean onClick(float x, float y, float w, float h)
	{
		if ((Mouse.getX() >= x && Mouse.getX() <= x + w) && (Mouse.getY() >= y && Mouse.getY() <= y + h) && checkMouse(0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean keyDown(int key)
	{
		if (Keyboard.isKeyDown(key))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static Texture loadTex(String name)
	{
		try
		{
			return TextureLoader.getTexture("PNG", new FileInputStream(new File(name + ".png")));
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("File not found!");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
