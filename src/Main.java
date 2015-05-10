import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		// List<Integer> score = new ArrayList<Integer>();

		GetData data = new GetData();
		 //DrawGraph form = new DrawGraph(score);
		data.SetAllValue();
		// data.Print_List();
		Graph g = new Graph(data.N_NODE, data.EDGE.size(), data.LISTNODE,
				data.EDGE, data.DISTANCE);
		DrawGraph form = new DrawGraph();
		//Form form = new Form();
		for (int i = 0; i < g.edge.size(); i++) {
			g.add(g.node.get(g.edge.get(i).getSource()),
					g.node.get(g.edge.get(i).getDestination()));
		}
		System.out.println("Chi phi ban dau: " + g.cost);
		for (int i = 0; i < data.REQUEST.size(); i++) {
			System.out.println("\n");
			data.REQUEST.get(i).String();
			java.util.List<Node> list = new ArrayList<Node>();
			java.util.List<Node> list_backup = new ArrayList<Node>();
			list = g.dijkstra(g.node.get(data.REQUEST.get(i).getSrc()),
					g.node.get(data.REQUEST.get(i).getDest()));
			for (int j = list.size() - 1; j >= 0; j--) {
				System.out.print(list.get(j) + "\t");
			}
			System.out.println("\nBackup path: ");
			list_backup = g.dijktra_backup(
					g.node.get(data.REQUEST.get(i).getSrc()),
					g.node.get(data.REQUEST.get(i).getDest()), g.weight);
			for (int j = list_backup.size() - 1; j >= 0; j--) {
				System.out.print(list_backup.get(j) + "\t");
			}
			System.out.println();
		}

		System.out.println("Chi phi toan mang: " + g.cost);
		form.run(g.node, g.edge, g.new_edge);
	}

}
