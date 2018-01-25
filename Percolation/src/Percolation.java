import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private final WeightedQuickUnionUF uf1;  // a unionUF with two virtual site
	private final WeightedQuickUnionUF uf2;  // a unionUF with one virtual sites
	private boolean[][] grid;   // matrix
	private final int N;   // length and width
	private int openNumber;
	
	// create N by N grid, with all sites blocked
	public Percolation(int N) {
		if (N < 1)
			throw new java.lang.IllegalArgumentException();
		
		openNumber = 0;
		this.N = N;
		grid = new boolean[N+1][N+1];// N*N
		uf1 = new WeightedQuickUnionUF(N * N + 2);
		uf2 = new WeightedQuickUnionUF(N * N + 1);
	}
	
	// open site (col = p, row = q)
	public void open(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		if (!grid[i][j]) 
		{
			if (i == 1)  // on the fisrt col
			{
				uf1.union(0, j);
				uf2.union(0, j);
			}
			if (i == N)  // on the last col
			{
				uf1.union(N * N + 1, (i-1) * N + j);
			}
			grid[i][j] = true;
			openNumber++;
			// judge this site is full or not
			// left
			if (j > 1) 
			{
				if (isOpen(i, j - 1))
				{
					uf1.union((i - 1) * N + j, (i - 1) * N + j - 1);
					uf2.union((i - 1) * N + j, (i - 1) * N + j - 1);
				}
			}
			
			// right
			if (j < N) 
			{
				if (isOpen(i, j + 1))
				{
					uf1.union((i - 1) * N + j, (i - 1) * N + j + 1);
					uf2.union((i - 1) * N + j, (i - 1) * N + j + 1);
				}
			}
			
			// up
			if (i > 1) 
			{
				if (isOpen(i - 1, j))
				{
					uf1.union((i - 1) * N + j, (i - 1 - 1) * N + j);
					uf2.union((i - 1) * N + j, (i - 1 - 1) * N + j);
				}
			}
			
			// down
			if (i < N) 
			{
				if (isOpen(i + 1, j))
				{
					uf1.union((i - 1) * N + j, (i - 1 + 1) * N + j);
					uf2.union((i - 1) * N + j, (i - 1 + 1) * N + j);
				}
			}
		}
	}
	
	// judge site(col, rol) is open?
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		if (grid[i][j])
			return true;
		return false;
	}
	
	// judge this site is full
	public boolean isFull(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N)
		{
			throw new java.lang.IllegalArgumentException();
		}
		
		if (grid[i][j])
			if(uf2.connected(0, (i - 1) * N + j))  // avoid backwater
					return true;
		return false;
	}
	
	public int numberOfOpenSites() {
		return openNumber;
	}
	
	// does the system percolate?
	public boolean percolates() {
		return uf1.connected(0, N * N + 1);
	}
	
	public static void main(String []args) {
		// test
	}
}
