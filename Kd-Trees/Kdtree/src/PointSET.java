import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PointSET {
	private SET<Point2D> set;
	
	public PointSET() { // construct an empty set of points 
		set = new SET<Point2D>();
	}
	public boolean isEmpty() { // is the set empty? 
		return set.isEmpty();
	}
	public int size() { // number of points in the set 
		return set.size();
	}
	public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
		if (p == null)
			throw new java.lang.IllegalArgumentException();
		set.add(p);
	}
	public boolean contains(Point2D p) { // does the set contain point p? 
		if (p == null)
			throw new java.lang.IllegalArgumentException();
		return set.contains(p);
	}
	public void draw() { // draw all points to standard draw 
		for (Point2D p : set) {
			p.draw();
		}
	}
	public Iterable<Point2D> range(RectHV rect) { // all points that are inside the rectangle (or on the boundary) 
		if (rect == null)
			throw new java.lang.IllegalArgumentException();
		Queue<Point2D> queue = new Queue<Point2D>();
		for (Point2D p : set) {
			if (rect.contains(p)) {
				queue.enqueue(p);
			}
		}
		return queue;
	}
	public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
		if (p == null)
			throw new java.lang.IllegalArgumentException();
		Point2D t = null;
		double dist = Double.MAX_VALUE;
		for (Point2D point : set) {
			if (p.distanceTo(point) < dist) {
				t =  point;
				dist = p.distanceTo(point);
			}
		}
		return t;
	}
	public static void main(String[] args) { // unit testing of the methods (optional)
//		PointSET s = new PointSET();
//		for (int i = 0; i < 1000;i++) {
//			Point2D p = new  Point2D(StdRandom.uniform(), StdRandom.uniform());
//			s.insert(p);
//		}
//		s.draw();
//		StdOut.println(s.size());
//		RectHV r = new RectHV(0.1, 0.1, 0.2, 0.2);
//		StdOut.println("the nearest point to (0.5, 0.5) is " + s.nearest(new Point2D(0.5, 0.5)).x() + " " + s.nearest(new Point2D(0.5, 0.5)).y());
//		for(Point2D t : s.range(new RectHV(0.1, 0.1, 0.2, 0.2))) {
//			StdOut.print(t.x() + " " + t.y() + "\n");
//		}
	}
}
