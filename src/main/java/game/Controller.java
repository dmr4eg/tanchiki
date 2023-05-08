
package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Thread.sleep;
import static javafx.application.Application.launch;
import static javafx.scene.input.KeyCode.*;

public class Controller extends Application{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private boolean isGameStart = true;
    private Model model;

    private int TILE = 50;

    private Bullet bullet;

    public void start(Stage stage) throws IOException {
        // Create group to hold all bricks
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        model = new Model(true, gc);

        // Set up scene and show stage
        Scene scene = new Scene(new StackPane(canvas));
        stage.setScene(scene);
        stage.show();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(30), e->run(gc, scene)));
        tl.setCycleCount(Timeline.INDEFINITE);
        // Add event handlers for moving the tank;
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    if ((!(model.getPlayer1().getPosX() <= 0))&& !model.isCsollision_tank()[0])model.getPlayer1().moveLeft();else model.setPlayer1Orientation(1);
                    break;
                case RIGHT:
                    if ((!(model.getPlayer1().getPosX() >= WIDTH-TILE)) && !model.isCsollision_tank()[1])model.getPlayer1().moveRight();else model.setPlayer1Orientation(2);
                    break;
                case UP:
                    if (!((model.getPlayer1().getPosY() <= 0)) && !model.isCsollision_tank()[2]) model.getPlayer1().moveUp();else model.setPlayer1Orientation(3);
                    break;
                case DOWN:
                    if ((!(model.getPlayer1().getPosY() >= HEIGHT- TILE)) && !model.isCsollision_tank()[3])model.getPlayer1().moveDown();else model.setPlayer1Orientation(4);
                    break;
                case E:
                    model.getPlayer1().fire();
            }
        });
        tl.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Scene scene) {
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        model.getPlayer1().update(0);
//        model.drawWalls();
        model.enemy_computicng();
        if (!model.getBullets().isEmpty()){
            model.update();
        }
    }
}
