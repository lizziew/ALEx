import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;



public class ALExGUI {
	
	Timer t;  							//Called every 25 ms to update painted component
	GraphicsPanel grid;					//The painted component
	JTextArea record; 					//Shows stuff that has been input and output
	JTextField input;					//Where you type
	JPanel bottomhalf;					//Contains text input, record, and help and go buttons
	int ALExx = 0;						//Alex's starting x and y coords					
	int ALExy = 0;
	
	ArrayList<String> todo;				//current pending commands
	
	BufferedImage alexsprite; 			//Alex's sprite image
	int columnwidth;					//will become the size of the graphicspanel divided by the number of columns, for drawing purposes
	ALEx alex; 							//Alex.
	JButton go;							//Press to enter text
	BufferedImage [][] itemsprites = new BufferedImage[5][11];	//an array of pictures of different-colored shapes
	int dimensions; 					//The size of the (square) board
	int objectdensity = 15; 			//out of 100 - how thickly the board is populated with random objects
	int stepcounter = 0; 				//Alex only moves every eighth time the board is redrawn, so this one increments each time and resets when it gets to eight
	
	JButton help;						//the button pressed to summon the help window
	
	public static void main(String[] args) {		//Main method calls constructor, where most of the stuff happens
		ALExGUI letsgo = new ALExGUI();
	}

