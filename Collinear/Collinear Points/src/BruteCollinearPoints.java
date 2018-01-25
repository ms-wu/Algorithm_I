import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private int lineNumber;
	private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
	
	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new java.lang.NullPointerException();
		lineNumber = 0;
		
		int num = points.length;
		Point[] copy = new Point[num];
		
		for (int i = 0; i < num; i++) {
			if (points[i] == null)
				throw new java.lang.IllegalArgumentException();
			
			for (int j = i + 1; j < num; j++) {
				if (points[i].compareTo(points[j]) == 0) {
					throw new java.lang.IllegalArgumentException();
				}
			}
			copy[i] = points[i];
		}
		Arrays.sort(copy);
		
		for (int i = 0; i < num - 3; i++) {
			for (int j = i + 1; j < num - 2; j++) {
				double slope1 = copy[i].slopeTo(copy[j]);
				for (int k = j + 1; k < num - 1; k++) {
					double slope2 = copy[i].slopeTo(copy[k]);
					if (Double.compare(slope1, slope2) != 0)
						continue;
					int temp = 0;
					for (int m = k + 1; m < num; m++) {
						double slope3 = copy[i].slopeTo(copy[m]);
						if (Double.compare(slope1, slope3) == 0) {
							temp = m;
						}
						if (m == num - 1 && temp != 0) { // we can find the last point in a line
							lineNumber++;
							segments.add(new LineSegment(copy[i], copy[temp]));
						}
					}
				}
			}
		}
	}
	
	public int numberOfSegments() {
		return lineNumber;
	}
	
	public LineSegment[] segments() {
		LineSegment[] results = new LineSegment[lineNumber];
		for (int i = 0; i < lineNumber; i++) {
			results[i] = segments.get(i);
		}
		return results;
	}
	
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // draw the points
	    StdDraw.enableDoubleBuffering();
	    StdDraw.setXscale(0, 32768);
	    StdDraw.setYscale(0, 32768);
	    for (Point p : points) {
	        p.draw();
	    }
	    StdDraw.show();

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
