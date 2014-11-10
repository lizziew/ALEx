import java.util.*; 
import java.lang.*;
import java.io.*; 

public class ALEx{
	private int x;
	private int y; 
	private Environment world; 
	private int dimension;

	private ArrayList<Item> items = new ArrayList<Item>();

	private ArrayList<String> fuzzy_verbs;
	private ArrayList<String> move_verbs;
	private ArrayList<String> pickup_verbs;
	private ArrayList<String> putdown_verbs;

	private ArrayList<String> colors; 
	private ArrayList<String> shapes;

	public ALEx (int dim) {
		
		fuzzy_verbs = new ArrayList<String>();
		fuzzy_verbs.add("move");
		
		move_verbs = new ArrayList<String>();
		move_verbs.add("walk");
		move_verbs.add("go");
		move_verbs.add("find");
		
		pickup_verbs = new ArrayList<String>();
		pickup_verbs.add("get");
		pickup_verbs.add("fetch");
		pickup_verbs.add("pickup");
		pickup_verbs.add("carry");
		pickup_verbs.add("transport");
		
		putdown_verbs = new ArrayList<String>();
		putdown_verbs.add("putdown");
		putdown_verbs.add("drop");
		putdown_verbs.add("setdown");
		
		colors = new ArrayList<String>();
		colors.add("red");
		colors.add("orange");
		colors.add("yellow");
		colors.add("green");
		colors.add("blue");
		colors.add("lightblue");
		colors.add("purple");
		colors.add("pink");
		colors.add("brown");
		colors.add("gray");
		colors.add("black");
		
		shapes = new ArrayList<String>();
		shapes.add("moon");
		shapes.add("crescent");
		shapes.add("circle");
		shapes.add("square");
		shapes.add("star");
		shapes.add("triangle");
		
		x = 0;
		y = 0; 
		dimension = dim;
		world = new Environment(dimension); 
	}

	public String parseText(String s){
		
		String rtn = "";
		
		//make this into arraylist plz
		
		String[] words = s.split(" ");
		String[] processedwords = new String[words.length];
		
		for (int i = 0; i<words.length-1; i++){
			if (words[i].equals("light") && words[i+1].equals("blue")){
				processedwords[i] = "lightblue";
				i++;
			}else if (words[i].equals("put") && words[i+1].equals("down")){
				processedwords[i] = "putdown";
				i++;
			}else if (words[i].equals("pick") && words[i+1].equals("up")){
				processedwords[i] = "pickup";
				i++;
			}else{
				processedwords[i] = words[i];
			}
	
		}
		
		String color = "";
		String shape = "";
		String verb = "";
		for (int i = 0; i<words.length; i++){
			if (fuzzy_verbs.contains(words[i])){
				verb = "fuzzy";
			}
			if (move_verbs.contains(words[i])){
				verb = "move";
			}
			if (pickup_verbs.contains(words[i])){
				verb = "pick up";
			}
			if (putdown_verbs.contains(words[i])){
				verb = "put down";
			}
			if (colors.contains(words[i])){
				color = words[i];
			}
			if (shapes.contains(words[i])){
				shape = words[i];
			}
			
		}
		
		return rtn;
	}
	
	public ArrayList<Coord> findItem(Item it){
		ArrayList<Coord> rtn = new ArrayList<Coord>();
		for (int i=0; i<dimension; i++){
			for (int j=0; j<dimension; j++){
				if (world.getStuff()[i][j].equals(it)){
					rtn.add(new Coord(i,j));
				}
			}
		}
		return rtn;
	}
	
	public ArrayList<Coord> findColorItem(String c){
		ArrayList<Coord> rtn = new ArrayList<Coord>();
		for (int i=0; i<dimension; i++){
			for (int j=0; j<dimension; j++){
				if (world.getStuff()[i][j].getColor().equals(c)){
					rtn.add(new Coord(i,j));
				}
			}
		}
		return rtn;
	}
	
	public ArrayList<Coord> findShapeItem(String s){
		ArrayList<Coord> rtn = new ArrayList<Coord>();
		for (int i=0; i<dimension; i++){
			for (int j=0; j<dimension; j++){
				if (world.getStuff()[i][j].getColor().equals(s)){
					rtn.add(new Coord(i,j));
				}
			}
		}
		return rtn;
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

}
