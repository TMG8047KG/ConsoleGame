package Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Terminal {
    private JPanel MainPanel;
    private JTextArea textArea;
    private final int height;
    private final int width;
    private final List<String> input = new ArrayList<>();
    
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

    public String readLine() {
        try {
            synchronized (input){
                while (input.isEmpty())
                    input.wait();

                return input.remove(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void setText(String text){
        textArea.setText(text);
    }

    public void print(String text){
        textArea.append(text);
    }
    public void println(){
        textArea.append("\n");
    }
    public void println(String text){
        textArea.append(text + "\n");
    }

    private void createUIComponents() {
        textArea.setBackground(new Color(0, 0,0));
        textArea.setForeground(new Color(0, 222, 29));
        textArea.setFont(new Font("Cascadia Code", Font.PLAIN,14));

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    synchronized (input){
                        String[] text = textArea.getText().split("\\n");
                        int lastLine = text.length-1;
                        //TODO: make it not to get the last line if nothing is typed
                        input.add(text[lastLine]);
                        input.notify();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }
}
