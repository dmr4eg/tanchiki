
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

public class Controller extends Application{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private boolean isGameStart = true;


    public void start(Stage stage) throws IOException {
        // Create group to hold all bricks
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Tanks tanks = new Tanks(100, 1, 20,gc, 1, 100, 100);

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e->run(gc, tanks)));
        tl.setCycleCount(Timeline.INDEFINITE);

        // Set up scene and show stage
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Tanks tanks) {
        gc.setFill(Color.BLACK);
        }
}
