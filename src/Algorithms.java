import java.util.Arrays;
import java.util.LinkedList;

/**
 * Class used to hold all of the algorithms.
 */
public class Algorithms {
   /**
    * Breadth First Search algorithm
    * @param graph to search
    * @return value of the puzzle.
    */
   public static int BFS(Graph graph){

      LinkedList<Cell> queue = new LinkedList<>();
      
      Cell cell = graph.getStart();

      int numCells = graph.getN()*graph.getN();
       int BFSdistance[] = new int[numCells];
      Arrays.fill(BFSdistance, 0);
      
      
      cell.visit();
      queue.add(cell);
      System.out.println("before while loop");
      while (queue.size() != 0) {
    	  System.out.println("entered while loop");
         cell = queue.remove();
         System.out.println("cell = queue.remove(); => " + cell);

         if(!cell.equals(graph.getGoal())){
         LinkedList<Cell> neighbors = cell.getNeighbors();
         System.out.println("neighbors.size()  = " + neighbors.size());
         
         if (!(cell.getNeighbors() == null)){
        	 System.out.println("entered  if (neighbors.size() > 0) ");
        	 for (int i = 0; i < neighbors.size(); i++){
                 Cell neighbor = neighbors.get(i);
                 System.out.println("neighbors.get(i) = " + neighbors.get(i));
                 if (neighbor.isVisited() == false){
                    neighbor.visit();
                    neighbor.setPrev(cell);
                    System.out.println("neighbor = " +neighbor +" and its prev= " + neighbor.getPrev());
                    queue.add(neighbor);
                 }
              } 
         }
      }
         
      }

 
      for(Cell c : graph.getCells()){
    	  int index = graph.getIndex(c);
    	  	Cell currentCell = c;
    	  if(!currentCell.equals(graph.getStart())){
    		  
	    	  if(currentCell.getPrev() == null ){
	    		  BFSdistance[index] = -1;
	    	  }else{
		    	  while(currentCell.getPrev() != null){
		    		  BFSdistance[index]++;
		    		  currentCell = currentCell.getPrev();
		    	  }
	    	  }
    	  	}
      }

      int puzzleValue = BFSdistance[  graph.getIndex(graph.getGoal())  ];
      if (puzzleValue == -1){
    	  int count =0;
    	  for(int distance: BFSdistance){
    		  if(distance == -1){
    			  count++;
    		  }
    	  }
    	  puzzleValue = -1*count;
      }
      GUIAfterBFS(graph.getN(), BFSdistance);
      
      for(int i=0; i < BFSdistance.length; i++){
    	  System.out.println("Distance at index " + i + " = " + BFSdistance[i]);
      }
      
      return puzzleValue; // returns only count of goal vertex
   }

   
   public static void GUIAfterBFS(int n, int[] distances){
	   GUI g = new GUI();
	   g.createSimpleGUI(n, distances);
	   
   }

}