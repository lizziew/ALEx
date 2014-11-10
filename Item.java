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

	public Item(int a, int b, int newcolor, int newshape){
		x=a;
		y=b;
		isCarried = false;
		
		if (newcolor == 0){
			color = "red";
		}else if(newcolor == 1){
			color = "orange";
		}else if(newcolor == 2){
			color = "yellow";
		}else if(newcolor == 3){
			color = "green";
		}else if(newcolor == 4){
			color = "blue";
		}else if(newcolor == 5){
			color = "lightblue";
		}else if(newcolor == 6){
			color = "purple";
		}else if(newcolor == 7){
			color = "pink";
		}else if(newcolor == 8){
			color = "brown";
		}else if(newcolor == 9){
			color = "gray";
		}else if(newcolor == 10){
			color = "black";
		}
		
		if(newshape == 0){
			shape = "circle";
		}else if(newshape == 1){
			shape = "moon";
		}else if(newshape == 2){
			shape = "square";
		}else if(newshape == 3){
			shape = "star";
		}else if(newshape == 4){
			shape = "triangle";
		}
	}
	
	public Item(int a, int b, String c, String s)
	{
		x = a;
		y = b;
		isCarried = false;
		color = c;
		shape = s;
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
