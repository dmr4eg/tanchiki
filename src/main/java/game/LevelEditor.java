package game;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LevelEditor {
    private final LevelContainer levelContainer = new LevelContainer();
    private final JsonUtil jsonUtil = new JsonUtil();
    private final String fileName = "level1.json";
    private final GraphicsContext gc;
    private final Bricks bricks;
    private final Pane canvas;
    private Scene scene;
    private EventLis eventLis;
    private boolean isProcessingBrick;
    private boolean isProcessingPlayer1;
    private boolean isProcessingPlayer2;
    private boolean isProcessingBase;
    private boolean isProcessingArmoredBrick;
    private boolean isProcessingEnemy;

    public LevelEditor(GraphicsContext gc) {
        this.gc = gc;
        this.bricks = new Bricks(fileName, gc);
        this.canvas = new Pane();
        canvas.setStyle("-fx-background-color: black;");
        this.canvas.setPrefSize(800, 600);
        scene = new Scene(setButtonsField());
        eventLis = new EventLis(this, scene)
        ;
    }

    private Pane setButtonsField(){
        VBox vbox = new VBox(10,  new MenuItem("BRICK", () -> {
            isProcessingBrick = true;
        }, "levelEditor"),
                new MenuItem("TANK", () -> {}, "levelEditor"),
                new MenuItem("BASE", () -> {}, "levelEditor"),
                new MenuItem("ARMORED", () -> {}, "levelEditor"),
                new MenuItem("ENEMY", () -> {}, "levelEditor")
        );
        Rectangle lineLeft = new Rectangle(10, 600, Color.GRAY);
        vbox.setPrefSize(190, 600);
        vbox.setBackground(new Background(
                new BackgroundFill(Color.web("black", 0.6), null, null))
        );
        vbox.setAlignment(Pos.BASELINE_LEFT);
        HBox hbox = new HBox( 10 ,lineLeft, vbox);
        hbox.setPrefSize(200,600);
        hbox.setTranslateX(600);
        hbox.setTranslateY(0);
        canvas.getChildren().add(hbox);
        return canvas;
    }


    public void setBlock(int posX, int posY) {
        if(isProcessingBrick) {
            //TODO..


            Obj object = new Obj(0, 1, posX, posY, "brick", gc);
            levelContainer.addToLevelObjects(object);
            isProcessingBrick = false;
        }
        if(isProcessingPlayer1) {
            //TODO..

            Obj object = new Obj(1, 100, posX, posY, "player", gc);
            levelContainer.addToLevelObjects(object);
            isProcessingPlayer1 = false;
        }
        if(isProcessingPlayer2) {
            //TODO..

            Obj object = new Obj(1, 100, posX, posY, "player", gc);
            levelContainer.addToLevelObjects(object);
            isProcessingPlayer2 = false;
        }
        if(isProcessingBase) {
            //TODO..

            Obj object = new Obj(1, 1, posX, posY, "base", gc);
            levelContainer.addToLevelObjects(object);
            isProcessingBase = false;
        }
        if(isProcessingEnemy) {
            //TODO..

            Obj object = new Obj(1, 30, posX, posY, "tank", gc);
            levelContainer.addToLevelObjects(object);
            isProcessingEnemy = false;
        }
        if(isProcessingArmoredBrick) {
            //TODO..

            Obj object = new Obj(1, 30, posX, posY, "armoredbrick", gc);
            levelContainer.addToLevelObjects(object);
            isProcessingBrick = false;
        }

    }
    //----------------------------------draw--------------------------------------------------------------

    private void repaint(){
        
    }


    //---------------------------------getters------------------------------------------------------------
    public Scene getScene() {
        return scene;
    }
    public void addBrick(Brick brick) {
        bricks.add(brick);
        saveData();
    }

    public void removeBrick(Brick brick) {
        bricks.removeBrick(brick);
        saveData();
    }

    public Bricks getBricks() {
        return bricks;
    }

    public void saveData() {
        jsonUtil.saveJson(bricks, fileName);
    }

}