package core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Connect4Server extends Application {
    private int sessionNo = 1; // Number a session

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        TextArea taLog = new TextArea();

        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(taLog), 450, 200);
        primaryStage.setTitle("Connect4Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        new Thread(() -> {
            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() -> taLog.appendText(new Date() +
                        ": Server started at socket 8000\n"));

                // Ready to create a session for every two players
                while (true) {
                    Platform.runLater(() -> taLog.appendText(new Date() +
                            ": Wait for players to join session " + sessionNo + '\n'));

                    // Connect to player 1
                    Socket player1 = serverSocket.accept();

                    Platform.runLater(() -> {
                        taLog.appendText(new Date() + ": Player 1 joined session "
                                + sessionNo + '\n');
                        taLog.appendText("Player 1's IP address" +
                                player1.getInetAddress().getHostAddress() + '\n');
                    });


                    // Connect to player 2
                    Socket player2 = serverSocket.accept();

                    Platform.runLater(() -> {
                        taLog.appendText(new Date() +
                                ": Player 2 joined session " + sessionNo + '\n');
                        taLog.appendText("Player 2's IP address" +
                                player2.getInetAddress().getHostAddress() + '\n');
                    });

                    // Display this session and increment session number
                    Platform.runLater(() ->
                            taLog.appendText(new Date() +
                                    ": Start a thread for session " + sessionNo++ + '\n'));

                    // Launch a new thread for this session of two players
                    new Thread(new HandleASession(player1, player2)).start();
                }
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    class HandleASession implements Runnable {

        private Socket player1;
        private Socket player2;

        private DataInputStream fromPlayer1;
        private DataOutputStream toPlayer1;
        private DataInputStream fromPlayer2;
        private DataOutputStream toPlayer2;

        /** Construct a thread */
        public HandleASession(Socket player1, Socket player2) {
            this.player1 = player1;
            this.player2 = player2;
        }

        //Start new game.
        Connect4ForGUI game = new Connect4ForGUI();

        @Override
        public void run() {
            try {
                // Create data input and output streams
                DataInputStream fromPlayer1 = new DataInputStream(
                        player1.getInputStream());
                DataOutputStream toPlayer1 = new DataOutputStream(
                        player1.getOutputStream());
                DataInputStream fromPlayer2 = new DataInputStream(
                        player2.getInputStream());
                DataOutputStream toPlayer2 = new DataOutputStream(
                        player2.getOutputStream());

                //Send players which player they are and the gameboard
                toPlayer1.writeUTF("X");
                toPlayer2.writeUTF("O");
                toPlayer1.writeUTF(game.toString() + "\nPlayer X, your turn.");
                toPlayer2.writeUTF(game.toString() + "\nPlayer X's turn. Please wait.");

                while (true){
                    //Player X's turn
                    game.takeTurn(fromPlayer1.readInt());
                    toPlayer1.writeBoolean(game.checkWin());
                    toPlayer2.writeBoolean(game.checkWin());
                    //Check if game has been won.
                    if (game.checkWin()){
                        toPlayer1.writeUTF(game.toString() + "\nPlayer "+game.checkTurnForWin()+" won. Please start a new game.");
                        toPlayer2.writeUTF(game.toString() + "\nPlayer "+game.checkTurnForWin()+" won. Please start a new game."); }

                    else{
                        //Player O's turn
                    toPlayer1.writeUTF(game.toString()+"Player O's turn. Please wait.");
                    toPlayer2.writeUTF(game.toString()+ "Player O, your turn."); }
                    game.takeTurn(fromPlayer2.readInt());
                    toPlayer1.writeBoolean(game.checkWin());
                    toPlayer2.writeBoolean(game.checkWin());
                    //Check if game has been won.
                    if (game.checkWin()){
                        toPlayer1.writeUTF(game.toString() + "\nPlayer "+game.checkTurnForWin()+" won. Please start a new game.");
                        toPlayer2.writeUTF(game.toString() + "\nPlayer "+game.checkTurnForWin()+" won. Please start a new game."); }
                    toPlayer1.writeUTF(game.toString() + "\nPlayer X, your turn.");
                    toPlayer2.writeUTF(game.toString() + "\nPlayer X's turn."); }
            }
            catch (IOException e ){
                e.printStackTrace(); }
        }
    }
}
