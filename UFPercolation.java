import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    // Creating the instance variables.
    private int n; 
    private boolean[][] open; 
    private int openSites; 
    private WeightedQuickUnionUF uf; 
    private WeightedQuickUnionUF object; 
  
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n; 
        this.open = new boolean[n][n]; 
        this.openSites = 0; 
        this.uf = new WeightedQuickUnionUF(n * n + 2); 
        this.object = new WeightedQuickUnionUF(n * n + 1); 
    }
    
    public void open(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!open[i][j]) {
            uf.union(0, encode(0, j));
            uf.union(n * n + 1, encode(n - 1, j));
            object.union(0, encode(0, j));
        }
        if (!open[i][j]) {
            open[i][j] = true;
            openSites++;
            if (i - 1 >= 0 && open[i - 1][j]) {
                uf.union(encode(i, j), encode(i - 1, j));
                object.union(encode(i, j), encode(i - 1, j));
            }
            if (i + 1 <= n - 1 && open[i + 1][j]) {
                uf.union(encode(i, j), encode(i + 1, j));
                object.union(encode(i, j), encode(i + 1, j));
            }
            if (j - 1 >= 0 && open[i][j - 1]) {
                uf.union(encode(i, j), encode(i, j - 1));
                object.union(encode(i, j), encode(i, j - 1));
            }
            if (j + 1 <= n - 1 && open[i][j + 1]) {
                uf.union(encode(i, j), encode(i, j + 1));
                object.union(encode(i, j), encode(i, j + 1));
            }

        }

    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (open[i][j]) {
            return true;
        }
        return false;
    }

    public boolean isFull(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return object.connected(encode(i, j), 0) && open[i][j];
    }


    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        int source = 0;
        int sink = n * n + 1;
        return uf.connected(source, sink);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        int ID;
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
