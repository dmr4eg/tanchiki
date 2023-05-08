package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class LevelEditor {
    private final JsonUtil jsonUtil = new JsonUtil();
    private final String fileName = "level1.json";

    private final GraphicsContext gc;
    private final Bricks bricks;

    public LevelEditor(GraphicsContext gc) {
        this.gc = gc;
        this.bricks = new Bricks("", gc);
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