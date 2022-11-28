import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using a 2D array.
public class ArrayPercolation implements Percolation {
    // Creating instance variables.
    public boolean[][] open;  //  Creating a Percolating system
    public int openSites;    // It counts the number of open sites
    private int n;          // size of the percolating system

    // Constructs an n x n percolation system, with all sites blocked.
    public ArrayPercolation(int n) {
        // If the value of n is less than 0 or equals 0, then throw IllegalArgumentException error .
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        // Initialise the instance variables.
        this.n = n; // initialise this.n = n.
        this.open = new boolean[n][n]; // Initialise system's size to be n by n.
        this.openSites = 0; // initialise the openSites to 0.
    }

    // Opens site (i, j) of the percolating system, if it is not already open.
    public void open(int i, int j) {
        // Throw an IndexOutOfBoundsException error if i or j is outside the interval [0, n−1].
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // If the site(i, j) is not open in the system, then open the
        // respective site.
        if (!open[i][j]) {
            open[i][j] = true;
            // Increment the number of open sites by one.
            openSites++;
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        // Throw an IndexOutOfBoundsException error if i or j is outside the interval [0, n−1].
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // If the site(i, j) of the system is open, then
        // return true or else return false.
        if (open[i][j]) {
            return true;
        }
        return false;

    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        // TThrow an IndexOutOfBoundsException error if i or j is outside the interval [0, n−1].
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Creating a 2D array full(percolating system) of size n by n.
        boolean[][] full = new boolean[n][n];
        // For every site in the first row of the system full, call the
        // floodFill method to check if the site is full or not.
        for (int k = 0; k <= n - 1; k++) {
            floodFill(full, 0, k);
        }
        // If the site is full, then return true.
        return full[i][j];
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;

    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // In the last row of the system, if at least one site(n-1, j) is open
        // and is full, then the system percolates. Thus return true or else return false.
        for (int j = 0; j <= n - 1; j++) {
            if (isFull(n - 1, j)) {
                return true;
            }
        }
        return false;
    }

    // Recursively flood fills full[][] using depth-first exploration, starting
    // at (i, j).
    private void floodFill(boolean[][] full, int i, int j) {
        // Base case : If the site(i, j) is out of the system or if the site is
        // not open or if the site is full, then return.
        if (i < 0 || i > (n - 1) || j < 0 || j > (n - 1) || (!isOpen(i, j))
                || full[i][j]) {
            return;
        }

        // If none of the above condition is true then fill the site (i, j) in
        // the system full.
        full[i][j] = true;
        // Then call the floodFill method recursively on the north, east, west,
        // and south directions of the site(i, j) in the system full.
        floodFill(full, i - 1, j);  // north
        floodFill(full, i, j + 1); // right
        floodFill(full, i, j - 1); // left
        floodFill(full, i + 1, j); // south

    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        ArrayPercolation perc = new ArrayPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}