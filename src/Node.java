public class Node {
	private int x;
	private int y;
	// private String name;
	private int id;

	public Node(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Node(int x, int y, int id) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Node" + id;
	}
}
