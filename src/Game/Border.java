package Game;

/**
 * @author TMG8047KG
 */
public class Border {

    private char[][] border;
    private char symbol;
    private int columns;
    private int rows;

    /**
     * Sets the border size
     * @param rows
     * Number of rows
     * @param columns
     * Number of columns
     * */
    public void setBorderSize(int rows, int columns){
        border = new char[rows][columns];
        this.columns = columns;
        this.rows = rows;
    }

    /**
     * Sets the symbol that is going to be used for all border sides
     * @param symbol
     * Sets the symbol
     * */
    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    /**
     * Builds the border by setting all sides with the set {@link Game.Border#symbol}
     * */
    private void buildBorder(){
        for(int a = 0; a < rows; a++){
            for(int b = 0; b < columns; b++){
                border[a][b] = ' ';
                if (a == 0)
                    border[a][b] = symbol;
                else if (a == rows - 1)
                    border[a][b] = symbol;
                else if (b == 0)
                    border[a][b] = symbol;
                else if (b == columns - 1)
                    border[a][b] = symbol;
            }
        }
    }

    /**
     * Prints the {@link #border}
     */
    public void printBorder(){
        buildBorder();
        for(int a = 0; a < rows; a++){
            for(int b = 0; b < columns; b++){
                System.out.print(border[a][b] + "  ");
            }
            System.out.println();
        }
    }

    /**
     * Gets the matrix with the set {@link #border}
     * @return Returns matrix with border
     * */
    public char[][] getBorder() {
        buildBorder();
        return border;
    }
}
