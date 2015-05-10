import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel {
	private static final int PREF_W = 1366;
	private static final int PREF_H = 768;
	// private static final int BORDER_GAP = 30;
	private static final Color GRAPH_COLOR = Color.red;
	private static final Color GRAPH_COLOR_new = Color.blue;
	private static final Color GRAPH_POINT_COLOR = Color.red;
	private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
	private static final int GRAPH_POINT_WIDTH = 12;
	private List<Node> nodes;
	private List<Edge> edges;
	private boolean edges_new[][];

	public DrawGraph(List<Node> nodes, List<Edge> edges, boolean edges_new[][]) {
		this.nodes = nodes;
		this.edges = edges;
		this.edges_new = edges_new;
	}
	public DrawGraph(){
		
	}

	private Image image;

	public DrawGraph(Image image) {
		this.image = image;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);

		g.drawImage(image, 0, 0,1024, 768,  this);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// double xScale = ((double) getWidth() - 2 * BORDER_GAP) /
		// (scores.size() - 1);
		// double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (MAX_SCORE
		// - 1);
		List<Point> graphPoints = new ArrayList<Point>();
		if (nodes == null) {
			return;
		}
		for (int i = 0; i < nodes.size(); i++) {
			// int x1 = (int) (i * xScale + BORDER_GAP);
			// int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale +
			// BORDER_GAP);
			graphPoints.add(new Point(nodes.get(i).getX()+60, nodes.get(i)
					.getY()));
		}
		// System.out.println(graphPoints);

		// create x and y axes
		// g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP,
		// BORDER_GAP);
		// g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() -
		// BORDER_GAP, getHeight() - BORDER_GAP);
		// Ve duong di
		Stroke oldStroke = g2.getStroke();
		g2.setColor(GRAPH_COLOR);
		g2.setStroke(GRAPH_STROKE);
		if(edges == null) return;
		 for (int i = 0; i < edges.size(); i++) {
		 g2.drawLine(nodes.get(edges.get(i).getSource()).getX()+60,nodes.get(edges.get(i).getSource()).getY(),
				 nodes.get(edges.get(i).getDestination()).getX()+60,nodes.get(edges.get(i).getDestination()).getY());
		//System.out.println(edges);
//		System.out.println(nodes.get(edges.get(2).getSource()).getX() + " " +nodes.get(edges.get(2).getSource()).getY()+" "+
//				 nodes.get(edges.get(2).getDestination()).getX()+" "+nodes.get(edges.get(2).getDestination()).getY());
		 }
		// g2.drawLine(40,170 ,100 , 170);
		g2.setStroke(oldStroke);
		g2.setColor(GRAPH_COLOR_new);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (edges_new[i][j]) {
					g2.drawLine(nodes.get(i).getX()+60, nodes.get(i).getY(), nodes.get(j).getX()+60, nodes.get(j).getY());
				}
			}
		}
		g2.setStroke(oldStroke);
		g2.setColor(GRAPH_POINT_COLOR);
		for (int i = 0; i < graphPoints.size(); i++) {
			if(nodes.get(i).getDis() != 0){
			int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
			int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
			int ovalW = GRAPH_POINT_WIDTH;
			int ovalH = GRAPH_POINT_WIDTH;
			g2.fillOval(x, y, ovalH, ovalW);}
		}
		// ve diem voi ham fillOval(toa do x, toa do y, kich thuoc, kick thuoc)
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}


	public void run(final List<Node> nodes, final List<Edge> edges, final boolean edges_new[][]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BufferedImage myImage = null;
				DrawGraph mainPanel = new DrawGraph(nodes, edges, edges_new);
				try {
					myImage = ImageIO.read(new File(
							"/home/phuong-hoang/Desktop/Data/Archive/anh.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JFrame frame = new JFrame("DrawGraph");
				frame.setContentPane(new DrawGraph(myImage));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(mainPanel);
				frame.pack();
				frame.setLocationByPlatform(true);
//				frame.setSize(500,500);
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				ImageIcon image = new ImageIcon("/home/phuong-hoang/Desktop/Data/Archive/anh.jpg");
//				JLabel label = new JLabel("", image, JLabel.CENTER);
//				JPanel panel = new JPanel(new BorderLayout());
//				panel.add( label, BorderLayout.CENTER );
//				// Make me last
//				frame.setVisible(true);
				
				frame.setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		// new DrawGraph();
		
	}
}