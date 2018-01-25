import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point>{
	final private int x;
	final private int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw() { // draws this point
		StdDraw.point(x, y);
	}
	
	public void drawTo(Point that) {
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	public String toString() { // this part is used to debugging
		return "("+ x +"),("+ y +")";
	}
	
	@Override
	public int compareTo(Point that) {
		if (this.y < that.y || (this.y == that.y && this.x < that.x))
			return -1;
		else if (this.y == that.y && this.x == that.x)
			return 0;
		else
			return 1;
	}
	
	public double slopeTo(Point that) {
		if (that == null)
			throw new java.lang.NullPointerException();
		if (this.x == that.x && this.y == that.y)
			return Double.NEGATIVE_INFINITY;
		else if (this.x == that.x)
			return Double.POSITIVE_INFINITY;
		else if (this.y == that.y)
			return 0;
		else
			return (this.x - that.x)/(double)(this.y - that.y);
	}
	
	public Comparator<Point> slopeOrder() {
		return  new SlopeOrder(this);
	}
	
	private static class SlopeOrder implements Comparator<Point> {
		final private Point p;
		public SlopeOrder(Point point) {
			p = point;
		}

		@Override
		public int compare(Point p1, Point p2) {
			double d1 = p.slopeTo(p1);
			double d2 = p.slopeTo(p2);
			if (d1 < d2)
				return -1;
			else if (d1 > d2)
				return 1;
			else
				return 0;
		}
		public static void main(String[] args) {
			// test
		}
	}
}
