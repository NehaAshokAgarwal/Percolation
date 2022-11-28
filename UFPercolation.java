import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    // Creating the instance variables.
    private int n; // Percolation system size
    private boolean[][] open; // Creating a Percolation system
    private int openSites; // It Counts the number of open sites in the system.
    private WeightedQuickUnionUF uf; // Creating a union find representation of the system
    // (connected to the virtual source and sink.)
    private WeightedQuickUnionUF object; // Creating a union find representation of the system
    // (connected to the source.)

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
         // If the value of n is less than 0 or equals 0, then throw
        // IllegalArgumentException error .
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
         // Initialising the instance variables.
        this.n = n; // Initialise this.n = n.
        this.open = new boolean[n][n]; // Initialise the system's size to be n by n
        this.openSites = 0; // Initialise the openSites to 0.
        this.uf = new WeightedQuickUnionUF(n * n + 2); // Initialise uf to (n*n+2)
        this.object = new WeightedQuickUnionUF(n * n + 1); // Initialise object to (n*n+1)
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
         // Throw an IndexOutOfBoundsException error if i or j is outside the interval [0, n−1].
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
         // If the site(i,j) of the percolating system is not open.
        if (!open[i][j]) {
             // connect site (i, j) to source in uf only if i = 0(the first row of the system.)
            uf.union(0, encode(0, j));
             // Connect site (i, j) to sink in uf only if i = n-1 (last row of the system)
            uf.union(n * n + 1, encode(n - 1, j));
             // connect site (i, j) to source in object only if i = 0(the first row of the system.)
            object.union(0, encode(0, j));
        }
         // If the site(i,j) of the percolating system is not open.
        if (!open[i][j]) {
             // Then open the site.
            open[i][j] = true;
             // Increment the number of open sites by one.
            openSites++;
             // Check the site's neighbour to the north, if it is open
             // then connect the uf and object site corresponding to its neighbour.
             // Also, check the bounds for the north neighbour i.e. i-1 >=0.
            if (i - 1 >= 0 && open[i - 1][j]) {
                uf.union(encode(i, j), encode(i - 1, j));
                object.union(encode(i, j), encode(i - 1, j));
            }
             // Check the site's neighbour to the south, if it is open
             // then connect the uf and object site corresponding to its neighbour.
             // Also, check the bounds for the south neighbour i.e. i + 1 <= n - 1.
            if (i + 1 <= n - 1 && open[i + 1][j]) {
                uf.union(encode(i, j), encode(i + 1, j));
                object.union(encode(i, j), encode(i + 1, j));
            }
             // Check the site's neighbour to the west, if it is open
             // then connect the uf and object site corresponding to its neighbour.
             // Also, check the bounds for the west neighbour i.e. j - 1 >= 0.
            if (j - 1 >= 0 && open[i][j - 1]) {
                uf.union(encode(i, j), encode(i, j - 1));
                object.union(encode(i, j), encode(i, j - 1));
            }
             // Check the site's neighbour to the east, if it is open
             // then connect the uf and object site corresponding to its neighbour.
             // Also, check the bounds for the east neighbour i.e. j + 1 <= n - 1.
            if (j + 1 <= n - 1 && open[i][j + 1]) {
                uf.union(encode(i, j), encode(i, j + 1));
                object.union(encode(i, j), encode(i, j + 1));
            }

        }

    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        // Throw an IndexOutOfBoundsException error if i or j is outside the interval [0, n−1].
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (open[i][j]) {
            return true;
        }
        return false;
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        // Throw an IndexOutOfBoundsException error if i or j is outside the interval [0, n−1].
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // Return true if the site of the object (i,j) is connected to the source
        // and is open or else return false.
        return object.connected(encode(i, j), 0) && open[i][j];
    }


    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        // A system percolates if the source(0) and sink(n*n+1) are connected.
        // i.e. the first and last virtual UF site of the system.
        int source = 0;
        int sink = n * n + 1;
        // Return true if the two virtual UF sites(source and sink) are connected
        // or else return false.
        return uf.connected(source, sink);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        // Return the UF site (1, 2, . . . , n2)
        // corresponding to the percolation system site (i, j).
        int ID;
        // Calculate index to row major order to return the corresponding Uf site.
        // Add 1 to start the Uf site from 1 and not from 0.
        ID = (n * i) + j + 1;
        return ID;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
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