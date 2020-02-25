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
      PriorityQueue<Cell> queue = new PriorityQueue<>(new Heuristic(graph));

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
   public static Result HillClimbing(Graph graph, Solution solution,
                                                 int iterations) {

      long startTime_ns = System.nanoTime();
      Graph curGraph = new Graph(graph);
      for (int i = 0; i < iterations; i++){
         Graph newGraph = new Graph(curGraph);
         newGraph.changeOneRandomCell();
         Solution newSolution = BFS(newGraph);
         if (newSolution.getK() >= solution.getK()){
            solution = newSolution;
            curGraph = new Graph(newGraph);
         }
      }
      long totalTime_ns = System.nanoTime() - startTime_ns;
      double totalTime_ms = totalTime_ns / Math.pow(10, 6);

      Result result = new Result(curGraph, solution, totalTime_ms);
      return result;

   }

   public static Result Genetic(int n, int population, int cycles){

      long startTime_ns = System.nanoTime();
      if(population > 1){
         // Generate initial population
         Graph [] intialPopulation = new Graph[population];

         for (int i = 0; i < population; i++){
            intialPopulation[i] = new Graph(n);
            intialPopulation[i].populateGraph();
            intialPopulation[i].populateNeighbors();
            intialPopulation[i].setDistances();
            intialPopulation[i].cleanGraph();

         }
         Arrays.sort(intialPopulation, new GenAlgoComparator());

         Solution newSolution = BFS(intialPopulation[0]);
         intialPopulation[0].cleanGraph();
         //System.out.println(" ~~~~~~~~~~~~~Best k value in initial population: \n" + newSolution.toString());

         Graph [] mutatedGraphs = null;
         double[] times_ms = new double[cycles];

         while(cycles > 0){
            Graph [] CrossOvers =  GeneticSelection(intialPopulation);
            //STEP 3: MUTATION
            mutatedGraphs = GeneticMutation(CrossOvers);
            cycles--;
            for(int i = 0; i< mutatedGraphs.length; i++){
               intialPopulation[i] = new Graph(mutatedGraphs[i]);
               intialPopulation[i].cleanGraph();
            }
         }

         Solution s = BFS(mutatedGraphs[0]);
         int newK = s.getK();
         // LASTC 		  // System.out.println("  \n RETURNED K = "+ newK + "\n" + s.toString());
         mutatedGraphs[0].cleanGraph();
         Solution solution = Algorithms.BFS(mutatedGraphs[0]);
         long totalTime_ns = System.nanoTime() - startTime_ns;
         double totalTime_ms = totalTime_ns / Math.pow(10, 6);
         Result result = new Result(mutatedGraphs[0], solution, totalTime_ms);
         return result;

      }else{
         System.out.println("Enter at least a population of 2 or more.");
         return null;
      }

   }


   /**
    * GeneticSelection Selects the fittest graphs by coupling the graphs with the highest fitness together.
    * @param intialPopulation : population at the start of the cycle
    * @return Graph[] CrossOvers : the resulting population from coupling fittest graphs together.
    */
   public static Graph[] GeneticSelection( Graph[] intialPopulation){
      boolean even = (intialPopulation.length%2 == 0);

      for(Graph g : intialPopulation){  g.cleanGraph(); }
      Arrays.sort(intialPopulation, new GenAlgoComparator());
      for(int i = 0; i < intialPopulation.length; i++){
         intialPopulation[i].cleanGraph(); // just in case
      }


      int max;
      if(even){max =intialPopulation.length; }
      else		{ max = intialPopulation.length -1;} //drop the one with the lowest fitness

      Graph [] CrossOvers = new Graph[max];

      //Elitism: always keep the first one and put it in the last index
      CrossOvers[0] = new Graph(intialPopulation[0]);
      CrossOvers[0].cleanGraph();

      Graph parent1 = null, parent2= null;
      for(int i = 0; i < max ; i+=2){ // selection
         if(i+ 1 < max){

            parent1 = intialPopulation[i];
            parent2 = intialPopulation[i+1];
            CrossOvers[i+1] = new Graph(GeneticCrossOver( parent1, parent2));
            CrossOvers[i+1].cleanGraph();
         }
         if(i+2 < max){
            CrossOvers[i+2] = new Graph(GeneticCrossOver( parent2, parent1));
            CrossOvers[i+2].cleanGraph();
         }
      }
      Arrays.sort(CrossOvers, new GenAlgoComparator());

      return CrossOvers;
   }

   /**
    * GeneticMutation takes in a population and mutate a graph with 90% chance. It also perpetuate the best graph untouched
    * into the next generation.
    * @param CrossOvers : graph array that holds population after applying the cross-over function
    * @return the mutated version of CrossOvers
    */
   public static Graph[] GeneticMutation(Graph [] CrossOvers){

      for(Graph g : CrossOvers){  g.cleanGraph(); }

      Arrays.sort(CrossOvers, new GenAlgoComparator());
      for(int i = 0; i < CrossOvers.length; i++){
         CrossOvers[i].cleanGraph(); // just in case
      }

      Graph [] mutatedGraphs = new Graph[CrossOvers.length];
      for(int i = 0; i < CrossOvers.length; i++){

         if(i== 0){ // Elitism: maintains the graph with the best k value untouched
            mutatedGraphs[i] = new Graph(CrossOvers[i]);

         }else{
            Random r = new Random();
            double randMutation = r.nextDouble();

            if (randMutation < 0.9){

               mutatedGraphs[i] = new Graph(CrossOvers[i]);
               mutatedGraphs[i].cleanGraph();
               mutatedGraphs[i].changeOneRandomCell();

            }else{
               mutatedGraphs[i] = new Graph(CrossOvers[i]);
            }
         }
         mutatedGraphs[i].cleanGraph();

      }

      Arrays.sort(mutatedGraphs, new GenAlgoComparator());
      for(int i = 0; i < mutatedGraphs.length; i++){
         mutatedGraphs[i].cleanGraph();
      }

      Solution ss = BFS(mutatedGraphs[0]);
      //System.out.println("  After Mutation, best k : \n" + ss.toString());
      mutatedGraphs[0].cleanGraph();


      return mutatedGraphs;
   }


   /**
    *
    * @param  parent1 : graph1 for cross-over
    * @param  parent2 : graph2 for cross-over
    * @return graph with upper half from parent1 and lower half from parent2
    */
   public static Graph GeneticCrossOver(Graph parent1, Graph parent2){
      parent1.cleanGraph();
      parent2.cleanGraph();
      int n = parent1.getN();
      int half = n/2; // rounded down
      // just get all jump values then assemble graph? Split values horizontally!
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
      crossedOverGraph.populateGraph();
      //crossedOverGraph.populateGraph(jumpValues);
      crossedOverGraph.populateNeighbors();
      crossedOverGraph.setDistances();
      crossedOverGraph.cleanGraph();
      return crossedOverGraph;


   }

}