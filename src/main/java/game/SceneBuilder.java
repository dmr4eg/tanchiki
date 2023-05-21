package game;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class SceneBuilder {
    private Stage stage;
    private GraphicsContext gc;
    private Canvas canvas;
    StackPane gamepane;

    private int WIDTH, HEIGHT;

    public SceneBuilder(Stage stage, int WIDTH,int  HEIGHT) {
        this.stage = stage;
        this.canvas = new Canvas(WIDTH, HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        this.gamepane = new StackPane(canvas);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    public void createGameView(){
        // Create group to hold all bricks
        Scene scene = new Scene(gamepane);
        stage.setScene(scene);
    }

    public void drawPane(){
        gc.setFill(BLACK);
        gc.fillRect(0, 0, WIDTH - 200, HEIGHT);
        gc.setFill(WHITE);
    }

    public GraphicsContext getGc() {
        return gc;
    }
}
