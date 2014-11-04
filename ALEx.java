import java.util.*; 
import java.util.ArrayList;
import java.io.*; 

public class ALEx{
	private int x;
	private int y; 

	private ArrayList<Item> items = new ArrayList<Item>();

	public boolean moveTo(int x, int y) { //move ALEx to position x, y 
		this.x = x;
		this.y = y; 

		for(int i = 0; i < items.size(); i++) {
			items.get(i).setX(this.x);
			items.get(i).setY(this.y); 
		}
		return true; 
	}

	public boolean pickUp(Item item) { //pick up item in environment 
		return true; 
	}

	public boolean putDown(Item item) { //put down item in environment 
		int i = hasItem(item); 
		if(i != -1) {
			items.get(i).setCarried(false); 
			items.remove(i); 
			return true; 
		}

		return false; 
	}

	public boolean lookForItem(Item item) { //look for an item in environment 
		return true; 
	}

	public int hasItem(Item item) { //check if ALEx is carrying an item
		for(int i = 0; i < items.size(); i++) 
			if(items.get(i).equals(item)) 
				return i; 
			
		return -1; 
	}

	public static void main(String[] args) {

	}
}