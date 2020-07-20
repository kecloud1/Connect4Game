/**
 * Description: Class that contains the text console for a Connect 4 game. Program asks
 * player if they would like to play agaisnt a computer, and then proceeds based on the selection.
 *
 * If the player opts to play against another play, the program allows for a turn based game,
 * which allows for input from each player. A winning state is checked after each turn
 * made by either player.
 *
 * If the player opts to play against the computer, the program prompts for input
 * and then the computer AI takes a turn immediately after. A winning state is checked after each turn
 * made by either the player or the computer.
 *
 * @author Kelsey Cloud
 * @version 1.0
 *
 */
package ui;

import java.util.Scanner;

import core.*;
import javafx.application.Application;
import java.io.*;

public class Connect4TextConsole {
    /** Entry point of program*/
    public static void main(String[] args) {


        Connect4 game = new Connect4();
        Scanner scanner = new Scanner(System.in);
        boolean endGame = game.checkWin();
        String player = "";
        String playComp = "C";
        String playPlayer = "P";
        String select = "";

        //Program prompts for selection. Choices are to play online, play offline, or play computer
        System.out.println("Please select your game experience. \nEnter \"O\" to play online against another player. " +
                "\nEnter \"L\" to play against another player locally.");
        String experience = scanner.next();
        while (!experience.equalsIgnoreCase("O") &&
                !experience.equalsIgnoreCase("L") && !experience.equalsIgnoreCase("C")){
            System.out.println(experience + " is not a valid entry. Please select your game experience. Enter \"O\" " +
                    "to play online against another player. \nEnter \"L\" to play against another player locally.");
            experience = scanner.next();
        }
        if (experience.equalsIgnoreCase("O")){
            Application.launch(Connect4Server.class);
            System.exit(0);
        }


        // program prompt user for a selection. Player can choose to play against a computer
        //or another player.
        System.out.println("Enter T to use the text console, or G to use the GUI.");
        select = scanner.next();

        while (select.equalsIgnoreCase("T") == false && select.equalsIgnoreCase("G") == false) {
            System.out.println(select + " is not a valid input. Please enter valid input.");
            select = scanner.next();
        }
        if (select.equalsIgnoreCase("G")) {
            Application.launch(Connect4GUI.class);
            System.exit(0);
        }

        System.out.println(game.toString());
        System.out.println("Begin Game. Select 'P' if you want to play against another player, "
                + "or 'C' to play against the computer.");
        player = scanner.next();

        /** Checks for valid input and reprompts user for a selection. The only valid inputs are c or p,
         * which is not case sensitive*/
        while (player.equalsIgnoreCase("C") == false && player.equalsIgnoreCase("P") == false) {
            System.out.println( player + " is not a valid input. Please enter valid input.");
            player = scanner.next();
        }

        /** Proceeds with program if the player chooses to play another player*/
        if (player.equalsIgnoreCase(playPlayer) == true) {

            while (endGame == false) {
                String playerTurn = game.checkTurnForPlay();
                System.out.println("Player "+ playerTurn +", your turn. Choose a column 1-7.");

                while (scanner.hasNextInt() == false) {
                    String trash = scanner.next();
                    System.out.println("Please enter valid input. Player "
                            + playerTurn+", your turn. Choose a column 1-7.");
                }
                int col = scanner.nextInt();

                game.takeTurn(col);

                endGame = game.checkWin();

                System.out.println(game.toString());
            }
            String winner = game.checkTurnForWin();
            System.out.println("Player "+ winner + " won the game!");
        }
        /** Proceeds with program if the player chooses to play against the computer*/
        else if (player.equalsIgnoreCase(playComp) == true) {
            while (endGame == false) {
                String playerTurn = game.checkTurnForPlay();
                Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
                if (playerTurn == "X") {
                    System.out.println("Player X, your turn. Choose a column 1-7.");

                    while (scanner.hasNextInt() == false) {
                        String trash = scanner.next();
                        System.out.println("Please enter valid input. Player X, your turn. "
                                + "Choose a column 1-7.");
                    }
                    int col = scanner.nextInt();

                    game.takeTurn(col);
                    System.out.println(game.toString());
                    System.out.println("You have selected column "+col+".");
                    endGame = game.checkWin();
                }
                else {
                    int cTurn = comp.CtakeTurn();
                    game.takeTurn(cTurn);
                    System.out.println(game.toString());
                    System.out.println("The computer has taken its turn in column "+cTurn+".");
                    endGame = game.checkWin();
                }
            }
            String winner = game.checkTurnForWin();
            System.out.println("Player "+ winner + " won the game!");
        }
    }
}

