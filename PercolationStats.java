import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {
    private int m; // number of independent experiments
    private double[] x; // Percolation threshold for the m experiments.


    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        // Throw an error IllegalArgumentException if n and m is less than or
        // equals to zero.
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        this.m = m; // Initialise this.m = m
        this.x = new double[m]; // Initialise this.x to an array of size m.
        // Repeating the following experiment m times to calculate the percolation
        // threshold m number of times for the greater efficiency.
        for (int k = 0; k <= m - 1; k++) {
            // Calling the constructor to create an object 'system' of union find
            // datatype which represents the percolation system.
            UFPercolation system = new UFPercolation(n);
            // number of open sites is set as 0.
            int open = 0;
            // until the system does not percolate.
            while (!system.percolates()) {
                // select a random row in the system.
                int i = StdRandom.uniform(0, n);
                // select a random column in the system.
                int j = StdRandom.uniform(0, n);
                // if the above selected site is not open, then open it and
                // increment the number of sites(open) by 1.
                if (!system.isOpen(i, j)) {
                    system.open(i, j);
                    open++;
                }
            }
            // Calculate the Percolation threshold which is the number of sites open
            /// divided by the total number of sites in the system.
            double threshold = (double) open / (double) (n * n);
            // store the calculated threshold value in the one dimensional array x.
            x[k] = threshold;

        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);
    }
    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(m));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(m));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}
