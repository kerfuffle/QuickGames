package net.kerfuffle.Utilities;

import org.newdawn.slick.opengl.Texture;
import static net.kerfuffle.Utilities.Util.*;

public class Quad {

	public float x, y, w, h;
	public RGB color;
	public Texture tex;
	
	public Quad(float x, float y, float w, float h, RGB color)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
	}
	
	
	public void draw()
	{
		color(color);
		if (tex != null)
		{
			quadTex(x, y, w, h, tex);
		}
		else
		{
			quad(x, y, w, h);
		}
	}
}
