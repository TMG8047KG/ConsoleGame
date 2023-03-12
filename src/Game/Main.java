package Game;

public class Main {
    public static void main(String[] args) {
        Border border = new Border();
        border.setBorderSize(20, 10);
        border.setSymbol('*');
        border.printBorder();
    }
}