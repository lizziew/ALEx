import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;



public class ALExGUI {

	
	Timer t;
	GraphicsPanel grid;
	JTextArea record; 
	JTextField input;
	JPanel righthalf;
	int ALExx = 2;
	int ALExy = 3;
	BufferedImage img; 
	int columnwidth;
	ALEx alex; 
	BufferedImage [][] itemsprites = new BufferedImage[5][11];
	
	int dimensions; 
	
	public static void main(String[] args) {
		ALExGUI letsgo = new ALExGUI();
	}

	public ALExGUI() {
		
		dimensions = 5;
		
		alex = new ALEx(dimensions);
		
		try {
		    img = ImageIO.read(new File("sprite.png"));
		    
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
		
		JFrame frame = new JFrame("ALEx - Artificial Linguistic Executor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		righthalf = new JPanel();
		
		columnwidth = Math.round((float)500/(float)dimensions);
		
		grid = new GraphicsPanel();
		grid.setPreferredSize(new Dimension(500,500));
		grid.setMaximumSize(new Dimension(500,500));
		grid.setAlignmentX((float)0.5);
		grid.setAlignmentY((float)0.5);
		
		frame.add(grid);
		Box.createRigidArea(new Dimension(0,5));
		frame.add(righthalf);
		
		righthalf.setLayout(new BorderLayout());
		
		record = new JTextArea();
		record.setEditable(false);
		record.setLineWrap(true);
		record.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(record);
		
		righthalf.add(scrollPane, BorderLayout.CENTER);
		
		input = new JTextField("Input");
		righthalf.add(input, BorderLayout.PAGE_END);
		
		t= new Timer(25,new TimerHandler());
		t.start(); 
		
		//Show the frame
		frame.setSize(700, 700);
		frame.setVisible(true);
		
		alex.getEnviron().addItem(2,2,new Item(2,2,"red", "square"));
		alex.getEnviron().addItem(4,2,new Item(4,2,"blue", "square"));
		alex.moveTo(3,3);
		
	}
	
	
	private class GraphicsPanel extends JPanel {
		BufferedImage graph;
		Graphics graphdraw;
		
		public GraphicsPanel() {
			graph = new BufferedImage(502, 502, BufferedImage.TYPE_INT_ARGB); 
			graphdraw = graph.createGraphics(); 
		}

		//Update is called by the TimerHandler when the timer updates.
		public void update(){
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			
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
	
			graphdraw.drawImage(img, ALExx*columnwidth, ALExy*columnwidth, columnwidth, columnwidth, null);
			
			g.drawImage(graph, 0, 0, null);
		}
	}	

	
	//All the timerhandler does is call piebar's update() method. 
	private class TimerHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			grid.update();
		}

	}
	

}
