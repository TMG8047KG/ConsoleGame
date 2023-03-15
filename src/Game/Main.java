package Game;

import Console.Terminal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main{

    public static void main(String[] args) {
        Terminal terminal = new Terminal(450, 650);
        Matrix matrix = new Matrix();
        terminal.setEditable(false);
        terminal.build();
        matrix.setBorderSize(20, 30);
        matrix.setSymbol('*');
        terminal.setMatix(matrix.getMatrixWithBorder());
        terminal.printMatrix();

        //WASD input
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
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


        //Problem thing
        System.out.println(terminal.readLine());
    }
}