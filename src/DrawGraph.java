import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel {
	private static final int PREF_W = 1200;
	private static final int PREF_H = 768;
	// private static final int BORDER_GAP = 30;
	private static final Color GRAPH_COLOR = Color.red;
	private static final Color GRAPH_COLOR_new = Color.blue;
	private static final Color GRAPH_POINT_COLOR = Color.red;
	private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
	private static final int GRAPH_POINT_WIDTH = 8;
	private List<Node> nodes;
	private List<Edge> edges;
	private List<Node> nodes_mid;
	private List<Path_Physical> path_mid;
	private boolean edges_new[][];
	private int num_edges_new = 0;
	private float old_cost, cost;

	public DrawGraph(List<Node> nodes, List<Edge> edges, boolean edges_new[][],
			float old_cost, float cost, int n, List<Node> nodes_mid,
			List<Path_Physical> path_mid) {
		this.nodes = nodes;
		this.edges = edges;
		this.edges_new = edges_new;
		this.old_cost = old_cost;
		this.cost = cost;
		this.num_edges_new = n;
		this.nodes_mid = nodes_mid;
		this.path_mid = path_mid;
		// JButton bChange = new JButton("Click Me!"); // construct a JButton
		// add( bChange );
		// bChange.setBounds(100, 120, 10, 20);
		// JButton button = new JButton("hello agin1");
		// button.setLayout(null);
		// button.setBounds(0, 500, 100, 100);
		// add(button);
	}

	public DrawGraph() {

	}

	private Image image;

	public DrawGraph(Image image) {
		this.image = image;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintComponent(Graphics g) {
		// super.paintComponent(g);

		g.drawImage(image, 0, 0, 1024, 784, this);
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
			graphPoints
					.add(new Point(nodes.get(i).getX(), nodes.get(i).getY()));
		}
		// In duong lam viec
		Stroke oldStroke = g2.getStroke();
		g2.setColor(GRAPH_COLOR);
		g2.setStroke(GRAPH_STROKE);
		if (edges == null)
			return;
		int a = 0, b =0 ;
		for (int i = 0; i < edges.size(); i++) {
			for (int j = 0; j < path_mid.size(); j++) {
				if (nodes.get(edges.get(i).getSource()).getId() == path_mid.get(j).getSrc()
						&& nodes.get(edges.get(i).getDestination()).getId() == path_mid.get(j).getDest()) {
					a++;
					path_mid.get(j).getListX().add(0, nodes.get(edges.get(i).getSource()).getX());
					path_mid.get(j).getListY().add(0, nodes.get(edges.get(i).getSource()).getY());
					path_mid.get(j).getListX().add(nodes.get(edges.get(i).getDestination()).getX());
					path_mid.get(j).getListY().add(nodes.get(edges.get(i).getDestination()).getY());
					g2.drawPolyline(
							convertIntArray(path_mid.get(j).getListX()),
							convertIntArray(path_mid.get(j).getListY()),
							path_mid.get(j).getListX().size());
					}
				 else if (nodes.get(edges.get(i).getSource()).getId() != path_mid.get(j).getSrc()
						&& nodes.get(edges.get(i).getDestination()).getId() != path_mid.get(j).getDest())
				 {
					 b++;
				 g2.drawLine(nodes.get(edges.get(i).getSource()).getX(),nodes.get(edges.get(i).getSource()).getY(),
				 nodes.get(edges.get(i).getDestination()).getX(),nodes.get(edges.get(i).getDestination()).getY());
				 System.out.println(b);
				 }
			}
		}
		//In duong backup
		g2.setStroke(oldStroke);
		g2.setColor(GRAPH_COLOR_new);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (edges_new[i][j]) {
					g2.drawLine(nodes.get(i).getX(), nodes.get(i).getY(), nodes
							.get(j).getX(), nodes.get(j).getY());
				}
			}
		}
		g2.setStroke(oldStroke);
		g2.setColor(GRAPH_POINT_COLOR);
		for (int i = 0; i < graphPoints.size(); i++) {
			if (nodes.get(i).getDis() != 0) {
				int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
				int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
				int ovalW = GRAPH_POINT_WIDTH;
				int ovalH = GRAPH_POINT_WIDTH;
				g2.fillOval(x, y, ovalH, ovalW);
			}
		}

		// In thong so
		g2.drawString("Number of nodes: " + nodes.size(), 1030, 50);
		g2.drawString("Number of initial links: " + edges.size(), 1030, 70);
		// System.out.println(num_edges_new);
		g2.drawString("Initial network cost: ", 1030, 90);
		g2.drawString(" " + old_cost, 1030, 110);
		g2.drawString("Number of addition links: " + num_edges_new, 1030, 130);
		g2.drawString("Additional network cost: ", 1030, 150);
		g2.drawString(" " + (cost - old_cost), 1030, 170);
		g2.drawString("Total network cost: ", 1030, 190);
		g2.drawString(" " + cost, 1030, 210);
		// ve diem voi ham fillOval(toa do x, toa do y, kich thuoc, kick thuoc)
	}

	private int[] convertIntArray(List<Integer> integers) {
		{
			int[] ret = new int[integers.size()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = integers.get(i).intValue();
			}
			return ret;
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	private void open_file() throws IOException {
		File file = new File(
				"/home/phuong-hoang/Desktop/Data/result/OutFile.txt");
		Desktop dt = Desktop.getDesktop();
		dt.open(file);
	}

	public void run(final List<Node> nodes, final List<Edge> edges,
			final boolean edges_new[][], final float old_cost,
			final float cost, final int n, final List<Node> node_mid,
			final List<Path_Physical> path_mid) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				BufferedImage myImage = null;
				DrawGraph mainPanel = new DrawGraph(nodes, edges, edges_new,
						old_cost, cost, n, node_mid, path_mid);
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
				frame.setLayout(null);
				frame.setLocationByPlatform(true);
				JButton button = new JButton("Show result");
				button.setSize(new Dimension(100, 30));
				button.setBounds(1030, 10, 120, 30);
				frame.getContentPane().add(button);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							open_file();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				// button.addActionListener (new Action1());
				//
				// frame.setSize(500,500);
				// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				// ImageIcon image = new
				// ImageIcon("/home/phuong-hoang/Desktop/Data/Archive/anh.jpg");
				// JLabel label = new JLabel("", image, JLabel.CENTER);
				// JPanel panel = new JPanel(new BorderLayout());
				// panel.add( label, BorderLayout.CENTER );
				// // Make me last
				// frame.setVisible(true);

				frame.setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		// new DrawGraph();

	}
}