package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bricks {
    private ArrayList<Brick> bricksClasses = new ArrayList<>();
    private GraphicsContext gc;
    private Brick base;
    private ArrayList<BrickData> bricksData;
    public Brick getBase() {
        return base;
    }

    public Bricks(String filename, GraphicsContext gc) {
        this.gc = gc;

        loadBricksData(filename);
        generateBricks();
        base = new Brick(300, 550, 100000, gc, "base", true);
        bricksClasses.add(base);
    }

    private void generateBricks() {
        for (BrickData brickData : bricksData) {
            Brick brick = new Brick(brickData.getX(), brickData.getY(), 1, gc, "brick");
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

    private void loadBricksData(String filename) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            bricksData = objectMapper.readValue(new File(filename), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, BrickData.class));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
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
