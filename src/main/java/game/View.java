package game;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

public class View {
    private Stage stage;
    private GraphicsContext gc;
    private Canvas canvas;
    private StackPane gamepane;
    private Controller controller;

    private int WIDTH, HEIGHT;

    public View(Stage stage, int WIDTH,int  HEIGHT) {
        this.stage = stage;
        this.canvas = new Canvas(WIDTH, HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        this.gamepane = new StackPane(canvas);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
//        stage.setScene(new Scene(creatiContent(stage, scene)));
//        stage.show();
//        stage.setTitle("Tanchiki");
//        stage.setResizable(false);
    }

    public void createGameView(){
        // Create group to hold all bricks
        Scene scene = new Scene(gamepane);
        stage.setScene(scene);
    }

    public void drawPane(Model model){
        gc.setFill(BLACK);
        gc.fillRect(0, 0, WIDTH - 200, HEIGHT);
        gc.setFill(WHITE);
        gc.fillRect(WIDTH-200, 0, 200, HEIGHT );
        gc.setFill(BLACK);
        model.UpdateModel();
        gc.setFont(Font.loadFont(getClass().getResourceAsStream("/CCOverbyteOffW00-Regular.ttf"), 20));
        gc.fillText(String.format("Player 1 HP: %d", model.getPlayer1().getHP()), 625,100, 300);
        if(model.getPlayer2()!=null)gc.fillText(String.format("Player 2 HP: %d", model.getPlayer2().getHP()), 625,150, 300);
        gc.fillText(String.format("Enemies left: %d", model.getEnemies()), 625,200, 300);
    }

    public GraphicsContext getGc() {
        return gc;
    }
    public StackPane getGamepane() {
        return gamepane;
    }
}
