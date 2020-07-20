package core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Connect4Client extends Application {

    /**
     * Display is the label that will be at the center of teh Border Pane, and which will display the gameboard
     * and prompts for input.
     */
    private TextArea display = new TextArea("Connecting to server. Please wait.");


    /**
     * Creating buttons for input field at the bottom of the BorderPane. There is one for each column
     */
    private Button one = new Button("1");
    private Button two = new Button("2");
    private Button three = new Button("3");
    private Button four = new Button("4");
    private Button five = new Button("5");
    private Button six = new Button("6");
    private Button seven = new Button("7");

    // Input and output streams from/to server
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    private String host = "localhost";
    int col = 0;
    boolean isGameOver = false;
    boolean waiting = true;
    String myPlayer;


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


        mainPane.setBottom(inputField);
        mainPane.setCenter(center);


        //Listeners and handlers for event processing
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

        connectToServer();
    }

    private void connectToServer() {

        new Thread(() -> {
            try {
                // Create a socket to connect to the server
                Socket socket = new Socket(host, 8000);
                // Create an input stream to receive data from the server
                fromServer = new DataInputStream(socket.getInputStream());
                // Create an output stream to send data to the server
                toServer = new DataOutputStream(socket.getOutputStream());
                myPlayer = fromServer.readUTF();
                display.setText("I am player "+ myPlayer+ "\n" + fromServer.readUTF());
                while (!isGameOver) {
                try {
                    while(!isGameOver) {
                        //If I am player X, take my turn and send selection.
                        if (myPlayer.equalsIgnoreCase("X")) {
                            //Check for selection and put thread to sleep if turn hasn't been taken.
                            while (col == 0) {
                                Thread.sleep(100);}
                            toServer.writeInt(col);
                            col = 0; }
                        isGameOver = fromServer.readBoolean();
                        if (isGameOver) {
                            display.setText(fromServer.readUTF()); }
                        display.setText("I am player "+ myPlayer+ "\n" + fromServer.readUTF() + "Player O's turn.");
                        //If I am player O, take my turn and send selection.
                        if (myPlayer.equalsIgnoreCase("O")) {
                            //Check for selection and put thread to sleep if turn hasn't been taken.
                            while (col == 0) {
                                Thread.sleep(100); }
                            toServer.writeInt(col);
                            col = 0; }
                        isGameOver = fromServer.readBoolean();
                        if (isGameOver) {
                            display.setText(fromServer.readUTF()); }
                        display.setText("I am player "+ myPlayer+ "\n" + fromServer.readUTF() + "Player X's turn.");
                    }
                }
                catch (IOException e){
                    e.printStackTrace(); }
                catch (InterruptedException e) {
                    e.printStackTrace(); }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }
    /**Handler for button for column 1*/
    private void handle1 () {
        col = 1;
        waiting = false;}
    /**Handler for button for column 2*/
    private void handle2 ()  {
        col = 2;
        waiting = false; }
    /**Handler for button for column 3*/
    private void handle3 () {
        col = 3;
        waiting = false; }
    /**Handler for button for column 4*/
    private void handle4 ()  {
        col = 4;
        waiting = false; }

    /**Handler for button for column 5*/
    private void handle5 () {
        col = 5;
        waiting = false; }
    /**Handler for button for column 6*/
    private void handle6 ()  {
        col = 6;
        waiting = false; }
    /**Handler for button for column 7*/
    private void handle7 () {
        col = 7;
        waiting = false; }
}
