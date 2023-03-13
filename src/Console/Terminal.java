package Console;

import javax.swing.*;
import java.awt.*;

public class Terminal {
    private JPanel MainPanel;
    private JTextArea textArea;
    private final int height;
    private final int width;
    
    public Terminal(int height, int width){
        this.height = height;
        this.width = width;
    }

    public void buildWindow(){
        JFrame frame = new JFrame("Console");
        frame.setSize(width, height);
        createUIComponents();
        frame.setContentPane(textArea);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void clear(){
        textArea.setText("");
    }

    public void setText(String text){
        textArea.setText(text);
    }

    public void print(String text){
        textArea.setText(textArea.getText() + text);
    }

    public void println(){
        textArea.setText(textArea.getText() + "\n");
    }
    public void println(String text){
        textArea.setText(textArea.getText() + text + "\n");
    }

    private void createUIComponents() {
        textArea.setBackground(new Color(0, 0,0));
        textArea.setForeground(new Color(0, 222, 29));
        textArea.setFont(new Font("Cascadia Code", Font.PLAIN,14));
    }
}
