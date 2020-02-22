
import java.util.Comparator;

public class GenAlgoComparator  implements Comparator<Graph>{

	  @Override
	   public int compare(Graph g1, Graph g2) {
		  Solution solution1 = Algorithms.BFS(g1);
	      int k1 = solution1.getK();
	         
		  Solution solution2 = Algorithms.BFS(g2);
	      int k2 = solution2.getK();
	      g1.cleanGraph();
	      g2.cleanGraph();
	      
	      if(k1 >= k2){
	    	  return -1;
	      }else{
	    	  return 1;
	      }

	    }
}