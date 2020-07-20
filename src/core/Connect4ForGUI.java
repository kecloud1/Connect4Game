/**
 * Description: Class that contains methods and variables for constructing a Connect 4 gameboard specifically for
 * JavaFX Gui class Connect4gui.java.
 * @author Kelsey Cloud
 * @version 1.0
 *
 */
package core;

public class Connect4ForGUI {

    /** Array of String objects to act as a gameboard. */
    private String [][] gameBoard = new String [6][15];
    /**int variable to keep track of whose turn it is. */
    private int count = 0;

    /**Default constructor to create the gameboard. */
    public Connect4ForGUI() {
        for (int row = 0; row<6; row++) {
            for (int col = 0; col<15; col++) {
                if (col%2 == 1) {
                    gameBoard [row][col] = "   ";
                }
                else {
                    gameBoard [row][col] = "|";
                }
            }
        }
    }

    /**Method checks if a space is empty
     * @param row is the row being checked
     * @param col is the column being checked
     * @return boolean */
    public boolean isSpaceEmpty(int row, int col) {
        if (gameBoard [row][col] == "   ")
            return true;
        else
            return false;
    }
    /**Method is called when a player takes turn.
     * @param col is column in which player wishes to place game piece
     * @return boolean. True if turn was taken, false if not. */
    public boolean takeTurn(int col) {
        int row = 5;

        if (col == 2) {
            col = 3;
        }
        else if (col == 3 ) {
            col = 5;
        }
        else if (col == 4) {
            col = 7;
        }
        else if  (col == 5) {
            col = 9;
        }
        else if  (col == 6) {
            col = 11;
        }
        else if (col == 7) {
            col = 13;
        }
        while (row >= 0) {
            if (this.isSpaceEmpty(row, col)) {
                if (this.checkTurnForPlay()== "X") {
                    gameBoard [row][col] = "X";}
                else {
                    gameBoard [row][col] = "O";}
                count ++;
                return true;
            }
            else row--;
        }

        return false;
    }
    /**method returns whose turn it is in order to determine who plays next
     * @return boolean */
    public String checkTurnForPlay() {
        if (count % 2 == 0) {
            return "X";
        }
        else {
            return "O";
        }
    }
    /**Method returns whose turn it is in order to determine if the
     * last turn resulted in a win
     * @return boolean */
    public String checkTurnForWin() {
        if ((count-1) % 2 == 0) {
            return "X";
        }
        else {
            return "O";
        }
    }
    /**Method checks if there are four consecutive pieces of the
     * same type in a horizontal line
     * @return boolean*/
    public boolean checkWinRight() {
        String turn = this.checkTurnForWin();
        for (int row=5; row>=0; row--) {
            for (int col=1; col<14; col= col+2) {
                if ( (col + 7)<=14) {
                    int a = col;
                    if (gameBoard[row][a]==turn) {
                        a = a + 2;
                        if (gameBoard[row][a] == turn) {
                            a = a + 2;
                            if (gameBoard [row][a] == turn) {
                                a = 2 + a;
                                if (gameBoard [row][a] == turn) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /**Method checks if there are four consecutive pieces of the
     * same type in a vertical line
     * @return boolean*/
    public boolean checkWinUp() {
        String turn = this.checkTurnForWin();
        for (int col=1; col<14; col= col+2) {
            for (int row=5; row>=0; row--) {
                if ( (row - 3)>=0) {
                    int b = row;
                    if (gameBoard[b][col] == turn) {
                        b = b-1;
                        if (gameBoard[b][col] == turn) {
                            b = b-1;
                            if (gameBoard [b][col] == turn) {
                                b = b-1;
                                if (gameBoard [b][col] == turn) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /**Method checks if there are four consecutive pieces of the
     * same type in a diagonal line up and to the right/down and to the left.
     * @return boolean*/
    public boolean checkWinUpRight() {
        String turn = this.checkTurnForWin();
        for (int col=1; col<14; col= col+2) {
            for (int row=5; row>=0; row--) {
                if ( (row - 3)>=0) {
                    if(col+7 <15) {
                        int a = col;
                        int b = row;
                        if (gameBoard[b][a]==turn) {
                            b = b-1;
                            a = a + 2;
                            if (gameBoard[b][a] == turn) {
                                b = b-1;
                                a = a + 2;
                                if (gameBoard [b][a] == turn) {
                                    b = b-1;
                                    a = a + 2;
                                    if (gameBoard [b][a] == turn) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /**Method checks if there are four consecutive pieces of the
     * same type in a line up and to the left/ down and to the right.
     * @return boolean*/
    public boolean checkWinUpLeft() {
        String turn = this.checkTurnForWin();
        for (int col=1; col<14; col= col+2) {
            for (int row=5; row>=0; row--) {
                if ( (row - 3)>=0) {
                    if(col - 7 >= 0) {
                        int a = col;
                        int b = row;
                        if (gameBoard[b][a] == turn) {
                            b = b - 1;
                            a = a - 2;
                            if (gameBoard[b][a] == turn) {
                                b = b - 1;
                                a = a - 2;
                                if (gameBoard [b][a] == turn) {
                                    b = b - 1;
                                    a = a - 2;
                                    if (gameBoard [b][a] == turn) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    /**Method checks if there are four consecutive pieces of the
     * same type on the board
     * @return boolean*/
    public boolean checkWin() {
        if (this.checkWinRight()) {
            return true;}
        else if (this.checkWinUp()) {
            return true;}
        if (this.checkWinUpRight()) {
            return true;}
        else if (this.checkWinUpLeft()) {
            return true;}
        else
            return false;
    }

    /**toString returns a String that prints the game board and pieces
     * @return String */
    public String toString() {
        String str = "";
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col<15; col++) {
                str = str + gameBoard [row][col];
            }
            str = str + "\n";
        }
        str = str + " 1  2  3  4  5  6  7\n";
        return str;
    }

}//End Class
