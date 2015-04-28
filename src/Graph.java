import java.util.*;

public class Graph {
	final public int MAX = 1000;
	private int v, e;
	public List<Node> node;
	public List<Edge> edge;
	private boolean connected[][];
	private float distance[][];

	// public void getData(){
	// GetData a = new GetData();
	// a.SetAllValue();
	// a.Print_Link();
	// node = a.LISTNODE;
	// edge = a.EDGE;
	// connected = new boolean[a.LISTNODE.size()][a.LISTNODE.size()];
	// }
	public Graph(int v, int e, List<Node> node, List<Edge> edge,
			float distance[][]) {
		// Only allow positive number of vertices
		if (v > 0) {
			this.node = node;
			this.edge = edge;
			this.e = 0;
			this.v = v;
			this.distance = distance;
			// Connected array is automatically initialized with “false”
			connected = new boolean[v][v];
		}
	}

	public boolean add(Node u, Node v) {
		if (!isValidNode(u) || !isValidNode(v) || (u == v) || contains(u, v))
			return false;
		connected[u.getId()][v.getId()] = true;
		connected[v.getId()][u.getId()] = true;
		this.e++;
		return true;
	}

	public boolean remove(Node u, Node v) {
		if (!isValidNode(u) || !isValidNode(v) || (u == v) || !contains(u, v))
			return false;
		connected[u.getId()][v.getId()] = false;
		connected[v.getId()][u.getId()] = false;
		this.e--;
		return true;
	}

	public List<Node> adj(Node u) {
		if (!isValidNode(u))
			return null;
		LinkedList<Node> adjList = new LinkedList<Node>();
		for (int i = 0; i < this.node.size(); i++)
			if (connected[u.getId()][i])
				adjList.add(node.get(i));
		return (List<Node>) Collections.unmodifiableList(adjList);
	}

	public boolean contains(Node u, Node v) {
		if (!isValidNode(u) || !isValidNode(v))
			return false;
		else
			return connected[u.getId()][v.getId()];
	}

	public int v() {
		return v;
	}

	public int e() {
		return e;
	}

	// dijkstra
	public List<Node> dijkstra(Node src, Node dest) {
		List<Node> workpath1 = new ArrayList<Node>();
		int workpath[] = new int[node.size()];
		float minDistances[] = new float[MAX];
		boolean check[] = new boolean[MAX];

		check[src.getId()] = true;
		// khoi tao mang khoang cach
		for (int i = 0; i < node.size(); i++) {
			//workpath[i] = i;
			if (connected[src.getId()][node.get(i).getId()]) {
				minDistances[i] = distance[src.getId()][node.get(i).getId()];
//				System.out.println(i + " : " + minDistances[node.get(i).getId()]);
			} else {
				if (node.get(i) != src) {
					minDistances[i] = Float.MAX_VALUE;
//					System.out.println(i + " : MAX");
				}else {
					minDistances[node.get(i).getId()] = 0;
				}
			}
			if (node.get(i) != src)
				check[node.get(i).getId()] = false;
//			System.out.println("Node"+node.get(i).getId() + " " +check[node.get(i).getId()]);
		}
		
		
		List<Node> adj1 = adj(src);
//		System.out.println(adj1);
		for (int i = 0; i < adj1.size(); i++) {
			workpath[adj1.get(i).getId()] = src.getId();
//			System.out.println( adj1.get(i).getId() +">>>" + workpath[adj1.get(i).getId()]);
		}
		minDistances[src.getId()] = 0;

		for (int i = 0; i < node.size(); i++) {
			float min = Float.MAX_VALUE;
			int minV = -1;
			for (int w = 0; w < node.size(); w++) {
				if (minDistances[node.get(w).getId()] < min && !check[node.get(w).getId()]) {
					minV = node.get(w).getId();
					min = minDistances[node.get(w).getId()];
				}
			}

			if (minV == -1)
				break;
			check[minV] = true;

			// Them v vao trong workpath
//			System.out.println("---------------------------------");
//			System.out.println("minV: " + minV + "-" + minDistances[minV]);
//			List<Node> listAdj = adj(node.get(minV));
//			System.out.println(" ==>>>>>>>>>");
//			System.out.println(listAdj);
			for (int w = 0; w < node.size(); w++) {
				if (connected[node.get(minV).getId()][node.get(w).getId()]) {
					// System.out.println(w+ " " + listAdj.get(w) + " " +
					// minDistances[listAdj.get(w)]);

					if (minDistances[node.get(w).getId()] > minDistances[node.get(minV).getId()]
							+ distance[node.get(minV).getId()][node.get(w).getId()]) {
//						System.out.println(node.get(w).getId() + " - " + minDistances[node.get(w).getId()]);
						minDistances[node.get(w).getId()] = minDistances[node.get(minV).getId()]
								+ distance[node.get(minV).getId()][node.get(w).getId()];
						workpath[node.get(w).getId()] = node.get(minV).getId();
						//System.out.println(w + " - " + minDistances[w]);
						//System.out.println("set parent: " + node.get(minV).getId() + " - " + node.get(w).getId());
					}
				}
			}
		}
//		 System.out.println("sadkjshdkjashsakjdhsajdhajka");
		 while (dest != src) {
		 workpath1.add(dest);
//		 System.out.print(dest + "-");
		 dest = node.get(workpath[dest.getId()]);
		 }
//		for (int i = 0; i < node.size(); i++) {
//			System.out.println("Parent of " + node.get(i).getId() + ": " + workpath[node.get(i).getId()]);
//		}
		workpath1.add(src);
//		 System.out.print(src);
		return workpath1;
	}

	public void displayGraph() {
		System.out.println("****GRAPH*****");
		System.out.println("Number of vertices: " + this.v);
		System.out.println("Number of edges: " + this.e);
		System.out.println("Connection - Matrix");
		for (int i = 0; i < this.node.size(); i++) {
			for (int j = 0; j < this.node.size(); j++)
				System.out.print(connected[i][j] ? "1 " : "0 ");
			System.out.println();
		}
		System.out.println("***************");
	}

	private boolean isValidNode(Node u) {
		return (u.getId() >= 0) && (u.getId() <= this.v - 1);
	}
	
	public float Cost(){
		float s = 0;
		for (int i = 0; i < edge.size(); i++) {
			s += distance[edge.get(i).getSource()][edge.get(i).getDestination()];
		}
		return s;
	}
}