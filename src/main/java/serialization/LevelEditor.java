package serialization;

import frontend.MenuItem;
import frontend.View;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import objects.Obj;
import structure.EventLis;
import structure.modules.LevelContainer;

import java.io.File;
import java.util.ArrayList;

import static javafx.scene.paint.Color.BLACK;

public class LevelEditor {
    //Creating an image
    Image imageBrick = new Image(String.valueOf(new File("brickdef.png")));
    Image imageEnemy = new Image(String.valueOf(new File("endown.png")));
    Image imageBase = new Image(String.valueOf(new File("base_block.png")));
    Image imagePlayer = new Image(String.valueOf(new File("p1up.png")));
    Image imageArmoredBrick = new Image(String.valueOf(new File("solidbrick.png")));
    private ArrayList<LevelContainer.SaveObj> saveObjs = new ArrayList<>();
    private final LevelContainer levelContainer;
    private final JsonUtil jsonUtil = new JsonUtil();
    private final GraphicsContext gc;
    private final Group canvas;
    private final Scene scene;
    private final EventLis eventLis;
    private boolean isProcessingBrick;
    private boolean isProcessingPlayer;
    private boolean isProcessingBase;
    private boolean isProcessingArmoredBrick;
    private boolean isProcessingEnemy;
    private boolean removeMode;

    public LevelEditor(GraphicsContext gc, String filename) {
        levelContainer = new LevelContainer(filename);
        //root.getChildren().add(gc.getCanvas());
        this.gc = gc;
        gc.fillRect(0, 0, 800,600);
        gc.setFill(BLACK);
        //canvas = new GridPane();
        canvas = new Group();
        canvas.setStyle("-fx-background-color: black;");
//        this.canvas.setPrefSize(800, 600);
        canvas.getChildren().add(gc.getCanvas());
        setButtonsField();
        scene = new Scene(canvas);
        eventLis = new EventLis(this, scene);
    }

