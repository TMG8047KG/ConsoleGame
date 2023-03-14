package Game;

import Console.Terminal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal(450, 650);
        Border border = new Border();
        terminal.buildWindow();
        border.setBorderSize(20, 20);
        border.setSymbol('*');
//        border.printBorder();
        terminal.println("tosho");
        System.out.println(terminal.readLine());

        char[][] test = border.getBorder();
        for(int a = 0; a<border.getRows();a++){
            for(int b = 0; b<border.getColumns();b++){
                terminal.print(test[a][b] + " ");
            }
            terminal.println();
        }
        //Problem thing
        System.out.println(terminal.readLine());
    }
}