<<<<<<< HEAD
import java.util.LinkedList;

/**
 Cell class is used to represent each cell within a n-by-n puzzle.
=======
/**
 * A cell represents a pair of column and row coordinates.
 * A 2-Dimensional array of Cells can later be used to represent a single
 * state.
>>>>>>> ab02f182a079bf575720f782b674e3c5c4c3b020
 */
public class Cell {

   private int r;
   private int c;
   private int numberOfJumps;
   private LinkedList<Cell> neighbors;
   private Cell prev;

   /**
<<<<<<< HEAD
    * Creates a cell based on its (r,c) pair and the number of cells it jumps
    * @param r row
    * @param c column
    * @param numberOfJumps cells it can jump
    */
   public Cell(int r, int c, int numberOfJumps){
=======
    * Generates a cell object when given two coordinates.
    * @param c column
    * @param r row
    */
   public Cell(int c, int r){
      this.c = c;
>>>>>>> ab02f182a079bf575720f782b674e3c5c4c3b020
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

   public LinkedList<Cell> getNeighbors(){
      return this.neighbors;
   }

   public void setNeighbors(LinkedList<Cell> neighbors){
      this.neighbors = neighbors;
   }

   /**
    * Prints a cell in (c,r) format.
    */
   public String toString(){
      String string = "(" + getR() + " , " + getC() + ")";
      return string;
   }
}
