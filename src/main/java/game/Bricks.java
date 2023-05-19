package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Bricks {
    private static ArrayList<Brick> bricksClasses = new ArrayList<>();
    private GraphicsContext gc;
    private Brick base;
    private ArrayList<BrickData> bricksData;
    private final JsonUtil jsonUtil = new JsonUtil();
    private String filename;
    private LevelContainer levelContainer;

    public Bricks(String filename, GraphicsContext gc) {
        this.gc = gc;
        bricksData = (ArrayList<BrickData>) jsonUtil.loadJson(filename);
        generateBricks();
        base = new Brick(300, 550, 100000, gc, "base", true);
        bricksClasses.add(base);
        this.filename = filename;
        this.levelContainer = getLevelContainerFromFile();

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

    private ArrayList<Obj> getObjectsFromFile(){
        return levelContainer.getLevelObjects();
    }

    private ArrayList<Tanks> getTanksFromFile(){
        return  levelContainer.parse_From_Obj_To_Tank(levelContainer.getLevelObjects(), 25, 10);
    }

    private ArrayList<Tanks> getPlayersFromContainer(){
        ArrayList<Tanks> players = new ArrayList<Tanks>();
        for(Tanks player: getTanksFromFile()){
            if(player.getType().equals("Player"))players.add(player);
        }
        return players;

    }


    private LevelContainer getLevelContainerFromFile(){
        LevelContainer levelContainer= jsonUtil.loadJsonObj(filename);
        return levelContainer;
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
