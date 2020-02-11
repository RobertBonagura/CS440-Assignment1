import java.util.Arrays;
import java.util.LinkedList;

/**
 * Class used to hold all of the algorithms.
 */
public class Algorithms {

   /**
    * Breadth First Search algorithm
    * @param graph to search
    * @return number of moves to Goal state.
    */
   public static int BFS(Graph graph){

      LinkedList<Cell> queue = new LinkedList<>();
      Cell cell = graph.getStart();
      cell.visit();
      queue.add(cell);
      while (queue.size() != 0) {
         cell = queue.remove();
         if (cell.getNumberOfJumps() == 0){
            break;
         }
         LinkedList<Cell> neighbors = cell.getNeighbors();
         for (int i = 0; i < neighbors.size(); i++){
            Cell neighbor = neighbors.get(i);
            if (neighbor.isVisited() == false){
               neighbor.visit();
               neighbor.setPrev(cell);
               queue.add(neighbor);
            }
         }
      }

      int count = 0;
      while (cell.getPrev() != null){
         count++;
         cell = cell.getPrev();
      }
      return count;
   }


}