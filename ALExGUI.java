import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

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
	int dimensions = 6; 
	
	public static void main(String[] args) {
		ALExGUI letsgo = new ALExGUI();
	}

	public ALExGUI() {
		
		JFrame frame = new JFrame("ALEx - Artificial Linguistic Executor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		righthalf = new JPanel();
		grid = new GraphicsPanel();
		
		frame.add(grid, BorderLayout.CENTER);
		frame.add(righthalf, BorderLayout.EAST);
		
		righthalf.setLayout(new BorderLayout());
		
		record = new JTextArea();
		record.setEditable(false);
		record.setLineWrap(true);
		record.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(record);
		
		righthalf.add(scrollPane, BorderLayout.CENTER);
		
		input = new JTextField("Input");
		righthalf.add(input, BorderLayout.SOUTH);
		
		t= new Timer(25,new TimerHandler());
		t.start(); 
		
		//Show the frame
		frame.setSize(900, 700);
		frame.setVisible(true);
	}
	
	
	private class GraphicsPanel extends JPanel {
		BufferedImage graph;
		Graphics graphdraw;
		int columnwidth;
		
		public GraphicsPanel() {
			graph = new BufferedImage(550, 550, BufferedImage.TYPE_INT_ARGB); 
			graphdraw = graph.createGraphics(); 
			columnwidth = Math.round((float)500/(float)dimensions);
			System.out.println(columnwidth);
		}

		//Update is called by the TimerHandler when the timer updates.
		public void update(){
			repaint();
		}
		
		public void paintComponent(Graphics g) {
			graphdraw.setColor(Color.black);
			
			for(int i=1; i<=dimensions;i++){
				graphdraw.drawLine(i*columnwidth, 0, i*columnwidth, 500);
			}
			
			for(int i=1; i<=dimensions;i++){
				graphdraw.drawLine(0, i*columnwidth, 500, i*columnwidth);
			}
			
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
