package net.kerfuffle.lanwar;

public interface Entity {

	public int getId();
	
	public void setId(int id);
	
	public boolean isDead();
	
	public abstract void draw();
	
	public abstract void move();
	
	public abstract String toString();
	
}
