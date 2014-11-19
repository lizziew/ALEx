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
	private ArrayList<String> num_words; 

	private String def_colors[] = {"red", "orange", "yellow", "green", "blue", "lightblue", "purple", "pink", "brown", "gray", "black"}; 
	private String def_shapes[] = {"circle", "moon", "crescent", "square", "star", "triangle"};


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
		
		num_words = new ArrayList<String>(); 
		num_words.add("zero");
		num_words.add("one");
		num_words.add("two");
		num_words.add("three");
		num_words.add("four");
		num_words.add("five");
		num_words.add("six");
		num_words.add("seven");
		num_words.add("eight");
		num_words.add("nine");
		num_words.add("ten");
		num_words.add("eleven");
		num_words.add("twelve");
		num_words.add("thirteen");
		num_words.add("fourteen");
		num_words.add("fifteen");
		num_words.add("sixteen");
		num_words.add("seventeen");
		num_words.add("eighteen");
		num_words.add("nineteen");

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
		
		//convert string to lower case and get rid of punctuation; split around periods, "and", "then", ?, !

		s=s.toLowerCase();


		ArrayList<String> clauses = toClauses(s);
		
		//then go through each clause and convert it to a command
		for (int j = 0; j<clauses.size(); j++){
			if (!(clauses.get(j).equals(" ")||clauses.get(j).contains(" no ") || clauses.get(j).contains(" not ") || clauses.get(j).contains("n't") || clauses.get(j).equals("and "))){
				//^ignores blank clauses and clauses containing negatives
				
				if (!rtn.equals("")){
					rtn = rtn + "|";
				}
				
				String[] words = clauses.get(j).split(" ");

				ArrayList<String> processedwords = processWords(words);
				
				String verb = "";
				if (hasMoveVerb(processedwords))
					verb = "move";
				if (hasPickUpVerb(processedwords))
					verb = "pick up";
				if (hasPutDownVerb(processedwords))
					verb = "put down";

			
				boolean all = false; //contains special keyword "all"
				String color = "";
				String shape = "";
				Coord dest = null;
				String dir = "";

				int num_moves = 1; 
			
				for (int i = 0; i<processedwords.size(); i++){
					if (processedwords.get(i).equals("grey"))
					{
						processedwords.remove(i);
						processedwords.add(i, "gray");
						color = processedwords.get(i);
					}
					if (colors.contains(processedwords.get(i))){
						color = processedwords.get(i);
					}
					if (isPlural(processedwords.get(i)))
					{
						shape = makeSingular(processedwords.get(i));
					}	
					if (shapes.contains(processedwords.get(i))){
						shape = processedwords.get(i);
					}
					if(num_words.contains(processedwords.get(i))) {
						processedwords.set(i, Integer.toString(num_words.indexOf(processedwords.get(i)))); 
						num_moves = num_words.indexOf(processedwords.get(i)); 
					}
					if (processedwords.get(i).equals("all") || processedwords.get(i).equals("every") || processedwords.get(i).equals("each") || processedwords.get(i).equals("everything")){
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
						verb = "move"; 
						if(processedwords.get(i).equals("north") || processedwords.get(i).equals("up")) 
							dir = "n"; 
						else if(processedwords.get(i).equals("west") || processedwords.get(i).equals("left"))
							dir = "w";
						else if(processedwords.get(i).equals("down") || processedwords.get(i).equals("south"))
							dir = "s";
						else
							dir = "e";
					}
					if(processedwords.get(i).matches("[0-9]+")) //move down 2 squares 
						num_moves = Integer.parseInt(processedwords.get(i)); 
				}


				//special cases: pick up, put down
				for(int i = 0; i < processedwords.size(); i++) 
					if(processedwords.get(i).equals("pick"))
						for(int k = i; k < processedwords.size(); k++)
							if(processedwords.get(k).equals("up"))
								verb = "pick up"; 
				
				for(int i = 0; i < processedwords.size(); i++) 
					if(processedwords.get(i).equals("put"))
						for(int k = i; k < processedwords.size(); k++)
							if(processedwords.get(k).equals("down"))
								verb = "put down"; 
			
			if (shape.equals("crescent")){shape="moon";}

			//if the verb is move, send back coords, or else the item, color, or shape provided
			if (verb.equals("move")){
				if (dest != null){													//destination provided
					rtn = rtn + "move " + dest.getL() + " " + dest.getR();
					prevcommands.add("move " + dest.getL() + " " + dest.getR());
				}else if(!dir.equals("")){											//direction provided
					rtn = rtn + "move " + dir;
					prevcommands.add("move " + dir);
					for(int i = 1; i < num_moves; i++) {
						rtn = rtn + "|move " + dir;
						prevcommands.add("move " + dir);
					}
				}else if ((!color.equals("")||!shape.equals(""))){
					if (!color.equals("")&&!shape.equals("")){						//both color and shape
						rtn = rtn + "moveto " + color + " " + shape;
						prevcommands.add("moveto " + color + " " + shape);
					}else if (!color.equals("")){									//just color - search back 

							rtn = rtn + "movetoc " + color;
							prevcommands.add("movetoc " + color);

					}else if (!shape.equals("")){									//just shape - search back if appropriate

							rtn = rtn + "movetos " + shape;
							prevcommands.add("movetos" + shape);
						
					}else{
						rtn = rtn + "unknown";
					}
					
				}
				
			}
			
			
		
				//if verb is pick up, send back thing we're picking up, or coords
				if (verb.equals("pick up"))
				{
					if (all)
					{
////////////////////////////////////////////////////geass
						ArrayList<Coord> toGet = findItem(color, shape);
						for (int i = 0; i < toGet.size(); i++)
						{
							rtn = rtn + "pick up loc " + toGet.get(i).getL() + " " + toGet.get(i).getR();
							if (i != toGet.size()-1)
								rtn = rtn + "|";
							prevcommands.add("pick up loc " + toGet.get(i).getL() + " " + toGet.get(i).getR());
						}
						if (color.equals("blue"))
						{
							color = "lightblue";
							toGet.clear();
							toGet = findItem(color, shape);
							if (toGet.size() != 0)
								rtn = rtn + "|";
							for (int i = 0; i < toGet.size(); i++)
							{
								rtn = rtn + "pick up loc " + toGet.get(i).getL() + " " + toGet.get(i).getR();
								if (i != toGet.size()-1)
									rtn = rtn + "|";
								prevcommands.add("pick up loc " + toGet.get(i).getL() + " " + toGet.get(i).getR());
							}
						}
						all = false;
						return rtn;
					}else if(dest != null){
						rtn = rtn + "pick up loc " + dest.getL() + " " + dest.getR();				//"pick up loc" is for pickup at coordinates
						prevcommands.add("pick up loc " + dest.getL() + " " + dest.getR());
					}else if(!(color.equals("")||shape.equals(""))){
						
						rtn = rtn + "pick up " + color + " " + shape;
						prevcommands.add("pick up " + color + " " + shape);
						
					}else if(!color.equals("")){

						rtn = rtn + "pick upc " + color;
						prevcommands.add("pick upc " + color);

					}else if(!shape.equals("")){
							
								rtn = rtn + "pick ups " + shape;
								prevcommands.add("pick ups" + shape);

					}else{
						int n = 1;
						//iterate through previous commands to find an appropriate object
						while (dest == null && color.equals("") && shape.equals("") && n<=prevcommands.size()){
							String[] split = prevcommands.get(prevcommands.size()-n).split(" ");
							for (int i = 0; i<split.length; i++){
								if (colors.contains(split[i])){
									color = split[i];
								}
								if (shapes.contains(split[i])){
									shape = split[i];
								}
								if (split[i].matches("[0-9]+") && i<split.length-1){
									dest = new Coord(Integer.parseInt(split[i]),Integer.parseInt(split[i+1]));
								}
							}
							n++;
						}
						if (!(color.equals("") || shape.equals(""))){
							if (shape.equals("crescent")){shape = "moon";}
							rtn = rtn + "pick up " + color + " " + shape;
							prevcommands.add("pick up " + color + " " + shape);
						}else if (dest != null){
							rtn = rtn + "pick up loc " + dest.getL() + " " + dest.getR();
							prevcommands.add("pick up loc " + dest.getL() + " " + dest.getR());
						}else{
							rtn = rtn + "tryimmediatepickup";
							prevcommands.add("tryimmediatepickup");
						}
					}
				}

				//if the verb is put down, send back put down + the color and shape
				if (verb.equals("put down"))
				{
					if (all)
					{
						rtn = rtn + "!I can't do that...";
						prevcommands.add("!I can't do that...");
					}
					else if (dest != null)
					{
						if(!(color.equals("") || shape.equals("")))
						{
							rtn = rtn + "move " +  dest.getL() + " " + dest.getR() + "|put down " + color + " " + shape; 
							prevcommands.add("move " +  dest.getL() + " " + dest.getR() + "|put down " + color + " " + shape);
						}else if(!color.equals("")){
							rtn = rtn + "move " + dest.getL() + " " + dest.getR() + "|put downc " + color;
							prevcommands.add("move " + dest.getL() + " " + dest.getR() + "|put downc " + color);
						}else if(!shape.equals("")){
							rtn = rtn + "move " + dest.getL() + " " + dest.getR() + "|put downs " + shape;
							prevcommands.add("move " + dest.getL() + " " + dest.getR() + "|put downs " + shape);
						}
						else
						{
							rtn = rtn + "move " + dest.getL() + " " + dest.getR() + "|immediateputdown"; 
							prevcommands.add("move " + dest.getL() + " " + dest.getR() + "|immediateputdown");
						}
					}
					else if (!(color.equals("") || shape.equals(""))){
						rtn = rtn + "put down " + color + " " + shape;
						prevcommands.add("put down " + color + " " + shape);
					}else if(!color.equals("")){
						rtn = rtn + "put downc " + color;
						prevcommands.add("put downc " + color);
					}else if(!shape.equals("")){
						rtn = rtn + "put downs " + shape;
						prevcommands.add("put downs " + shape);
					}else{
						int n = 1;
						//iterate through previous commands to find an appropriate object
						while (color.equals("") && shape.equals("") && n<=prevcommands.size()){
							String[] split = prevcommands.get(prevcommands.size()-n).split(" ");
							for (int i = 0; i<split.length; i++){
								if (colors.contains(split[i])){
									color = split[i];
								}
								if (shapes.contains(split[i])){
									shape = split[i];
								}
							}
							n++;
						}
						if (!(color.equals("") || shape.equals(""))){
							if (shape.equals("crescent")){shape = "moon";}
							rtn = rtn + "put down " + color + " " + shape;
							prevcommands.add("put down " + color + " " + shape);
						}else{ //if there still isn't anything, put down whatever you're holding
							rtn = rtn + "immediateputdown";
							prevcommands.add("immediateputdown");
						}
					}
				}
				
				
				
				
				if (verb.equals("") && !(color.equals("")||shape.equals(""))){ //a color and shape, but not a verb
					int n = 1;
					//iterate through previous commands to find an appropriate verb, since we have an object but not a verb
					while (verb.equals("") && n<=prevcommands.size()){
						String[] split = prevcommands.get(prevcommands.size()-n).split(" ");
						
						for (int i = 0; i<split.length; i++){
							if (split[i].equals("put")){		//(we're only dealing with the nice processed commands that get sent to gui, so we can assume put -> put down)
								verb = "put down";
							}
							if (split[i].equals("move") || split[i].equals("moveto") || split[i].equals("movetos") || split[i].equals("movetoc")){
								verb = "moveto";				//we know the destination is an item, and putting move instead of moveto saves us the substitution that would otherwise occur later
							}
							if (split[i].equals("pick")){ 		//same for pick as put - we know "up" is also there, since this is a string we constructed earlier
								verb = "pick up";
							}
						}
						n++;
					}
					if(!verb.equals("")){
						rtn = rtn + verb + " " + color + " " + shape;
						prevcommands.add(verb + " " + color + " " + shape);
					}
				}else if(verb.equals("") && !color.equals("") && shape.equals("")){	//only a color
					//first find a verb
					int n = 1;
					//iterate through previous commands to find an appropriate verb, since we have an object but not a verb
					while (verb.equals("") && n<=prevcommands.size()){
						String[] split = prevcommands.get(prevcommands.size()-n).split(" ");
						
						for (int i = 0; i<split.length; i++){
							if (split[i].equals("put")){		//(we're only dealing with the nice processed commands that get sent to 
								verb = "put down";				//gui, so we can assume put -> put down)
							}
							if (split[i].equals("move") || split[i].equals("moveto") || split[i].equals("movetos") || split[i].equals("movetoc")){
								verb = "moveto";				//we know the destination is an item, and putting move instead of moveto saves us the 
																//substitution that would otherwise occur later
							}									
							if (split[i].equals("pick")){ 		//same for pick as put - we know "up" is also there, since this is a string we constructed earlier
								verb = "pick up";
							}
						}
						n++;
					}
					//then a shape
					int m = 1;
					while (shape.equals("") && m<=prevcommands.size()){
						String[] split = prevcommands.get(prevcommands.size()-m).split(" ");
						for (int i = 0; i<split.length; i++){
							if (shapes.contains(split[i])){
								shape = split[i];
							}
						}
						m++;
					}
					
					if (!(verb.equals("")||color.equals("")||shape.equals(""))){ 	//all three things, yay
						if (verb.equals("moveto")){
							rtn = rtn + "moveto " + color + " " + shape; 
							prevcommands.add("moveto " + color + " " + shape);
						}
						if (verb.equals("put down")){
							rtn = rtn + "put down " + color + " " + shape;
							prevcommands.add("put down " + color + " " + shape);
						}
						if (verb.equals("pick up")){
							rtn = rtn + "pick up " + color + " " + shape;
							prevcommands.add("pick up " + color + " " + shape);
						}		
					}else if(!verb.equals("")&&!color.equals("")&&shape.equals("")){	//still just a color and verb
						if (verb.equals("moveto")){
							rtn = rtn + "movetoc " + color;
							prevcommands.add("movetoc " + color);
						}
						if (verb.equals("put down")){
							rtn = rtn + "put downc " + color;
							prevcommands.add("put downc " + color);
						}
						if (verb.equals("pick up")){
							rtn = rtn + "pick upc " + color;
							prevcommands.add("pick upc " + color);
						}	
					}else{																//otherwise (just a color) 
						rtn = rtn + "unknown";
						prevcommands.add(color);
					}
						
				}else if(verb.equals("") && color.equals("") && !shape.equals("")){	//only a shape

					//first find a verb
					int n = 1;
				

					
					//iterate through previous commands to find an appropriate verb, since we have an object but not a verb
					while (verb.equals("") && n<=prevcommands.size()){
						
						
						String[] split = prevcommands.get(prevcommands.size()-n).split(" ");
						
						for (int i = 0; i<split.length; i++){
							if (split[i].equals("put")){		//(we're only dealing with the nice processed commands that get sent to gui, so we can assume put -> put down)
								verb = "put down";
							}
							if (split[i].equals("move") || split[i].equals("moveto")|| split[i].equals("movetoc")|| split[i].equals("movetos")){
								verb = "moveto";				//we know the destination is an item, and putting move instead of moveto saves us the substitution that would otherwise occur later
							}
							if (split[i].equals("pick")){ 		//same for pick as put - we know "up" is also there, since this is a string we constructed earlier
								verb = "pick up";
							}
						}
						n++;
					}
					//then a color
					int m = 1;
					while (color.equals("") && m<=prevcommands.size()){
						String[] split = prevcommands.get(prevcommands.size()-m).split(" ");
						for (int i = 0; i<split.length; i++){
							if (colors.contains(split[i])){
								color = split[i];
							}
						}
						m++;
					}
					
					if (!(verb.equals("")||color.equals("")||shape.equals(""))){ 	//all three things, yay
						if (verb.equals("moveto")){
							rtn = rtn + "moveto " + color + " " + shape; 
							prevcommands.add("moveto " + color + " " + shape);
						}
						if (verb.equals("put down")){
							rtn = rtn + "put down " + color + " " + shape;
							prevcommands.add("put down " + color + " " + shape);
						}
						if (verb.equals("pick up")){
							rtn = rtn + "pick up " + color + " " + shape;
							prevcommands.add("pick up " + color + " " + shape);
						}		
					}else if(!verb.equals("")&&color.equals("")&&!shape.equals("")){	//just  a verb and shape
						if (verb.equals("moveto")){
							rtn = rtn + "movetos " + shape;
							prevcommands.add("movetos " + shape);
						}
						if (verb.equals("put down")){
							rtn = rtn + "put downs " + shape;
							prevcommands.add("put downs " + shape);
						}
						if (verb.equals("pick up")){
							rtn = rtn + "pick ups " + shape;
							prevcommands.add("pick ups " + shape);
						}	
					}else{																	//just a shape
						rtn = rtn + "unknown";
						prevcommands.add(shape);
					}
					
				}else if (verb.equals("") && dest!=null){ //coords present, but not verb
					//we see whether it should be move or pick up (assume move if neither is present)
					int n = 1;
					//iterate through previous commands to find an appropriate verb, since we have an object but not a verb
					while (verb.equals("") && n<=prevcommands.size()){
						String[] split = prevcommands.get(prevcommands.size()-n).split(" ");
						
						for (int i = 0; i<split.length; i++){
							if (split[i].equals("move") || split[i].equals("moveto")|| split[i].equals("movetoc")|| split[i].equals("movetos")){
								verb = "move";				//we know the destination is coords, and putting move instead of moveto saves us the substitution that would otherwise occur later
							}
							if (split[i].equals("pick")){ 		//same for pick as put - we know "up" is also there, since this is a string we constructed earlier
								verb = "pick up loc";
							}
						}
						n++;
					}
					if(!verb.equals("")){
						rtn = rtn + verb + " " + dest.getL() + " " + dest.getR();
						prevcommands.add(verb + " " + dest.getL() + " " + dest.getR());
					}else{
						rtn = rtn + "move " + dest.getL() + " " + dest.getR();
					}
				}else if (verb.equals("") && !dir.equals("")){	//direction present but not verb. assume it is move.
					rtn = rtn + "move " + dir;
					prevcommands.add("move " + dir);
				}else if(verb.equals("") && dir.equals("")) {
					rtn = rtn + "unknown"; 
					String prevcommandsadd = "";
					for (int i = 0; i<processedwords.size(); i++){
						prevcommandsadd = prevcommandsadd + " " + processedwords.get(i);
					}
					prevcommands.add(prevcommandsadd);
				}
			}	
		}
		
		return rtn;
	}


