package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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
    private EventLis eventLis;

    public void start(Stage stage) throws IOException {
        // Create group to hold all bricks
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        model = new Model(true, gc);

        // Set up scene and show stage
        Scene scene = new Scene(new StackPane(canvas));
        stage.setScene(scene);
        stage.show();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(20), e->run(gc, scene)));
        tl.setCycleCount(Timeline.INDEFINITE);
        //eventLis = new EventLis(scene, model, WIDTH, HEIGHT, TILE);

        // Add event handlers for moving the tank;


        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(1);
//                    if ((!(model.getPlayer1().getPosX() <= 0))&& !model.getPlayer1().getIsColision()[0]){
//                        model.getPlayer1().moveLeft();
//                    }
//                    else model.setPlayer1Orientation(1);
                    break;
                case D:
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(2);
//                    if ((!(model.getPlayer1().getPosX() >= WIDTH-TILE)) && !model.getPlayer1().getIsColision()[1]){
//                        model.getPlayer1().moveRight();
//                    }else model.setPlayer1Orientation(2);
                    break;
                case W:
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(3);
//                    if (!((model.getPlayer1().getPosY() <= 0)) && !model.getPlayer1().getIsColision()[2]) {
//                        model.getPlayer1().moveUp();
//                    }else model.setPlayer1Orientation(3);
                    break;
                case S:
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(4);
//                    if ((!(model.getPlayer1().getPosY() >= HEIGHT- TILE)) && !model.getPlayer1().getIsColision()[3]){
//                        model.getPlayer1().moveDown();
//                    }else model.setPlayer1Orientation(4);
                    break;
                case E:
                    model.getPlayer1().fire();
                    break;
                default :
                    model.getPlayer1().setTankIsMove(false);
                    break;
            }
        });
        //-------------------------------------------------------------------------------------------------------------------

        //----------------------------------------
        tl.play();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Scene scene) {

        gc.fillRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.BLACK);
        model.getPlayer1().update(0);
        model.isCsollision_tank();
//        model.drawWalls();
        model.enemy_computing();
        if (!model.getBullets().isEmpty()){
            model.update();
        }
    }
}
