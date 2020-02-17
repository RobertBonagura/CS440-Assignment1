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
      getKValue(graph.getDistances());

      int kValue = graph.cellDistance(graph.getStart(), graph.getGoal());
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
            System.out.printf("In the queue:");
            for (Cell c : queue){
               System.out.print(c.toString());
            }
            System.out.println();
         }
      }

      long totalTime = System.nanoTime() - startTime;
      graph.setDistances();
      getKValue(graph.getDistances());

      int kValue = graph.cellDistance(graph.getStart(), graph.getGoal());
      if (kValue < 0){
         solution = new Solution(kValue, totalTime);
      } else {
         LinkedList<Cell> pathToSolution = generateSolutionPath(graph);
         solution = new Solution(kValue, pathToSolution, totalTime);
      }

      return solution;

   }

   public static Graph HillClimbingHelp(Graph graph){
      int n = graph.getN();
      Graph newGraph = new Graph(graph);

      int randomIndex = (int )(Math.random() * graph.getNumberOfCells() -1); // 0(inclusive) to n^2 - 1exclusive so that 24 is not picked
      Cell randCell = newGraph.findCell(randomIndex);

      int R_MIN, R_MAX, C_MIN, C_MAX;
      R_MIN = C_MIN = 1;
      R_MAX = C_MAX = graph.getN();

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

      newGraph.findCell(randomIndex).setNumberOfJumps(randjumps);// updates number of jumps
      newGraph.deleteNeighbors(randCell); // erases old neighbors
      newGraph.findNeighbors(randCell);
      //newGraph.showNeighbors(randCell);

      // Now replace setPrev and visited !!!!!!!!!!!!!!!!!!!!!!
      for(int i = 0 ; i < n*n ; i++){
         newGraph.findCell(i).setPrev(null);
         newGraph.findCell(i).setVisited(false);
      }

      return newGraph;

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
      Graph curGraph = graph;
      int currentK = k;

      for (int i = 1; i <= iterations; i++){
         Graph newGraph = HillClimbingHelp(curGraph);
         Solution solution = BFS(newGraph);
         int newK = solution.getK();
         if (newK <= currentK){
            currentK = newK;
            curGraph = newGraph;
         }
         valueOfIterations[i] = newK;
      }
      return curGraph;






   }
}