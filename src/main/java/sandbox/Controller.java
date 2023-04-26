package sandbox;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller extends Application implements Runnable {
    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    public void start(Stage stage) throws IOException {
        // Create group to hold all bricks
        Group root = new Group();

        // Set up scene and show stage
        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        stage.setScene(scene);
        stage.show();
        Tanks tanks = new Tanks(100, 1, 20, root, 1, 100, 100);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void run() {

    }
}
