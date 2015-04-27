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

	public boolean add(int u, int v) {
		if (!isValidNode(u) || !isValidNode(v) || (u == v) || contains(u, v))
			return false;
		connected[u][v] = true;
		connected[v][u] = true;
		this.e++;
		return true;
	}

	public boolean remove(int u, int v) {
		if (!isValidNode(u) || !isValidNode(v) || (u == v) || !contains(u, v))
			return false;
		connected[u][v] = false;
		connected[v][u] = false;
		this.e--;
		return true;
	}

	public List<Integer> adj(int u) {
		if (!isValidNode(u))
			return null;
		LinkedList<Integer> adjList = new LinkedList<Integer>();
		for (int i = 0; i < this.v; i++)
			if (connected[u][i])
				adjList.add(i);
		return (List<Integer>) Collections.unmodifiableList(adjList);
	}

	public boolean contains(int u, int v) {
		if (!isValidNode(u) || !isValidNode(v))
			return false;
		else
			return connected[u][v];
	}

	public int v() {
		return v;
	}

	public int e() {
		return e;
	}

	// dijkstra
	public List<Integer> dijkstra(int src, int dest) {
		List<Integer> workpath1 = new ArrayList<Integer>();
		int workpath[] = new int[node.size()];
		float minDistances[] = new float[MAX];
		boolean check[] = new boolean[MAX];

		check[src] = true;
		// khoi tao mang khoang cach
		for (int i = 0; i < node.size(); i++) {
			workpath[i] = i;
			if (connected[src][i]) {
				minDistances[i] = distance[src][i];
				System.out.println(i + " : " + minDistances[i]);
			} else {
				if (i != src) {
					minDistances[i] = Float.MAX_VALUE;
					System.out.println(i + " : MAX");
				}else {
					minDistances[i] = 0;
				}
			}
			if (i != src)
				check[i] = false;
		}
		List<Integer> adj1 = adj(src);
		for (Integer integer : adj1) {
			workpath[integer] = src;
		}

		minDistances[src] = 0;

		for (int i = 0; i < node.size(); i++) {
			float min = Float.MAX_VALUE;
			int minV = -1;
			for (int w = 0; w < node.size(); w++) {
				if (minDistances[w] < min && !check[w]) {
					minV = w;
					min = minDistances[w];
				}
			}

			if (minV == -1)
				break;
			check[minV] = true;

			// Them v vao trong workpath
			System.out.println("---------------------------------");
			System.out.println("minV: " + minV + "-" + minDistances[minV]);
			List<Integer> listAdj = adj(minV);
			System.out.println(listAdj);
			for (int w = 0; w < node.size(); w++) {
				if (connected[minV][w]) {
					// System.out.println(w+ " " + listAdj.get(w) + " " +
					// minDistances[listAdj.get(w)]);

					if (minDistances[w] > minDistances[minV]
							+ distance[minV][w]) {
						System.out.println(w + " - " + minDistances[w]);
						minDistances[w] = minDistances[minV]
								+ distance[minV][w];
						workpath[w] = minV;
						System.out.println(w + " - " + minDistances[w]);
						System.out.println("set parent: " + minV + " - " + w);
					}
				}
			}
		}
		// System.out.println("sadkjshdkjashsakjdhsajdhajka");
		 while (dest != src) {
		 workpath1.add(dest);
		 System.out.print(dest + "-");
		 dest = workpath[dest];
		 }
		for (int i = 0; i < node.size(); i++) {
			System.out.println("Parent of " + i + ": " + workpath[i]);
		}
		workpath1.add(src);
		// System.out.print(src);
		return workpath1;
	}

	public void displayGraph() {
		System.out.println("****GRAPH*****");
		System.out.println("Number of vertices: " + this.v);
		System.out.println("Number of edges: " + this.e);
		System.out.println("Connection - Matrix");
		for (int i = 0; i < this.v; i++) {
			for (int j = 0; j < this.v; j++)
				System.out.print(connected[i][j] ? "1 " : "0 ");
			System.out.println();
		}
		System.out.println("***************");
	}

	private boolean isValidNode(int u) {
		return (u >= 0) && (u <= this.v - 1);
	}
}