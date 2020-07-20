/**
 * Description: Class that contains methods and variables for constructing a user interface for a Connect 4 game.
 * @author Kelsey Cloud
 * @version 1.0
 *
 */
package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import core.Connect4ForGUI;
import core.Connect4ComputerPlayer;



import java.util.Scanner;

public class Connect4GUI extends Application {
    /** Display is the label that will be at the center of teh Border Pane, and which will display the gameboard
     * and prompts for input.*/
    private Label display = new Label("To begin the game, select an option above. " +
            "Either play against a computer or against another player.");

    /**Creating buttons for input field at the bottome of the BorderPane. There is one for each column*/
    private Button one = new Button("1");
    private Button two = new Button("2");
    private Button three = new Button("3");
    private Button four = new Button("4");
    private Button five = new Button("5");
    private Button six = new Button("6");
    private Button seven = new Button("7");

    /**Creates two buttons to Start a new game with the desired player*/
    private Button playComp = new Button("Start new Game: Play Computer");
    private Button playPlayer = new Button("Start new Game: Play other Player");

    /**Game variables necessary for game logic in button handlers*/
    Connect4ForGUI game = new Connect4ForGUI();
    boolean endGame = game.checkWin();
    boolean playingComp;
    Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
    String playerTurn = "X";

    /** Creates window with GUI components for the game*/

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Creates border pane to be set in the scene */
        BorderPane mainPane = new BorderPane();

        // Create Hbox for bottom portion of BorderPane and adds the buttons to the HBox*/
        HBox inputField = new HBox(15);
        inputField.setPadding(new Insets(5));
        inputField.setAlignment(Pos.CENTER);
        inputField.getChildren().add(one);
        inputField.getChildren().add(two);
        inputField.getChildren().add(three);
        inputField.getChildren().add(four);
        inputField.getChildren().add(five);
        inputField.getChildren().add(six);
        inputField.getChildren().add(seven);

        //Create center VBox for display and add the label "display" to it.*/
        VBox center = new VBox(15);
        center.setPadding(new Insets(5));
        center.getChildren().add(display);
        center.setAlignment(Pos.CENTER);
        display.setPadding(new Insets(5));
        display.setPrefSize(250, 250);
        display.setStyle("-fx-border-color: black");

        //Creates HBox for play options and places the in the top of the Border Pane.
        HBox top = new HBox(15);
        top.setPadding(new Insets(5));
        top.getChildren().add(playComp);
        top.getChildren().add(playPlayer);
        top.setAlignment(Pos.CENTER);


        mainPane.setTop(top);
        mainPane.setBottom(inputField);
        mainPane.setCenter(center);


        /**Listeners and handlers for event processing*/
        playComp.setOnAction(event -> handlePlayComp());
        playPlayer.setOnAction(event -> handlePlayPlayer());
        one.setOnAction(event -> handle1());
        two.setOnAction(event -> handle2());
        three.setOnAction(event -> handle3());
        four.setOnAction(event -> handle4());
        five.setOnAction(event -> handle5());
        six.setOnAction(event -> handle6());
        seven.setOnAction(event -> handle7());


