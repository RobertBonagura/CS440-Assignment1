import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class GUI {

    static JFrame f;
    static Scanner scan;

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

    private static String generateLabel(int numberOfMoves) {
        Random r = new Random();
        int randNum =  r.nextInt((numberOfMoves - 1) + 1) + 1;
        String label = Integer.toString(randNum);
        return label;
    }

}
