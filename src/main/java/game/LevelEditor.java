package game;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
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
    private final LevelContainer levelContainer = new LevelContainer();
    private final JsonUtil jsonUtil = new JsonUtil();
    private final String fileName = "level2.json";
    private final GraphicsContext gc;
    private final Group canvas;
    private final Scene scene;
    private final EventLis eventLis;
    private boolean isProcessingBrick;
    private boolean isProcessingPlayer1;
    private boolean isProcessingPlayer2;
    private boolean isProcessingBase;
    private boolean isProcessingArmoredBrick;
    private boolean isProcessingEnemy;

    public LevelEditor(GraphicsContext gc) {
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
            isProcessingPlayer1 = true;
            isProcessingBase = false;
            isProcessingArmoredBrick = false;
            isProcessingEnemy = false;
            isProcessingPlayer2 = false;
            System.out.println("BrickButton");
        }, "levelEditor"),
                new MenuItem("TANK", () -> {
                    isProcessingPlayer1 = !isTwoPlayers();
                    isProcessingBase = false;
                    isProcessingArmoredBrick = false;
                    isProcessingBrick = false;
                    isProcessingEnemy = false;
                    isProcessingPlayer2 = false;
                }, "levelEditor"),
                new MenuItem("BASE", () -> {
                    isProcessingBase = !isOneBase();
                    isProcessingPlayer1 = false;
                    isProcessingArmoredBrick = false;
                    isProcessingBrick = false;
                    isProcessingEnemy = false;
                    isProcessingPlayer2 = false;

                }, "levelEditor"),
                new MenuItem("ARMORED", () -> {
                    isProcessingArmoredBrick = true;
                    isProcessingPlayer1 = false;
                    isProcessingBase = false;
                    isProcessingBrick = false;
                    isProcessingEnemy = false;
                    isProcessingPlayer2 = false;
                }, "levelEditor"),
                new MenuItem("ENEMY", () -> {
                    isProcessingEnemy = true;
                    isProcessingPlayer1 = false;
                    isProcessingBase = false;
                    isProcessingArmoredBrick = false;
                    isProcessingBrick = false;
                    isProcessingPlayer2 = false;
                }, "levelEditor"),
                new MenuItem("Save", ()->{
                    levelContainer.setSaveObjs(saveObjs);
                    levelContainer.saveData();
                }, "levelEditor"),
                new MenuItem("Back", () -> {}, "levelEditor")
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


    public void setBlock(int posX, int posY) {
        int[] pos = neerAvalibleBlock(posX, posY);
        posX = pos[0];
        posY = pos[1];
        if(isProcessingBrick) {
            //TODO..
            System.out.println("brick processing...");
            Obj object = new Obj(0, 1, posX, posY, "brick", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(1, 1, posX, posY);
            System.out.println("check collision");
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                System.out.println("brick added...");
                drawimageOnPane(imageBrick, posX, posY);
                System.out.println("have to be painted");
            }
        }
        if(isProcessingPlayer1) {
            //TODO..
            System.out.println("Player1 processing...");
            Obj object = new Obj(1, 100, posX, posY, "player", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(100, 4, posX, posY);
            System.out.println("check collision");
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                System.out.println("Player1 added...");
                drawimageOnPane(imagePlayer, posX, posY);
                System.out.println("have to be painted");
            }
        }
        if(isProcessingPlayer2) {
            //TODO..
            System.out.println("Player2 processing...");
            Obj object = new Obj(1, 100, posX, posY, "player", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(100, 4, posX, posY);
            System.out.println("check collision");
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                System.out.println("brick added...");
                drawimageOnPane(imagePlayer, posX, posY);
                System.out.println("have to be painted");
            }
        }
        if(isProcessingBase) {
            //TODO..
            System.out.println("Base processing...");
            Obj object = new Obj(1, 1, posX, posY, "base", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(1, 3, posX, posY);
            System.out.println("check collision");
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                System.out.println("brick added...");
                drawimageOnPane(imageBase, posX, posY);
                System.out.println("have to be painted");
            }
        }
        if(isProcessingEnemy) {
            //TODO..
            System.out.println("Enemy processing...");

            Obj object = new Obj(1, 30, posX, posY, "tank", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(100, 2, posX, posY);
            System.out.println("check collision");
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                System.out.println("brick added...");
                drawimageOnPane(imageEnemy, posX, posY);
                System.out.println("have to be painted");
            }
        }
        if(isProcessingArmoredBrick) {
            //TODO..
            System.out.println("ArmoredBrick processing...");
            Obj object = new Obj(1, 30, posX, posY, "armoredbrick", gc);
            LevelContainer.SaveObj saveObj = new LevelContainer.SaveObj(1, 5, posX, posY);
            System.out.println("check collision");
            if(!isCollision(posX, posY)){
                saveObjs.add(saveObj);
                levelContainer.addToLevelObjects(object);
                System.out.println("brick added...");
                drawimageOnPane(imageArmoredBrick, posX, posY);
                System.out.println("have to be painted");
            }
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