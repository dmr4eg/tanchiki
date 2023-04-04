package model;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

import static javafx.application.Application.launch;


public class GameModel extends Application {
//    private final Font font = Font.loadFont("file:resources/CCOverbyteOffW00-Regular.ttf", 20);
    private boolean isRunning = false;
    private boolean isAlive = false;

    private final int BLOCK_SIZE = 20;
    private final int BLOCKS_AMOUNT = 20;
    private final int SCREEN_SIZE = BLOCK_SIZE * BLOCKS_AMOUNT;
    private int GHOSTS = 4;
    private final int PACMAN_SPEED = 3;

    private int lives, score;
    private int [] dx, dy;
    private int[] ghostX, ghostY, ghostDX, ghostDY, ghostSpeed;
    private Object root;

//    private imageLoader imageLoader = new imageLoader();

    public void start (Stage stage) throws FileNotFoundException {
        Text text = new Text(30.0, 75.0, "LOL");
        Font font = Font.loadFont(getClass().getResourceAsStream("/CCOverbyteOffW00-Regular.ttf"), 30);

        text.setFont(font);

        text.setFill(Color.WHITE);
//        text.setStroke(Color.RED);
        text.setStrokeWidth(1);

//        VBox root = new VBox(text);
        Group root = new Group(text);
        Scene scene = new Scene(root, 595, 150, Color.BLACK);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

