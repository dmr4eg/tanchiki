
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileReader;
import java.io.IOException;

import static javafx.application.Application.launch;
import static javafx.scene.input.KeyCode.*;

public class Controller extends Application{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private boolean isGameStart = true;


    public void start(Stage stage) throws IOException {
        // Create group to hold all bricks
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Tanks tanks = new Tanks(100, 5, 20, gc, 1, 100, 100);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e->run(gc, tanks)));
//        tl.setCycleCount(Timeline.INDEFINITE);

        // Set up scene and show stage
        Scene scene = new Scene(new StackPane(canvas));
        stage.setScene(scene);
        stage.show();

        // Add event handlers for moving the tank
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    tanks.moveLeft();
                    break;
                case RIGHT:
                    tanks.moveRight();
                    break;
                case UP:
                    tanks.moveUp();
                    break;
                case DOWN:
                    tanks.moveDown();
                    break;
            }
        });

        tl.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Tanks tanks) {
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);

        tanks.update(1, 0, 20);
    }
}
