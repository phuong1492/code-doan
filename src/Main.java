
public class Main {
	public static void main(String[] args) {

		GetData data = new GetData();
		data.SetAllValue();
		data.Print_Link();
		System.out.println(data.N_NODE);
		//data.Print_List();
		Graph g = new Graph(data.N_NODE);
		for(int i = 0; i < data.EDGE.size(); i++){
			g.add(data.EDGE.get(i).getSource(),data.EDGE.get(i).getDestination());
		}
		g.displayGraph();
		System.out.println(g.adj(0));
	}

}
