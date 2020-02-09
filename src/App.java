import java.awt.event.WindowEvent;

/**
 * Application responsible for taking user input to display n-by-n puzzle.
 * First pop-up window asks user to choose size of puzzle.
 * Second window displays GUI of puzzle with a button representing each cell.
 */
public class App {

    public static void main(String[] args) {
        Window window = new Window();
        System.out.println("Waiting for user to select n-by-n size...");
        int n = window.getN();
        System.out.printf("Found n value\n");
        //window.closeWindow();
        GUI gui = new GUI();
        System.out.printf("Creating %s-by-%s sized puzzle...\n", n, n);
        gui.run(n);
    }

}
