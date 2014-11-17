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
	JPanel bottomhalf;					//Contains components other than the graphicspanel
	int ALExx = 0;						//Alex's starting x and y coords
	int ALExy = 0;
	
	ArrayList<String> todo;				//current pending commands
	
	BufferedImage alexsprite; 			//Alex's sprite image
	int columnwidth;					//will become the size of the graphicspanel divided by the number of columns, for drawing purposes
	ALEx alex; 							//Alex.
	JButton go;							//Press to enter text
	BufferedImage [][] itemsprites = new BufferedImage[5][11];	//an array of pictures of different-colored shapes
	int dimensions; 					//The size of the (square) board
	int objectdensity = 25; 					//out of 100
	int stepcounter = 0; 				//Alex only moves every eighth time the board is redrawn
	
	public static void main(String[] args) {		//Main method calls constructor, where most of the stuff happens
		ALExGUI letsgo = new ALExGUI();
	}

	public ALExGUI() {
		
		dimensions = 15;
		
		alex = new ALEx(dimensions);
		
		todo = new ArrayList<String>();
		
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
		
		JFrame frame = new JFrame("ALEx - Artificial Linguistic Executor");					//Making and placing all the components
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		bottomhalf = new JPanel();
		
		columnwidth = Math.round((float)500/(float)dimensions);
		
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
		inputandgo.add(input, BorderLayout.CENTER);
		
		go = new JButton("GO");
		go.addMouseListener(new MouseHandler());
		inputandgo.add(go, BorderLayout.LINE_END);
		
		t= new Timer(25,new TimerHandler());
		t.start(); 
		
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
	
	private void processInput(String inputtext){		//Processes and acts on a string of text inputted
		
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
		Graphics graphdraw;
		Graphics backpackdraw;
		
		public GraphicsPanel() {
			graph = new BufferedImage(502, 502, BufferedImage.TYPE_INT_ARGB); //The extra two pixels are necessary because the division is inexact integer division
			backpack = new BufferedImage(100,502, BufferedImage.TYPE_INT_ARGB);
			graphdraw = graph.createGraphics(); 
			backpackdraw = backpack.createGraphics();
		}

		//Update is called by the TimerHandler when the timer updates.
		public void update(){
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			
			// 1/8 milliseconds, progress alex's current objective
			
			if (stepcounter == 0 && todo.size() != 0){
				String current = todo.get(0);
				if (current.startsWith("!")){
					record.append(current.substring(1) + "\n");
				}else if(current.equals("unknown")) {
					record.append("I don't understand.\n"); 
					todo.remove(0); 
				}else if (current.contains("moveto")){
					//the command for moving to an item

					//parse the color and shape out of the command
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					String color = currentcopy.substring(0, currentcopy.indexOf(" "));
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					
					todo.remove(0);
					
					ArrayList<Coord> coordlist = alex.findItem(color, shape);
					if (coordlist.size() == 1){
						todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
					}else if (coordlist.size() == 0){
						record.append("There aren't any " + color + " " + shape + "s.");
					}else if (coordlist.size() > 1){
						record.append("I don't know which " + color + " " + shape + " you mean.");
					}
					
				}else if (current.contains("move")){
					
					//is it move [direction] (n,e,s,w) or move [coords]?
					if (current.contains(" n") || current.contains(" e") || current.contains(" s") || current.contains(" w")){
						
						//move in the appropriate direction, if you are not at that edge of the board
						int i = 0; 
						if(current.contains(" n")) {
							System.out.println("got to the north");
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" n")) {
									if(ALExy != 0) {
										ALExy--; 
										alex.setY(ALExy); 
									}
									else {
										record.append("I can't go any further north.\n");
										break; 
									}
								}
						}
						else if(current.contains(" e")) {
							System.out.println("got to the east");
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" e")) {
									if(ALExx != dimensions-1) {
										ALExx++; 
										alex.setX(ALExx); 
									}
									else {
										record.append("I can't go any further east.\n");
										break; 
									}
								}
						}
						else if(current.contains(" s")) {
							System.out.println("got to the south");
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" s")) {
									if(ALExy != dimensions-1) {
										ALExy++; 
										alex.setY(ALExy); 
									}
									else {
										record.append("I can't go any further south.\n");
										break; 
									}
								}
						}
						else if(current.contains(" w")) {
							System.out.println("Got to the west"); 
							for(i = 0; i < current.length()-1; i++) 
								if(current.substring(i, i+2).equals(" w")) {
									if(ALExx != 0) {
										ALExx--; 
										alex.setX(ALExx); 
									}
									else {
										record.append("I can't go any further west.\n");
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
							record.append("Those are invalid coordinates!\n");
						}
					}
					
				}else if(current.contains("pick up")){
					
					if (current.contains("loc")){ //then you are picking up at a destination
						String currentcopy = current.substring(current.indexOf(" ") + 1);
						currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
						currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
						
						int pickupx = Integer.parseInt(currentcopy.substring(0,currentcopy.indexOf(" ")));
						int pickupy = Integer.parseInt(currentcopy.substring(currentcopy.indexOf(" ") + 1));
						
						todo.remove(0);
						
						todo.add(0,"move " + pickupx + " " + pickupy);
						todo.add(1, "immediatepickup");
					}else{//otherwise an object
					
					//parse the color and shape out of the command
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					String color = currentcopy.substring(0, currentcopy.indexOf(" "));
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					
					//check if that exists and if more than one/none exist, and if there's a unique one, go to it
					
					todo.remove(0);
					
					ArrayList<Coord> coordlist = alex.findItem(color, shape);
					if (coordlist.size() == 1){
						todo.add(0,"move " + coordlist.get(0).getL() + " " + coordlist.get(0).getR());
						todo.add(1, "immediatepickup");
					}else if (coordlist.size() == 0){
						record.append("There aren't any " + color + " " + shape + "s.\n");
					}else if (coordlist.size() > 1){
						record.append("I don't know which " + color + " " + shape + " you mean.\n");
					}
					}
					
				}else if(current.contains("put down")){
					
					//check if we have that, and if we do put it down
					String currentcopy = current.substring(current.indexOf(" ") + 1);
					currentcopy = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					String color = currentcopy.substring(0, currentcopy.indexOf(" "));
					String shape = currentcopy.substring(currentcopy.indexOf(" ") + 1);
					
					if (alex.hasItem(new Item(-1, -1, color, shape)) != -1){
						if (alex.getEnviron().getStuff()[ALExx][ALExy] != null){
							record.append("There's already something here.\n");
						}else{
							alex.putDown(new Item(-1,-1,color,shape));
						}		
					}else{
						record.append("I don't have one of those to put down.\n");
					}
					todo.remove(0);
				}else if (current.equals("immediatepickup")){
					//immediatepickup is a command the GUI appends to its own todo list to tell it to pick up whatever object is at its location
					if (alex.getEnviron().getStuff()[ALExx][ALExy] == null){
						record.append("There's nothing here to pick up!\n");
					}else{
						alex.pickUp();
					}
					todo.remove(0);
				}else if (current.equals("immediateputdown")){
					//immediateputdown just puts down whatever the first object alex is holding is
					if (alex.getBackpack().size() != 0){
						if (alex.getEnviron().getStuff()[ALExx][ALExy] != null){
							record.append("There's already something here.\n");
						}else{
							alex.putDown(alex.getBackpack().get(0));
						}
					}else{
						record.append("I don't have anything to put down...\n");
					}
					todo.remove(0);
				}
			}

			stepcounter++;
			if (stepcounter == 8){
				stepcounter = 0; 
			}

			ALExx = alex.getX();
			ALExy = alex.getY();
			
			//draw the grid and fill in objects and alex
			
			graphdraw.setColor(Color.white);
			graphdraw.fillRect(0, 0, 500, 500);
			
			graphdraw.setColor(Color.black);
			for(int i=0; i<=dimensions;i++){
				graphdraw.drawLine(i*columnwidth, 0, i*columnwidth, 500);
			}
			
			for(int i=0; i<=dimensions;i++){
				graphdraw.drawLine(0, i*columnwidth, 500, i*columnwidth);
			}

			Item[][] stuffs = alex.getEnviron().getStuff();
			
			for (int i = 0; i<dimensions; i++){
				for (int j = 0; j<dimensions; j++){
					
					int color=0;
					int shape=0;
					
					if (stuffs[i][j] != null){		
						
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

		@Override
		public void keyPressed(KeyEvent arg0) {
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			if (arg0.getKeyChar() == '\n'){
				String inputtext = input.getText();
				record.append(inputtext + "\n");
				processInput(alex.parseText(inputtext));
				input.setText("");
			}
		}
		
	}
	
	private class MouseHandler implements MouseListener{	//registers when someone clicks the go button
		
		public void mouseClicked(MouseEvent e){
			if (e.getSource().equals(go)){
				String inputtext = input.getText();
				record.append(inputtext + "\n");
				processInput(alex.parseText(inputtext));
				input.setText("");
			}
		}

		@Override										//the other methods have to be here but don't do anything
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
	
	//All the timerhandler does is call piebar's update() method. 
	private class TimerHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			grid.update();
		}

	}
	

}
