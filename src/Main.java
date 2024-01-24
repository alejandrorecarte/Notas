import controllers.NotasMain;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class Main {

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static Dimension SIZE = new Dimension(700,500);
    public static void main(String[] args) {
        JFrame frame = new JFrame(ResourceBundle.getBundle("languages").getString("lTitulo"));
        frame.setContentPane(new NotasMain(frame).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(screenSize.width/2 - SIZE.width/2,screenSize.height/2 - SIZE.height/2, SIZE.width, SIZE.height);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}