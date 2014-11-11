import java.util.*; 
import java.lang.*;
import java.io.*; 

public class ALEx{
	private int x;
	private int y; 
	private Environment world; 
	private int dimension;

	private ArrayList<Item> items = new ArrayList<Item>();

	private ArrayList<String> move_verbs;
	private ArrayList<String> pickup_verbs;
	private ArrayList<String> putdown_verbs;

	private ArrayList<String> colors; 
	private ArrayList<String> shapes;

	private String def_colors[] = {"red", "orange", "yellow", "green", "blue", "lightblue", "purple", "pink", "brown", "gray", "black"}; 
	private String def_shapes[] = {"circle", "moon", "square", "star", "triangle"};

	public ALEx (int dim) {
		
		move_verbs = new ArrayList<String>();
		move_verbs.add("walk");
		move_verbs.add("go");
		move_verbs.add("find");
		move_verbs.add("moveto");
		
		pickup_verbs = new ArrayList<String>();
		pickup_verbs.add("get");
		pickup_verbs.add("fetch");
		pickup_verbs.add("pickup");
		pickup_verbs.add("carry");
		pickup_verbs.add("transport");
		pickup_verbs.add("move");
		
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
		
		s=s.toLowerCase();

////dealing with negatives
		if (s.contains(" no ") || s.contains(" not ") || s.contains"n't")
		{
			System.out.println("I am confused...");
			return "*I don't quite understand what you want me to do.";
		}


		String[] words = s.split(" ");
		ArrayList<String> processedwords = new ArrayList<String>();
		
		//get word phrases and coord if in cmd
		for (int i = 0; i<words.length-1; i++){
			if (words[i].equals("light") && words[i+1].equals("blue")){
				processedwords.add("lightblue");
				words[i+1] = "";
			}else if (words[i].equals("put") && words[i+1].equals("down")){
				processedwords.add("putdown");
				words[i+1] = "";
			}else if (words[i].equals("pick") && words[i+1].equals("up")){
				processedwords.add("pickup");
				words[i+1] = "";
			}else if (words[i].equals("move") && words[i+1].equals("to")){
				processedwords.add("moveto");
				words[i+1] = "";
			}else if (words[i].matches("[0-9]+") && words[i+1].matches("[0-9]+")){
				processedwords.add("coord " + words[i] + " " + words[i+1]);
				words[i+1] = "";
			}else if (words[i].matches("[0-9]+,") && words[i+1].matches("[0-9]+")){
				processedwords.add("coord " + words[i].substring(0,words[i].indexOf(",")) + " " + words[i+1]);
				words[i+1] = "";
			}else if (words[i].matches("[0-9]+,[0-9]+")){
				System.out.println("aaaa");
				processedwords.add("coord " + words[i].substring(0,words[i].indexOf(",")) + " " + words[i].substring(words[i].indexOf(",")+1));
			}else if (!words[i].equals("")){
				processedwords.add(words[i]);
			}
		}
		
		//what does this do? 
		if (words[words.length-1] != null){
			if (words[words.length-1].matches("[0-9]+,[0-9]+")){
				System.out.println("bbbb");
				processedwords.add("coord " + words[words.length-1].charAt(0) + " " + words[words.length-1].charAt(2));
			}else{
				processedwords.add(words[words.length-1]);
			}
		}
		
		for (int i = 0; i<processedwords.size(); i++){
			System.out.println("processed " + processedwords.get(i));
		}
		
		boolean neg = false; //negative command
		boolean all = false; //contains special keyword "all"
		String color = "";
		String shape = "";
		String verb = "";
		Coord dest = null;
		
		for (int i = 0; i<processedwords.size(); i++){
			if (move_verbs.contains(processedwords.get(i))){
				verb = "move";
			}
			if (pickup_verbs.contains(processedwords.get(i))){
				verb = "pick up";
			}
			if (putdown_verbs.contains(processedwords.get(i))){
				verb = "put down";
			}
			if (colors.contains(processedwords.get(i))){
				color = processedwords.get(i);
			}
			if (shapes.contains(processedwords.get(i))){
				shape = processedwords.get(i);
			}
			if (processedwords.get(i).equals("all")){
				all = true;
			}
			if (processedwords.get(i).contains("coord")){
				String inputtext = processedwords.get(i);
				inputtext = inputtext.substring(inputtext.indexOf(" ") + 1);
				int destx = Integer.parseInt(inputtext.substring(0,inputtext.indexOf(" ")));
				int desty = Integer.parseInt(inputtext.substring(inputtext.indexOf(" ") + 1));
				dest = new Coord(destx, desty);
			}
		} 

		ArrayList<Coord> coord_list = new ArrayList<Coord>(); 
		if(dest == null) {  
			coord_list = findItem(color, shape);
			if(coord_list.size() > 0) dest = coord_list.get(0); 
		}
	
		//based on input, send back a cmd to GUI
		if(verb.equals("move") && coord_list.size() > 1) 
			rtn = "!I don't know which " + color + " " + shape + " you're referring to."; 
		else if(verb.equals("move") && coord_list.size() == 0)
			rtn = "!I don't see any " + color + " " + shape + "s"; 
		else if (verb.equals("move") && dest != null)
			rtn = ("move " + dest.getL() + " " + dest.getR());
		return rtn;
	}
	
	private ArrayList<Coord> findItem(String color, String shape){
		ArrayList<Coord> rtn = new ArrayList<Coord>();

		if(color == "" && shape == "")
			return rtn; 
		if(color == "") {
			rtn = findShapeItem(shape); 
			return rtn; 
		}
		if(shape == "") {
			rtn = findColorItem(color);
			return rtn; 
		}

		for (int i=0; i<dimension; i++)
			for (int j=0; j<dimension; j++)
				if (world.getStuff()[i][j] != null)
					if (world.getStuff()[i][j].getColor().equals(color) && world.getStuff()[i][j].getShape().equals(shape))
						rtn.add(new Coord(i,j));
		return rtn;
	}
	
	private ArrayList<Coord> findColorItem(String c){
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
	
	private ArrayList<Coord> findShapeItem(String s){
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
	

	public boolean pickUp() { //pick up item in environment 
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
