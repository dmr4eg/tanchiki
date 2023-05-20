package structure;

import frontend.MenuItem;
import javafx.scene.layout.HBox;
import serialization.LevelEditor;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;


public class EventLis {
    private static final Logger LOGGER = Logger.getLogger(EventLis.class.getName());
    private Scene scene;
    private Model model;
    private Controller controller;
    private LevelEditor levelEditor;

    private Stage stage;
    private StackPane stackPane;
    public EventLis(Model model, Scene scene, StackPane stackPane) {
        this.stackPane = stackPane;
        this.stage = stage;
        this.scene = scene;
        this.model = model;
        setEventLisModel();
    }

    public EventLis(LevelEditor levelEditor, Scene scene) {
        this.scene = scene;
        this.levelEditor = levelEditor;
        setEventLisLevelEditor();
    }

    private void setEventLisModel() {
        FileHandler fhc = null;
        try {
            fhc = new FileHandler("controllerLogs.txt");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open log file", e);
        }
        fhc.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fhc);
        HBox hbox = new HBox(new MenuItem("Save",()->{}, "levelEditor" ));
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
                case ESCAPE -> {
                    System.out.println("forGame");
                    if(model.getGameIsStart()) {
                        model.setGameIsStart(false);
                        stackPane.getChildren().add(hbox);
                    } else {
                        stackPane.getChildren().remove(hbox);
                        model.setGameIsStart(true);
                        System.out.println(model.getGameIsStart());
                    }
                }
                default -> model.getPlayer1().setTankIsMove(false);
            }
        });
    }

    private void setEventLisLevelEditor(){
        scene.setOnMouseDragged(mouseEvent -> {
            int posX =(int)mouseEvent.getX();
            int posY =(int)mouseEvent.getY();
            System.out.println(posX + " " + posY);
            if(posX < 600)levelEditor.setBlock(posX, posY );
        });
        scene.setOnMouseClicked(mouseEvent ->{
            int posX =(int)mouseEvent.getX();
            int posY =(int)mouseEvent.getY();
            System.out.println(posX + " " + posY);
            if(posX < 600)levelEditor.setBlock(posX, posY );
        } );
    }
}
