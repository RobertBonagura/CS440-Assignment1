import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

/**
 * GUI used to represent n-by-n puzzle
 */
public class GUI {

    static JFrame f;
    static Scanner scan;

    /**
     * Creates puzzle.
     * Determines maximum number of moves a given cell has based on its
     * location within the puzzle.
     * Each cell displays a random number not exceeding this maximum number.
     * @param n size of puzzle
     */
    public static void run (int n) {
        f = new JFrame();

        int C_MIN, C_MAX, R_MIN, R_MAX;
        C_MIN = R_MIN = 1;
        C_MAX = R_MAX = n;
        Cell start = new Cell(C_MIN, R_MIN);
        Cell goal = new Cell(C_MAX, R_MAX);

        int numberOfButtons = (int) Math.pow(n, 2);
        ArrayList<JButton> buttons = new ArrayList(numberOfButtons);
        for (int c = C_MIN; c <= C_MAX; c++){
            for (int r = R_MIN; r <= R_MAX; r++) {
                if (c == C_MAX && r == R_MAX) {
                    int numberOfMoves, cValidMoves, rValidMoves;
                    numberOfMoves = cValidMoves = rValidMoves = 0;
                    String label = Integer.toString(numberOfMoves);
                    buttons.add(new JButton(label));
                    continue;
                }
                int cValidMoves = Math.max((C_MAX - c), (c - C_MIN));
                int rValidMoves = Math.max((R_MAX - r), (r - R_MIN));
                int numberOfMoves = Math.max(cValidMoves, rValidMoves);
                String label = generateLabel(numberOfMoves);
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

}
