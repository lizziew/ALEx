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

	private ArrayList<String> pos_words; 

	private ArrayList<String> prevcommands;
	
	public ALEx (int dim) {
		pos_words = new ArrayList<String>(); 
		pos_words.add("up"); 
		pos_words.add("north"); 
		pos_words.add("west"); 
		pos_words.add("south"); 
		pos_words.add("east"); 
		pos_words.add("down"); 
		pos_words.add("left"); 
		pos_words.add("right"); 

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
		pickup_verbs.add("grab");

		//need to account for "carry object from here to there"		
		//also "send object from here to there"
		//basically, the word "from" means we need to both pickup and putdown
	
		putdown_verbs = new ArrayList<String>();
		putdown_verbs.add("putdown");
		putdown_verbs.add("drop");
		putdown_verbs.add("set");
		putdown_verbs.add("leave");
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
		
		prevcommands = new ArrayList<String>();
		
		x = 0;
		y = 0; 
		dimension = dim;
		world = new Environment(dimension); 
	}
	
	public String parseText(String s){
		
		String rtn = "";
		
		//convert string to lower case and get rid of punctuation; split around periods, "and", "then"
		s=s.toLowerCase();
		ArrayList<String> clauses = toClauses(s);
		
		for (int j = 0; j<clauses.size(); j++){
			
			if (!clauses.get(j).equals(" ")){
			
				if (!rtn.equals("")){
					rtn = rtn + "|";
				}
			
				prevcommands.add(clauses.get(j));
				
				//dealing with negatives
				if (clauses.get(j).contains(" no ") || clauses.get(j).contains(" not ") || clauses.get(j).contains("n't"))
				{
					System.out.println("I am confused...");
					return "!I don't quite understand what you want me to do.";
				}

				String[] words = clauses.get(j).split(" ");

				ArrayList<String> processedwords = processWords(words);
				for (int i = 0; i<processedwords.size(); i++) System.out.println("processed " + processedwords.get(i));

				String verb = "";
				if (hasMoveVerb(processedwords))
					verb = "move";
				else if (hasPickUpVerb(processedwords))
					verb = "pick up";
				else if (hasPutDownVerb(processedwords))
					verb = "put down";

				System.out.println("Found verb: " + verb);
			
				boolean all = false; //contains special keyword "all"
				String color = "";
				String shape = "";
				Coord dest = null;
			
				for (int i = 0; i<processedwords.size(); i++){
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
					if(pos_words.contains(processedwords.get(i))) {
						int destx = this.x; 
						int desty = this.y; 
						if(processedwords.get(i).equals("north") || processedwords.get(i).equals("up")) 
							desty = this.y-1; 
						else if(processedwords.get(i).equals("west") || processedwords.get(i).equals("left"))
							destx = this.x-1; 
						else if(processedwords.get(i).equals("down") || processedwords.get(i).equals("south"))
							desty = this.y+1;
						else
							destx = this.x+1; 
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
					rtn = rtn + "!I don't know which " + color + " " + shape + " you're referring to."; 
				else if (verb.equals("move") && dest != null) {
					rtn = rtn + ("move " + dest.getL() + " " + dest.getR());
				}
				else if(verb.equals("move") && coord_list.size() == 0)
					rtn = rtn + "!I don't see any " + color + " " + shape + "s"; 

		
				if (verb.equals("pick up"))
				{
					if (coord_list.size() > 1)
						rtn = rtn + "!I don't know which " + color + " " + shape + " you're referring to.";
					else if (coord_list.size() == 0) 
						rtn = rtn + "!I don't see any " + color + " " + shape + "s"; 
					else if (dest != null) 
						rtn = rtn + ("move " + dest.getL() + " " + dest.getR() + "|pick up");
				}
				
				//check if we have it in the backpack, and if so, drop it
				if (verb.equals("put down"))
				{
					if (hasItem(new Item(-1, -1, color,shape)) != -1){
						if (dest != null){
							//moves to correct location, then drops it
							//(putdown takes item's index in the backpack)
							rtn = rtn + ("move " + dest.getL() + " " + dest.getR() +"|put down " + hasItem(new Item(-1,-1,color,shape)));
						}else{
							rtn = rtn + ("put down " + hasItem(new Item(-1,-1,color,shape)));
						}
					}else{
						rtn = rtn + "!I'm not carrying a " + color + " " + shape;
					}	
				}
			
			}	
		
		}
		
		System.out.println("Here's what's being sent to GUI: " + rtn);
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

	
	//Splits a string into clauses by periods, "and", "then", et cetera
	private ArrayList<String> toClauses(String s){
		ArrayList<String> rtn = new ArrayList<String>();
		
		//remove commas
		s = s.replace(",","");
		
		//first split by periods and semicolons
		String[] sentences = s.split("[\\.;]");

		//then for each sentence, go through and split around "and" (or equivalent of "and")
		for (int i = 0; i<sentences.length; i++){
			//split around and...
			String[] ands = sentences[i].split("and|then");
			//add the first one: we want the rest to start with 'and' still so the parser knows more easily to look back
			for(int j = 0; j < ands.length; j++) {
				if(j == 0)
					rtn.add(ands[j]);
				else 
					rtn.add("and " + ands[j]); 
			}
		}

		for(int i = 0; i < rtn.size(); i++)
			System.out.println("clauses split: " + rtn.get(i)); 
		
		return rtn;
	}
	
	private ArrayList<String> processWords(String[] words) {
		ArrayList<String> processedwords = new ArrayList<String>(); 

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
			} else if(words[i].equals("move") && pos_words.contains(words[i+1])) {
				processedwords.add("moveto");
				processedwords.add(words[i+1]);
				words[i+1] = ""; 
			}
			else if (words[i].matches("[0-9]+") && words[i+1].matches("[0-9]+")){
				processedwords.add("coord " + words[i] + " " + words[i+1]);
				words[i+1] = "";
			}else if (words[i].matches("[0-9]+,") && words[i+1].matches("[0-9]+")){
				processedwords.add("coord " + words[i].substring(0,words[i].indexOf(",")) + " " + words[i+1]);
				words[i+1] = "";
			}else if (words[i].matches("[0-9]+,[0-9]+")){
				processedwords.add("coord " + words[i].substring(0,words[i].indexOf(",")) + " " + words[i].substring(words[i].indexOf(",")+1));
			}
			else if (!words[i].equals("")){
				processedwords.add(words[i]);
			}
		}

		if(!words[words.length-1].equals("")) {
			if(words[words.length-1].matches("[0-9]+,[0-9]+")) {
				processedwords.add("coord" + words[words.length-1].substring(0,words[words.length-1].indexOf(",")) + " " + words[words.length-1].substring(words[words.length-1].indexOf(",")+1));
			}else{
				System.out.println("got ehre"); 
				processedwords.add(words[words.length-1]);
			}
		}

		return processedwords; 
	}

	private boolean hasMoveVerb(ArrayList<String> s)
	{
		for (int i = 0; i < s.size(); i++)
		{
			if (move_verbs.contains(s.get(i)))
				return true;
		}
		return false;
	}
	private boolean hasPickUpVerb(ArrayList<String> s)
	{
		for (int i = 0; i < s.size(); i++)
		{
			//deal with the weird case where "pick" and "up" are far apart from each other
			if (s.get(i).equals("pick"))
			{
				if (i+1 < s.size() && s.get(i+1).equals("up"))
					return true;
				if (i+2 < s.size() && s.get(i+2).equals("up"))
					return true;
				if (i+3 < s.size() && s.get(i+3).equals("up"))
					return true;
				if (i+4 < s.size() && s.get(i+4).equals("up"))
					return true;
				if (i+5 < s.size() && s.get(i+5).equals("up"))
					return true;
			}
			if (pickup_verbs.contains(s.get(i)))
				return true;
		}
		return false;
	}

	private boolean hasPutDownVerb(ArrayList<String> s)
	{
		//copy and paste from hasPickUpVerb, then revise a bit
		for (int i = 0; i < s.size(); i++)
		{
			//deal with the weird case where "put" and "down" are far apart from each other
			if (s.get(i).equals("put"))
			{
				if (i+1 < s.size() && s.get(i+1).equals("down"))
					return true;
				if (i+2 < s.size() && s.get(i+2).equals("down"))
					return true;
				if (i+3 < s.size() && s.get(i+3).equals("down"))
					return true;
				if (i+4 < s.size() && s.get(i+4).equals("down"))
					return true;
				if (i+5 < s.size() && s.get(i+5).equals("down"))
					return true;
			}
			if (putdown_verbs.contains(s.get(i)))
				return true;
		}
		return false;
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
