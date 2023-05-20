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
    private Bullet bullet;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private GraphicsContext gc;
    private Model model;
    private Obj obj;
    private GameServer socketServer;
    private GameClient socketClient;

    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        // Create group to hold all bricks
        this.gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(new StackPane(canvas));
        // Set up scene and show stage
        stage.setScene(new Scene(creatiContent(stage, scene)));
        stage.show();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(15), e -> run(gc, scene)));
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

    private Parent creatiContent(Stage stage, Scene scene) {
        Pane root = new Pane();
        root.setPrefSize(800, 480);
        Image bgrImage = new Image(("bgr.jpg"),
                1980,
                1080,
                false, false
        );
        VBox box = new VBox(10,
                new MenuItem("Single player", () -> {
                    model = new Model(gc);
                    model.startGame();
                    stage.setScene(scene);
                    setEventLis(model, scene);
//                    rightPanel();
                }, "menu"),
                new MenuItem("Multiplayer player", () -> {
                    Label nameLabel = new Label("Enter your name:");
                    TextField nameField = new TextField();

                    nameField.setOnKeyPressed((event) -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            model = new Model(gc, nameField.getText().trim());
                            setEventLis(model, scene);
                            model.startGame();
                            stage.setScene(scene);
                        }
                    });
                    Button startButton = new Button("Start");
                    startButton.setOnAction((ActionEvent e) -> {
                        // 2. as above
                        model = new Model(gc, nameField.getText().trim());
                        setEventLis(model, scene);
                        model.startGame();
                        stage.setScene(scene);
                        rightPanel();
                    });
                    HBox hbox = new HBox(4, nameLabel, nameField, startButton);
                    hbox.setPadding(new Insets(8));
                    hbox.setAlignment(Pos.CENTER);
                    Scene startScene = new Scene(hbox);
                    stage.setScene(startScene);
                }, "menu"),
                new MenuItem("Level Editor", () -> {
                    LevelEditor levelEditor = new LevelEditor(gc);
                    stage.setScene(levelEditor.getScene());
                }, "menu"),
                new MenuItem("Quit", Platform::exit, "menu")
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

    private void setEventLis(Model model, Scene scene) {
        EventLis eventLis = new EventLis(model, scene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run(GraphicsContext gc, Scene scene) {
        if (model != null) {
            if (model.isGameIsStart()) {
                gc.setFill(BLACK);
                gc.fillRect(0, 0, WIDTH-200, HEIGHT);
                gc.setFill(WHITE);
                model.UpdateModel();
                gc.fillRect(WIDTH -200, 0, 200,600 );
                gc.setFill(BLACK);
                gc.setFont(Font.loadFont(getClass().getResourceAsStream("/CCOverbyteOffW00-Regular.ttf"), 30));
                gc.fillText(String.format("playerHP: %d", model.getPlayer1().getHP()), 625,100, 150);
                if(model.getPlayer2()!=null)gc.fillText(String.format("playerHP: %d", model.getPlayer2().getHP()), 625,300, 150);

            }
            }
        }

    public void rightPanel(){
        VBox rightPanel = new VBox(
                10, new MenuItem("HP" ,() -> {
//            System.out.println("fdasfa");
        }, "panel")
        );
        Rectangle lineLeft = new Rectangle(10, 600, Color.GRAY);
        rightPanel.setAlignment(Pos.BASELINE_LEFT);
        HBox hbox = new HBox( 10 ,lineLeft, rightPanel);
        rightPanel.setTranslateX(600);
        rightPanel.setTranslateY(0);
        rightPanel.setBackground(new Background(
                new BackgroundFill(Color.web("black", 0.6), null, null))
        );
        rightPanel.setPadding(new Insets(8));
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPrefSize(100, 480);
        rightPanel.getChildren().addAll();
        hbox.setPrefSize(200,600);
        hbox.setTranslateX(600);
        hbox.setTranslateY(0);
        hbox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        hbox.getChildren().addAll(lineLeft, rightPanel);
    }
}