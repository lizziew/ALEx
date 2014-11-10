import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	int destinationx = 0;				//Alex's current destination x
	int destinationy = 0;				//current destination y
	boolean pickuppending = false; 				//is alex on his way to pick something up? 
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
		grid.setPreferredSize(new Dimension(500,500));
		grid.setMaximumSize(new Dimension(500,500));
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
	
		if (inputtext.contains("move")){
			pickuppending = false;
			inputtext = inputtext.substring(inputtext.indexOf(" ") + 1);
			int destx = Integer.parseInt(inputtext.substring(0,inputtext.indexOf(" ")));
			int desty = Integer.parseInt(inputtext.substring(inputtext.indexOf(" ") + 1));
			if (destx >= 0 && destx < dimensions && desty >=0 && desty < dimensions){
				destinationx = destx; 
				destinationy = desty; 
				System.out.println(destx + " " + desty);
			}else{
				record.append("Those are invalid coordinates!");
			}
		}else if(inputtext.contains("pick up")){
			inputtext = inputtext.substring(inputtext.indexOf(" ") + 1);
			inputtext = inputtext.substring(inputtext.indexOf(" ") + 1);
			int x = Integer.parseInt(inputtext.substring(0,inputtext.indexOf(" ")));
			int y = Integer.parseInt(inputtext.substring(inputtext.indexOf(" ") + 1));
			if (x >= 0 && x < dimensions && y >=0 && y < dimensions){
				destinationx = x; 
				destinationy = y; 
				pickuppending = true;
			}else{
				record.append("Those are invalid coordinates!");
			}
		}else if(inputtext.contains("put down")){
			if (alex.getBackpack().size() != 0){
				if (alex.getEnviron().getStuff()[ALExx][ALExy] == null){
					alex.putDown(alex.getItem(0));
				}else{
					record.append("There's already something here...");
				}
			}else{
				record.append("I don't have anything to put down!");
			}
		}
	}
	
	
	private class GraphicsPanel extends JPanel{
		BufferedImage graph;
		Graphics graphdraw;
		
		public GraphicsPanel() {
			graph = new BufferedImage(502, 502, BufferedImage.TYPE_INT_ARGB); //The extra two pixels are necessary because the division is inexact integer division
			graphdraw = graph.createGraphics(); 
		}

		//Update is called by the TimerHandler when the timer updates.
		public void update(){
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			
			if (stepcounter == 0){
				
				System.out.println("Alex x " + ALExx + " Alex y " + ALExy + " Destination x " + destinationx + " Destination y " + destinationy);
				
				if (ALExx != destinationx || ALExy != destinationy){
					if (ALExx != destinationx && ALExy != destinationy){
						if(ALExy < destinationy){
							alex.setY(ALExy + 1);
						}else{
							alex.setY(ALExy - 1);
						}
					}else if (ALExx != destinationx){
						if(ALExx < destinationx){
							alex.setX(ALExx + 1);
						}else{
							alex.setX(ALExx - 1);
						}
					}else if (ALExy != destinationy){
						if(ALExy < destinationy){
							alex.setY(ALExy + 1);
						}else{
							alex.setY(ALExy - 1);
						}
					}
					
				}
			}
			
			stepcounter++;
			if (stepcounter == 8){
				stepcounter = 0;
			}
			
			if (pickuppending && ALExx == destinationx && ALExy == destinationy){
				if (alex.getEnviron().getStuff()[ALExx][ALExy] != null){
					alex.pickUp();
				}else{
					record.append("There's nothing here to pick up!");
				}
				pickuppending = false;
			}
			
			ALExx = alex.getX();
			ALExy = alex.getY();
			
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
			
			g.drawImage(graph, 0, 0, null);
		}
	}	

	
	
	private class MouseHandler implements MouseListener{	//registers when someone clicks the go button
		
		public void mouseClicked(MouseEvent e){
			if (e.getSource().equals(go)){
				String inputtext = input.getText();
				processInput(inputtext);
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
