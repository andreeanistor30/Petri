package View;

import javax.swing.*;
import java.awt.*;
public class WelcomePage {
    JFrame frame;
    JProgressBar progressBar = new JProgressBar();
    JLabel message = new JLabel();
    GUI gui = new GUI();

    WelcomePage() {
        createGUI();
        addProgressBar();
        addMessage();
        runningPBar();
    }

    public void createGUI() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(211,211,211));
        frame.setVisible(true);
    }

    public void addMessage() {
        message.setBounds(250, 320, 200, 40);//Setting the size and location of the label
        message.setForeground(Color.black);//Setting foreground Color
        message.setFont(new Font("Serif", Font.BOLD, 20));//Setting font properties
        frame.add(message);//adding label to the frame
    }

    public void addProgressBar() {
        progressBar.setBounds(100, 280, 400, 30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(new Color(192,192,192));
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        frame.add(progressBar);
    }

    public void runningPBar() {
        int i = 0;

        while (i <= 100) {
            try {
                Thread.sleep(50);
                progressBar.setValue(i);
                message.setText("LOADING " + Integer.toString(i) + "%");
                i++;
                if (i == 100) {
                    frame.dispose();
                    gui.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new WelcomePage();

    }
}



