package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.*;
import java.util.ArrayList;

public class Bricks {
    private ArrayList<Brick> bricksClasses = new ArrayList<Brick>();
    private ArrayList<int[]> bricksData;

    private GraphicsContext gc;


    public Bricks (String filename, GraphicsContext gc, ArrayList<int[]> bricksData) {
        this.gc =gc;
        this.bricksData = bricksData;
        ArrayList<int[]> arr = new ArrayList<>();
        int [][] arrayBlocksCoords = {{100, 0},{150,0},{200,0},{250, 0}, {300,0},{350, 0}, {400, 0}, {450, 0}, {500, 0}, {550, 0},
                {150,125},{200,125},{250, 125}, {300,125},{350, 125}, {400, 125}, {450, 125}, {500, 125}, {550, 125}};
        for (int[] i: arrayBlocksCoords){
            arr.add(i);
        }
        generate_brick(arr);


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

    private void generate_brick(ArrayList<int[]> arr){
        for(int[] coord : arr){
            Brick brick = new Brick(coord[0],coord[1] , 1);
            bricksClasses.add(brick);
        }
    }

    public ArrayList<Brick> getBricksClasses() {
        return bricksClasses;
    }
}
