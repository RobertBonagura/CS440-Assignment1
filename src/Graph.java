import java.util.LinkedList;
import java.util.Random;

/**
 * Class used to store graph representation of puzzle.
 * Uses an underlying array of Cells to represent each node.
 */
class Graph {
   public int n, numberOfCells;
   private Cell[] cells; //Adjacency Lists
   private Cell start, goal;

   /**
    * Creates a graph of size n-by-n.
    *
    * @param n number of cells in each dimension
    */
   Graph(int n) {
      this.n = n;
      this.numberOfCells = n * n;
      this.cells = new Cell[numberOfCells];
      this.start = cells[0];
      this.goal = cells[numberOfCells - 1];
   }

   /**
    * Populates graph with cells based on n-by-n dimensions.
    * Each cell consists of a (c,r) pair and a numberOfJumps value
    * representing how many cells it jumps per move.
    */
   public void populateGraph() {

      int R_MIN, R_MAX, C_MIN, C_MAX;
      R_MIN = C_MIN = 1;
      R_MAX = C_MAX = n;

      for (int r = R_MIN; r <= R_MAX; r++) {
         for (int c = C_MIN; c <= C_MAX; c++) {
            if (c == C_MAX && r == R_MAX) { // On last cell
               Cell cell = new Cell(n - 1, n - 1, 0);
               this.goal = cell;
               this.add(cell);
               continue;
            }
            int rJumps = Math.max((R_MAX - r), (r - R_MIN));
            int cJumps = Math.max((C_MAX - c), (c - C_MIN));
            int numberOfJumps = Math.max(rJumps, cJumps);
            numberOfJumps = genRandNumber(numberOfJumps);
            Cell cell = new Cell(r - 1, c - 1, numberOfJumps);
            if (c == C_MIN && r == R_MIN){
               this.start = cell;
            }
            this.add(cell);
         }
      }
   }

   /**
    * Find all neighbors of each cell.
    * Once neighbors for each cell are found, they are added to that
    * individual cell instance's neighbors field.
    */
   public void populateNeighbors() {

      int R_MIN, R_MAX, C_MIN, C_MAX;
      R_MIN = C_MIN = 0;
      R_MAX = C_MAX = n - 1;

      for (int r = R_MIN; r <= R_MAX; r++) {
         for (int c = C_MIN; c <= C_MAX; c++) {
            if (c == C_MAX && r == R_MAX) { // On last cell
               break;
            }
            Cell cell = findCell(r, c);
            findNeighbors(cell);
         }
      }
   }

   /**
    * Creates a linked list of cells that are neighbors to the given cell.
    *
    * @param cell that you want to check neighbors of
    * @return linked list of neighbors
    */
   public LinkedList<Cell> findNeighbors(Cell cell) {
      int r = cell.getR();
      int c = cell.getC();
      int jumps = cell.getNumberOfJumps();
      LinkedList<Cell> neighbors = new LinkedList<Cell>();

      Cell neighborCell;
      int newR, newC;
      if ((r + jumps) <= n - 1) {
         newR = r + jumps;
         neighborCell = findCell(newR, c);
         neighbors.add(neighborCell);
      }
      if ((r - jumps) >= 0) {
         newR = r - jumps;
         neighborCell = findCell(newR, c);
         neighbors.add(neighborCell);
      }
      if ((c + jumps) <= n - 1) {
         newC = c + jumps;
         neighborCell = findCell(r, newC);
         neighbors.add(neighborCell);
      }
      if ((c - jumps) >= 0) {
         newC = c - jumps;
         neighborCell = findCell(r, newC);
         neighbors.add(neighborCell);
      }
      cell.setNeighbors(neighbors);
      return neighbors;
   }

   /**
    * Add cell to graph.
    * Calculates index to add to based on cell's (c,r) values.
    *
    * @param cell to be added
    */
   public void add(Cell cell) {
      int index = (cell.getR() * n) + cell.getC();
      cells[index] = cell;
   }

   /**
    * Finds cell in graph.
    * Calculates index to get from based on cell's (c,r) values.
    *
    * @param r row index
    * @param c coulumn index
    * @return cell to be returned
    */
   public Cell findCell(int r, int c) {
      int index = (n * r) + c;
      Cell cell = cells[index];
      return cell;
   }

   /**
    * Get index of cell in underlying array.
    * @param cell
    * @return index
    */
   public int getIndex(Cell cell) {
      int r, c;
      r = cell.getR();
      c = cell.getC();
      int index = (n * r) + c;
      return index;
   }


   /**
    * Generates a random number between 1 and the maximum number of jumps.
    *
    * @param numberOfJumps maximum value returned by function
    * @return randNum The random number no greater than numberOfMoves
    */
   private int genRandNumber(int numberOfJumps) {
      Random r = new Random();
      int randNum = r.nextInt((numberOfJumps - 1) + 1) + 1;
      return randNum;
   }

   /**
    * Prints to console each cell with descriptive information.
    */
   public void show() {
      for (int i = 0; i < numberOfCells; i++) {
         System.out.printf("Cell %d is %s: it can jump %d times and its " +
                         "neighbors are: ", i, cells[i].toString(),
                 cells[i].getNumberOfJumps());
         showNeighbors(cells[i]);
      }
   }

   public int getNumberOfCells() {
      return this.numberOfCells;
   }

   public int getN() {
      return this.n;
   }

   public Cell[] getCells(){
      return this.cells;
   }

   public Cell getStart() {
      return start;
   }

   public Cell getGoal() {
      return goal;
   }

   /**
    * Prints to console all of the neighbors of the given cell.
    *
    * @param cell you want to show the neighbors of
    */
   public void showNeighbors(Cell cell) {
      if (cell.getNeighbors() == null) {
         System.out.printf(" No neighbors\n", cell.toString());
      } else {
         for (int i = 0; i < cell.getNeighbors().size(); i++) {
            System.out.print(cell.getNeighbors().get(i).toString());
         }
         System.out.println();
      }
   }
}


