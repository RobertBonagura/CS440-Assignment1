/**
 * Driver application for Assignment
 * Generates user prompt to ask for size of puzzle.
 * Creates a graph to represent a puzzle and then creates a GUI based on
 * the graph.
 */
public class App {

    public static void main(String[] args) {

        //UserPrompt prompt = new UserPrompt();
        //System.out.println("Waiting for user to select n-by-n size...");
        //int n = prompt.getN();
        int n = 100;

        Graph graph = new Graph(n);
        graph.populateGraph(); // binds numbers of jumps to graph
        graph.populateNeighbors();
        GUI gui = new GUI();
        System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
        gui.run(graph);
        System.out.printf("Finished creating %s-by-%s sized puzzle.\n", n, n);

        System.out.println("BFS search on original graph");
        Solution solution = Algorithms.BFS(graph);
        System.out.println(solution);

        //int[] distancePerCell = graph.getDistances();
        //GUI numberOfMovesGUI = new GUI();
        //numberOfMovesGUI.createNumberOfMovesGUI(n, distancePerCell);

        int iterations = 100;
        System.out.printf("Performing Hill Climbing algorithm to create" +
                        "difficult %s-by-%s puzzle...\nUsing %s iterations.\n ", n, n,
                iterations);
        HillClimbingResult hillResult = Algorithms.HillClimbing(graph, solution, iterations);

        Graph hillGraph = hillResult.getGraph();
        GUI gui2 = new GUI() ;
        gui2.run(hillGraph);

        System.out.println("BFS search on result of HillClimbing");
        Solution[] hillClimbingIterations = hillResult.getSolutions();
        Solution solution2 = hillClimbingIterations[hillResult.getSolutionIndex()];
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
