import java.awt.event.WindowEvent;

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