        Scene scene = new Scene(mainPane);
        primaryStage.setTitle("Connect 4 Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    /**Entry point of program*/
    public static void main(String args[]) {
        launch(args);
    }
    /**Handler for Play Computer button*/
    private void handlePlayComp() {
        playingComp = true;
        endGame = false;
        game = new Connect4ForGUI();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");
    }
    /**Handler for Play other Player button*/

    private void handlePlayPlayer() {
        playingComp = false;
        endGame = false;
        game = new Connect4ForGUI();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");
    }
    /**Handler for button for column 1*/
    private void handle1() {
        if (endGame){
            display.setText(game.toString() + "\n Game over. Please start another game");
            return;
        }
        playerTurn = game.checkTurnForPlay();
        boolean turnTaken = game.takeTurn(1);
        if (!turnTaken){
            display.setText(game.toString() + "\n That column is full. Please choose another column Player "
                    +playerTurn+ ".");
            return;

        }

        endGame = game.checkWin();
        playerTurn = game.checkTurnForPlay();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");

        if (endGame){
            String winner = game.checkTurnForWin();
            display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            return;
        }

        //Only executes if playing the computer
        if (playingComp) {
            int cTurn = comp.CtakeTurn();
            game.takeTurn(cTurn);
            display.setText(game.toString() + "\n The computer has taken its turn in column " + cTurn + ".");

            if (endGame){
                String winner = game.checkTurnForWin();
                display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            }
        }
    }
    /**Handler for button for column 2*/
    private void handle2() {
        if (endGame) {
            display.setText(game.toString() + "\n Game over. Please start another game");
            return;
        }
        playerTurn = game.checkTurnForPlay();
        boolean turnTaken = game.takeTurn(2);
        if (!turnTaken){
            display.setText(game.toString() + "\n That column is full. Please choose another column Player "
                    +playerTurn+ ".");
            return;

        }
        endGame = game.checkWin();
        playerTurn = game.checkTurnForPlay();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");

        if (endGame){
            String winner = game.checkTurnForWin();
            display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            return;
        }

        //Only executes if playing the computer
        if (playingComp) {
            int cTurn = comp.CtakeTurn();
            game.takeTurn(cTurn);
            display.setText(game.toString() + "\n The computer has taken its turn in column " + cTurn + ".");

            if (endGame){
                String winner = game.checkTurnForWin();
                display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            }
        }
    }
    /**Handler for button for column 3*/
    private void handle3() {
        if (endGame) {
            display.setText(game.toString() + "\n Game over. Please start another game");
            return;
        }
        playerTurn = game.checkTurnForPlay();
        boolean turnTaken = game.takeTurn(3);
        if (!turnTaken){
            display.setText(game.toString() + "\n That column is full. Please choose another column Player "
                    +playerTurn+ ".");
            return;

        }
        endGame = game.checkWin();
        playerTurn = game.checkTurnForPlay();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");

        if (endGame){
            String winner = game.checkTurnForWin();
            display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            return;
        }

        //Only executes if playing the computer
        if (playingComp) {
            int cTurn = comp.CtakeTurn();
            game.takeTurn(cTurn);
            display.setText(game.toString() + "\n The computer has taken its turn in column " + cTurn + ".");

            if (endGame){
                String winner = game.checkTurnForWin();
                display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            }
        }
    }
    /**Handler for button for column 4*/
    private void handle4() {
        if (endGame) {
            display.setText(game.toString() + "\n Game over. Please start another game");
            return;
        }
        playerTurn = game.checkTurnForPlay();
        boolean turnTaken = game.takeTurn(4);
        if (!turnTaken){
            display.setText(game.toString() + "\n That column is full. Please choose another column Player "
                    +playerTurn+ ".");
            return;

        }
        endGame = game.checkWin();
        playerTurn = game.checkTurnForPlay();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");

        if (endGame){
            String winner = game.checkTurnForWin();
            display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            return;
        }

        //Only executes if playing the computer
        if (playingComp) {
            int cTurn = comp.CtakeTurn();
            game.takeTurn(cTurn);
            display.setText(game.toString() + "\n The computer has taken its turn in column " + cTurn + ".");

            if (endGame){
                String winner = game.checkTurnForWin();
                display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            }
        }
    }
    /**Handler for button for column 5*/
    private void handle5() {
        if (endGame) {
            display.setText(game.toString() + "\n Game over. Please start another game");
            return;
        }
        playerTurn = game.checkTurnForPlay();
        boolean turnTaken = game.takeTurn(5);
        if (!turnTaken){
            display.setText(game.toString() + "\n That column is full. Please choose another column Player "
                    +playerTurn+ ".");
            return;

        }
        endGame = game.checkWin();
        playerTurn = game.checkTurnForPlay();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");

        if (endGame){
            String winner = game.checkTurnForWin();
            display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            return;
        }

        //Only executes if playing the computer
        if (playingComp) {
            int cTurn = comp.CtakeTurn();
            game.takeTurn(cTurn);
            display.setText(game.toString() + "\n The computer has taken its turn in column " + cTurn + ".");

            if (endGame){
                String winner = game.checkTurnForWin();
                display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            }
        }
    }
    /**Handler for button for column 6*/
    private void handle6() {
        if (endGame) {
            display.setText(game.toString() + "\n Game over. Please start another game");
            return;
        }
        playerTurn = game.checkTurnForPlay();
        boolean turnTaken = game.takeTurn(6);
        if (!turnTaken){
            display.setText(game.toString() + "\n That column is full. Please choose another column Player "
                    +playerTurn+ ".");
            return;

        }
        endGame = game.checkWin();
        playerTurn = game.checkTurnForPlay();
        display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");

        if (endGame){
            String winner = game.checkTurnForWin();
            display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            return;
        }

        //Only executes if playing the computer
        if (playingComp) {
            int cTurn = comp.CtakeTurn();
            game.takeTurn(cTurn);
            display.setText(game.toString() + "\n The computer has taken its turn in column " + cTurn + ".");

            if (endGame){
                String winner = game.checkTurnForWin();
                display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
            }
        }
    }
    /**Handler for button for column 7*/
        private void handle7 () {
            if (endGame) {
                display.setText(game.toString() + "\n Game over. Please start another game");
                return;
            }
            playerTurn = game.checkTurnForPlay();
            boolean turnTaken = game.takeTurn(7);
            if (!turnTaken){
                display.setText(game.toString() + "\n That column is full. Please choose another column Player "
                        +playerTurn+ ".");
                return;

            }
            endGame = game.checkWin();
            playerTurn = game.checkTurnForPlay();
            display.setText(game.toString() + "\n Player " + playerTurn + ", your turn. Choose a column 1-7.");

            if (endGame){
                String winner = game.checkTurnForWin();
                display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
                return;
            }

            //Only executes if playing the computer
            if (playingComp) {
                int cTurn = comp.CtakeTurn();
                game.takeTurn(cTurn);
                display.setText(game.toString() + "\n The computer has taken its turn in column " + cTurn + ".");

                if (endGame){
                    String winner = game.checkTurnForWin();
                    display.setText((game.toString()+ "\n Player "+ winner+ ", won the game!"));
                }
            }
    }

}





