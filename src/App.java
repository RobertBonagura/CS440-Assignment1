import java.util.Scanner;

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
        System.out.printf("Found n value\n");
        Graph graph = new Graph(n);
        graph.populateGraph();
        graph.populateNeighbors();
        //graph.show(); JUST FOR NOW
        GUI gui = new GUI();
        System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
        gui.run(graph);
        
        System.out.printf("Finished creating %s-by-%s sized puzzle.\n", n, n);
        
        int [] BFSdistances = Algorithms.BFS(graph);
        int puzzleValue = Algorithms.getPuzzleValue(BFSdistances);       
        System.out.println("puzzleValue = " + puzzleValue);
        GUI g = new GUI();
 	   	g.createSimpleGUI(n, BFSdistances);

        
        
        int iterations = 0;      
        
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Please enter number of iterations (at least 50) for hill climbing: ");
            String next = scanner.next();
            try {
            	iterations = Integer.parseInt(next);
            } catch (NumberFormatException exp) {
            }
        } while (iterations < 0);

        System.out.println("You input " + iterations);
        
        Algorithms.HillClimbing(graph, puzzleValue, iterations);

        
    }

}