package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Bricks {
    private static ArrayList<Brick> bricksClasses = new ArrayList<>();
    private GraphicsContext gc;
    private Brick base;
    private ArrayList<BrickData> bricksData;
    private final JsonUtil jsonUtil = new JsonUtil();

    public Bricks(String filename, GraphicsContext gc) {
        this.gc = gc;
        bricksData = (ArrayList<BrickData>) jsonUtil.loadJson(filename);
        generateBricks();
        base = new Brick(300, 550, 100000, gc, "base", true);
        bricksClasses.add(base);
    }

    public Brick getBase() {
        return base;
    }

    private void generateBricks() {
        for (BrickData brickData : bricksData) {
            Brick brick = new Brick(brickData.x,brickData.y,1,gc,"brick");
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

    public static class BrickData {
        private int x;
        private int y;
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }
}
