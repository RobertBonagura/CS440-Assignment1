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

      LinkedList<Cell> queue = new LinkedList<>();
      
      Cell cell = graph.getStart();

      int numCells = graph.getN()*graph.getN();
       int BFSdistance[] = new int[numCells];
      Arrays.fill(BFSdistance, 0);
      
      
      cell.visit();
      queue.add(cell);
      //System.out.println("before while loop");
      while (queue.size() != 0) {
    	 // System.out.println("entered while loop");
         cell = queue.remove();
         //System.out.println("cell = queue.remove(); => " + cell);

         if(!cell.equals(graph.getGoal())){
         LinkedList<Cell> neighbors = cell.getNeighbors();
        // System.out.println("neighbors.size()  = " + neighbors.size());
         
         if (!(cell.getNeighbors() == null)){
        	 for (int i = 0; i < neighbors.size(); i++){
                 Cell neighbor = neighbors.get(i);
                //System.out.println("neighbors.get(i) = " + neighbors.get(i));
                 if (neighbor.isVisited() == false){
                    neighbor.visit();
                    neighbor.setPrev(cell);
                    //System.out.println("neighbor = " +neighbor +" and its prev= " + neighbor.getPrev());
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
   
   
   
   

   
  
   
   public static Graph HillClimbingHelp(Graph graph){
	   
	   Graph currentGraph  = graph; // does it clone it?
	   

		   int randomIndex = (int )(Math.random() * graph.getNumberOfCells() -1); // 0(inclusive) to n^2 - 1exclusive so that 24 is not picked
		   
		   Cell randCell = graph.getCellAt(randomIndex);
		   System.out.println("Cell randomly picked for exachange is" + randCell + "  at index = " +  randomIndex
				   + "  value = " + randCell.getNumberOfJumps());
		      int R_MIN, R_MAX, C_MIN, C_MAX;
		      R_MIN = C_MIN = 1;
		      R_MAX = C_MAX = graph.n;
		      
		      int r = randCell.getR() + 1;
		      int c = randCell.getC() + 1;
		      
		      
		      //if row = 0 --> r = 1
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
           currentGraph.findNeighbors(randCell); // adds the new neighbors
           
           System.out.println("new value of cell is " + randCell.getNumberOfJumps() + "  at index = " +  randomIndex);
           System.out.println("new neighbors of cell are: " );
           currentGraph.showNeighbors(randCell);
           //replacement is done correctly.
	return currentGraph;
	   
   }
   public static void HillClimbing(Graph graph, int puzzleValue, int iterations){
	   int oldPuzzleValue = puzzleValue;
	   GUI gui1 = new GUI(); // new graph
	   GUI gui2 = new GUI();// bfs of graph
	   GUI gui3 = new GUI();
	   
	   int valueafterIteration[]= new int[iterations +1];
	   valueafterIteration[0]= oldPuzzleValue;
	   
	   int n = graph.getN();
	   Graph lastGraph = HillClimbingHelp(graph);  
	   int lastPuzzleValue = 0;
	   int newpuzzleValue = 0;

	   System.out.println("#ITERATIONS  = " + iterations);
	   
	   for(int i = 1; i <= iterations; i++){
		   System.out.println("entered iteration loop");

		    newpuzzleValue = Algorithms.getPuzzleValue(Algorithms.BFS(lastGraph));  
		   valueafterIteration[i]= newpuzzleValue; 
		   System.out.println("valueafterIteration[i]= " + valueafterIteration[i]);
		   
		   // if it was negative, it has to become less negative, and if +, it has to be smaller in magnitude		   
		   if(Math.abs(newpuzzleValue)  <= Math.abs(lastPuzzleValue) ){
			   lastPuzzleValue = newpuzzleValue;	
			   if(i+1 <= iterations){ // to save the last graph
			   lastGraph = HillClimbingHelp(lastGraph);
			   }
		   }		 // NOTE: the only time graph changes is if it improves!   //problem: it's ahead!
	   }
	 
	   
	   
	   
	   
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
   }

}