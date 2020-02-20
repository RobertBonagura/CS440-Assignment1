import sun.awt.image.ImageWatched;

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


   public static HillClimbingResult HillClimbing(Graph graph,
                                                 Solution solution,
                                                 int iterations) {

      Graph curGraph = new Graph(graph);
      Solution[] solutions = new Solution[iterations + 1];
      solutions[0] = solution;
      int currentK = solution.getK();
      int solutionKept = 0;

      for (int i = 1; i <= iterations; i++){
         Graph newGraph = new Graph(curGraph);
         newGraph.changeOneRandomCell();
         Solution newSolution = BFS(newGraph);
         int newK = newSolution.getK();
         if (newK >= currentK){
            currentK = newK;
            curGraph = new Graph(newGraph);
            solutionKept = i;
         }
         solutions[i] = newSolution;
      }

      HillClimbingResult result = new HillClimbingResult(curGraph, solutions,
              solutionKept);
      return result;

   }


}