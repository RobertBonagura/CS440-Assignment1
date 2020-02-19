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

      int numberOfCells = graph.getNumberOfCells();
      for(int i = 0 ; i < numberOfCells ; i++){
         graph.findCell(i).setPrev(null);
         graph.findCell(i).setVisited(false);
      }

      Solution solution;
      LinkedList<Cell> queue = new LinkedList<>();
      Cell cell = graph.getStart();

      long startTime = System.nanoTime();
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

      int numberOfCells = graph.getNumberOfCells();
      for(int i = 0 ; i < numberOfCells ; i++){
         graph.findCell(i).setPrev(null);
         graph.findCell(i).setVisited(false);
      }

      Solution solution;
      PriorityQueue<Cell> queue = new PriorityQueue<>(new Heuristic(graph));
      Cell cell = graph.getStart();

      long startTime = System.nanoTime();
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


   public static Graph HillClimbing(Graph graph, int k, int iterations) {

      int[] valueOfIterations = new int[iterations + 1];
      valueOfIterations[0] = k;
      Graph curGraph = new Graph(graph);
      int currentK = k;

      for (int i = 1; i <= iterations; i++){
         System.out.print("iteration " + i + ": ");
         Graph newGraph = new Graph(curGraph);
         newGraph.changeOneRandomCell();
         Solution solution = BFS(newGraph);
         int newK = solution.getK();
         System.out.printf("current is %d and new is %d\n", currentK, newK);
         if (currentK > 0) {
            if (newK <= currentK && newK > 0){
               System.out.printf("Replacing %d with %d\n", currentK, newK);
               currentK = newK;
               curGraph = new Graph(newGraph);
            }
         } else {
            if (newK >= currentK){
               System.out.printf("Replacing %d with %d\n", currentK, newK);
               currentK = newK;
               curGraph = new Graph(newGraph);
            }
         }
         valueOfIterations[i] = newK;
      }
      return curGraph;

   }


}