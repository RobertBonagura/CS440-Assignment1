import java.util.LinkedList;

/**
 * Class used to represent solution returned by an algorithm.
 * Consists of the number of moves, as well as a path from the Start cell
 * to the Goal cell, and then time it takes to reach the goal cell in
 * nanoseconds.
 */
public class Solution {

   private int k; // number of moves to solution
   private LinkedList<Cell> path;
   private long time;

   /**
    * Default constructor for when there is a k value but no path to solution.
    */
   public Solution(int k, long time){
      this.k = k;
      this.time = time;
   }

   /**
    * Default constructor for when there is a path to the solution.
    */
   public Solution(int k, LinkedList<Cell> path, long time){
      this.k = k;
      this.path = path;
      this.time = time;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("The solution is: " + k + "\n");
      if (path != null) {
         sb.append("The path to the solution is: ");
         Cell cell;
         for (int i = 0; i < k; i++){
            cell = path.get(i);
            sb.append(cell.toString());
         }
      }
      double ms = (double) time / Math.pow(10,6);
      sb.append("\nFound in: " + (time / Math.pow(10,6)) + " ms\n");
      String result = sb.toString();
      return result;
   }

   public int getK(){
      return this.k;
   }

   public long getTime(){
      return this.time;
   }
}

