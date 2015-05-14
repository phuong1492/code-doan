import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		// List<Integer> score = new ArrayList<Integer>();
		try {
			PrintStream out = new PrintStream(new FileOutputStream("/home/phuong-hoang/Desktop/Data/result/OutFile.txt"));

			float old_cost;
			int num_edges_new = 0;
			GetData data = new GetData();
			// DrawGraph form = new DrawGraph(score);
			data.SetAllValue();
			// data.Print_List();
			Graph g = new Graph(data.N_NODE, data.EDGE.size(), data.LISTNODE,
					data.EDGE, data.DISTANCE, data.SRG);
			DrawGraph form = new DrawGraph();
			// Form form = new Form();
			 out.println("+ Number of nodes: "+ g.node.size());
			 out.println("+ Number of initial links: " + g.edge.size());
			 out.println("+ Initial network cost: " + g.cost);
			for (int i = 0; i < g.edge.size(); i++) {
				g.add(g.node.get(g.edge.get(i).getSource()),
						g.node.get(g.edge.get(i).getDestination()));
			}
			old_cost = g.cost;
			System.out.println("Chi phi ban dau: " + g.cost);
			for (int i = 0; i < data.REQUEST.size(); i++) {
				System.out.println("\n");
				data.REQUEST.get(i).String();
				out.println("\tWorking path from "+ data.REQUEST.get(i).getSrc() +" to " +data.REQUEST.get(i).getDest());
				java.util.List<Node> list = new ArrayList<Node>();
				java.util.List<Node> list_backup = new ArrayList<Node>();
				list = g.dijkstra(g.node.get(data.REQUEST.get(i).getSrc()),
						g.node.get(data.REQUEST.get(i).getDest()));
				for (int j = list.size() - 1; j >= 0; j--) {
					System.out.print(list.get(j) + "\t");
					if(j == list.size() - 1)
						out.print("\t");
					out.print(list.get(j));
					if(j != 0){
						out.print(" --> ");
					}
				}
				System.out.println("\nBackup path: ");
				out.println("\n\tBackup path: ");
				list_backup = g.dijktra_backup(
						g.node.get(data.REQUEST.get(i).getSrc()),
						g.node.get(data.REQUEST.get(i).getDest()), g.weight);
				for (int j = list_backup.size() - 1; j >= 0; j--) {
					System.out.print(list_backup.get(j) + "\t");
					if(j == list_backup.size() - 1)
						out.print("\t");
					out.print(list_backup.get(j));
					if(j != 0){
						out.print(" --> ");
					}
				}
				System.out.println();
				out.println();
			}

			System.out.println("Chi phi toan mang: " + g.cost);
			out.println("Additonal links is: ");
			for (int i = 0; i < g.node.size(); i++) {
				for (int j = 0; j < i; j++) {
					if (g.new_edge[i][j]){
						num_edges_new += 1;
						out.println("Node" + i + " to Node "+ j);
						}
				}
			}
			out.println("Total network cost: " + g.cost);
//			System.out.println(num_edges_new);
			form.run(g.node, g.edge, g.new_edge, old_cost, g.cost,
					num_edges_new);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
