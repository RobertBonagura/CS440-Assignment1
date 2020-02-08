import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class GUI {

    static JFrame f;
    static Scanner scan;

    public static void run (int n) {
        f = new JFrame();

        int numberOfButtons = (int) Math.pow(n, 2);
        ArrayList<JButton> buttons = new ArrayList(numberOfButtons);

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++) {
                String label = "(" + i + " , " + j + " )";
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

}
