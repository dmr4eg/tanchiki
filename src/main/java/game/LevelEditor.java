package game;

import java.util.ArrayList;

public class LevelEditor {
    private final ArrayList<Brick> bricks = new ArrayList<>();
    private final JsonUtil jsonUtil = new JsonUtil();
    private final String fileName = "level1.json";

    public void addBrick(Brick brick) {
        bricks.add(brick);
        saveData();
    }

    public void removeBrick(Brick brick) {
        bricks.remove(brick);
        saveData();
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void loadData() {
        ArrayList<Brick> loadedData = (ArrayList<Brick>) jsonUtil.loadJson(fileName, ArrayList.class);
        if (loadedData != null) {
            bricks.clear();
            bricks.addAll(loadedData);
        }
    }

    public void saveData() {
        jsonUtil.saveJson(bricks, fileName);
    }
}
