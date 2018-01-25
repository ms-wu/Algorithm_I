import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private final double mean;  //sample mean of percolation threshold
	private final double stddev;  // sample standard deviation of percolation threshold
	private final double confidenceLo;  //low  endpoint of 95% confidence interval
	private final double confidenceHi;  //high endpoint of 95% confidence interval
	private static double constent = 1.96;
	
	public PercolationStats(int n, int trials) {
		if (n < 1 || trials < 1)
			throw new java.lang.IllegalArgumentException();
		
		Percolation p;
		double results[] = new double[trials];
		for (int i = 0; i < trials; i++)
		{
			p = new Percolation(n);
			results[i] = 0;
			while(!p.percolates())
			{
				//find a site blocked
				int x = StdRandom.uniform(n) + 1;
				int y = StdRandom.uniform(n) + 1;
				while(p.isOpen(x, y))
				{
					x = StdRandom.uniform(n) + 1;
					y = StdRandom.uniform(n) + 1;
				}
				p.open(x, y);
				results[i]++;
			}
			results[i] = p.numberOfOpenSites()/(n * n * 1.0);
		}
		mean = StdStats.mean(results);
		stddev = StdStats.stddev(results);
		confidenceLo = mean - constent * stddev/Math.sqrt(trials);
		confidenceHi = mean + constent * stddev/Math.sqrt(trials);
	}
	
	public double mean() {
		return mean;
	}
	
	public double stddev() {
		return stddev;
	}
	
	public double confidenceLo() {
		return confidenceLo;
	}
	
	public double confidenceHi() {
		return confidenceHi;
	}
	
	public static void main(String[] args) {
		int n = StdIn.readInt();
		int trials = StdIn.readInt();
		
		PercolationStats ps = new PercolationStats(n, trials);
		
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = [" + ps.confidenceLo() + ',' + ps.confidenceHi() + ']');
	}
}
