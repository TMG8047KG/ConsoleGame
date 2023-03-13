package Game;

import Console.Terminal;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal(450, 650);
        Border border = new Border();
        terminal.buildWindow();
        border.setBorderSize(20, 20);
        border.setSymbol('*');
        border.printBorder();
        char[][] gey = border.getBorder();
        for(int a = 0; a<border.getRows();a++){
            for(int b = 0; b<border.getColumns();b++){
                terminal.print(gey[a][b] + " ");
            }
            terminal.println();
        }
    }
}