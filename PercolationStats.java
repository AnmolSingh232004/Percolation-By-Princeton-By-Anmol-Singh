import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_CONSTANT = 1.96;
    private int n;
    private int trials;
    private double[] valuesForPercolation;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.n = n;
        this.trials = trials;
        valuesForPercolation = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolationLoop = new Percolation(n);
            long totalNoOfOpenSites = 0;

            while (!percolationLoop.percolates()) {


            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);

            if (!percolationLoop.isOpen(row, col) && !percolationLoop.isFull(row, col)) {
                percolationLoop.open(row, col);
                totalNoOfOpenSites++;
            }
        }
            totalNoOfOpenSites = percolationLoop.numberOfOpenSites();
            valuesForPercolation[i] = (double) totalNoOfOpenSites / (n*n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(valuesForPercolation);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(valuesForPercolation);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE_CONSTANT*stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE_CONSTANT*stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        String confidence = percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi();

        StdOut.print("mean                    = " + percolationStats.mean());
        StdOut.print("stddev                  = " + percolationStats.stddev());
        StdOut.print("95% confidence interval = " + confidence);
    }

}