package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

public class Bricks {
    private ArrayList<Brick> bricksClasses = new ArrayList<>();
    private int[][] bricksData;
    private GraphicsContext gc;

    private Brick base;

    public Brick getBase() {
        return base;
    }

    public Bricks(String filename, GraphicsContext gc) {
        this.gc = gc;
        bricksData = new int[][] {{100,100,10}, {200,300,10}, {100,500,10}, {500,500,10}};
//        loadBricksData(filename);
        generateBricks();
        base = new Brick(275, 550, 100000, gc, true);
        bricksClasses.add(base);
    }


//    public void draw(){
//        for (Brick brick: bricksClasses){
//            gc.drawImage(new Image("brickdef.png"), brick.getPosX(), brick.getPosY());
//        }
//    }

    private void generateBricks() {
        for (int[] coord : bricksData) {
            Brick brick = new Brick(coord[0], coord[1], 1, gc);
            bricksClasses.add(brick);
        }
    }

    public ArrayList<Brick> getBricksClasses() {
        return bricksClasses;
    }

    public void removeBrick(Brick brick){
        bricksClasses.remove(brick);
    }

    public void add(Brick brick){
        bricksClasses.add(brick);
    }



//    private void loadBricksData(String filename) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            bricksData = objectMapper.readValue(new File(filename), ArrayList.class);
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
}
