import java.util.Comparator;
import java.util.LinkedList;

/**
 * Cell class is used to represent each cell within a n-by-n puzzle.
 */
public class Cell{

   private int r;
   private int c;
   private int numberOfJumps;
   private LinkedList<Cell> neighbors;
   private Cell prev;
   private boolean visited = false;

   /**
    * Creates a cell based on its (r,c) pair and the number cells it jumps.
    *
    * @param r             row
    * @param c             column
    * @param numberOfJumps cells it can jump
    */
   public Cell(int r, int c, int numberOfJumps) {
      this.r = r;
      this.c = c;
      this.numberOfJumps = numberOfJumps;
   }

   public int getR() {
      return r;
   }

   public int getC() {
      return c;
   }

   public int getNumberOfJumps() {
      return numberOfJumps;
   }

   public LinkedList<Cell> getNeighbors() {
      return this.neighbors;
   }

   public void setNeighbors(LinkedList<Cell> neighbors) {
      this.neighbors = neighbors;
   }

   public void setPrev(Cell cell) {
      this.prev = cell;
   }

   public boolean isVisited() {
      return visited;
   }

   public void setVisited(boolean visited) {
      this.visited = visited;
   }

   public void visit() {
      setVisited(true);
   }

   public Cell getPrev() {
      return prev;
   }

   /**
    * Prints a cell in (c,r) format.
    */
   public String toString() {
      String string = "(" + getR() + " , " + getC() + ")";
      return string;
   }
}
