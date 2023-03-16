package Console;

import Game.Main;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.WrappedPlainView;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Terminal {
    private JPanel MainPanel;
    private JTextArea textArea;
    private JScrollPane scroll;
    private final int height;
    private final int width;
    private boolean editable;
    private char[][] charMatrix;
    private String[][] stringMatrix;
    private int[][] intMatrix;
    private final List<String> input = new ArrayList<>();
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    TerminalScheduleUpdate scheduleUpdate;

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

    public void addSchedule(TerminalScheduleUpdate scheduleUpdate){
        this.scheduleUpdate = scheduleUpdate;
    }
    public TerminalScheduleUpdate getSchedule(){
        return scheduleUpdate;
    }
    public void start(){
        executor.scheduleWithFixedDelay(scheduleUpdate::Update, 1000, 1000, TimeUnit.MILLISECONDS);
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
    public int[][] getIntMatrix(){
        return intMatrix;
    }
    public char[][] getCharMatrix(){
        return charMatrix;
    }
    public String[][] getStringMatrix(){
        return stringMatrix;
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
        textArea.setFont(new Font("Courier New", Font.BOLD,16));
        textArea.setEditable(editable);

//        textArea.setEditable(false);
//        scroll = new JScrollPane(textArea);
//        scroll.getVerticalScrollBar().setBackground(new Color(0, 0,0));
//        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
//            @Override
//            protected void configureScrollBarColors() {
//                this.thumbColor = new Color(0, 222, 29);
//            }
//        });
//        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    public void build(){
        createUIComponents();

        JFrame frame = new JFrame("Console");
        frame.setSize(width, height);
        //TODO: add scroll
        frame.setContentPane(textArea);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
