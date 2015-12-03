package net.kerfuffle.Utilities;

import java.util.ArrayList;
import static net.kerfuffle.Utilities.Util.*;

public class DropMenu{

	class Block
	{
		Coord c;
		Size s;
		String str;
	}
	
	private Coord c;
	private Size s;
	private String name;
	private RGB color = new RGB(1, 1, 1);
	private RGB hoverColor = new RGB(1, 0, 0);
	private boolean expand = false;
	
	ArrayList <Block> blocks = new ArrayList<Block>();
	
	
	public DropMenu(String newBlocks[], Coord c, Size s, String name)
	{
		this.c = c;
		this.s = s;
		this.name = name;
		
		for (int i = 0; i < newBlocks.length; i++)
		{
			Block b = new Block();
			b.c = new Coord(c.getX(), c.getY() - ((i+1)*s.getHeight()));
			b.s = s;
			blocks.add(b);
		}
	}
	
	
	public void draw()
	{
		color(color);
		quad(c, s);
		if (expand)
		{
			for (int i = 0; i < blocks.size(); i++)
			{
				color(color);
				quad(blocks.get(i).c, blocks.get(i).s);
				color(new RGB(0, 0, 0));
				lineH(blocks.get(i).c.getX(), blocks.get(i).c.getY() + blocks.get(i).s.getHeight(), blocks.get(i).s.getWidth());
			}
		}
	}
	
	public void checkClick()
	{
		if (onClick(c.getX(), c.getY(), s.getWidth(), s.getHeight()))
		{
			expand = !expand;
		}
	}
	
	//public void addBlock()
	
	public void setColor(RGB color)
	{
		this.color = color;
	}
	public void setHoverColor(RGB hoverColor)
	{
		this.hoverColor = hoverColor;
	}
	
	public RGB getColor()
	{
		return color;
	}
	public RGB getHoverColor()
	{
		return hoverColor;
	}


	public void setClickListener(int i, ButtonListener b)
	{
		if (expand)
		{
			if (onClick(blocks.get(i).c.getX(), blocks.get(i).c.getY(), blocks.get(i).s.getWidth(), blocks.get(i).s.getHeight()))
			{
				b.ifClick();
			}
		}
	}


}
