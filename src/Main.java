

public class Main {

   public static void main(String[] args) {

      Task3();
      Graph difficultGraph = Task4();
      Task5(difficultGraph);
   }



   /**
    * Task 3 Driver Method.
    */
   private static void Task3() {

      int[] puzzleSizes = new int[]{5, 7, 9, 11};
      for (int puzzleSize : puzzleSizes){
         createT3Puzzles(puzzleSize, true);
         createT3Puzzles(puzzleSize, false);
      }
   }

   /**
    * Task 4 Driver Method.
    */
   private static Graph Task4() {

      int[] puzzleSizes = new int[]{5, 7, 9, 11};
      int hillIterations = 100;
      int task4Iterations = 100;
      Graph graph;
      Graph maxGraph = null;
      Solution solution = null;
      double maxGraphTime_ms = 0;

      for (int puzzleSize : puzzleSizes){
         double avgTimes_ms = 0;
         int maxK = 0;
         for (int i = 0; i < task4Iterations; i++) {
            graph = new Graph(puzzleSize);
            graph.populateGraph();
            graph.populateNeighbors();
            solution = Algorithms.BFS(graph);
            HillClimbingResult hillResult = Algorithms.HillClimbing(graph,
                    solution, hillIterations);
            avgTimes_ms += hillResult.getAvgTimeMs();
            if (hillResult.getK() > maxK) {
               maxK = hillResult.getK();
               maxGraph = hillResult.getGraph();
               maxGraphTime_ms = hillResult.getTimeMs();
            }
         }
         //plotHillClimb(hillResult);
         double avgTime_ms = avgTimes_ms / task4Iterations;
         System.out.printf("The average Hill Climbing runtime on a %sx%s with %s " +
                 "iterations is %.4f ms\n", puzzleSize, puzzleSize,
                 hillIterations, avgTime_ms);
      }

      GUI gui = new GUI();
      gui.run(maxGraph, "Max Difficulty Graph");
      int[] distancePerCell = maxGraph.getDistances();
      GUI numberOfMovesGUI = new GUI();
      numberOfMovesGUI.createNumberOfMovesGUI(maxGraph.getN(),
              distancePerCell, "Max Graph Solution");
      System.out.printf("\nThe Max Difficulty graph was found in %.4f ms\n\n",
              maxGraphTime_ms);
      Solution difficultSolution = Algorithms.BFS(maxGraph);
      System.out.println("The result of BFS on difficult graph");
      System.out.println(difficultSolution);

      return maxGraph;

   }


   private static void Task5(Graph difficultGraph) {

      Graph graph = new Graph(difficultGraph);
      Solution solution = Algorithms.SPF(graph);
      System.out.println("The result of SPF on difficult graph");
      System.out.println(solution);
   }


   /**
    * Creates puzzles for Task 3
    * Based on puzzle size creates a puzzle and a visual representation of
    * its solution.\
    * Based on boolean paramater, ensures whether or not the puzzle returned
    * is solvable.
    * @param puzzleSize
    * @param hasSolution
    */
   private static void createT3Puzzles(int puzzleSize, boolean hasSolution) {

      Solution solution;
      Graph graph;

      do {
         graph = new Graph(puzzleSize);
         graph.populateGraph(); // binds numbers of jumps to graph
         graph.populateNeighbors();
         solution = Algorithms.BFS(graph);
      } while ( hasSolution ? solution.getK() < 0 : solution.getK() > 0);

      GUI gui = new GUI();
      String sizeAsStr = Integer.toString(puzzleSize);
      String kStr = Integer.toString(solution.getK());
      String name = hasSolution ?
              "Task 3a: " + sizeAsStr + "x" + sizeAsStr :
              "Task 3b: " + sizeAsStr + "x" + sizeAsStr;
      gui.run(graph, name);

      int[] distancePerCell = graph.getDistances();
      GUI numberOfMovesGUI = new GUI();
      numberOfMovesGUI.createNumberOfMovesGUI(puzzleSize, distancePerCell,
              name + " = " + kStr);
   }

}
