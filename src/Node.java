
public class Node {
	private int x;
	private int y;
	private String name;
	public Node(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Node(int x, int y, String name){
		super();
		this.x = x;
		this.y = y;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return  name+" :[x=" + x + ", y=" + y + "]";
	}
}
