import java.util.Comparator;

public class TotalPath implements Comparator<Cell>{

      Graph graph;

   public TotalPath(Graph graph){
      this.graph = graph;
   }

      @Override
      public int compare(Cell cell1, Cell cell2) {
      int d1 = graph.cellDistance(graph.getStart(), cell1);
      int d2 = graph.cellDistance(graph.getStart(), cell2);

      if (d1 > d2){
         return 1;
      }
      else if (d1 < d2){
         return -1;
      }
      else {
         return 0;
      }
   }

   }
