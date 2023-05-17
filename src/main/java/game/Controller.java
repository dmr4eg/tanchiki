package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.GameClient;
import net.GameServer;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static javafx.scene.paint.Color.*;

public class Controller extends Application{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private Bullet bullet;
    private EventLis eventLis;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private GraphicsContext gc;
    private Model model;
    private GameServer socketServer;
    private GameClient socketClient;
    public void start(Stage stage) {
        // Create group to hold all bricks
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));

        //initializing gameClient

        // Set up scene and show stage
        stage.setScene(new Scene(creatiContent(stage, scene)));
        stage.show();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(15), e->run(gc, scene)));
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
                new MenuItem("Single player", () -> {
                    model = new Model(false, gc);
                    model.startGame();
                    stage.setScene(scene);
                    setEventLis(model, scene);
                }),

                new MenuItem("Multiplayer player", () -> {
                    Label nameLabel = new Label("Enter your name:");
                    TextField nameField = new TextField();

                    nameField.setOnKeyPressed((event) -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            model = new Model(false, gc, nameField.getText().trim());
                            setEventLis(model, scene);
                            model.startGame();
                            stage.setScene(scene);
                        }
                    });
                    Button startButton = new Button("Start");
                    startButton.setOnAction((ActionEvent e) -> {
                        // 2. as above
                        model = new Model(false, gc, nameField.getText().trim());
                        setEventLis(model, scene);
                        model.startGame();
                        stage.setScene(scene);
                    });
                    HBox hbox = new HBox(4, nameLabel, nameField, startButton);
                    hbox.setPadding(new Insets(8));
                    hbox.setAlignment(Pos.CENTER);
                    Scene startScene = new Scene(hbox);
                    stage.setScene(startScene);
//                    try {
//                        app.start(stage);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }),

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

    private void setEventLis(Model model,Scene scene){
        EventLis eventLis = new EventLis(model, scene);
        eventLis.setEventLis();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Scene scene) {
        if(model != null) {
            if(model.isGameIsStart()) {
                gc.fillRect(0, 0, WIDTH, HEIGHT);
                gc.setFill(BLACK);

                //--------------------------------
                model.updateDraw();
                model.isCollision_tankObj(model.getPlayer1());
                model.getPlayer1().update();
                model.enemy_computingObj();
                if(model.getPlayer2() != null) model.getPlayer2().updateCooldownAndAnimation();
                if (!model.getBullets().isEmpty()) {
                    model.updateObj();
                }
            }
        }
        //-------------------------------



    }

}

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