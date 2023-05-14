package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static javafx.scene.paint.Color.*;

public class Controller extends Application{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private boolean isGameStart = true;
    private Model model;
    private int TILE = 50;
    private Bullet bullet;
    private EventLis eventLis;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    public void start(Stage stage) {
        // Create group to hold all bricks
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));
        model = new Model(false, gc);

        // Set up scene and show stage


        stage.setScene(new Scene(creatiContent(stage, scene)));
        stage.show();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(20), e->run(gc, scene)));
        tl.setCycleCount(Timeline.INDEFINITE);
        //eventLis = new EventLis(scene, model, WIDTH, HEIGHT, TILE);

        FileHandler fhc = null;
        try {
            fhc = new FileHandler("controllerLogs.txt");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open log file", e);
        }
        fhc.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fhc);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A, LEFT -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(1);
                    LOGGER.log(Level.INFO, "Player1 move left");
                }
//                    if ((!(model.getPlayer1().getPosX() <= 0))&& !model.getPlayer1().getIsColision()[0]){
//                        model.getPlayer1().moveLeft();
//                    }
//                    else model.setPlayer1Orientation(1);
                case D, RIGHT -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(2);
                    LOGGER.log(Level.INFO, "Player1 move right");
                }
//                    if ((!(model.getPlayer1().getPosX() >= WIDTH-TILE)) && !model.getPlayer1().getIsColision()[1]){
//                        model.getPlayer1().moveRight();
//                    }else model.setPlayer1Orientation(2);
                case W, UP -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(3);
                    LOGGER.log(Level.INFO, "Player1 move up");
                }
//                    if (!((model.getPlayer1().getPosY() <= 0)) && !model.getPlayer1().getIsColision()[2]) {
//                        model.getPlayer1().moveUp();
//                    }else model.setPlayer1Orientation(3);
                case S, DOWN -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(4);
                    LOGGER.log(Level.INFO, "Player1 move down");
                }
//                    if ((!(model.getPlayer1().getPosY() >= HEIGHT- TILE)) && !model.getPlayer1().getIsColision()[3]){
//                        model.getPlayer1().moveDown();
//                    }else model.setPlayer1Orientation(4);
                case E -> {
                    model.getPlayer1().fire();
                    LOGGER.log(Level.INFO, "Player1 fire");
                }

                default -> model.getPlayer1().setTankIsMove(false);
            }
        });

        tl.play();
    }

    private Parent creatiContent(Stage stage, Scene scene){
        Pane root = new Pane();
        root.setPrefSize(800, 480);

        Image bgrImage = new Image(("bgr.jpg"),
                1980,
                1080,
                false, false
        );

        VBox box = new VBox(10,
                new MenuItem("START (single player)", () -> { model.startGame();
                    stage.setScene(scene);}),
                new MenuItem("START (multiplayer player)", () -> {}),
                new MenuItem("Level Editor", () -> {}),
                new MenuItem("Quit", Platform::exit)
        );

        box.setBackground(new Background(
                new BackgroundFill(Color.web("black", 0.6), null, null))
        );
        box.setTranslateX(40);
        box.setTranslateY(40);

        root.getChildren().addAll(
                new ImageView(bgrImage),
                box
        );
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Scene scene) {
        if(model.isStart) {
            gc.fillRect(0, 0, WIDTH, HEIGHT);
            gc.setFill(BLACK);
            //--------------------------------
            model.updateDraw();
            model.isCollision_tankObj(model.getPlayer1());
            model.getPlayer1().update();
            model.enemy_computingObj();
            if (!model.getBullets().isEmpty()) {
                model.updateObj();
            }
        }
        //-------------------------------


//
//        model.getPlayer1().update(0);
//
//        model.isCsollision_tank();
//        //model.isCsollision_tankObj(model.getPlayer1());
//        //model.drawWalls();
//
//        model.enemy_computing();
//        //model.updateObjDraw();
//
//        model.update();
//        if (!model.getBullets().isEmpty()){
//            model.update();
//            //model.updateObj();
//
//        }
    }
}
