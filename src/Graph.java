import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class used to store graph representation of puzzle.
 * Uses an underlying array of Cells to represent each node.
 */
class Graph {
   private int n, numberOfCells;
   private Cell[] cells; //Adjacency Lists
   private Cell start, goal;
   private int[] distances;

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

   Graph(Graph graph){
      this.n = graph.getN();
      this.numberOfCells = graph.getNumberOfCells();
      this.cells = new Cell[numberOfCells];
      Cell[] graphCells = graph.getCells();
      for (int i = 0; i < numberOfCells; i++){
         this.cells[i] = new Cell(graphCells[i]);
      }
      this.start = this.cells[0];
      this.goal = this.cells[numberOfCells - 1];
      this.findAndSetNeighbors();
   }

   private void findAndSetNeighbors() {
      for (int i = 0; i < this.numberOfCells; i++){
         LinkedList<Cell> neighbors = this.findNeighbors(cells[i]);
         this.cells[i].setNeighbors(neighbors);
      }
   }

   public void changeOneRandomCell(){
      int randomIndex = (int )(Math.random() * this.getNumberOfCells() -1); // 0(inclusive) to n^2 - 1exclusive so that 24 is not picked
      Cell randCell = this.findCell(randomIndex);

      int R_MIN, R_MAX, C_MIN, C_MAX;
      R_MIN = C_MIN = 1;
      R_MAX = C_MAX = this.getN();

      int r = randCell.getR() + 1;
      int c = randCell.getC() + 1;

      int rJumps = Math.max((R_MAX - r), (r - R_MIN));
      int cJumps = Math.max((C_MAX - c), (c - C_MIN));
      int numberOfJumps = Math.max(rJumps, cJumps);
      numberOfJumps = genRandNumber(numberOfJumps);

      //System.out.printf("RandCell is %s and numberJumps is %d\n", randCell, numberOfJumps);

      randCell.setNumberOfJumps(numberOfJumps);// updates number of jumps
      this.deleteNeighbors(randCell); // erases old neighbors
      this.findNeighbors(randCell);

      return;

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

   public Cell findCell(int index){
      return cells[index];
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
   private static int genRandNumber(int numberOfJumps) {
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

   public void deleteNeighbors(Cell cell){
      cell.setNeighbors(null);
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

   /**
    * Starts at Goal cell and traces back to find distance to the Start cell.
    * This method relies on each cell's getPrev() method, which will be null
    * if BFS has not yet been used on the graph.
    * If any cell in the path from Goal to Start is null, the method will
    * return -1.
    * @param start
    * @param goal
    * @return distance from Start to Goal in terms of number of jumps
    */
   public int cellDistance(Cell start, Cell goal) {
      Cell cell = goal;
      int kValue = 0;
      if (start.equals(goal)){
         return 0;
      }
      while (cell.getPrev() != null){
         cell = cell.getPrev();
         kValue++;
         if (cell.equals(start)) {
            return kValue;
         }
      }
      return -1;
   }

   public int heuristic(Cell cell){
     if (cell.getR() == n-1 && cell.getC() == n-1){
        return 0;
     } else if (cell.getR() == n-1 || cell.getC() == n-1){
        return 1;
     } else {
        return 2;
     }
   }

   public void setDistances(){
      this.distances = new int[numberOfCells];
      for (int i = 0; i < numberOfCells; i++) {
         int value = cellDistance(start, cells[i]);
         this.distances[i] = value;
      }
   }

   public int[] getDistances(){
      return this.distances;
   }


}






