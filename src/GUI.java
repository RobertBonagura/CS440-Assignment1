import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
<<<<<<< HEAD
 * Graphical user interface to display n-by-n puzzle.
=======
 * GUI used to represent n-by-n puzzle
>>>>>>> ab02f182a079bf575720f782b674e3c5c4c3b020
 */
public class GUI {

    static JFrame f;

    /**
<<<<<<< HEAD
     * Creates n-by-n puzzle based on graph.
     * For each cell in the graph, a button is added to the GUI.
     * Each button displays the number of jumps that cell can make.
     * @param graph the puzzle is modeled after.
     */
    public static void run (Graph graph) {

=======
     * Creates puzzle.
     * Determines maximum number of moves a given cell has based on its
     * location within the puzzle.
     * Each cell displays a random number not exceeding this maximum number.
     * @param n size of puzzle
     */
    public static void run (int n) {
>>>>>>> ab02f182a079bf575720f782b674e3c5c4c3b020
        f = new JFrame();
        int n = graph.getN();
        int numberOfButtons = graph.getNumberOfCells();
        ArrayList<JButton> buttons = new ArrayList(numberOfButtons);

        int R_MIN, R_MAX, C_MIN, C_MAX;
        R_MIN = C_MIN = 0;
        R_MAX = C_MAX = n-1;

        for (int r = R_MIN; r <= R_MAX; r++){
            for (int c = C_MIN; c <= C_MAX; c++) {
                if (c == C_MAX && r == R_MAX) {
                    String label = Integer.toString(0);
                    buttons.add(new JButton(label));
                    continue;
                }
                Cell cell = graph.findCell(r, c);
                int numberOfJumps = cell.getNumberOfJumps();
                String label = Integer.toString(numberOfJumps);
                buttons.add(new JButton(label));
            }
        }

        for (int i = 0; i < numberOfButtons; i++){
            f.add(buttons.get(i));
        }

        f.setLayout(new GridLayout(n,n));
        f.setSize(600,600);
        f.setVisible(true);
    }

<<<<<<< HEAD
=======
    /**
     * Convert to String a number not exceeding the maximum number of moves.
     * @param maxValue representing maximum number of possible moves
     * @return a String representation of randomly chosen number
     */
    private static String generateLabel(int maxValue) {
        Random r = new Random();
        int randNum =  r.nextInt((maxValue - 1) + 1) + 1;
        String label = Integer.toString(randNum);
        return label;
    }

>>>>>>> ab02f182a079bf575720f782b674e3c5c4c3b020
}
