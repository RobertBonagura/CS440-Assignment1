import java.util.Comparator;

public class Heuristic implements Comparator<Cell> {

   Graph graph;

   public Heuristic(Graph graph){
      this.graph = graph;
   }

   @Override
   public int compare(Cell cell1, Cell cell2) {
      int h1 = graph.heuristic(cell1);
      int h2 = graph.heuristic(cell2);

      if (h1 == h2){
         return 0;
      }
      else if (h1 < h2){
         return -1;
      }
      else {
         return 1;
      }
   }

}
