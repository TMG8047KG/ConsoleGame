package Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author TMG8047KG
 * @version 1.0.0*/
public class Terminal {
    private JPanel MainPanel;
    private JTextArea textArea;
    private JScrollPane scroll;
    private final int height;
    private final int width;
    private boolean editable;
    private Color backgroundColor = new Color(0, 0,0);
    private Color fontColor = new Color(0, 220, 30);
    private Font font = new Font("Courier New", Font.BOLD,16);
    private final List<String> input = new ArrayList<>();
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    //TODO: Add option for multiple schedules
    TerminalScheduleUpdate scheduleUpdate;
    int initialDelay = 0;
    int delay = 500;
    private char[][] charMatrix;
    private String[][] stringMatrix;
    private int[][] intMatrix;

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

    /**
     * Sets the schedule (periodic execution) for the terminal
     * Schedules default execute times are on every second executes 2 times (2t/s)
     * This executes everything in {@link TerminalScheduleUpdate#Update()} indefinitely, until stopped
     * @param scheduleUpdate
     * adds new {@link Console.TerminalScheduleUpdate}
     * @see "{@link #}"
     * */
    public void setSchedule(TerminalScheduleUpdate scheduleUpdate){
        this.scheduleUpdate = scheduleUpdate;
    }
    /**
     * Returns the set schedule
     * @return Returns schedule
     * */
    public TerminalScheduleUpdate getSchedule(){
        return scheduleUpdate;
    }
    /**
     * Sets the delay between executions in the schedule (in milliseconds)
     * @param delay
     * delay in milliseconds
     * */
    public void setDelay(int delay){
        this.delay = delay;
    }
    /**
     * Sets the initial delay for the schedule (in milliseconds)
     * @param initialDelay
     * delay in milliseconds
     * */
    public void setInitialDelay(int initialDelay){
        this.initialDelay = initialDelay;
    }
    /**
     * Starts the schedule
     * Uses on one CPU thread
     * */
    public void start(){
        executor.scheduleWithFixedDelay(scheduleUpdate::Update, initialDelay, delay, TimeUnit.MILLISECONDS);
    }
    /**
     * Stops the schedule*/
    public void stop(){
        executor.shutdown();
    }


    public void addKeyListener(KeyListener listener){
        textArea.addKeyListener(listener);
    }
    public void removeKeyListener(KeyListener listener){
        textArea.removeKeyListener(listener);
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

    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
    }
    public void setFontColor(Color fontColor){
        this.fontColor = fontColor;
    }
    public void setFont(Font font){
        this.font = font;
    }

    private void createUIComponents() {
        textArea.setBackground(backgroundColor);
        textArea.setForeground(fontColor);
        textArea.setFont(font);
        textArea.setEditable(editable);
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
