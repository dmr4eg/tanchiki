package structure;

import frontend.View;
import net.GameClient;
import net.GameServer;
import serialization.LevelEditor;
import frontend.MenuItem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Controller extends Application {
    private final int WIDTH = 800; // set Width
    private final int HEIGHT = 600; // set Height
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName()); //initialization Loggers
    private GraphicsContext gc;
    private Model model;
    private View view;
    private Label nameLabel = new Label("Enter your name:");
    private TextField nameField = new TextField();

    public void start(Stage stage) {
        //Set of View
        view = new View(stage, WIDTH, HEIGHT);
        this.gc = view.getSPGc();
        Scene menuScene = new Scene(createContent(stage));
        View.addToScenes(menuScene);
        View.putOnGame_scenes("menu",menuScene );
        stage.setScene(menuScene);
        stage.show();
        stage.setTitle("Tanchiki");
        stage.setResizable(false);

        //set cycle
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(15), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        FileHandler fhc = null;
        try {
            fhc = new FileHandler("controllerLogs.txt", true);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open log file", e);
        }
        fhc.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fhc);
        tl.play();
    }

    //event Listener for set model and scene
    private void setEventLis(Model model, Scene scene, StackPane gamepane) {
        EventLis eventLis = new EventLis(model, scene, gamepane);
    }


    public static void main(String[] args) {
        launch(args);
    } // start of application

    public void run(GraphicsContext gc) {
        if (model != null && model.getGameIsStart()) {//waiting for start game and executing of model
            if (model.isOnlineStart()){ //if game is Online -> will waiting second Player
                if (model.getGameIsStart() == true) { // method observed if game is paused
                    view.drawSPPane(model);
                    model.UpdateModel();
                }
            }
        }
    }

    private Parent createContent(Stage stage) { // Set game menu buttons
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        VBox box = new VBox(10,
                new MenuItem("Single player", () -> {
                    StackPane canvas = view.buildNewStackPaneWithGc(gc);
                    Scene scene = view.buildNewSceneWithStackPane(canvas);
                    model = new Model(gc);
                    setEventLis(model, scene, canvas); // set Event listener for this scene and model
                    View.addToScenes(scene);// add to stack of scenes and set on STAGE
                }, "menu"),
                new MenuItem("Multiplayer player", () -> {
                    StackPane canvas = view.buildNewStackPaneWithGc(gc);
                    Scene scene = view.buildNewSceneWithStackPane(canvas);
                    nameField.setOnKeyPressed((event) -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            model = new Model(gc, nameField.getText().trim());
                            setEventLis(model, scene, canvas); // set Event listener for this scene and model
                            View.addToScenes(scene); // add to stack of scenes and set on STAGE
                        }
                    });
                    Button startButton = new Button("Start");
                    startButton.setOnAction((ActionEvent e) -> {
                        model = new Model(gc, nameField.getText().trim());
                        setEventLis(model, scene, canvas); // set Event listener for this scene and model
                        View.addToScenes(scene);  // add to stack of scenes and set on STAGE
                    });
                    HBox hbox = new HBox(4, nameLabel, nameField, startButton);
                    hbox.setPadding(new Insets(8));
                    hbox.setAlignment(Pos.CENTER);
                    Scene startScene = new Scene(hbox);
                    stage.setScene(startScene);
                    stage.setOnCloseRequest(e -> { //stop of threads and game
                        GameServer.stopRunning();
                        GameClient.stopRunning();
                        Platform.exit();
                    });
                }, "menu"),

                new MenuItem("Level Editor SP", () -> {
                    LevelEditor levelEditor = new LevelEditor(gc, "offline");
                    View.addToScenes(levelEditor.getScene());
//                stage.setScene(levelEditor.getScene());
                }, "menu"),

                new MenuItem("Level Editor MP", () -> {
                    LevelEditor levelEditor = new LevelEditor(gc, "online");
                    View.addToScenes(levelEditor.getScene());
                }, "menu"),
                new MenuItem("Quit", ()->{ //stop of threads and game
                    if(model!=null&& model.gameMode.equals("online")){
                        GameClient.stopRunning();
                        if(model.socketServer != null)GameServer.stopRunning();
                    }
                    Platform.exit();
                }, "menu"));
        box.setBackground(new Background(
                new BackgroundFill(Color.web("black", 1), null, null))
        );
        root.getChildren().addAll(
                box
        );
        return root;
    }
}