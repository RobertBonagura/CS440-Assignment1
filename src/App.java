/**
 *
 * @author Manel Bermad and Robert Bonagura
 *
 * Driver application for CS440 Assignment 1
 * Generates user prompt to ask for size of puzzle.
 *
 * Next, creates a graph to represent a puzzle and then creates a GUI based on
 * the graph.
 *
 * A second graph is created using a Hill Climbing algorithm to generate a
 * difficult puzzle.
 *
 * Various algorithms are run on both graphs, and their runtime is printed
 * to the console.
 *
 */
public class App {

    /**
     * Main method of program invoking various methods on different graphs.
     * @param args
     */
    public static void main(String[] args) {

        //UserPrompt prompt = new UserPrompt();
        //System.out.println("Waiting for user to select n-by-n size...");
        //int n = prompt.getN();
        int n = 5;

        Graph graph = new Graph(n);
        graph.populateGraph(); // binds numbers of jumps to graph
        graph.populateNeighbors();
        GUI gui = new GUI();
        System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
        gui.run(graph, "Puzzle One");
        System.out.printf("Finished creating %s-by-%s sized puzzle.\n", n, n);

        System.out.println("BFS search on original graph");
        Solution solution = Algorithms.BFS(graph);
        System.out.println(solution);

        //int[] distancePerCell = graph.getDistances();
        //GUI numberOfMovesGUI = new GUI();
        //numberOfMovesGUI.createNumberOfMovesGUI(n, distancePerCell);

        int iterations = 100;
        System.out.printf("Performing Hill Climbing algorithm to create a " +
                        "difficult %s-by-%s puzzle...\n", n, n);
        HillClimbingResult hillResult = Algorithms.HillClimbing(graph,
                solution, iterations);
        Graph hillGraph = hillResult.getGraph();
        GUI gui2 = new GUI() ;
        gui2.run(hillGraph, "Puzzle Two");

        System.out.println("BFS search on result of HillClimbing");
        Solution solution2 = Algorithms.BFS(hillGraph);
        System.out.println(solution2);

        System.out.println("SPF search on original graph");
        Solution solution3 = Algorithms.SPF(graph);
        System.out.println(solution3);

        System.out.println("SPF search on result of HillClimbing");
        Solution solution4 = Algorithms.SPF(hillGraph);
        System.out.println(solution4);

        System.out.println("A* search on original graph");
        Solution solution5 = Algorithms.AStarSearch(graph);
        System.out.println(solution5);

        System.out.println("A* search on result of HillClimbing");
        Solution solution6 = Algorithms.AStarSearch(hillGraph);
        System.out.println(solution6);
    }

}
