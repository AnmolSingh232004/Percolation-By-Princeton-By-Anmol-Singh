// must not use retarded chatgpt this time for any help
// must keep in mind the coding conventions like good naming, proper spacing etc.
// must test the methods I create first before proceeding
// must not have commented out previous type of implemntations if I want to change my logic or smth like that I just change it no commenting previous code
// must refer to the princeton specifications at regular intervals
// if need help use google, stackoverflow and stuff not shitty chatgpt or any ai
// MUST START THIS PROJECT THE MOMENT I FUCKING WAKE UP AND NEVER LEAVE UNTIL DONE
// USE BRAIN YOU ARE SMART !!!
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int n;
    private int virutalTop;
    private int virutalBottom;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufBackWashFix; // basically fixes backwash(google it for percolation) by using this in isFull and open method
    private int countOpen;

    // creates n-by-n grid, with all sites initially blocked

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        grid = new boolean[n][n];
        uf =  new WeightedQuickUnionUF(n * n + 2);
        ufBackWashFix = new WeightedQuickUnionUF(n * n + 1);
        virutalTop = n*n;
        virutalBottom = n*n+1;
        countOpen = 0;

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        check(row, col);

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            countOpen++;

        }
        if (row == 1) { // connects to virtual top if any element is in top row
            ufBackWashFix.union(col-1, virutalTop);
            uf.union(col-1, virutalTop);
        }
        if (row == n) {
            uf.union(virutalBottom, getOneDIndex(row, col));
        }
        // union with neighbours that are open themselves if possible
        if (row > 1 && isOpen(row - 1, col)) {
            ufBackWashFix.union(getOneDIndex(row, col), getOneDIndex(row - 1, col));
            uf.union(getOneDIndex(row, col), getOneDIndex(row - 1, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            ufBackWashFix.union(getOneDIndex(row, col), getOneDIndex(row + 1, col));
            uf.union(getOneDIndex(row, col), getOneDIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            ufBackWashFix.union(getOneDIndex(row, col), getOneDIndex(row, col - 1));
            uf.union(getOneDIndex(row, col), getOneDIndex(row, col - 1));
        }
        if (col < n && isOpen(row, col + 1)) {
            ufBackWashFix.union(getOneDIndex(row, col), getOneDIndex(row, col + 1));
            uf.union(getOneDIndex(row, col), getOneDIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
       check(row, col);
        return grid[row - 1][col - 1];

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        check(row, col);
        if (grid[row - 1][col - 1]) {
            // open(row, col); this prolly was causing some complications better off without it
            return ufBackWashFix.find(getOneDIndex(row, col)) == ufBackWashFix.find(virutalTop);
            // we use ufBackWashFix in isFull because it doesnt include virtual bottom so avoids percolation if some sites connected to bottom percolates without connection to top
// if the row,col and top are connected then return true
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virutalTop) == uf.find(virutalBottom);
    }

    private int getOneDIndex(int row, int col) {
        return (row - 1) * n + (col-1); // This conversion was wrong which ensure that my score stays below 60 :(
        // previously was (row-1) * n + col; previously was this which is wrong but easy to ignore and I like easy things so wasted a lot of my time finding this single -1 which wasted a lot of time TiME tIme TIMe TimE TIME is nothing but a illusion
    }
    private void check(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            System.out.println(row + "row");
            System.out.println(col + "col");
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

        // Testing methods
        
        Percolation percolation = new Percolation(5);
        percolation.open(5, 5);
        percolation.open(5, 5);
        percolation.open(3, 5);
        percolation.open(4, 5);
       boolean temp = percolation.isOpen(5, 5);
       boolean temp2 = percolation.isOpen(2, 5);
       System.out.println(temp);
       System.out.println(temp2);
       int temp3 = percolation.numberOfOpenSites();
       System.out.println(temp3);
      percolation.open(1, 1);
      percolation.open(2, 1);
        percolation.open(3, 1);
         percolation.open(4, 1);
         percolation.open(5, 1);
        int temp4 = percolation.numberOfOpenSites();
        System.out.println(temp4);
        System.out.println(percolation.isFull(3,3));
         System.out.println(percolation.numberOfOpenSites());
         System.out.println(percolation.percolates());



    }
}


