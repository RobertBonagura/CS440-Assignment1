/**
 * Driver application for Assignment
 * Generates user prompt to ask for size of puzzle.
 * Creates a graph to represent a puzzle and then creates a GUI based on
 * the graph.
 */
public class App {

    public static void main(String[] args) {

        UserPrompt prompt = new UserPrompt();
        System.out.println("Waiting for user to select n-by-n size...");
        int n = prompt.getN();
        //int n = 100;
        Graph graph = new Graph(n);
        graph.populateGraph(); // binds numbers of jumps to graph
        graph.populateNeighbors();
        //graph.show();

        GUI gui = new GUI();
        System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
        gui.run(graph);
        System.out.printf("Finished creating %s-by-%s sized puzzle.\n", n, n);
        System.out.println("BFS search on original graph");
        Solution solution = Algorithms.BFS(graph);
        System.out.println(solution);

        // Visualize number of moves for each cell
        //int[] distancePerCell = graph.getDistances();
        //GUI numberOfMovesGUI = new GUI();
        //numberOfMovesGUI.createNumberOfMovesGUI(n, distancePerCell);

        int iterations = 5;
        System.out.println("BFS search on result of HillClimbing");
        Graph hillGraph = Algorithms.HillClimbing(graph, solution.getK(), iterations);
        Solution solution2 = Algorithms.BFS(hillGraph);
        System.out.println(solution2);

        System.out.println("A* search on original graph");
        Solution solution3 = Algorithms.AStarSearch(graph);
        System.out.println(solution3);

        System.out.println("A* search on result of HillClimbing");
        Solution solution4 = Algorithms.AStarSearch(hillGraph);
        System.out.println(solution4);


    }

}
