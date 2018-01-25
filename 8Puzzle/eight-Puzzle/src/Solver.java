import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private Node targetNode;
	
	private class Node implements Comparable<Node> {
		private Node pre;
		private Board board;
		private int moves;
		private boolean isTwin;
		
		@Override
		public int compareTo(Node that) {
			if (that == null) {
				throw new java.lang.IllegalArgumentException();
			}
			int thisPriority = this.moves + this.board.manhattan();
			int thatPriority = that.moves + that.board.manhattan();
			if (thisPriority > thatPriority)
				return 1;
			else if (thisPriority == thatPriority)
				return 0;
			else
				return -1;
		}
		
	}
	
	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)

		targetNode = new Node();
		targetNode = null;
		MinPQ<Node> minpq = new MinPQ<Node>();
		
		Node init = new Node();
		init.board = initial;
		init.moves = 0;
		init.isTwin = false;
		init.pre = null;
		minpq.insert(init);
		
		Node twininit = new Node();
		twininit.board = initial.twin();
		twininit.moves = 0;
		twininit.isTwin = true;
		twininit.pre = null;
		minpq.insert(twininit);
		
		while(!minpq.isEmpty()) {
			Node currentNode = minpq.delMin();
			Node ncurrentNode;
			// judge the node is goal or not then judge it is twinnode or not
			if (currentNode.board.isGoal()) {
				if (currentNode.isTwin)
					targetNode = null;
				else
					targetNode = currentNode;
				break;
			}
			
			for (Board b : currentNode.board.neighbors()) {
				if ( currentNode.pre == null || !b.equals(currentNode.pre.board)) {
					ncurrentNode = new Node();
					ncurrentNode.board = b;
					ncurrentNode.moves = currentNode.moves + 1;
					ncurrentNode.pre = currentNode;
					ncurrentNode.isTwin = currentNode.isTwin;
					minpq.insert(ncurrentNode);
				}
			}
		}
	}
	
    public boolean isSolvable() {  // is the initial board solvable?
    	return targetNode != null;
    }
    
    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
    	if (isSolvable())
    		return targetNode.moves;
    	else
    		return -1;
    }
    
    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
		Stack<Board> nodeSequence = new Stack<Board>();
		Node temp = targetNode;
		while (temp != null) {
			nodeSequence.push(temp.board);
			temp = temp.pre;
		}
		if (nodeSequence.isEmpty())
			return null;
		else
			return nodeSequence;
    }
    
    public static void main(String[] args) { // solve a slider puzzle (given below)
    	// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
}
