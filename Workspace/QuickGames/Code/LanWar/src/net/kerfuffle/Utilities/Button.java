package net.kerfuffle.Utilities;

import static net.kerfuffle.Utilities.Util.*;

public class Button{

	public Quad box;
	private String text;
	RGB hoverColor = new RGB(1, 0, 0);
	
	public Button(Quad box)
	{
		this.box = box;
	}
	
	public void setBox(Quad box)
	{
		this.box = box;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public void setHoverColor(RGB hoverColor)
	{
		this.hoverColor = hoverColor;
	}
	
	public RGB getHoverColor()
	{
		return hoverColor;
	}
	public String getText()
	{
		return text;
	}
	public Quad getBox()
	{
		return box;
	}
	
	public void setClickListener(ButtonListener b)
	{
		if (onClick(box.x, box.y, box.w, box.h))
		{
			b.ifClick();
		}
	}
	
}
