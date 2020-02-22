//import sun.awt.image.ImageWatched;

import java.util.*;

/**
 * Class used to hold all of the algorithms.
 */
public class Algorithms {

   /**
    * Breadth First Search algorithm
    * @param graph to search
    * @return number of moves to Goal state.
    */
   public static Solution BFS(Graph graph){

      Solution solution;
      graph.cleanGraph();
      LinkedList<Cell> queue = new LinkedList<>();

      long startTime = System.nanoTime();
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
      long totalTime = System.nanoTime() - startTime;
      graph.setDistances();

      int kValue = getKValue(graph.getDistances());
      if (kValue < 0){
         solution = new Solution(kValue, totalTime);
      } else {
         LinkedList<Cell> pathToSolution = generateSolutionPath(graph);
         solution = new Solution(kValue, pathToSolution, totalTime);
      }

      return solution;
   }

   /**
    * Shortest Path First algorithm.
    * Adds nodes to PriorityQueue based on distance from start cell.
    * @param graph to search
    * @return number of moves to Goal state.
    */
   public static Solution SPF(Graph graph){

      Solution solution;
      graph.cleanGraph();
      PriorityQueue<Cell> queue = new PriorityQueue<>(new TotalPath(graph));

      long startTime = System.nanoTime();
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
      long totalTime = System.nanoTime() - startTime;
      graph.setDistances();

      int kValue = getKValue(graph.getDistances());
      if (kValue < 0){
         solution = new Solution(kValue, totalTime);
      } else {
         LinkedList<Cell> pathToSolution = generateSolutionPath(graph);
         solution = new Solution(kValue, pathToSolution, totalTime);
      }

      return solution;
   }

   public static Solution AStarSearch(Graph graph){

      Solution solution;
      graph.cleanGraph();
      PriorityQueue<Cell> queue = new PriorityQueue<>(new Heuristic(graph)); // comparator

      long startTime = System.nanoTime();
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
      long totalTime = System.nanoTime() - startTime;
      graph.setDistances();

      int kValue = getKValue(graph.getDistances());
      if (kValue < 0){
         solution = new Solution(kValue, totalTime);
      } else {
         LinkedList<Cell> pathToSolution = generateSolutionPath(graph);
         solution = new Solution(kValue, pathToSolution, totalTime);
      }

      return solution;

   }
   



   private static int getKValue(int[] distances) {
      int kValue = distances[distances.length -1 ]; // get goal Value
      if (kValue == -1){
         int numberUnreachableCells = 0;
         for(int distance: distances){
            if(distance == -1){
               numberUnreachableCells++;
            }
         }
         kValue = -1*numberUnreachableCells;
      }
      return kValue;

   }

   private static LinkedList<Cell> generateSolutionPath(Graph graph) {
      LinkedList<Cell> pathToSolution = new LinkedList<Cell>();
      Cell start = graph.getStart();
      Cell goal = graph.getGoal();
      Cell cell = goal;

      int kValue = graph.cellDistance(start, goal);
      if (kValue < 0){
         return pathToSolution;
      }
      for (int i = 0; i < kValue; i++){
         pathToSolution.addFirst(cell);
         if (cell.getPrev() == null){
            break;
         }
         cell = cell.getPrev();
      }
      return pathToSolution;
   }

