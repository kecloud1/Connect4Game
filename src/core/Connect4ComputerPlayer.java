/**
 * Description: Contains method for Connect4.java that
 * generates a random move for a computer opponent.
 * @author Kelsey Cloud
 * @version 1.0
 *
 */package core;
import java.lang.Object;
import java.util.Random;


public class Connect4ComputerPlayer {
    /** class variables */
    int randCol = 0;
    Random rnd = new Random();

    /**
     * @return random int between 1-7 for turn
     */
    public int CtakeTurn() {
        randCol = 0 ;
        while (randCol == 0) {
            randCol = rnd.nextInt(8); }
        return randCol;
    }
}