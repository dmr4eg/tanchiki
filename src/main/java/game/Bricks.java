package game;

import javafx.scene.canvas.GraphicsContext;
import objects.Brick;
import serialization.JsonUtil;
import structure.modules.LevelContainer;
import structure.Model;

import java.util.ArrayList;

public class Bricks {
    private static ArrayList<Brick> bricksClasses = new ArrayList<>();
    private GraphicsContext gc;
    private Brick base;
    private ArrayList<BrickData> bricksData;
    private final JsonUtil jsonUtil = new JsonUtil();
    private final LevelContainer levelContainer;

    public Bricks(String filename, GraphicsContext gc, Model model) {
        this.gc = gc;
//        bricksData = (ArrayList<BrickData>) jsonUtil.loadJson(filename);
//        generateBricks();
        base = new Brick(300, 550, 100000, gc, "base", true);
        levelContainer = new LevelContainer(model, gc);
        bricksClasses = levelContainer.getLevelBricks();
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
