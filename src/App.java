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

        Graph graph = new Graph(n);
        graph.populateGraph();
        graph.populateNeighbors();
        graph.show();

        GUI gui = new GUI();
        System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
        gui.run(graph);
        System.out.printf("Finished creating %s-by-%s sized puzzle.\n", n, n);

        Solution solution = Algorithms.BFS(graph);
        System.out.println(solution);


    }

}
