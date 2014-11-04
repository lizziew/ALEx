import java.util.*; 
import java.util.ArrayList;
import java.io.*; 

public class Item {
	private int x;
	private int y; 
	private boolean isCarried; 

	private String color; 
	private String shape; 
	//private boolean edibility, heaviness; 	

	public static void main(String[] args) {

	}

	public String getColor()
	{
		return color;
	}
	public String getShape()
	{
		return shape;
	}	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public boolean setX(int a)
	{
		x = a;
		return true;
	} 
	public boolean setY(int b)
	{
		y = b;
		return true;
	}
	public boolean setCarried(boolean b)
	{
		isCarried = b;
		return true; 
	}
	public boolean equals(Item thing)
	{
		if (!color.equals(thing.getColor()))
			return false;
		if (!shape.equals(thing.getShape()))
			return false;
		return true;
	}

}
