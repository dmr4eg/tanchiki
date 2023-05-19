package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class LevelContainer {
    private static ArrayList<Obj> levelOvjects = new ArrayList<Obj>();
    public ArrayList<Obj> getLevelObjects(){
        return levelOvjects;
    }
    private static ArrayList<Tanks> levelTanks = new ArrayList<Tanks>();
    private static ArrayList<Brick> levelBricks = new ArrayList<Brick>();
    private GraphicsContext gc;
    private Model model;

    //data for Json
    private static final JsonUtil jsonUtil = new JsonUtil();
    private static final String filename = "levelPack.json"; //<- TODO sdelai file dlya etogo

    public LevelContainer(Model model, GraphicsContext gc) {
        this.model = model;
        this.gc = gc;
    }

    public LevelContainer(

    ){

    }

    //Level Objects methods
    public static void addToLevelObjects(Obj object) {
        levelOvjects.add(object);
    }
    public static void addToLevelObjects(ArrayList<Obj> objects){
        levelOvjects.addAll(objects);

    }

    public static ArrayList<Obj> getLevelOvjects() {
        return levelOvjects;
    }

    public void loadData(){
        jsonUtil.loadJsonObj(filename);
    }
    public void saveData(){
        jsonUtil.saveJsonObj(this, filename);
    }
    //ya v altTab
    // ок

//Level Tanks methods:

    public static void addTolevelTanks(Tanks tank){
        levelTanks.add(tank);
    }
    public static void addTolevelTanks(ArrayList<Tanks> tanks){
        levelTanks.addAll(tanks);
    }

    public static ArrayList<Tanks> getLevelTanks() {
        return levelTanks;
    }

    public ArrayList<Tanks> parse_From_Obj_To_Tank(ArrayList<Obj> objects, int PlayerDMG,int EnemyDMG ){
        ArrayList<Tanks> retTanks = new ArrayList<Tanks>();
        for (Obj object: objects){
            if (object.type.equals("tank"))retTanks.add(new Tanks(object.HP, 1, EnemyDMG, object.type, gc, 3, object.getPosX(), object.getPosY(),model));
            if (object.type.equals("player"))retTanks.add(new Tanks(object.HP, 1, PlayerDMG, object.type, gc, 3, object.getPosX(), object.getPosY(),model));
        }
        return retTanks;
    }

    //Level Bricks methods:
    public static void addTolevelBricks(Brick brick){
        levelBricks.add(brick);
    }
    public static void addTolevelBricks(ArrayList<Brick> bricks){
        levelBricks.addAll(bricks);
    }

    public static ArrayList<Brick> getLevelBricks() {
        return levelBricks;
    }

    public ArrayList<Brick> parse_From_Obj_To_Brick(ArrayList<Obj> objects, int DMG){
        ArrayList<Brick> retBricks = new ArrayList<Brick>();
        for (Obj object: objects){
            if (object.type.equals("brick")||object.type.equals("base")|| object.type.equals("armoredbrick")){
                retBricks.add(new Brick(object.getPosX(), object.PosY, 1, gc, object.getType()));
            }
        }
        return retBricks;
    }



    @Override
    public String toString() {
        String msg = "";
        for(Obj object : levelOvjects){
            msg += "|" + object.type +", PosX" + object.getPosX() + ", PosY" + object.getPosY();
        }

        return "LevelObjects{" +
                msg +
                '}';
    }
}
