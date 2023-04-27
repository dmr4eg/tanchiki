package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

public class Bricks {
    private ArrayList<Brick> bricksClasses = new ArrayList<Brick>();
    private ArrayList<int[]> bricksData;

    private GraphicsContext gc;


    public Bricks (String filename, GraphicsContext gc) {
        this.gc =gc;
        int[] arr = new int[] {0,50,100,150};
        for(int posX : arr){
            Brick brick = new Brick(posX, 0, 10);
            bricksClasses.add(brick);
        }

//        try (FileReader reader = new FileReader("sada.txt")) {
//            // читаем посимвольно
//            int c;
//            while ((c = reader.read()) != -1) {
//
//                System.out.print((char) c);
//            }
//        } catch (IOException ex) {
//
//            System.out.println(ex.getMessage());
//        }
    }


    public void draw(){
        for (Brick brick: bricksClasses){
            gc.drawImage(new Image("brickdef.png"), brick.getPosX(), brick.getPosY());
        }
    }

        private void generate_brick(){
        for (int[] oneBrick: bricksData ) {
            bricksClasses.add(new Brick(oneBrick[0], oneBrick[1], oneBrick[2]));
        }
    }

    public ArrayList<Brick> getBricksClasses() {
        return bricksClasses;
    }
}
