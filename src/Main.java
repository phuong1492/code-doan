import java.util.ArrayList;


public class Main {
	public static void main(String[] args) {

		GetData data = new GetData();
		data.SetAllValue();
		//data.Print_Link();
		System.out.println(data.N_NODE);
		//data.Print_List();
		Graph g = new Graph(data.N_NODE, data.EDGE.size(), data.LISTNODE, data.EDGE, data.DISTANCE);
		System.out.println(data.EDGE.size());
		for(int i = 0; i < g.edge.size(); i++){
			g.add(g.node.get(g.edge.get(i).getSource()),g.node.get(g.edge.get(i).getDestination()));
		}
		g.displayGraph();
//		for (int i = 0; i < g.dijkstra(0, 4).length; i++) {
//			System.out.println(g.dijkstra(0, 4)[i]);
//		}
		java.util.List<Node> list = new ArrayList<Node>();
		list = g.dijkstra(g.node.get(0), g.node.get(2));
		for (int i = list.size()-1; i >= 0; i--) {
			System.out.print(list.get(i)+"\t");
		}
//		System.out.println(g.adj(g.node.get(3)));
	}

}
