package Game;

import Console.Terminal;
import Console.TerminalScheduleUpdate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

    public static void main(String[] args) {
        Terminal terminal = new Terminal(450, 650);
        Matrix matrix = new Matrix();
        terminal.setEditable(false);
        terminal.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        terminal.setFontColor(new Color(140, 3, 252));
        terminal.build();
        matrix.setBorderSize(21, 31);
        matrix.setSymbol('*');
        terminal.setMatix(matrix.getMatrixWithBorder());
        terminal.printMatrix();

        TerminalScheduleUpdate update = new TerminalScheduleUpdate() {
            final char[][] test = terminal.getCharMatrix();
            int x = 0, y = 1;
            @Override
            public void Update() {
                x++;
                if(x == test.length-1){
                    y++;
                    x=1;
                    terminal.stop();
                }
                test[x][y] = '0';
                terminal.setMatix(test);
                terminal.clear();
                terminal.printMatrix();
            }
        };
        terminal.setSchedule(update);
        terminal.start();


        //WASD input
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_G){
                    terminal.clear();
                }
                if(e.getKeyCode() == KeyEvent.VK_W){
                    System.out.println("Forward!");
                }
                if(e.getKeyCode() == KeyEvent.VK_A){
                    System.out.println("Left!");
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    System.out.println("Downwards!");
                }
                if(e.getKeyCode() == KeyEvent.VK_D){
                    System.out.println("Right!");
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        terminal.addKeyListener(keyListener);
    }
}