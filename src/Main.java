import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

   public static void main(String[] args) throws IOException {

      Scanner scan = new Scanner(System.in);


      System.out.printf("NOTE TO USER:\nAs you progress through this " +
              "program, many puzzles and visualizations with be " +
              "created in the form of JFrame's.\nIt is recommended that" +
              " the user manually closes out of these of the windows " +
              "as you progress through the program.\nENJOY!\n\n");
      // Task 2
      task2();

      // Task 3
      task3();

      // Task 4 - Hill Climbing
      int ITERATIONS_4 = 500;
      int REPETITIONS_4 = 50;
      //plotTask(ITERATIONS_4, REPETITIONS_4, 4);
      TaskResult result = task4(ITERATIONS_4);
      Graph hillGraph = new Graph(result.getMaxGraph());
      System.out.println("Enter a character to move on to tasks 5 and 6 " +
              "where SPF and A* will be run on this newly created graph:");
      scan.nextLine();

      // Task 5 - SPF
      System.out.println("TASK 5:");
      task5(hillGraph);

      // Task 6 - A*
      System.out.println("TASK 6:");
      task6(hillGraph);
      System.out.println("Press enter to move on to task 7:");
      scan.nextLine();

      // Task 7 - Genetic Algorithm
      int ITERATIONS_7 = 200;
      int REPETITIONS_7 = 50;
      //plotTask(ITERATIONS_7, REPETITIONS_7, 7);
      result = task7(ITERATIONS_7);
      Graph geneticGraph = new Graph(result.getMaxGraph());
      // visualize
      System.out.println("Genetic Algorithm Complete!");
      System.out.println("Press enter to perform SPF and A* on the graph" +
              "returned by the Genetic Algorithm:");
      scan.nextLine();

      System.out.println("SPF on Genetic graph:");
      task5(geneticGraph);
      System.out.println();

      System.out.println("A* on Genetic graph:");
      task6(geneticGraph);
      System.out.println("Press enter to move on to task 9:");
      scan.nextLine();

      // Task 9
      System.out.println("TASK 9:");
      task9();
   }

   private static void task2() {

      System.out.printf("TASK 2:\nPlease enter any size n to " +
              "generate a new graph and a visualization of its " +
              "solution.\nFor sizes larger than 30, you may need to " +
              "manually resize your window to view the visualization.\n" +
              "Values larger than 100 are not recommended.\n");
      Scanner scan = new Scanner(System.in);
      int n = scan.nextInt();
      Graph graph = new Graph(n);
      graph.populateGraph(); // binds numbers of jumps to graph
      graph.populateNeighbors();
      GUI gui = new GUI();

      System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
      gui.run(graph, "Task 2");
      System.out.printf("Finished creating %s-by-%s sized puzzle.\n", n, n);


      GUI numberOfMovesGUI = new GUI();
      Solution solution = Algorithms.BFS(graph);
      int[] distancePerCell = graph.getDistances();
      System.out.println(solution);
      int k = solution.getK();
      numberOfMovesGUI.createNumberOfMovesGUI(n, distancePerCell,
              "Task 2 = " + k);
      System.out.println("Press enter a character to move on to task 3:");
      scan.next();
      return;
   }


   /**
    * Task 3 Driver Method.
    */
   private static void task3() {


      System.out.printf("\nTASK 3:\nSee the 8 puzzles generated by this task." +
              "\nEach size (5, 7, 9 & 11) has two puzzles each; one with a " +
              "positive k value and one with a negative k value.\n" +
              "Each puzzle is provided with a corresponding visualization" +
              "of it's solution\n");
      int[] puzzleSizes = new int[]{5, 7, 9, 11};
      for (int puzzleSize : puzzleSizes){
         createT3Puzzles(puzzleSize, true);
         createT3Puzzles(puzzleSize, false);
      }
      System.out.println("Enter a character to move on to task 4:");
      Scanner scan = new Scanner(System.in);
      scan.nextLine();

      return;
   }

   /**
    * Task 4 Driver Method.
    */
   private static TaskResult task4(int iterations) {

      System.out.println("TASK 4:");
      System.out.printf("Generating a graph using HillClimbing algorithm " +
              "with %s iterations...\n\n", iterations);
      int[] puzzleSizes = new int[]{5, 7, 9, 11};
      int ITERATIONS = iterations;
      Solution solutions[][] = new Solution[puzzleSizes.length][ITERATIONS];
      double times_ms[][] = new double[puzzleSizes.length][ITERATIONS];
      int maxK = 0;
      Graph maxGraph = null;
      for (int i = 0; i < puzzleSizes.length; i++) { // for each puzzle size
         maxK = 0;
         maxGraph = null;
         for (int j = 0; j < ITERATIONS; j++) {
            Graph graph = new Graph(puzzleSizes[i]);
            graph.populateGraph();
            graph.populateNeighbors();
            graph.setDistances();
            Solution solution = Algorithms.BFS(graph);
            Result hillResult = Algorithms.HillClimbing(graph, solution, j);
            solution = hillResult.getSolution();
            double time_ms = hillResult.getComputationTime_ms();
            if (solution.getK() > maxK){
               maxK = solution.getK();
               maxGraph = new Graph(graph);
            }
            solutions[i][j] = solution;
            times_ms[i][j] = time_ms;
         }
      }
      System.out.println("Hill Climbing Algorithm Complete!");
      TaskResult result = new TaskResult(solutions, times_ms, maxK, maxGraph);
      return result;
   }


   private static void task5(Graph difficultGraph) {

      Graph graph = new Graph(difficultGraph);
      Solution solution = Algorithms.SPF(graph);
      System.out.println("The result of SPF");
      System.out.println(solution);
   }

   private static void task6(Graph difficultGraph) {
      Graph graph = new Graph(difficultGraph);
      Solution solution = Algorithms.AStarSearch(graph);
      System.out.println("The result of A*");
      System.out.println(solution);

   }

   private static TaskResult task7(int iterations) {

      System.out.println("TASK 7:");
      System.out.printf("Generating a graph using Genetic algorithm " +
              "with %s iterations...\n\n", iterations);
      int[] puzzleSizes = new int[]{5, 7, 9, 11};
      int populationSize = 4;
      int ITERATIONS = iterations;
      Solution solutions[][] = new Solution[puzzleSizes.length][ITERATIONS];
      double times_ms[][] = new double[puzzleSizes.length][ITERATIONS];
      int maxK = 0;
      Graph maxGraph = null;
      for (int i = 0; i < puzzleSizes.length; i++) {
         maxK = 0;
         maxGraph = null;
         for (int j = 0; j < iterations; j++) {
            Graph graph = new Graph(puzzleSizes[i]);
            graph.populateGraph();
            graph.populateNeighbors();
            graph.setDistances();
            Solution solution;
            Result geneticResult = Algorithms.Genetic(puzzleSizes[i], populationSize, j+1);
            solution = geneticResult.getSolution();
            graph = geneticResult.getGraph();
            double time_ms = geneticResult.getComputationTime_ms();
            if (solution.getK() > maxK){
               maxK = solution.getK();
               maxGraph = new Graph(graph);
            }
            solutions[i][j] = solution;
            times_ms[i][j] = time_ms;
         }
      }
      TaskResult result = new TaskResult(solutions, times_ms, maxK, maxGraph);
      return result;
   }

   private static void task9() {

      int n = 500;
      Graph graph = new Graph(n);
      graph.populateGraph(); // binds numbers of jumps to graph
      graph.populateNeighbors();

      Solution solution = Algorithms.BFS(graph);
      int iterations = 100;
      System.out.printf("Performing Hill Climbing algorithm to create a " +
              "difficult %s-by-%s puzzle...\nThis puzzle will not be " +
              "visualized as the JFrame components are the bottleneck of" +
              "this task", n, n);
      Result hillResult = Algorithms.HillClimbing(graph, solution, iterations);
      Graph hillGraph = hillResult.getGraph();

      //GUI gui = new GUI();
      //gui.run(hillGraph, "Super Crazy Difficult Puzzle worthy of extra credit");

      System.out.println("BFS search on result of HillClimbing");
      Solution solution2 = Algorithms.BFS(hillGraph);
      System.out.println(solution2);

      System.out.println("SPF search on result of HillClimbing");
      Solution solution4 = Algorithms.SPF(hillGraph);
      System.out.println(solution4);

      System.out.println("A* search on result of HillClimbing");
      Solution solution6 = Algorithms.AStarSearch(hillGraph);
      System.out.println(solution6);
      return;
   }

   private static void plotTask(int iterations, int repetitions, int task)
           throws FileNotFoundException {

      System.out.printf("Plotting Graph...\n");
      int REPETITIONS = repetitions;
      int ITERATIONS = iterations;
      int[][] totalK = new int[4][ITERATIONS];
      double[][] totalTime_ms = new double[4][ITERATIONS];
      for (int i = 0; i < REPETITIONS; i++) {
         TaskResult result;
         if (task == 4){
            result = task4(ITERATIONS);
         } else if (task ==7 ){
            result = task7(ITERATIONS);
         } else {
            result = null;
         }
         Solution solutions[][] = result.getSolutions();
         double[][] times_ms = result.getTimes_ms();
         for (int j = 0; j < ITERATIONS ; j++){
            totalK[0][j] += solutions[0][j].getK();
            totalK[1][j] += solutions[1][j].getK();
            totalK[2][j] += solutions[2][j].getK();
            totalK[3][j] += solutions[3][j].getK();
            totalTime_ms[0][j] += times_ms[0][j];
            totalTime_ms[1][j] += times_ms[1][j];
            totalTime_ms[2][j] += times_ms[2][j];
            totalTime_ms[3][j] += times_ms[3][j];
         }
      }
      float[][] averageK = new float[4][ITERATIONS];
      double[][] averageTime_ms = new double[4][ITERATIONS];
      for (int j = 0; j < ITERATIONS ; j++) {
         averageK[0][j] = (float) totalK[0][j] / REPETITIONS;
         averageK[1][j] = (float) totalK[1][j] / REPETITIONS;
         averageK[2][j] = (float) totalK[2][j] / REPETITIONS;
         averageK[3][j] = (float) totalK[3][j] / REPETITIONS;
         averageTime_ms[0][j] = totalTime_ms[0][j] / REPETITIONS;
         averageTime_ms[1][j] = totalTime_ms[1][j] / REPETITIONS;
         averageTime_ms[2][j] = totalTime_ms[2][j] / REPETITIONS;
         averageTime_ms[3][j] = totalTime_ms[3][j] / REPETITIONS;
      }
      if (task == 4){
         writeXLSX(averageK, averageTime_ms, "Hill_Climbing.xlsx");
      } else if (task == 7){
         writeXLSX(averageK, averageTime_ms, "Genetic.xlsx");
      }
   }

   private static void writeXLSX(float[][] averageK, double[][] averageTime_ms,
                                 String name) throws FileNotFoundException {

      XSSFWorkbook workbook = new XSSFWorkbook();
      XSSFSheet sheet1 = workbook.createSheet(name + "_scores");

      int rowCount = 0;
      for (float[] size : averageK) {
         Row row = sheet1.createRow(++rowCount);
         int columnCount = 0;
         for (float avgK : size) {
            Cell cell = row.createCell(++columnCount);
            cell.setCellValue(avgK);
         }
      }
      XSSFSheet sheet2 = workbook.createSheet(name + "_time_ms");

      rowCount = 0;
      for (double[] size : averageTime_ms) {
         Row row = sheet2.createRow(++rowCount);
         int columnCount = 0;
         for (double avgT_ms : size) {
            Cell cell = row.createCell(++columnCount);
            cell.setCellValue(avgT_ms);
         }
      }

      try (FileOutputStream outputStream = new FileOutputStream(name)) {
         workbook.write(outputStream);
      } catch (IOException e) {
         e.printStackTrace();
      }
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
