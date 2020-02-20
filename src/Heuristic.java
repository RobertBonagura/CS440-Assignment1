import java.util.Comparator;

public class Heuristic implements Comparator<Cell> {

   Graph graph;

   public Heuristic(Graph graph){
      this.graph = graph;
   }

   @Override
   public int compare(Cell cell1, Cell cell2) {

      int d1 = graph.cellDistance(graph.getStart(), cell1);
      int d2 = graph.cellDistance(graph.getStart(), cell2);

      int h1 = graph.heuristic(cell1);
      int h2 = graph.heuristic(cell2);

      int g1 = d1 + h1;
      int g2 = d2 + h2;

      if (g1 > g2){
         return 1;
      }
      else if (g1 < g2){
         return -1;
      }
      else {
         return 1;
      }
   }

}
