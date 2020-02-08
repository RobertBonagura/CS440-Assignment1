import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class Window {

   private int n;
   private JFrame frame;

   public Window () {

      frame = new JFrame("Assignment1");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JLabel textLabel = new JLabel("What size puzzle do you want?",
              SwingConstants.CENTER);
      textLabel.setPreferredSize(new Dimension(500, 50));

      JRadioButton option1 = new JRadioButton("5-by-5", false);
      JRadioButton option2 = new JRadioButton("7-by-7", false);
      JRadioButton option3 = new JRadioButton("9-by-9", false);
      JRadioButton option4 = new JRadioButton("11-by-11", false);

      ButtonGroup group = new ButtonGroup();
      group.add(option1);
      group.add(option2);
      group.add(option3);
      group.add(option4);

      JButton submit = new JButton("Submit");
      submit.setSize(500, 100);
      submit.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (option1.isSelected()){
               n = 5;
            }
            if (option2.isSelected()){
               n = 7;
            }
            if (option3.isSelected()){
               n = 9;
            }
            if (option4.isSelected()){
               n = 11;
            }
         }
      });

      frame.setLayout(new FlowLayout());
      frame.add(textLabel);
      frame.add(option1);
      frame.add(option2);
      frame.add(option3);
      frame.add(option4);
      frame.add(submit);

      frame.setSize(500, 150);
      frame.setVisible(true);
   }

   public int getN() {
      while (n == 0){
         System.out.println("waiting for n value");
      }
      return n;
   }

   public void closeWindow(){
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
   }
}