//used to find the last object mentioned, used to deal with pronouns
	public String findAmbiguousItem()
	{
		String command = prevcommands.get(prevcommands.size()-1);
		String[] cmd = command.split("[ ]");

		//color and shape
		String c = "";
		String sh = "";

		for (int i = 0; i < cmd.length; i++)
		{
			if (colors.contains(cmd[i]))
				c = "" + cmd[i];
			if (shapes.contains(cmd[i]))
				sh = "" + cmd[i];
		}
		String str = "" + c + " " + sh;
		return str;
	}

//stupid plurals...
//turns "squares" into "square"
	public boolean isPlural(String s)
	{
		for (int i = 0; i < def_shapes.length; i++)
		{
			if (s.contains(def_shapes[i]))
				return true;
		}
		return false;
	}
	public String makeSingular(String s)
	{
		String str;
		for (int i = 0; i < def_shapes.length; i++)
		{
			if (s.contains(def_shapes[i]))
			{
				str = "" + def_shapes[i];
				return str;
			}
		}
		return null;
	}
//finds all items of certain colors and shapes
	//if no color or shape specified, returns locations of all items
	public ArrayList<Coord> findItem(String color, String shape){
		ArrayList<Coord> rtn = new ArrayList<Coord>();

		if(color == "" && shape == "")
		{
			for (int i = 0; i < dimension; i++)
			{
				for (int j = 0; j < dimension; j++)
				{
					if (world.getStuff()[i][j] != null)
					{
						rtn.add(new Coord(i,j));
					}
				}
			}
			return rtn;
		}
		if(color == "") {

			rtn = findShapeItem(shape);

			return rtn; 
		}
		if(shape == "") {
			rtn = findColorItem(color);
			return rtn; 
		}

		for (int i=0; i<dimension; i++){
			for (int j=0; j<dimension; j++){
				if (world.getStuff()[i][j] != null){
					if (world.getStuff()[i][j].getColor().equals(color) && (world.getStuff()[i][j].getShape().equals(shape) || (world.getStuff()[i][j].getShape().equals("moon") && shape.equals("crescent")))){
						rtn.add(new Coord(i,j));
					}
				}
			}
		}
			
		return rtn;
		
	}
	
	public ArrayList<Coord> findColorItem(String c){
		ArrayList<Coord> rtn = new ArrayList<Coord>();
		for (int i=0; i<dimension; i++){
			for (int j=0; j<dimension; j++){
				if (world.getStuff()[i][j] != null && world.getStuff()[i][j].getColor().equals(c)){
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
				if (world.getStuff()[i][j] != null && world.getStuff()[i][j].getShape().equals(s)){
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
//and (), and ? and !
		s = s.replace(","," ");
		s = s.replace("(","");
		s = s.replace(")","");
		
		s = s.replace("?","");
		s = s.replace("!","");

		//first split by periods and semicolons
		String[] sentences = s.split("[\\.;]");
		
		ArrayList<String> prertn1 = new ArrayList<String>();
		ArrayList<String> prertn2 = new ArrayList<String>();

		//then for each one, go through and split around "and"
		for (int i = 0; i<sentences.length; i++){
			//split around and...
			String[] ands = sentences[i].split("and");
	
			//add the first one
			if(!ands[0].equals(" ")) 
				prertn1.add(ands[0]);
			for (int j = 1; j<ands.length; j++){
				if(ands[j].equals(" ")) { 
					continue; 
				}
				prertn1.add("and" + ands[j]);
			}
		}

		//then split around "then"
		for (int i = 0; i<prertn1.size(); i++){
			//split around then...
			String[] thens = prertn1.get(i).split("then");
			//add the first one
			prertn2.add(thens[0]);
			for (int j = 1; j<thens.length; j++){
				if(thens[j].equals(" "))
					continue; 
				prertn2.add("then" + thens[j]);
			}
		}
				
		//then split around "but"
		for (int i = 0; i<prertn2.size(); i++){
			//split around then...
			String[] buts = prertn2.get(i).split("but");
			//add the first one
			rtn.add(buts[0]);
			for (int j = 1; j<buts.length; j++){
				if(buts[j].equals(" "))
					continue; 
				rtn.add("but" + buts[j]);
			}
		}


		
		return rtn;
	}
//turns command into something we can deal with more easily
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
			}else if (words[i].matches("[0-9]+") && words[i+1].matches("[0-9]+")){
				processedwords.add("coord " + words[i] + " " + words[i+1]);
				words[i+1] = "";
			}else if (words[i].matches("[0-9]+,") && words[i+1].matches("[0-9]+")){
				processedwords.add("coord " + words[i].substring(0,words[i].indexOf(",")) + " " + words[i+1]);
				words[i+1] = "";
			}else if (words[i].matches("[0-9]+,[0-9]+")){
				processedwords.add("coord " + words[i].substring(0,words[i].indexOf(",")) + " " + words[i].substring(words[i].indexOf(",")+1));
			}else if (!words[i].equals("")){
				processedwords.add(words[i]);
			}
		}
		
		if(!words[words.length-1].equals("")) {
			if(words[words.length-1].matches("[0-9]+,[0-9]+")) {
				processedwords.add("coord" + words[words.length-1].substring(0,words[words.length-1].indexOf(",")) + " " + words[words.length-1].substring(words[words.length-1].indexOf(",")+1));
			}else{
				processedwords.add(words[words.length-1]);
			}
		}

		return processedwords; 
	}

	private boolean hasMoveVerb(ArrayList<String> s)
	{
		for (int i = 0; i < s.size(); i++)
		{
			if (move_verbs.contains(s.get(i)))			//fairly straightforward, unless...
				return true;
			if (s.get(i).equals("move"))			//the verb is move, in which case we make sure it's "move left" or "move north" rather than "move the green square" 
				if (i < s.size()-1)
					if (s.get(i+1).equals("to") || pos_words.contains(s.get(i+1)))
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
				if (s.get(i).equals("move")){
					if (i<s.size()-1)
						if (!(s.get(i+1).equals("to") || pos_words.contains(s.get(i+1))))
							return true;
					
				}else{
					return true;
				}
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
		if(world.stuff[x][y] == null)
		{
			return false;
		}
		items.add(world.removeItem(x,y));
		return true; 
	}

	public boolean putDown(Item item) { //put down item in environment 
		int i = hasItem(item); 
		if(i != -1) {
			items.get(i).setCarried(false); 
			if(this.world.stuff[this.x][this.y] != null) {
				return false; 
			}
			this.world.addItem(this.x, this.y, items.get(i));
			items.remove(i);
			return true; 
		}

		return false; 
	}
	
	public Item hasColor(String c){ 	//returns the item of that color alex is carrying if he has one, or else a blank item
		Item rtn = new Item(-1,-1,"","");
		
		for (int i = 0; i<getBackpack().size(); i++){
			if (getBackpack().get(i).getColor().equals(c)){
				rtn = getBackpack().get(i);
			}
		}
		
		return rtn;
	}
	
	public Item hasShape(String s){		//returns the item of that shape alex is carrying if he has one, or else a blank item
		Item rtn = new Item(-1,-1,"","");
		
		for (int i = 0; i<getBackpack().size(); i++){
			if (getBackpack().get(i).getShape().equals(s)){
				rtn = getBackpack().get(i);
			}
		}
		
		return rtn;
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
