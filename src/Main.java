

public class Main {

   public static void main(String[] args) {

      Task3();
      Task4();
   }

   /**
    * Task 3 Driver Method.
    */
   private static void Task3() {

      int[] puzzleSizes = new int[]{5, 7, 9, 11};
      for (int puzzleSize : puzzleSizes){
         createPuzzle(puzzleSize, true);
         createPuzzle(puzzleSize, false);
      }
   }

   /**
    * Task 4 Driver Method.
    */
   private static void Task4() {
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
   private static void createPuzzle(int puzzleSize, boolean hasSolution) {

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
