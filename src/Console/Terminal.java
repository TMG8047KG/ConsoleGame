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
    private boolean editable;
    private char[][] charMatrix;
    private String[][] stringMatrix;
    private int[][] intMatrix;
    private final List<String> input = new ArrayList<>();

    //Constructor
    /**
     * Defines the size of the console
     * @param height
     * The height in pixels
     * @param width
     * The width in pixels*/
    public Terminal(int height, int width){
        this.height = height;
        this.width = width;
    }

    public String readLine() {
        readEvent();
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

    private void readEvent(){
        KeyListener readLine = new KeyListener() {
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
        };
        textArea.addKeyListener(readLine);
    }

    public void addKeyListener(KeyListener listener){
        textArea.addKeyListener(listener);
    }

    public void setEditable(boolean editable){
        this.editable = editable;
    }

    //Matrix implementation
    public void setMatix(int[][] intMatrix){
        this.intMatrix = intMatrix;
    }
    public void setMatix(char[][] charMatrix){
        this.charMatrix = charMatrix;
    }
    public void setMatix(String[][] stringMatrix){
        this.stringMatrix = stringMatrix;
    }
    public void printMatrix(){
        if(charMatrix != null){
            for (char[] matrix : charMatrix) {
                for (int b = 0; b < charMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
        if(stringMatrix != null){
            for (String[] matrix : stringMatrix) {
                for (int b = 0; b < stringMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
        if(intMatrix != null){
            for (int[] matrix : intMatrix) {
                for (int b = 0; b < intMatrix[0].length; b++) {
                    print(matrix[b] + " ");
                }
                println();
            }
        }
    }

    //Basic input methods
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
    public void clear(){
        textArea.setText("");
    }


    private void createUIComponents() {
        textArea.setBackground(new Color(0, 0,0));
        textArea.setForeground(new Color(0, 222, 29));
        textArea.setFont(new Font("Cascadia Code", Font.PLAIN,14));
        textArea.setEditable(editable);
    }

    public void build(){
        JFrame frame = new JFrame("Console");
        frame.setSize(width, height);
        frame.setContentPane(textArea);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        createUIComponents();
    }
}
