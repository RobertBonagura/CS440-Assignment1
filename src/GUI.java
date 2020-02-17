import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 * Graphical user interface to display n-by-n puzzle.
 * GUI used to represent n-by-n puzzle
 */
public class GUI {

    static JFrame f;

    /**
     * Creates n-by-n puzzle based on graph.
     * For each cell in the graph, a button is added to the GUI.
     * Each button displays the number of jumps that cell can make.
     * @param graph the puzzle is modeled after.
     */
    public static void run (Graph graph) {

        f = new JFrame();
        int n = graph.getN();
        int numberOfButtons = graph.getNumberOfCells();
        ArrayList<JButton> buttons = new ArrayList<>(numberOfButtons);

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


    /**
     * Creates n-by-n grid.
     * For each cell in the grid, a button is added to the GUI.
     * Each button displays the number of jumps to reach the cell.
     * @param the dimension of the grid.
     * @param the corresponding values for each cell.
     */
    public static void createNumberOfMovesGUI(int n, int[] values){

        f = new JFrame();
        int numberOfButtons = n*n;
        ArrayList<JButton> buttons = new ArrayList<>(numberOfButtons);

        int R_MIN, R_MAX, C_MIN, C_MAX;
        R_MIN = C_MIN = 0;
        R_MAX = C_MAX = n-1;

        //int index = (n * r) + c;
        for (int r = R_MIN; r <= R_MAX; r++){
            for (int c = C_MIN; c <= C_MAX; c++) {
                int index = (n * r) + c;
                if(values[index] == -1){
                    buttons.add(new JButton("X"));
                    continue;
                }else{
                    buttons.add(new JButton(values[index]+""));
                }
            }
        }

        for (int i = 0; i < numberOfButtons; i++){
            f.add(buttons.get(i));
        }

        f.setLayout(new GridLayout(n,n));
        f.setSize(600,600);
        f.setVisible(true);

    }
}
