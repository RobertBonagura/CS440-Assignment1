/**
 * A cell represents a pair of column and row coordinates.
 * A 2-Dimensional array of Cells can later be used to represent a single
 * state.
 */
public class Cell {

   private int c;
   private int r;

   /**
    * Generates a cell object when given two coordinates.
    * @param c column
    * @param r row
    */
   public Cell(int c, int r){
      this.c = c;
      this.r = r;
   }
}
