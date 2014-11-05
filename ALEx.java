import java.util.*; 
import java.lang.*;
import java.io.*; 

public class ALEx{
	private int x;
	private int y; 
	private Environment world; 

	private ArrayList<Item> items = new ArrayList<Item>();

	public boolean moveTo(int x, int y) { //move ALEx to position x, y 
		int dx = -1; 
		if(this.x < x) dx = 1; 

		for(int i = 0; i < Math.abs(this.x-x); i++) {
			this.x += dx; 
			try {
			    Thread.sleep(1000);                
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			for(int j = 0; j < items.size(); j++) 
				items.get(j).setX(this.x);
		}

		int dy = -1; 
		if(this.y < y) dy = 1; 

		for(int i = 0; i < Math.abs(this.y-y); i++) {
			this.y += dy; 
			try {
			    Thread.sleep(1000);                 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			for(int j = 0; j < items.size(); j++) 
				items.get(j).setY(this.y);
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

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y; 
	}

	public Environment getEnviron() {
		return this.world; 
	}

	public static void main(String[] args) {

	}
}