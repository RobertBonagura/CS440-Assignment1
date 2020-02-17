import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class used to hold all of the algorithms.
 */
public class Algorithms {
   /**
    * Breadth First Search algorithm
    * @param graph to search
    * @return value of the puzzle.
    */
   public static int[] BFS(Graph graph){
	   System.out.println("BFS called");

      LinkedList<Cell> queue = new LinkedList<>();
      
      Cell cell = graph.getStart();

      int numCells = graph.getN()*graph.getN();
       int BFSdistance[] = new int[numCells];
      Arrays.fill(BFSdistance, 0);
      
      
      cell.visit();
      queue.add(cell);
      while (queue.size() != 0) {
         cell = queue.remove();

         if(!cell.equals(graph.getGoal())){
         LinkedList<Cell> neighbors = cell.getNeighbors();
         
         if (!(cell.getNeighbors() == null)){
        	 for (int i = 0; i < neighbors.size(); i++){
                 Cell neighbor = neighbors.get(i);
                 if (neighbor.isVisited() == false){
                    neighbor.visit();
                    neighbor.setPrev(cell);
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
    
/*      for(int i=0; i < BFSdistance.length; i++){
    	  System.out.println("Distance at index " + i + " = " + BFSdistance[i]);
      }
*/      
      return BFSdistance;
   }
   
   
   
   
   
/**
 * @param array of distances from origin to every cell
 * @return k value of the puzzle
 * */
   public static int getPuzzleValue(int [] BFSdistance){
	    int puzzleValue = BFSdistance[ BFSdistance.length -1 ]; // get goal Value
	      if (puzzleValue == -1){
	    	  int count = 0;
	    	  for(int distance: BFSdistance){
	    		  if(distance == -1){
	    			  count++;
	    		  }
	    	  }
	    	  puzzleValue = -1*count;
	      }
	      return puzzleValue;
   }
   
   
   
   

  /**HillClimbingHelp duplicates the graph but changes 1 cell (and its properties) 
   * @param graph input
   * @return the new changed graph
   * */ 
  
   
   public static Graph HillClimbingHelp(Graph graph){
	   int n = graph.n;
	   
	   Graph currentGraph  = graph; // MAKE copy
	   

		   int randomIndex = (int )(Math.random() * graph.getNumberOfCells() -1); // 0(inclusive) to n^2 - 1exclusive so that 24 is not picked
		   
		   Cell randCell = currentGraph.getCellAt(randomIndex);
		   
		      int R_MIN, R_MAX, C_MIN, C_MAX;
		      R_MIN = C_MIN = 1;
		      R_MAX = C_MAX = graph.n;
		      
		      int r = randCell.getR() + 1;
		      int c = randCell.getC() + 1;
		      
		      
		   int rJumps = Math.max((R_MAX - r), (r - R_MIN));
           int cJumps = Math.max((C_MAX - c), (c - C_MIN));
           int numberOfJumps = Math.max(rJumps, cJumps);
           Random ran = new Random();
           int randjumps = randCell.getNumberOfJumps();
           
           while(randjumps == randCell.getNumberOfJumps()){
        	   randjumps = ran.nextInt((numberOfJumps - 1) + 1) + 1; // find a way to reduce code
           }
          
           currentGraph.getCellAt(randomIndex).setNumberOfJumps(randjumps);// updates number of jumps  
           currentGraph.deleteNeighbors(randCell); // erases old neighbors  
           
           currentGraph.findNeighbors(randCell);
           currentGraph.showNeighbors(randCell);

           
           // Now replace setPrev and visited !!!!!!!!!!!!!!!!!!!!!!
           for(int i = 0 ; i< n*n ; i++){
        	   currentGraph.getCellAt(i).setPrev(null);
        	   currentGraph.getCellAt(i).setVisited(false);
           }
           
	
           return currentGraph;
	   
   }
   
   
   
   
   /**ISSUE: outputs the right k value but finalGraph doesnt display the right graph!*/
   
   
   
   public static void HillClimbing(Graph graph, int puzzleValue, int iterations){
	   GUI gui1 = new GUI(); // for testing
	   GUI gui2 = new GUI(); // for testing

	   
	   int valueafterIteration[]= new int[iterations +1];
	   valueafterIteration[0]= puzzleValue;
	   
	   int n = graph.getN();
	   Graph lastGraph = HillClimbingHelp(graph); 
	   Graph finalGraph = graph;
	   int lastPuzzleValue = valueafterIteration[0];
	   int newpuzzleValue = 0;

	   System.out.println("#ITERATIONS  = " + iterations);
	   
	   for(int i = 1; i <= iterations; i++){
		   int [] BFSvals = Algorithms.BFS(lastGraph); 
		   newpuzzleValue = Algorithms.getPuzzleValue(BFSvals);  	   
		   valueafterIteration[i]= newpuzzleValue; 
		   
		   System.out.println("				lastPuzzleValue= " + lastPuzzleValue);
		   System.out.println("				newpuzzleValue= " + newpuzzleValue);
		   
		   
		   
		   if(Math.abs(newpuzzleValue)  <= Math.abs(lastPuzzleValue) ){
			  System.out.println("ENTERED if(Math.abs(newpuzzleValue)  <= Math.abs(lastPuzzleValue) ");
			  finalGraph = lastGraph; // why doesnt it work???
			  System.out.println("final graph:");
			  //finalGraph.show();
			  int x = Algorithms.getPuzzleValue(Algorithms.BFS(finalGraph));
			  System.out.println("k value of final graph = " + x);
			   lastPuzzleValue = newpuzzleValue;	
			   if(i+1 <= iterations){ // to save the last graph
				//   System.out.println("ENTERED if(i+1 <= iterations)");
				   lastGraph = HillClimbingHelp(lastGraph);
			   }
		   }
		   
		   // if this is last iteration and lastPuzzleValue is > newpuzzlevalue, save graph with lowest

		   
	   }
	 
	   
	   
	   
	   gui1.run(finalGraph);
	   int [] BFSdis = Algorithms.BFS(finalGraph);
	   gui2.createSimpleGUI(n, BFSdis);
/*	   
	   // display:
	   gui1.run(lastGraph);
	   int newpuzzleValue = Algorithms.getPuzzleValue(Algorithms.BFS(lastGraph));  
	   System.out.println("newpuzzleValue after " +iterations +" iterations is "+ lastPuzzleValue);
	   int [] BFSdis = Algorithms.BFS(lastGraph);
	   gui2.createSimpleGUI(n, BFSdis);
	   
	   Graph g3 = HillClimbingHelp(lastGraph);
	   gui3.run(g3);
	   int newpuzzleValue1 = Algorithms.getPuzzleValue(Algorithms.BFS(g3));  
	   int [] BFSdis1 = Algorithms.BFS(lastGraph);
	   gui3.createSimpleGUI(n, BFSdis);
*/	   
	   
	  // Graph g = HillClimbingHelp(graph, 1);
	   
// FOR DISPLAY	   
/*	   gui1.run(g);
	   
       int [] BFSdis = Algorithms.BFS(g);
       int newpuzzleValue = Algorithms.getPuzzleValue(BFSdis);       
       System.out.println("newpuzzleValue = " + newpuzzleValue);
      
	   	gui2.createSimpleGUI(n, BFSdis);
*/	   
   
	   System.out.println("After " + iterations +" iterations, the new k value is: "+ lastPuzzleValue);
   }

}