    private void setButtonsField(){
        MenuItem button;
        VBox vbox = new VBox(10,  new MenuItem("BRICK",() -> {
            isProcessingBrick = true;
            isProcessingBase = false;
            isProcessingArmoredBrick = false;
            isProcessingEnemy = false;
            isProcessingPlayer = false;
            removeMode = false;
            System.out.println("BrickButton");
        }, "levelEditor"),
                new MenuItem("TANK", () -> {
                    isProcessingBase = false;
                    isProcessingArmoredBrick = false;
                    isProcessingBrick = false;
                    isProcessingEnemy = false;
                    isProcessingPlayer = !isTwoPlayers();
                    removeMode = false;
                }, "levelEditor"),
                new MenuItem("BASE", () -> {
                    isProcessingBase = !isOneBase();
                    isProcessingArmoredBrick = false;
                    isProcessingBrick = false;
                    isProcessingEnemy = false;
                    isProcessingPlayer = false;
                    removeMode = false;

                }, "levelEditor"),
                new MenuItem("ARMORED", () -> {
                    isProcessingArmoredBrick = true;
                    isProcessingBase = false;
                    isProcessingBrick = false;
                    isProcessingEnemy = false;
                    isProcessingPlayer = false;
                    removeMode = false;
                }, "levelEditor"),
                new MenuItem("ENEMY", () -> {
                    isProcessingEnemy = true;
                    isProcessingBase = false;
                    isProcessingArmoredBrick = false;
                    isProcessingBrick = false;
                    isProcessingPlayer = false;
                    removeMode = false;
                }, "levelEditor"),
                new MenuItem("Save", ()->{
                    levelContainer.setSaveObjs(saveObjs);
                    levelContainer.saveData();
                }, "levelEditor"),
                new MenuItem("Remove", () -> {
                    removeMode = true;
                    isProcessingEnemy = false;
                    isProcessingBase = false;
                    isProcessingArmoredBrick = false;
                    isProcessingBrick = false;
                    isProcessingPlayer = false;
                }, "levelEditor"),
                new MenuItem("Back", () -> {
                    //View.setScene("menu");
                    View.previousScene();
                }, "levelEditor")
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
        hbox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        canvas.getChildren().add(hbox);
    }


    public void setBlock(int PosX, int PosY) {
        int[] coords = neerAvalibleBlock(PosX, PosY);
        int posX = coords[0];
        int posY = coords[1];
        if(isProcessingBrick) {
            //TODO..
            Obj object = new Obj(0, 1, posX, posY, "brick", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(1, 1, posX, posY);
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                drawimageOnPane(imageBrick, posX, posY);
            }
        }
        if(isProcessingPlayer) {
            //TODO..
            Obj object = new Obj(1, 100, posX, posY, "player", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(100, 4, posX, posY);
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                drawimageOnPane(imagePlayer, posX, posY);
                if(isTwoPlayers())isProcessingPlayer = false;
            }
        }
        if(isProcessingBase) {
            //TODO..
            Obj object = new Obj(1, 1, posX, posY, "base", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(1, 3, posX, posY);
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                drawimageOnPane(imageBase, posX, posY);
                isProcessingBase = false;
            }
        }
        if(isProcessingEnemy) {
            //TODO..
            Obj object = new Obj(1, 30, posX, posY, "tank", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(100, 2, posX, posY);
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                drawimageOnPane(imageEnemy, posX, posY);
            }
        }
        if(isProcessingArmoredBrick) {
            //TODO..
            Obj object = new Obj(1, 30, posX, posY, "armoredbrick", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(1, 5, posX, posY);
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                drawimageOnPane(imageArmoredBrick, posX, posY);
            }
        }
        if(removeMode){
            ArrayList<Obj> newlevelObjects = new ArrayList<>();
            for(Obj object: levelContainer.getLevelObjects()) {
                if ((object.getPosX() < PosX && object.getPosX() +50 > PosX) && (object.getPosY()< PosY && object.getPosY()+50 > PosY)){
                    gc.setFill(BLACK);
                    gc.fillRect(object.getPosX(), object.getPosY(),50, 50 );
                    continue;
                }
                newlevelObjects.add(object);
            }
            levelContainer.getLevelObjects().clear();
            levelContainer.getLevelObjects().addAll(newlevelObjects);
        }

    }
    private boolean isTwoPlayers(){
        int counter = 0;
        for (Obj object: levelContainer.getLevelObjects()){
            if(object.getType().equals("player"))counter++;
            if(counter == 2)return true;
        }
        return false;
    }
    private boolean isOneBase(){
        for(Obj object: levelContainer.getLevelObjects()){
            System.out.println(object.getType());
            if(object.getType().equals("base"))return true;
        }
        return false;
    }

    private int[] neerAvalibleBlock(int posX, int posY){
        int neerPosX = 0;
        int neerPosY = 0;
        for(int i = 0; i <= posX; i = i + 50){
            neerPosX = i;
        }
        for(int i = 0; i < posY; i = i + 50){
            neerPosY = i;
        }
        return new int[] {neerPosX, neerPosY};
    }

    private boolean isCollision(int PosX, int PosY){
        int coordXcenter = PosX + 25;
        int coordYcenter = PosY + 25;
        for(Obj object: levelContainer.getLevelObjects()){
            if((coordXcenter > object.getPosX()-25) && (coordXcenter < object.getPosX()+75) && (coordYcenter > object.getPosY()-25) && (coordYcenter < object.getPosY() + 75)){
                return true;
            }
        }
        return false;
    }

//----------------------------------draw--------------------------------------------------------------

    private void drawimageOnPane(Image image, int posX, int posY){
        ImageView imageView = new ImageView(image);
        imageView.setX(posX);
        imageView.setY(posY);
        gc.drawImage(image, posX, posY);
    }


    //---------------------------------getters------------------------------------------------------------
    public Scene getScene() {
        return scene;
    }
}