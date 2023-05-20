package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.GameClient;
import net.GameServer;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static javafx.scene.paint.Color.*;

public class Controller extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private GraphicsContext gc;
    private Model model;
    private View view;
    private Label nameLabel = new Label("Enter your name:");
    private TextField nameField = new TextField();

    public void start(Stage stage) {
        view = new View(stage, WIDTH, HEIGHT);
        this.gc = view.getGc();
        Scene scene = new Scene(view.getGamepane());

        stage.setScene(new Scene(createContent(stage, scene)));
        stage.show();
        stage.setTitle("Tanchiki");
        stage.setResizable(false);
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(15), e -> run(gc, scene)));
        tl.setCycleCount(Timeline.INDEFINITE);

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

    private void setEventLis(Model model, Scene scene) {
        EventLis eventLis = new EventLis(model, scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Scene scene) {
        if (model != null && model.getGameIsStart()) {
            if (model.getGameIsStart() == true) {
                view.drawPane(model);
            }
        }
    }

    public void modelStart(Scene scene, Stage stage){
        setEventLis(model, scene);
        model.startGame();
        stage.setScene(scene);
    }

    private Parent createContent(Stage stage, Scene scene) {
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        VBox box = new VBox(10,
            new MenuItem("Single player", () -> {
                model = new Model(gc);
                modelStart(scene, stage);
            }, "menu"),

            new MenuItem("Multiplayer player", () -> {
                nameField.setOnKeyPressed((event) -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        model = new Model(gc, nameField.getText().trim());
                        modelStart(scene, stage);
                    }
                });
                Button startButton = new Button("Start");
                startButton.setOnAction((ActionEvent e) -> {
                    model = new Model(gc, nameField.getText().trim());
                    modelStart(scene, stage);
                });
                HBox hbox = new HBox(4, nameLabel, nameField, startButton);
                hbox.setPadding(new Insets(8));
                hbox.setAlignment(Pos.CENTER);
                Scene startScene = new Scene(hbox);
                stage.setScene(startScene);
            }, "menu"),
            new MenuItem("Level Editor SP", () -> {
                    LevelEditor levelEditor = new LevelEditor(gc, "offline");
                    stage.setScene(levelEditor.getScene());
            }, "menu"),
            new MenuItem("Level Editor MP", () -> {
                    LevelEditor levelEditor = new LevelEditor(gc, "online");
                    stage.setScene(levelEditor.getScene());
            }, "menu"),
            new MenuItem("Level Editor", () -> {
                LevelEditor levelEditor = new LevelEditor(gc);
                stage.setScene(levelEditor.getScene());
            }, "menu"),
            new MenuItem("Quit", Platform::exit, "menu")
        );
        box.setBackground(new Background(
            new BackgroundFill(Color.web("black", 1), null, null))
        );
        root.getChildren().addAll(
            box
        );
        return root;
    }
}