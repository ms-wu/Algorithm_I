import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class KdTree {
	private Node root;
	private int N;
	private final RectHV defaultRoot = new RectHV(0, 0, 1, 1);
	
	private static class Node {
		private Point2D p; // the point
		private RectHV rect; // the axis-aligned rectangle corresponding to this node
		private Node lb; //the left/bottom subtree
		private Node rt; // the right/top subtree
		
		public Node(Point2D point, RectHV r) {
			p = point;
			rect = r;
			lb = null;
			rt = null;
		}
	}
	
	public KdTree(){
		root = null;
		N = 0;
	}
	
	public boolean isEmpty() { // is the set empty? 
		return N == 0;
	}
	
	public int size() { // number of points in the set 
		return N;
	}
	
	private int compareTo(Point2D l, Point2D r, boolean hor) {
		if (l == null || r == null)
			throw new java.lang.IllegalArgumentException();
		if (l.equals(r)) return 0;
		if(hor) {
			if (l.y() < r.y())
				return -1;
			else
				return 1;
		}
		else {
			if (l.x() < r.x())
				return -1;
			else
				return 1;
		}
	}
	
	public void insert(Point2D p) { // add the point to the set (if it is not already in the set)
		if (p == null) throw new java.lang.IllegalArgumentException();
		// in case the root is null, use defaultRoot as argument
		root = insert(root, p, defaultRoot.xmin(), defaultRoot.ymin(), defaultRoot.xmax(), defaultRoot.ymax(), false);
	}
	
	private Node insert(Node x, Point2D p, double xmin, double ymin, double xmax, double ymax, boolean hor) {
		if (x == null) {
			N++;
			return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
		}
		int cmp = compareTo(p, x.p, hor);
		if (cmp < 0) {
			if (hor) 
				x.lb = insert(x.lb, p, x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y(), !hor);
			else
				x.lb = insert(x.lb, p, x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax(), !hor);
		}
		else if (cmp > 0)
			if (hor)
				x.rt = insert(x.rt, p, x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax(), !hor);
			else
				x.rt = insert(x.rt, p, x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax(), !hor);
		else {
			// ignore the same point
		}
		return x;
	}
	
	public boolean contains(Point2D p) { // does the set contain point p? 
		if (p == null) throw new java.lang.IllegalArgumentException();
		return get(root, p, false) != null;
	}
	
	private Point2D get(Node x, Point2D p, boolean hor) {
		if (x == null) return null;
		int cmp = compareTo(p, x.p, hor);
		if (cmp < 0)
			return get(x.lb, p, !hor);
		else if (cmp > 0)
			return get(x.rt, p, !hor);
		else
			return x.p;
	}
	
	public void draw() {
		//draw the background
		StdDraw.setScale(0, 1);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius();
		defaultRoot.draw();
		draw(root, false);
	}
	
	private void draw(Node x, boolean hor) { // draw all points to standard draw 
		if (x == null) return;
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(0.01);
		StdDraw.point(x.p.x(), x.p.y());
		if(hor) {
			StdDraw.setPenColor(StdDraw.BLUE);
			StdDraw.setPenRadius();
			StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		}
		else {
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setPenRadius();
			StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		}
		draw(x.lb, !hor);
		draw(x.rt, !hor);
	}
	
	public Iterable<Point2D> range(RectHV rect){ // all points that are inside the rectangle (or on the boundary) 
		Queue<Point2D> point = new Queue<Point2D>();
		return range(root, rect, point);
	}
	
	private Iterable<Point2D> range(Node x, RectHV rect, Queue<Point2D> point) {
		if (x == null) return point;
		if (rect.contains(x.p)) point.enqueue(x.p);
		if (x.lb != null && rect.intersects(x.lb.rect)) point = (Queue<Point2D>) range(x.lb, rect, point);
		if (x.rt != null && rect.intersects(x.rt.rect)) point = (Queue<Point2D>) range(x.rt, rect, point);
		return point;
	}
	
	public Point2D nearest(Point2D p) { // a nearest neighbor in the set to point p; null if the set is empty 
		if (root == null) return null;
		if (p == null) throw new java.lang.IllegalArgumentException();
		double dist = Double.MAX_VALUE;
		return nearest(root, root.p, p, false);
	}
	private Point2D nearest(Node x, Point2D nearest, Point2D p, boolean hor) {
		if (x == null) return nearest;
		int cmp = compareTo(p, x.p, hor); // judge this point is the given point's left or right, if is left, search the left subtree
		
		if (p.distanceTo(x.p) < p.distanceTo(nearest)) nearest = x.p;
		if (cmp < 0) {
			if (x.lb != null)
				nearest = nearest(x.lb, nearest, p, !hor);
			if (x.rt != null && x.rt.rect.distanceTo(p) < nearest.distanceTo(p))
				nearest = nearest(x.rt, nearest, p, !hor);
		}
		if (cmp > 0) {
			if (x.rt != null)
				nearest = nearest(x.rt, nearest, p, !hor);
			if (x.lb != null && x.lb.rect.distanceTo(p) < nearest.distanceTo(p))
				nearest = nearest(x.lb, nearest, p, !hor);
		}
		return nearest;
	}
	
	public static void main(String[] args) {
//		KdTree tree = new KdTree();
//		for (int i = 0; i < 1000;i++) {
//			Point2D p = new Point2D(StdRandom.uniform(), StdRandom.uniform());
//			tree.insert(p);
//		}
//		StdOut.print(tree.size() + "\n");
//		tree.draw();
//		RectHV r = new RectHV(0, 0, 0.2, 0.2);
//		StdOut.println("the nearest point to (0.5, 0.5) is " + tree.nearest(new Point2D(0.5, 0.5)).x() + " " + tree.nearest(new Point2D(0.5, 0.5)).y());
//		int i = 0;
//		for(Point2D t : tree.range(new RectHV(0.1, 0.1, 0.2, 0.2))) {
//			StdOut.print(++i + " " + t.x() + " " + t.y() + "\n");
//		}
	}
}
