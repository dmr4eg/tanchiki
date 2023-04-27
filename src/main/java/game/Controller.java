
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
    private Model model = new Model(true);

    private int TILE = 50;

    private Bullet bullet;

    public void start(Stage stage) throws IOException {
        // Create group to hold all bricks
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Tanks tanks = new Tanks(100, 5, 20, gc, 1, 100, 100, model);
        Bricks brick = new Bricks("sada.txt");

        // Set up scene and show stage
        Scene scene = new Scene(new StackPane(canvas));
        stage.setScene(scene);
        stage.show();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(5), e->run(gc, tanks, scene)));
        tl.setCycleCount(Timeline.INDEFINITE);
        // Add event handlers for moving the tank;
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    if (!(tanks.getPosX() <= 0))tanks.moveLeft();
                    break;
                case RIGHT:
                    if (!(tanks.getPosX() >= WIDTH-TILE))tanks.moveRight();
                    break;
                case UP:
                    if (!(tanks.getPosY() <= 0)) {
                        tanks.moveUp();
                    }
                    break;
                case DOWN:
                    if (!(tanks.getPosY() >= HEIGHT- TILE))tanks.moveDown();
                    break;
                case E:
                    System.out.println(1);
                    tanks.fire();
            }
        });
        tl.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Tanks tanks, Scene scene) {
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        tanks.update(0);
        if (!model.getBullets().isEmpty()){
            model.update();
        }

    }
}
