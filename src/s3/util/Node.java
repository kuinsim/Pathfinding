package s3.util;

/*
 * Suin Kim
 * CS387-801
 * Project 2
 */

public class Node {
	public Node parent;
	public double x;
	public double y;
	public double g;
	public double h;
	
	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double heuristic(Node goal) {
		return Math.abs(goal.x - x) + Math.abs(goal.y - y);
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if ((o == null) || (getClass() != o.getClass())) {
			return false;
		}
		
		Node O = (Node)o;
		
		if ((x != O.x) || (y != O.y)) {
			return false;
		}
		
		return true;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result += prime * x;
		result += prime * y;
		
		return result;
	}
}
