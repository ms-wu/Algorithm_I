import java.util.Iterator;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Board {
	private int blocks[][]; // this array is used to save the numbers'sequence
	private int N; // dimension
	private int x_pos, y_pos;
	private int hamming;
	private int manhattan;
	
	public Board(int[][] blocks) { // construct a board from an n-by-n array of blocks
		if (blocks == null)
			throw new java.lang.IllegalArgumentException();
		
		N = blocks.length; // matrix's rol equal to dimension
		this.blocks = new int[N][N];
		// init the blocks
		for (int i = 0; i < N; i++)
			for(int j = 0; j < N ; j++) {
				this.blocks[i][j] = blocks[i][j];
				if (blocks[i][j] == 0) {
					x_pos = i;
					y_pos = j;
				}
			}
		
		// compute hamming
		hamming = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;
				if (blocks[i][j] != i * N + j + 1)
					hamming++;
			}
		}
		
		// compute manhattan
		manhattan = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;
				if (blocks[i][j] != i * N + j + 1) {
					manhattan += (Math.abs((blocks[i][j]-1)/N - i) + Math.abs((blocks[i][j]-1)%N - j));
				}
			}
		}
	}
	// (where blocks[i][j] = block in row i, column j)
	public int dimension() { // board dimension n
		return N;
	}
	
	public int hamming() { // number of blocks out of place
		return hamming;
	}
	
	public int manhattan() { // sum of Manhattan distances between blocks and goal
		return manhattan;
	}
	
	public boolean isGoal() { // is this board the goal board?
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;
				if (blocks[i][j] != i * N + j + 1)
					return false;
			}
		return true;
	}
	
	public Board twin() { // a board that is obtained by exchanging any pair of blocks
		int tempBlocks[][] = new int[N][N];
		int x = -1, y = -1;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (j < N-1 && blocks[i][j] != 0 && blocks[i][j+1] != 0) {
					x = i;
					y = j;
				}
				tempBlocks[i][j] = blocks[i][j];
			}
		}
		
		if (x == -1 && y == -1)
			throw new java.lang.IllegalArgumentException();
		int temp = tempBlocks[x][y];
		tempBlocks[x][y] = tempBlocks[x][y+1];
		tempBlocks[x][y+1] = temp;
		return new Board(tempBlocks);
	}
	
	public boolean equals(Object y) { // does this board equal y?
		if (this == y)
			return true;
		if (y == null)
			return false;
		if (this.getClass() != y.getClass())
			return false;
		Board that = (Board) y;
		if (this.dimension() != that.dimension())
			return false;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (this.blocks[i][j] != that.getBlocks(i, j))
					return false;
			}
		return true;
	}
	
	// get the private elements
	private int getBlocks(int i, int j) {
		return blocks[i][j];
	}
	
	public Iterable<Board> neighbors(){ // all neighboring boards
		Queue<Board> queue = new Queue<Board>();
		int [][]dirxy = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // up down left right
		
		for (int i = 0;i < 4; i++) {
			int x = x_pos + dirxy[i][0];
			int y = y_pos + dirxy[i][1];
			
			if (x < N && y < N && x >= 0 && y >= 0) {
				//move the blank
				int temp = blocks[x_pos][y_pos];
				blocks[x_pos][y_pos] = blocks[x][y];
				blocks[x][y] = temp;
				queue.enqueue(new Board(blocks));
				
				//change back
				temp = blocks[x_pos][y_pos];
				blocks[x_pos][y_pos] = blocks[x][y];
				blocks[x][y] = temp;
			}
		}
		return queue;
	}
	
	public String toString() { // string representation of this board (in the output format specified below)
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i< N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d", blocks[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	public static void main(String[] args) {
		int test[][] = {
				{8, 0, 3},
				{4, 1, 2},
				{7, 6, 5}
		};
		Board b = new Board(test);
		Board bt = b.twin();
		StdOut.print(b.toString());
		for (Board t : b.neighbors()) {
			StdOut.print(t.toString());
			StdOut.print("hamming: " + t.hamming() + "\n");
			StdOut.print("manhattan: " + t.manhattan() + "\n");
		}
	}
}
