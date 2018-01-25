import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private int lineNumber;
	private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
	
	public FastCollinearPoints(Point[] points) {
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
		
		for (int i = 0; i < num; i++) {
			Point[] temp = new Point[num-1];
			int tempNumber = 0;
			for (int j = 0; j < num; j++) {
				if (i != j)
					temp[tempNumber++] = copy[j];
			}
			
			Arrays.sort(temp, copy[i].slopeOrder());
			
			int count = 0;
			Point right = null;
			Point left = null;
			
			for (int j = 0; j < tempNumber - 1; j++) {
				if (copy[i].slopeTo(temp[j]) == copy[i].slopeTo(temp[j + 1])) {
					if (left == null && right == null) {
						if (copy[i].compareTo(temp[j]) == 1) {
							right = copy[i];
							left = temp[j];
						}
						else {
							right = temp[j];
							left = copy[i];
						}
					}
					
					if (left.compareTo(temp[j + 1]) == 1) {
						left = temp[j + 1];
					}
					if (right.compareTo(temp[j + 1]) == -1) {
						right = temp[j + 1];
					}
					
					count++;
					if (j == tempNumber - 2) {
						if (count >= 2 && copy[i].compareTo(left) == 0) {
							segments.add(new LineSegment(left, right));
							lineNumber++;
						}
						count = 0;
						left = null;
						right = null;
					}
				}
				else {
					if (count >= 2 && copy[i].compareTo(left) == 0) {
						segments.add(new LineSegment(left, right));
						lineNumber++;
					}
					count = 0;
					left = null;
					right = null;
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
