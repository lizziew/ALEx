import java.util.*; 
import java.lang.*;
import java.io.*; 

public class ALEx{
	private int x;
	private int y; 
	private Environment world; 
	private int dimension;

	private ArrayList<Item> items = new ArrayList<Item>();

	private String fuzzy_verbs[] = {"move"}; 
	private String move_verbs[] = {"walk", "go", "find"}; 
	private String pickup_verbs[] = {"get", "fetch", "pick up", "carry", "transport"};
	private String putdown_verbs[] = {"put down", "drop", "set down"}; 

	private String colors[] = {"red", "orange", "yellow", "green", "blue", "lightblue", "purple", "pink", "brown", "gray", "black"}; 
	private String shapes[] = {"moon", "crescent", "circle", "square", "star", "triangle"};

	public ALEx (int dim) {
		x = 0;
		y = 0; 
		dimension = dim;
		world = new Environment(dimension); 
	}

	public ArrayList<Move> moveTo(int x, int y) { //move ALEx to position x, y 
		int dx = -1; 
		if(this.x < x) dx = 1; 

		int DX = Math.abs(this.x-x); 

		ArrayList<Move> moves = new ArrayList<Move>(); 

		for(int i = 0; i < DX; i++) {
			this.x += dx; 
			for(int j = 0; j < items.size(); j++) 
				items.get(j).setX(this.x);
			moves.add(new Move(this.x, this.y)); 
		}

		int dy = -1; 
		if(this.y < y) dy = 1; 
		int DY = Math.abs(this.y-y); 

		for(int i = 0; i < DY; i++) {
			this.y += dy; 
			for(int j = 0; j < items.size(); j++) 
				items.get(j).setY(this.y);
			moves.add(new Move(this.x, this.y)); 
		}

		return moves; 
	}

	public boolean pickUp(/*int x, int y*/) { //pick up item in environment 
		//moveTo(x,y);
		if(world.stuff[x][y] == null)
		{
			System.out.println("There's nothing here!!!");
			return false;
		}
		items.add(world.removeItem(x,y));
		System.out.println("Got something!");	
		return true; 
	}

	public boolean putDown(Item item) { //put down item in environment 
		int i = hasItem(item); 
		if(i != -1) {
			items.get(i).setCarried(false); 
			if(this.world.stuff[this.x][this.y] != null) {
				System.out.println("ERROR: item already at " + this.x + ", " + this.y + ".");
				return false; 
			}
			this.world.addItem(this.x, this.y, items.get(i));
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
	
	public boolean setX(int newx){
		if (newx >= 0 && newx < dimension){
			this.x = newx;
			return true;
		}else{
			return false;
		}
	}
	
	public boolean setY(int newy){
		if (newy >= 0 && newy < dimension){
			this.y = newy;
			return true;
		}else{
			return false;
		}
	}
	
	public ArrayList<Item> getBackpack() {
		return this.items; 
	}

	public Item getItem(int i) {
		return this.items.get(i); 
	}

	public Environment getEnviron() {
		return this.world; 
	}

	public static void main(String[] args) {
		ALEx testbot = new ALEx(10); 
		ArrayList<Move> moves = testbot.moveTo(4, 4); 
		for(int i = 0; i < moves.size(); i++) {
			System.out.println("moving to " + moves.get(i).getL() + " " + moves.get(i).getR());
		}
	}
}