	public ALExGUI() {
		
		dimensions = 15;				
		//size of the board is changeable, but only by modifying this constant in the code
		
		columnwidth = Math.round((float)500/(float)dimensions);
		//set the width of each row/column based on that
		
		alex = new ALEx(dimensions);
		//initializing alex
		
		todo = new ArrayList<String>();
		//initializing the queue of commands
		
		try {														//Reading in the multitudinous sprites
			
			alexsprite = ImageIO.read(new File("sprite.png"));
		    
		    itemsprites[0][0] = ImageIO.read(new File("itemsprites/circle-red.png"));
		    itemsprites[0][1] = ImageIO.read(new File("itemsprites/circle-orange.png"));
		    itemsprites[0][2] = ImageIO.read(new File("itemsprites/circle-yellow.png"));
 		    itemsprites[0][3] = ImageIO.read(new File("itemsprites/circle-green.png"));
 		    itemsprites[0][4] = ImageIO.read(new File("itemsprites/circle-blue.png"));
 		    itemsprites[0][5] = ImageIO.read(new File("itemsprites/circle-lightblue.png"));
 		    itemsprites[0][6] = ImageIO.read(new File("itemsprites/circle-purple.png"));
 		    itemsprites[0][7] = ImageIO.read(new File("itemsprites/circle-pink.png"));
 		    itemsprites[0][8] = ImageIO.read(new File("itemsprites/circle-brown.png"));
 		    itemsprites[0][9] = ImageIO.read(new File("itemsprites/circle-gray.png"));
 		    itemsprites[0][10] =  ImageIO.read(new File("itemsprites/circle-black.png"));
 		    
		    itemsprites[1][0] = ImageIO.read(new File("itemsprites/moon-red.png"));
		    itemsprites[1][1] = ImageIO.read(new File("itemsprites/moon-orange.png"));
		    itemsprites[1][2] = ImageIO.read(new File("itemsprites/moon-yellow.png"));
 		    itemsprites[1][3] = ImageIO.read(new File("itemsprites/moon-green.png"));
 		    itemsprites[1][4] = ImageIO.read(new File("itemsprites/moon-blue.png"));
 		    itemsprites[1][5] = ImageIO.read(new File("itemsprites/moon-lightblue.png"));
 		    itemsprites[1][6] = ImageIO.read(new File("itemsprites/moon-purple.png"));
 		    itemsprites[1][7] = ImageIO.read(new File("itemsprites/moon-pink.png"));
 		    itemsprites[1][8] = ImageIO.read(new File("itemsprites/moon-brown.png"));
 		    itemsprites[1][9] = ImageIO.read(new File("itemsprites/moon-gray.png"));
 		    itemsprites[1][10] =  ImageIO.read(new File("itemsprites/moon-black.png"));  
 		    
		    itemsprites[2][0] = ImageIO.read(new File("itemsprites/square-red.png"));
		    itemsprites[2][1] = ImageIO.read(new File("itemsprites/square-orange.png"));
		    itemsprites[2][2] = ImageIO.read(new File("itemsprites/square-yellow.png"));
 		    itemsprites[2][3] = ImageIO.read(new File("itemsprites/square-green.png"));
 		    itemsprites[2][4] = ImageIO.read(new File("itemsprites/square-blue.png"));
 		    itemsprites[2][5] = ImageIO.read(new File("itemsprites/square-lightblue.png"));
 		    itemsprites[2][6] = ImageIO.read(new File("itemsprites/square-purple.png"));
 		    itemsprites[2][7] = ImageIO.read(new File("itemsprites/square-pink.png"));
 		    itemsprites[2][8] = ImageIO.read(new File("itemsprites/square-brown.png"));
 		    itemsprites[2][9] = ImageIO.read(new File("itemsprites/square-gray.png"));
 		    itemsprites[2][10] =  ImageIO.read(new File("itemsprites/square-black.png"));  
		
		    itemsprites[3][0] = ImageIO.read(new File("itemsprites/star-red.png"));
		    itemsprites[3][1] = ImageIO.read(new File("itemsprites/star-orange.png"));
		    itemsprites[3][2] = ImageIO.read(new File("itemsprites/star-yellow.png"));
 		    itemsprites[3][3] = ImageIO.read(new File("itemsprites/star-green.png"));
 		    itemsprites[3][4] = ImageIO.read(new File("itemsprites/star-blue.png"));
 		    itemsprites[3][5] = ImageIO.read(new File("itemsprites/star-lightblue.png"));
 		    itemsprites[3][6] = ImageIO.read(new File("itemsprites/star-purple.png"));
 		    itemsprites[3][7] = ImageIO.read(new File("itemsprites/star-pink.png"));
 		    itemsprites[3][8] = ImageIO.read(new File("itemsprites/star-brown.png"));
 		    itemsprites[3][9] = ImageIO.read(new File("itemsprites/star-gray.png"));
 		    itemsprites[3][10] =  ImageIO.read(new File("itemsprites/star-black.png"));  
 		    
		    itemsprites[4][0] = ImageIO.read(new File("itemsprites/triangle-red.png"));
		    itemsprites[4][1] = ImageIO.read(new File("itemsprites/triangle-orange.png"));
		    itemsprites[4][2] = ImageIO.read(new File("itemsprites/triangle-yellow.png"));
 		    itemsprites[4][3] = ImageIO.read(new File("itemsprites/triangle-green.png"));
 		    itemsprites[4][4] = ImageIO.read(new File("itemsprites/triangle-blue.png"));
 		    itemsprites[4][5] = ImageIO.read(new File("itemsprites/triangle-lightblue.png"));
 		    itemsprites[4][6] = ImageIO.read(new File("itemsprites/triangle-purple.png"));
 		    itemsprites[4][7] = ImageIO.read(new File("itemsprites/triangle-pink.png"));
 		    itemsprites[4][8] = ImageIO.read(new File("itemsprites/triangle-brown.png"));
 		    itemsprites[4][9] = ImageIO.read(new File("itemsprites/triangle-gray.png"));
 		    itemsprites[4][10] =  ImageIO.read(new File("itemsprites/triangle-black.png"));
 		    
		} catch (IOException e) {
			System.out.println(e);
		}
		
		
		
		//Making and placing all the components
		JFrame frame = new JFrame("ALEx - Artificial Linguistic Executor");					
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		bottomhalf = new JPanel();
		
		grid = new GraphicsPanel();
		grid.setPreferredSize(new Dimension(600,500));
		grid.setMaximumSize(new Dimension(600,500));
		grid.setAlignmentX((float)0.5);
		grid.setAlignmentY((float)0.5);
		
		frame.add(grid);
		Box.createRigidArea(new Dimension(0,5));
		frame.add(bottomhalf);
		
		bottomhalf.setLayout(new BorderLayout());
		
		record = new JTextArea();
		record.setEditable(false);
		record.setLineWrap(true);
		record.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(record);
		bottomhalf.add(scrollPane, BorderLayout.CENTER);
		
		JPanel inputandgo = new JPanel();
		bottomhalf.add(inputandgo, BorderLayout.PAGE_END);
		
		inputandgo.setLayout(new BorderLayout());
		
		input = new JTextField("Input");
		input.addKeyListener(new KeyHandler());
		//this keyhandler deals with enter being pressed to send input
		inputandgo.add(input, BorderLayout.CENTER);
		
		go = new JButton("GO");
		go.addMouseListener(new MouseHandler());
		//a mousehandler to track when the button is pressed
		inputandgo.add(go, BorderLayout.LINE_END);
		
		help = new JButton("HELP?");
		help.addMouseListener(new MouseHandler());
		//a mousehandler to track when the button is pressed
		inputandgo.add(help, BorderLayout.LINE_START);
		
		t= new Timer(25,new TimerHandler());
		t.start(); 
		//every time this timer goes off, it redraws the gridworld, alex, backpack etc. 
		//as well as increasing stepcounter
		
		//put some objects in the frame
		int objnumber = Math.round((float)((dimensions * dimensions)*objectdensity)/(float)(100));
		for (int i = 0; i<objnumber; i++){
			
			int randx = (byte) Math.round(Math.floor(Math.random()*dimensions));
			int randy = (byte) Math.round(Math.floor(Math.random()*dimensions));
			
			while(alex.getEnviron().getStuff()[randx][randy] != null){
				randx = (byte) Math.round(Math.floor(Math.random()*dimensions));
				randy = (byte) Math.round(Math.floor(Math.random()*dimensions));
			}
			
			int randcolor = (byte) Math.round(Math.floor(Math.random()*11));
			int randshape = (byte) Math.round(Math.floor(Math.random()*5));
			alex.getEnviron().addItem(randx, randy, new Item(randx, randy, randcolor, randshape));
		}
		
		//Show the frame
		frame.setSize(700, 700);
		frame.setVisible(true);
		
	}
	
