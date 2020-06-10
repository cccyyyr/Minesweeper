
public class OrderedPair implements Comparable<OrderedPair>{
	private final int x;
	private final int y;
	public OrderedPair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() { return x; }
	public int getY() { return y; }

	@Override
	public int compareTo(OrderedPair o) {		
		if (this.getX() < o.getX()) {
			return -1;
		} else if (this.getX() > o.getX()) {
			return 1;
		}
		if (this.getY() < o.getY()) {
			return -1;
		} else if (this.getY() > o.getY()) {
			return 1;
		} 
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof OrderedPair) {
			OrderedPair op = (OrderedPair) o;
			return (this.compareTo(op) == 0);
		}
		return false;
	}
	
	
}
