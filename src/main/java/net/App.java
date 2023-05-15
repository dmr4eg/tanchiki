package net;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.ServerSocket;

/**
*    udp connection is based on @seredlad's code from 10-11 lessons
**/
public class App extends Application {
    private static final int PORT_NUMBER = 63458;   // fixed port number and host!
    private static final String HOST = "localhost"; //  TODO: read port & host from configuration file
    private Stage stage;
    private TextField nameField;
    private TextArea messages;
    private TextArea input;
    private Server server;
    private Client client;
    private boolean clientOnly;

    @Override
    public void start(Stage stage) {
        // 1. login window: enter chat name
        this.stage = stage;
        showLoginWindow();
        stage.setTitle("Simple Chat");
        stage.centerOnScreen();
        stage.show();
    }

    @Override
    public void stop() {
        // 2. close all resources
        if (client != null) {
            client.close();
        }
        if (!clientOnly) {
            if (server != null) {
                server.stop();
            }
        }
    }

    private void showLoginWindow() {
        Label nameLabel = new Label("Enter your name:");
        nameField = new TextField();
        nameField.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                // 2. start server (if not already running) and client
                startupBackend();
            }
        });
        Button startButton = new Button("Start");
        startButton.setOnAction((ActionEvent e) -> {
            // 2. as above
            startupBackend();
        });
        HBox hbox = new HBox(4, nameLabel, nameField, startButton);
        hbox.setPadding(new Insets(8));
        hbox.setAlignment(Pos.CENTER);
        Scene startScene = new Scene(hbox);
        stage.setScene(startScene);
    }

    private void startupBackend() {
        String userName = nameField.getText().strip();
        if (userName.length() > 0) {
            client = new Client(this, HOST, PORT_NUMBER, userName);
            // 3. start server (if not already running) and client
            if (!isServerRunning()) {
                server = new Server(client, PORT_NUMBER);
                startServerThenClient();
                clientOnly = false;
            } else {
                startClient();
                clientOnly = true;
            }
        }
    }

    private boolean isServerRunning() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
            serverSocket.close();
            return false;
        } catch (IOException ex) {
            return true;
        }
    }

    private void startServerThenClient() {
        new Thread(server).start();
    }

    private void startClient() {
        new Thread(client).start();
    }

    public void showChatWindow(String userName) {
        // 4. after (server and) client is running open chat window
        Label msgLabel = new Label("Chat history");
        messages = new TextArea();
        messages.setEditable(false);
        messages.setWrapText(true);
        VBox.setVgrow(messages, Priority.ALWAYS); // takes all available vertical place
        Label inputLabel = new Label("Your message");
        input = new TextArea();
        input.setMaxHeight(60);
        input.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage();
            }
        });
        Button sendButton = new Button("Send");
        sendButton.setOnAction((ActionEvent e) -> {
            sendMessage();
        });
        VBox vbox = new VBox(4, msgLabel, messages, inputLabel, input, sendButton);
        vbox.setPadding(new Insets(8));
        vbox.setAlignment(Pos.CENTER);
        Scene chatScene = new Scene(vbox, 640, 480);
        stage.setTitle(userName);
        stage.setScene(chatScene);
    }

    public void displayIncoming(String msg) {
        messages.appendText(msg + "\n\n");
    }

    private void sendMessage() {
        String message = removeLineBreaks(input.getText().strip());
        if (message.length() > 0) {
            client.sendMessage(message);
            input.setText("");
        }
    }

    private String removeLineBreaks(String txt) {
        // see: https://stackoverflow.com/questions/2163045/how-to-remove-line-breaks-from-a-file-in-java
        return txt.replaceAll("\\R+", " ");
    }

    public void showAlert(String msg) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Chat Client");
            alert.setHeaderText(null);
            alert.setContentText(msg);
            alert.show();
        });
    }

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

}