	private void processInput(String inputtext){		
		//Splits a string of text returned from ALEx's language processing and sends it to todo
		//(called by the key and mousehandlers for input, which send their text to alex and then
		//give alex's output to this method) 
		
		//first, split string by | delimiter
		String[] inputsplit = inputtext.split("\\|");
		
		//then process each one
		for (int i = 0; i<inputsplit.length; i++){
			if (!inputsplit[i].equals("")){
				todo.add(inputsplit[i]);	
			}
		}
	}
	
	
	private class GraphicsPanel extends JPanel{
		BufferedImage graph; 		//the grid
		BufferedImage backpack; 	//the backpack
		Graphics graphdraw;			//Graphics for each of those, to draw lines etc. 
		Graphics backpackdraw;
		
		public GraphicsPanel() {
			graph = new BufferedImage(502, 502, BufferedImage.TYPE_INT_ARGB); 
			//The extra two pixels are necessary because the division is inexact integer division
			backpack = new BufferedImage(100,502, BufferedImage.TYPE_INT_ARGB);
			graphdraw = graph.createGraphics(); 
			backpackdraw = backpack.createGraphics();
		}

		//Update is called by the TimerHandler when the timer updates.
		public void update(){
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			
			// if stepcounter = 0 (i.e. it is the one-out-of-eight steps on which
			// alex moves)) then it processes the to-do list
			
			if (stepcounter == 0 && todo.size() != 0){
				
				String current = todo.get(0);
				
				if (current.startsWith("!")){
					//if it starts with !, it is a message that ALEx wants directly appended to the output
					//without acting on it
					record.append(current.substring(1) + "\n");
					todo.remove(0);
				}else if(current.equals("unknown")) {
					//if it is unknown, alex.. did not understand it, so we print accordingly
					record.append("~I don't understand.\n"); 
					todo.remove(0); 
				}else if (current.contains("movetoc")){			
					//the command sent for "move to a red object" or similar - only a color
					
					//first we extract the color from the command
					String color = current.substring(current.indexOf(" ") + 1);
					
					//see how many items of that color there are, and where
					ArrayList<Coord> coordlist = alex.findColorItem(color);
					
					//remove it from the queue since it is being acted on
					todo.remove(0);
					
					if (coordlist.size() == 1){
						//if there is one item, we insert moving to that item's coords at the front of the queue
						todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
					}else if(coordlist.size() == 0){
						//if there are none, we first check if the user wrote blue and meant to refer to light blue objects
						//and if not, print that there aren't any of the appropriate color
						
						// **the various lightblue -> light blue substitutions are just because internally,
						//alex refers to the color as "lightblue," but we want to print "light blue" 
						if (color.equals("blue")){
							coordlist = alex.findColorItem("lightblue");
							if (coordlist.size() == 1){
								todo.add(0, "move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
							}else if(coordlist.size() == 0){
								record.append("~There aren't any blue things.\n");
							}else if (coordlist.size() > 1){
								record.append("~Which blue thing do you mean?\n");
							}
						}else{
							if (color.equals("lightblue")){
								record.append("~There aren't any light blue things.\n");
							}else{
								record.append("~There aren't any " + color + " things.\n");
							}
						}
					}else if(coordlist.size() > 1){
						//if there's more than one, we ask for clarification
						if (color.equals("lightblue")){
							record.append("~Which light blue thing do you mean?\n");
						}else{
							record.append("~Which " + color + " thing do you mean?\n");
						}
					}

										
				}else if (current.contains("movetos")){
					
					//we have a shape to move to, but nothing else - e.g. 'move to the square' will be 'movetos square'
					
					//first extract the shape
					String shape = current.substring(current.indexOf(" ") + 1);
					
					//remove it from queue
					todo.remove(0);
					
					//find the items of that shape
					ArrayList<Coord> coordlist = alex.findShapeItem(shape);
					
					if (coordlist.size() == 1){
						//if there is one, move to it
						todo.add(0, "move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
					}else if(coordlist.size() == 0){
						//if there are zero, say so
						record.append("~There are no " + shape + " s.\n");
					}else if(coordlist.size() > 1){
						//if there is more than one, ask for clarification
						record.append("~Which " + shape + " did you mean?\n");
					}
					
				}else if (current.contains("moveto")){
					//the command for moving to an item with both color and shape provided

					//parse the color and shape out of the command
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					String color = currentcopy.substring(0, currentcopy.indexOf(" "));
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					
					//remove from queue
					todo.remove(0);
					
					ArrayList<Coord> coordlist = alex.findItem(color, shape);
					if (coordlist.size() == 1){
						//if there is one, move to it
						todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
					}else if (coordlist.size() == 0){
						//if there are none, check if they meant light blue and said blue
						//**as before, lightblue -> light blue so it prints in a correctly-spelled way
						if (color.equals("blue")){
							coordlist = alex.findItem("lightblue", shape);
							if (coordlist.size() == 1){
								todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
							}else if (coordlist.size() == 0){
								record.append("~There aren't any blue " + shape + "s.\n");
							}else if (coordlist.size() > 1){
								record.append("~Which blue " + shape + " do you mean? (Currently, coordinates are the only way to clarify this.)\n");
							}
						}else{
							//otherwise print that there are none
							if (color.equals("lightblue")){
								record.append("~There aren't any light blue " + shape + "s.\n");
							}else{
								record.append("~There aren't any " + color + " " + shape + "s.\n");
							}
						}
					}else if (coordlist.size() > 1){
						//otherwise ask for clarification
						if (color.equals("lightblue")){
							record.append("~Which light blue " + shape + " do you mean? (Currently, coordinates are the only way to clarify this.)\n");
						}else{
							record.append("~Which " + color + " " + shape + " do you mean? (Currently, coordinates are the only way to clarify this.)\n");
						}
					}
					
				}else if (current.contains("move")){
					
					//either 'move x y' or 'move [nesw]'
					//is it direction or coordinates? 
					
					if (current.contains(" n") || current.contains(" e") || current.contains(" s") || current.contains(" w")){
						
						//move in the appropriate direction a given number of times, if ALEx is not at that edge of the board
						int i = 0; 
						if(current.contains(" n")) { //north
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" n")) {
									if(ALExy != 0) {
										ALExy--; 
										alex.setY(ALExy); 
									}
									else {
										record.append("~I can't go any further north.\n");
										break; 
									}
								}
						}
						else if(current.contains(" e")) { //east
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" e")) {
									if(ALExx != dimensions-1) {
										ALExx++; 
										alex.setX(ALExx); 
									}
									else {
										record.append("~I can't go any further east.\n");
										break; 
									}
								}
						}
						else if(current.contains(" s")) { //south
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" s")) {
									if(ALExy != dimensions-1) {
										ALExy++; 
										alex.setY(ALExy); 
									}
									else {
										record.append("~I can't go any further south.\n");
										break; 
									}
								}
						}
						else if(current.contains(" w")) { //west
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" w")) {
									if(ALExx != 0) {
										ALExx--; 
										alex.setX(ALExx); 
									}
									else {
										record.append("~I can't go any further west.\n");
										break; 
									}
								}
						}

						todo.remove(0);
						
					}else{
						
						//checks if the coords are valid, and moves towards them if so
						
						String currentcopy = current.substring(current.indexOf(" ") + 1);
						int destx = Integer.parseInt(currentcopy.substring(0,currentcopy.indexOf(" ")));
						int desty = Integer.parseInt(currentcopy.substring(currentcopy.indexOf(" ") + 1));
						if (destx >= 0 && destx < dimensions && desty >=0 && desty < dimensions){
							if (ALExx != destx || ALExy != desty){
								if (ALExx != destx && ALExy != desty){
									if(ALExy < desty){
										alex.setY(ALExy + 1);
									}else{
										alex.setY(ALExy - 1);
									}
								}else if (ALExx != destx){
									if(ALExx < destx){
										alex.setX(ALExx + 1);
									}else{
										alex.setX(ALExx - 1);
									}
								}else if (ALExy != desty){
									if(ALExy < desty){
										alex.setY(ALExy + 1);
									}else{
										alex.setY(ALExy - 1);
									}
								}
							}else{
								todo.remove(0);
							}
						}else{
							record.append("~Those are invalid coordinates!\n");
						}
					}
				
				}else if (current.contains("pick ups")){ //pick up object with a given shape 
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					
					todo.remove(0);
					
					ArrayList<Coord> coordlist = alex.findShapeItem(shape); //find all objects of a given shape 
					
					if (coordlist.size() == 1){ //if only one object of a given shape, pick up that object 
						todo.add(0, "move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
						todo.add(1, "immediatepickup");
					}else if(coordlist.size() == 0){ //if no objects of a given shape, pick up nothing, and notify user 
						record.append("~There are no " + shape + "s.\n");
					}else if(coordlist.size() > 1){ //if there are multiple objects of a given shape, ask for clarification 
						record.append("~Which " + shape + " did you mean?\n");
					}
				}else if (current.contains("pick upc")){ //pick up object with a given color 
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					String color = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					ArrayList<Coord> coordlist = alex.findColorItem(color); //find all objects of a given color 
					
					todo.remove(0);
					
					if (coordlist.size() == 1){ //if only one object oa given color, pick up that object 
						todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
						todo.add(1, "immediatepickup");
					}else if(coordlist.size() == 0){ //if no objects of a given color, pick up nothing, and notify user 
						if (color.equals("blue")){
							coordlist = alex.findColorItem("lightblue");
							if (coordlist.size() == 1){
								todo.add(0, "move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
								todo.add(1, "immediatepickup");
							}else if(coordlist.size() == 0){
								record.append("~There aren't any blue things.\n");
							}else if (coordlist.size() > 1){
								record.append("~Which blue thing do you mean?\n");
							}
						}else{ //all the light blue and blue cases 
							if (color.equals("lightblue")){
								record.append("~There aren't any light blue things.\n");
							}else{
								record.append("~There aren't any " + color + " things.\n");
							}
						}
					}else if(coordlist.size() > 1){
						if (color.equals("lightblue")){
							record.append("~Which light blue thing do you mean?\n");
						}else{
							record.append("~Which " + color + " thing do you mean?\n");
						}
					}
				}else if(current.contains("pick up")){ //if cmd is pick up something 
					if (current.contains("loc")){ //pick up object at a given location
						String currentcopy = current.substring(current.indexOf(" ") + 1);
						currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
						currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
						
						int pickupx = Integer.parseInt(currentcopy.substring(0,currentcopy.indexOf(" ")));
						int pickupy = Integer.parseInt(currentcopy.substring(currentcopy.indexOf(" ") + 1));
						
						todo.remove(0);
						
						todo.add(0,"move " + pickupx + " " + pickupy);
						todo.add(1, "immediatepickup");
					}else{ //pick up an object 
					//parse the color and shape out of the command
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					String color = currentcopy.substring(0, currentcopy.indexOf(" "));
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					
					todo.remove(0);
					
					ArrayList<Coord> coordlist = alex.findItem(color, shape); //find all items with given color and shape 
					if (coordlist.size() == 1){ //if item is unique, pick it up 
						todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
						todo.add(1, "immediatepickup");
					}else if (coordlist.size() == 0){ //if there are no items with given info, notify them and ask them for clarification if cmd includes light blue/blue
						if (color.equals("blue")){ 			//check if they meant light blue
							coordlist = alex.findItem("lightblue", shape);
							if (coordlist.size() == 1){
								todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
								todo.add(1, "immediatepickup");
							}else if (coordlist.size() == 0){
								record.append("~There aren't any blue " + shape + "s.\n");
							}else if (coordlist.size() > 1){
								record.append("~Which blue " + shape + " do you mean? (Currently, coordinates are the only way to clarify this.)\n");
							}
						}else if (color.equals("lightblue")){
							record.append("~There aren't any light blue " + shape + "s.\n");
						}else{
							record.append("~There aren't any " + color + " " + shape + "s.\n");
						}
					}else if (coordlist.size() > 1){ //if there are multiple items with given info, ask for clarification and coordinates of object user is referring to
						if (color.equals("lightblue")){
							record.append("~Which light blue " + shape + " do you mean? (Currently, coordinates are the only way to clarify this.)\n");
						}else{
							record.append("~Which " + color + " " + shape + " do you mean? (Currently, coordinates are the only way to clarify this.)\n");
						}
					}
				}
				}else if(current.contains("put downs")){ //put down object of a certain shape 
					
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					Item i = alex.hasShape(shape); //look through backpack for objects of that shape 
					todo.remove(0);
					
					if (i.getX() != -1){
						todo.add(0,"put down " + i.getColor() + " " + shape); //if ALEx is carrying object of that shape, put it down 
					}else{
						record.append("~I'm not carrying any " + shape + "s.\n"); //ALEx is not carrying an object of that shape 
					}
					

					
				}else if (current.contains("put downc")){ //put down object of a certain color 
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					String color = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					Item i = alex.hasColor(color); //look through backpack for objects of that color 
					todo.remove(0);
					
					if (i.getX() != -1){
						todo.add(0,"put down " + color + " " + i.getShape()); //if ALEx is carrying object of that color, put it down 
					}else{
						if (color.equals("blue")){ //clarify in case cmd includes light blue/blue
							i = alex.hasColor("lightblue");
							if (i.getX() != -1){
								todo.add(0,"put down lightblue " + i.getShape());
							}else{
								record.append("~I'm not carrying anything " + color + ".\n");
							}
						}
						record.append("~I'm not carrying anything " + color + ".\n"); //ALEx is not carrying an object of that color 
					}
					
					
				}else if(current.contains("put down")){ //cmd is put down  
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					String color = currentcopy.substring(0, currentcopy.indexOf(" "));
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					
					if (alex.hasItem(new Item(-1, -1, color, shape)) != -1){ 
						if (alex.getEnviron().getStuff()[ALExx][ALExy] != null){ //if there's already something at square in grid world 
							record.append("~There's already something here.\n");
						}else{
							alex.putDown(new Item(-1,-1,color,shape));
						}		
					}else{
						record.append("~I don't have a " + color + " " + shape + " to put down.\n"); //if ALEx is not carrying certain object 
					}
					todo.remove(0);
				}else if (current.equals("immediatepickup")){
					//immediatepickup is a command the GUI appends to its own todo list to tell it to pick up whatever object is at its location
					if (alex.getEnviron().getStuff()[ALExx][ALExy] == null){
						record.append("~There's nothing here to pick up.\n");
					}else{
						alex.pickUp();
					}
					todo.remove(0);
				}else if(current.equals("tryimmediatepickup")){
					//alex is confused but tries picking up what is wherever he is standing just in case
					//functionally the same as immediatepickup but needs a different error message
					//because someone might have tried another command
					if (alex.getEnviron().getStuff()[ALExx][ALExy] == null){
						record.append("~I don't understand.\n");
					}else{
						alex.pickUp();
					}
					todo.remove(0);
				}else if (current.equals("immediateputdown")){
					//immediateputdown just puts down whatever the first object alex is holding is
					if (alex.getBackpack().size() != 0){
						if (alex.getEnviron().getStuff()[ALExx][ALExy] != null){
							record.append("~There's already something here.\n");
						}else{
							alex.putDown(alex.getBackpack().get(alex.getBackpack().size()-1));
						}
					}else{
						record.append("~I don't have anything to put down...\n");
					}
					todo.remove(0);
				}
			}
			
			//ok, todo processing done! that was a trip
			
			//increment the step counter, and loop it back to 0 if it's reached 8
			stepcounter++;
			if (stepcounter == 8){
				stepcounter = 0; 
			}

			//update ALExx and ALExy, our idea of alex's location, to reflect alex's actual location
			ALExx = alex.getX();
			ALExy = alex.getY();
			
			//draw the grid and fill in objects and alex
			
			//first, draw the white background
			graphdraw.setColor(Color.white);
			graphdraw.fillRect(0, 0, 500, 500);
			
			//then the grid lines
			
			graphdraw.setColor(Color.black);
			
			for(int i=0; i<=dimensions;i++){
				graphdraw.drawLine(i*columnwidth, 0, i*columnwidth, 500);
			}
			
			for(int i=0; i<=dimensions;i++){
				graphdraw.drawLine(0, i*columnwidth, 500, i*columnwidth);
			}
			
			//labels the first couple of x and y coordinates. very ugly, sorry. 
			for (int i=0; i<4 && i<dimensions; i++){			//label x coordinates
				graphdraw.drawString("x= " + i, columnwidth*i + 2, 15);
			}
			
			for (int i=0; i<4 && i<dimensions; i++){			//label y coordinates
				graphdraw.drawString("y= " + i,5,columnwidth*(i+1)-2);
			}
			
			
			//gets the array of objects and draws them with the appropriate sprites
			
			Item[][] stuffs = alex.getEnviron().getStuff();
			
			
			for (int i = 0; i<dimensions; i++){
				for (int j = 0; j<dimensions; j++){
					
					int color=0;
					int shape=0;
					
					//null check necessary because empty spaces on the grid have null in their corresponding array spots
					if (stuffs[i][j] != null){		
						
						//sets the appropriate color and shape
						
						if(stuffs[i][j].getColor().equals("red")){
							color = 0;
						}else if(stuffs[i][j].getColor().equals("orange")){
							color = 1; 
						}else if(stuffs[i][j].getColor().equals("yellow")){
							color = 2; 
						}else if(stuffs[i][j].getColor().equals("green")){
							color = 3; 
						}else if(stuffs[i][j].getColor().equals("blue")){
							color = 4; 
						}else if(stuffs[i][j].getColor().equals("lightblue")){
							color = 5; 
						}else if(stuffs[i][j].getColor().equals("purple")){
							color = 6; 
						}else if(stuffs[i][j].getColor().equals("pink")){
							color = 7; 
						}else if(stuffs[i][j].getColor().equals("brown")){
							color = 8; 
						}else if(stuffs[i][j].getColor().equals("gray")){
							color = 9; 
						}else if(stuffs[i][j].getColor().equals("black")){
							color = 10; 
						}
						
						if(stuffs[i][j].getShape().equals("circle")){
							shape = 0;
						}else if(stuffs[i][j].getShape().equals("moon")){
							shape = 1;
						}else if(stuffs[i][j].getShape().equals("square")){
							shape = 2;
						}else if(stuffs[i][j].getShape().equals("star")){
							shape = 3;
						}else if(stuffs[i][j].getShape().equals("triangle")){
							shape = 4;
						}
						
						//draws the appropriate shape in the appropriate spot
						
						graphdraw.drawImage(itemsprites[shape][color], i*columnwidth, j*columnwidth, columnwidth, columnwidth, null);
					
					}
					
				}
			}

			graphdraw.drawImage(alexsprite, ALExx*columnwidth, ALExy*columnwidth, columnwidth, columnwidth, null);
			
			//draw the backpack
			
			backpackdraw.setColor(Color.white);
			backpackdraw.fillRect(0,0,100,500);
			backpackdraw.setColor(Color.black);
			backpackdraw.drawString("Backpack", 0, 475);
			
			int items = alex.getBackpack().size();
			int width = -1;								//initialize width (of backpack item sprites)
			if (items != 0){
				width = 475/items;						//make it the correct size
				if (width > 100){						//if it is wider than 100, set it to 100 so it does not extend outside the backpack bar	
					width = 100;						
				}
			}
			
			for (int i = 0; i<items; i++){
				
				String c = alex.getBackpack().get(i).getColor();
				String s = alex.getBackpack().get(i).getShape();
				int color = -1;
				int shape = -1;
				
				if(c.equals("red")){
					color = 0;
				}else if(c.equals("orange")){
					color = 1; 
				}else if(c.equals("yellow")){
					color = 2; 
				}else if(c.equals("green")){
					color = 3; 
				}else if(c.equals("blue")){
					color = 4; 
				}else if(c.equals("lightblue")){
					color = 5; 
				}else if(c.equals("purple")){
					color = 6; 
				}else if(c.equals("pink")){
					color = 7; 
				}else if(c.equals("brown")){
					color = 8; 
				}else if(c.equals("gray")){
					color = 9; 
				}else if(c.equals("black")){
					color = 10; 
				}
				
				if(s.equals("circle")){
					shape = 0;
				}else if(s.equals("moon")){
					shape = 1;
				}else if(s.equals("square")){
					shape = 2;
				}else if(s.equals("star")){
					shape = 3;
				}else if(s.equals("triangle")){
					shape = 4;
				}
				
				
				backpackdraw.drawImage(itemsprites[shape][color], 0, i*width, width, width, null);
				
			}
			
			g.drawImage(backpack, 0, 0, null);
			g.drawImage(graph, 100, 0, null);
		}
	}	
	
	private class KeyHandler implements KeyListener{ 	
		//key handler for the main window - used to detect when enter is pressed
		//the other methods are necessary for the interface but nonfunctional
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			//if enter is typed, it appends the input to the record,
			//sends that text to alex, gives alex's response to the method that will add it to the todo queue
			//and clears the input box
			if (arg0.getKeyChar() == '\n'){
				String inputtext = input.getText();
				record.append(inputtext + "\n");
				processInput(alex.parseText(inputtext));
				input.setText("");
			}
		}
		
	}
	
	private class MouseHandler implements MouseListener{	
		//registers when someone clicks the go or help button
		
		public void mouseClicked(MouseEvent e){
			if (e.getSource().equals(go)){
				//if it is go, it adds the text in the input box to the record,
				//sends that text to alex,
				//calls the method that will add alex's response to the todo queue,
				//and clears the input box
				String inputtext = input.getText();
				record.append(inputtext + "\n");
				processInput(alex.parseText(inputtext));
				input.setText("");
			}
			if (e.getSource().equals(help)){
				//if it is help, it just opens the help window with this preset text. ugly because of the newlines ): 
				JFrame help = new JFrame("Tips");
				help.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JTextArea helptips = new JTextArea("ALEx (the pink being with eyes, antlers, and a backpack) is in a grid world \n" +
						"filled with objects of various shapes and colors. You can command ALEx to \n" +
						"manipulate the world. \n \n" +
						"The left column in the window displays the contents of ALEx’s backpack\n" +
						"where ALEx stores the items you tell it to pick up. You can type your\n" +  
						"commands in the input field at the bottom of the window, and press enter\n" +
						"or click GO to submit your command.(If you would like to see suggestions for\n" +
						"possible commands, click HELP.) If ALEx thinks your command is too vague,\n"+ 
						"it will ask a clarifying question.\n\n" +
						"List of colors: red, orange, yellow, green, blue, light blue, purple, pink, brown, \ngray, black \n" +
						"List of shapes: circle, moon or crescent, square, star, triangle \n\n" + 
						"Possible commands you can try: \n"
						+ "- \"Find the green moon\" \n"
						+ "- \"Move to 0 0 then move right\" \n"
						+ "- \"Walk to 10 10 and drop item\" \n"
						+ "- \"Move 10 squares south\" \n"
						+ "- \"Move to a blue thing\" \n"
						+ "- \"Pick up the blue square\" \n"
						+ "- \"Do not pick up anything\" \n" 
						+ "- \"Pick up all the moons\" \n" 
						+ "- \"Pick up every moon\" \n" 
						+ "- \"Pick up each moon\" \n" 
						+ "- \"Pick up all the blue things\" \n" 
						+ "- \"Pick up all the gray stars\" \n" 
						+ "- \"Drop at 0 0\" \n" 
						+ "- \"Drop the pink star at 10 10\" \n" 
						+ "- \"Put down a moon\" \n" 
						+ "- \"Put down a red object\" \n" 
						+ "- \"Pick up the red star and drop it at 1 0\" \n"
						+ "- \"Pick up the red star and circle\" \n\n"  
						+ "Things ALEx does not understand yet: \n"
						+ "- \"Go the nearest pink circle\"\n"
						+ "- \"Get the green triangle on your left\" \n" 
						+ "- \"Pick up all the round objects\" \n" 
						+ "- \"Move green square to 0 0\" \n" 
						+ "- \"Where is the pink circle?\" \n" 
						+ "- \"Pick up the yellow and brown stars\"\n\n"
						+ "Consult the user manual for more information."
						);
				helptips.setEditable(false);
				help.add(helptips);
				help.setSize(500,900);
				help.setVisible(true);
			}
		}

		//the other methods have to be here but don't do anything
		@Override										
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	//All the timerhandler does is call the graphic's update() method. 
	private class TimerHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			grid.update();
		}

	}
	

}
