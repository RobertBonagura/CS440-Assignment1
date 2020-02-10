<<<<<<< HEAD
/**
 * Driver application for Assignment
 * Generates uer prompt to ask for size of puzzle.
 * Creates a graph to represent a puzzle and then creates a GUI based on
 * the graph.
=======
import java.awt.event.WindowEvent;

/**
 * Application responsible for taking user input to display n-by-n puzzle.
 * First pop-up window asks user to choose size of puzzle.
 * Second window displays GUI of puzzle with a button representing each cell.
>>>>>>> ab02f182a079bf575720f782b674e3c5c4c3b020
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
        graph.show();
        GUI gui = new GUI();
        System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
<<<<<<< HEAD
        gui.run(graph);

=======
        gui.run(n);
>>>>>>> ab02f182a079bf575720f782b674e3c5c4c3b020
    }

}