   /**
    *
    * @param graph
    * @param solution
    * @param iterations
    * @return
    */
   public static HillClimbingResult HillClimbing(Graph graph,
                                                 Solution solution,
                                                 int iterations) {
      Graph curGraph = new Graph(graph);
      int[] kValues = new int[iterations];
      double[] times_ms = new double[iterations];
      int currentK = solution.getK();

      long bestTime_ns, totalTime_ns;
      bestTime_ns = totalTime_ns = System.nanoTime();
      int chosenIndex = 0;
      for (int i = 0; i < iterations; i++){
         long startTime_ns = System.nanoTime();
         Graph newGraph = new Graph(curGraph);
         newGraph.changeOneRandomCell();
         Solution newSolution = BFS(newGraph);
         int newK = newSolution.getK();
         if (newK >= currentK){
            currentK = newK;
            curGraph = new Graph(newGraph);
            chosenIndex = i;
         }
         kValues[i] = newK;
         long iterationTime_ns = (System.nanoTime() - startTime_ns);
         times_ms[i] = (iterationTime_ns / Math.pow(10,6));
      }

      HillClimbingResult result = new HillClimbingResult(curGraph, kValues,
              chosenIndex, times_ms);

      return result;

   }
   /** 
    * @param lastPoulation sorted array of objects (graphs) from previous population
    * @return new population after Selection, Cross-over, and Mutation
    */
 /*  public static Graph[] GeneticHelp(Graph[] lastPoulation){
	   Graph [] intialPopulation = lastPoulation;	   	
   }

   
   
   */
   // genetic ALG
  /**
   * BUG: dummy and mutatedGraphs[0] should return the same answer but 
   * mutatedGraphs[0] sometimes return something else
   * @param n size of puzzle
   * @param population : number of graphs in original population
   * @return graph with highest k value after reaching a plateau
   * 	  // how to define plateau? a delta value? an upper bound?a certain number of cycles?
   */
   
   
   public static Graph Genetic(int n, int population){
	   if(population > 1){
	   int generations = 100;
	   // initial population
	   Graph [] intialPopulation = new Graph[population];
	   
	   for (int i = 0; i < population; i++){
		   intialPopulation[i] = new Graph(n);
		   intialPopulation[i].populateGraph(); 
		   intialPopulation[i].populateNeighbors();
		   intialPopulation[i].setDistances();
	       Solution newSolution = BFS(intialPopulation[i]);
	         int newK = newSolution.getK();
		   System.out.println("   Puzzle #"+ i+ "\n" + newSolution.toString());
		   intialPopulation[i].cleanGraph();
		   
	   }
 /*      GUI gui0 = new GUI() ;
       gui0.run(intialPopulation[0], "graph 0");
       
       GUI gui1 = new GUI() ;
       gui1.run(intialPopulation[1], "graph 1");
 */      
	   Arrays.sort(intialPopulation, new GenAlgoComparator());  

	   Graph [] CrossOvers =  genCross(intialPopulation);
	
		   
// MUTATION make it 90 percent?
		Graph [] mutatedGraphs = new Graph[CrossOvers.length];
		   for(int i = 0; i < CrossOvers.length; i++){
			   CrossOvers[i].cleanGraph();
			   
			   Random r = new Random();
			   double randMutation = r.nextDouble();
			   
			   if (randMutation < 0.9){
				   
				   mutatedGraphs[i] = new Graph(CrossOvers[i]);
				   mutatedGraphs[i].changeOneRandomCell();	

			   }else{
				   mutatedGraphs[i] = new Graph(CrossOvers[i]);
			   }
			   mutatedGraphs[i].cleanGraph();

			   
			       Solution s = BFS(mutatedGraphs[i]);
			         int newK = s.getK();
				   System.out.println("  After Mutation, graph#" +i + "\n" + s.toString());
				   mutatedGraphs[i].cleanGraph();
				  // GUI gui8 = new GUI() ;
				  //gui8.run(mutatedGraphs[i], "Mutated 1");
			   
			   
		   }
	      
		   Arrays.sort(mutatedGraphs, new GenAlgoComparator());  
 		 
	       Solution s = BFS(mutatedGraphs[0]);
	         int newK = s.getK();
		   System.out.println("  \n RETURNED K = "+ newK + "\n" + s.toString());
		   mutatedGraphs[0].cleanGraph();
		   
		   return mutatedGraphs[0];
		   
	   }else{
		   System.out.println("Enter at least a population of 2 or more.");
		   return null;
	   }
	   }
   
 
   
   
   public static Graph[] genCross( Graph[] intialPopulation){
	   boolean even = (intialPopulation.length%2 == 0);
	   
	   for(Graph g : intialPopulation){
		   g.cleanGraph();
	   }
	   
	   
	   int max;
	   if(even){max =intialPopulation.length; }
	   else		{ max = intialPopulation.length -1;} //drop the one with the lowest fitness
	   
	   Graph [] CrossOvers = new Graph[max];

	   for(int i = 0; i < max ; i+=2){ // selection
			   Graph parent1 = intialPopulation[i];
			   Graph parent2 = intialPopulation[i+1];
			   CrossOvers[i] = new Graph(GeneticCrossOver( parent1, parent2));
			   CrossOvers[i].cleanGraph();
			   CrossOvers[i+1] = new Graph(GeneticCrossOver( parent2, parent1));
			   CrossOvers[i+1].cleanGraph();
	}

	   Arrays.sort(CrossOvers, new GenAlgoComparator());  

/*	 //Elitism: always keep the first one and put it in the last index
	   CrossOvers[intialPopulation.length -1 ] = new Graph(intialPopulation[0]);
	   CrossOvers[intialPopulation.length -1 ].cleanGraph();
	   //NO NEED to sort here!
*/
	   return CrossOvers;
	
   }
   
   
   /**
    * 
    * @param  parent1
    * @param  parent2
    * @return graph with left half from g1 and right half from g2
    */
      public static Graph GeneticCrossOver(Graph parent1, Graph parent2){
    	  parent1.cleanGraph();
    	  parent2.cleanGraph();
   	   int n = parent1.getN();
   	   int half = n/2; // rounded down 
   	   // just get all jump values then assemble graph? could be split horizontally! easier!
   	   int[] jumpValues = new int[n*n];
   	   int index = 0;
   	   for(int i = 0; i <= half ; i++){// half rows
   		   for(int j = 0; j < n ; j++){ //cols
   			jumpValues[i*n + j] = parent1.findCell(i*n + j).getNumberOfJumps();
    		   
   		   }
   	   }
   	
   	   for(int i = half + 1 ; i < n ; i++){// half rows
   		   for(int j = 0; j < n ; j++){ //cols
   			jumpValues[i*n + j] = parent2.findCell(i*n + j).getNumberOfJumps();
   		   }
   	   }

   	
   	   Graph crossedOverGraph = new Graph(n);
   	   crossedOverGraph.populateGraph(jumpValues);
   	   crossedOverGraph.populateNeighbors();
   	   crossedOverGraph.setDistances();
   	   crossedOverGraph.cleanGraph();
   	   return crossedOverGraph;
       
 	   
      }
    

   }
   


   
   

