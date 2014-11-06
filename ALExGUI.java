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
	
	int dimensions; 
	
	public static void main(String[] args) {
		ALExGUI letsgo = new ALExGUI();
	}

	public ALExGUI() {
		
		dimensions = 5;
		
		alex = new ALEx(dimensions);
		
		try {
		    img = ImageIO.read(new File("sprite.png"));
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
					if (stuffs[i][j] != null){
						
						if(stuffs[i][j].getColor().equals("red")){
							graphdraw.setColor(Color.red);
						}else if(stuffs[i][j].getColor().equals("blue")){
							graphdraw.setColor(Color.blue);
						}
						
						if(stuffs[i][j].getShape().equals("square")){
							graphdraw.fillRect(columnwidth*i + columnwidth/4, columnwidth*j + columnwidth/4, columnwidth/2, columnwidth/2);
						}
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
