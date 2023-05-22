package structure.modules;

import serialization.JsonUtil;
import javafx.scene.canvas.GraphicsContext;
import objects.Brick;
import objects.Obj;
import objects.Tanks;
import structure.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class LevelContainer   {
    public void setSaveObjs(ArrayList<SaveObj> saveObjs) {
        this.saveObjs = saveObjs;
    }
    private ArrayList<LevelContainer.SaveObj> saveObjs = new ArrayList<>();
    private ArrayList<LevelContainer.loadObj> loadObjs = new ArrayList<>();
    private ArrayList<Obj> levelOvjects = new ArrayList<Obj>();
    private ArrayList<Tanks> levelTanks = new ArrayList<Tanks>();
    public ArrayList<Tanks> getLevelTanks() {
        return levelTanks;
    }

    public ArrayList<Tanks> players;
    private static ArrayList<Brick> levelBricks = new ArrayList<Brick>();
    private GraphicsContext gc;
    private Model model;

    //data for Json
    private static final JsonUtil jsonUtil = new JsonUtil();
    private String filename;


    public LevelContainer(Model model, GraphicsContext gc, String mode) {
        this.model = model;
        this.gc = gc;
        if (mode.equals("offline")){
            loadObjs = (ArrayList<LevelContainer.loadObj>) jsonUtil.loadJsonSaveObj("level3.json");
            filename = "level3.json";
        }
        if (mode.equals("online"))loadObjs = (ArrayList<LevelContainer.loadObj>) jsonUtil.loadJsonSaveObj("level4.json");
        generateFromSaveObj();
        parse_From_Obj_To_Brick(levelOvjects);
        parse_From_Obj_To_Tank(levelOvjects, 25, 1);
        players = getPlayersFromContainer();
        levelOvjects.clear();
        levelOvjects.addAll(levelTanks);
        levelOvjects.addAll(levelBricks);
    }

    public LevelContainer(String mode) {
        if (mode.equals("offline"))this.filename = "level3.json";
        if (mode.equals("online"))this.filename = "level4.json";
    }
    private void generateFromSaveObj(){
        for(loadObj loadObj: loadObjs){
            if(loadObj.param[2] == 1)levelOvjects.add(new Obj(0, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"brick" , gc));
            if(loadObj.param[2] == 2)levelOvjects.add(new Obj(1, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"tank" , gc));
            if(loadObj.param[2] == 3)levelOvjects.add(new Obj(0, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"base" , gc));
            if(loadObj.param[2] == 4)levelOvjects.add(new Obj(1, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"player" , gc));
            if(loadObj.param[2] == 5)levelOvjects.add(new Obj(0, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"armoredbrick", gc));
        }
    }
    //Level Objects methods
    public void addToLevelObjects(Obj object) {
        levelOvjects.add(object);
    }

    public ArrayList<Obj> getLevelOvjects() {
        return levelOvjects;
    }

    public void saveData() {
        jsonUtil.saveJsonSaveObj(saveObjs, filename);
    }

//Level Tanks methods:

    public void parse_From_Obj_To_Tank(ArrayList<Obj> objects, int PlayerDMG, int EnemyDMG) {
        ArrayList<Tanks> retTanks = new ArrayList<Tanks>();
        for (Obj object : objects) {
            if (object.getType().equals("tank"))retTanks.add(new Tanks(object.getHP(), 1, EnemyDMG, object.getType(), gc, 3, object.getPosX(), object.getPosY(), model));
        }
        levelTanks = retTanks;
    }

    //Level Bricks methods:

    public void parse_From_Obj_To_Brick(ArrayList<Obj> objects) {
        ArrayList<Brick> retBricks = new ArrayList<Brick>();
        for (Obj object : objects) {
            if(object.getType().equals("armoredbrick")) {
                retBricks.add(new Brick(object.getPosX(), object.getPosY(), 1, gc, object.getType()));
                continue;
            }
            if (object.getType().equals("brick")) {
                retBricks.add(new Brick(object.getPosX(), object.getPosY(), 1, gc, object.getType()));
                continue;
            }
            if (object.getType().equals("base"))retBricks.add(new Brick(object.getPosX(), object.getPosY(), 1, gc, object.getType(),true));
        }
        levelBricks = retBricks;
    }
    //getters
    public ArrayList<Tanks> getPlayersFromContainer() {
        ArrayList<Tanks> players = new ArrayList<Tanks>();
        for (Obj player : getLevelObjects()) {
            if (player.getType().equals("player")) players.add(new Tanks(player.getHP(), player.getMS(), 25, player.getType(), gc, 1,player.getPosX() ,player.getPosY() ,model));
        }
        return players;
    }


    public ArrayList<Brick> getBricks(){return levelBricks;}
    public ArrayList<Brick> getLevelBricks() {
        return levelBricks;
    }
    public ArrayList<Obj> getLevelObjects() {
        return levelOvjects;
    }

    public Brick getBase() {
        for (Brick base : levelBricks) {
            if (base.getType().equals("base")) return base;
        }
        return new Brick(100, 100, 1, gc, "base", true);
    }


    public static class loadObj {

        private int hp;
        private int[] param;
        public int[] getParam() {
            return param;
        }

        public int getHP() {
            return hp;
        }
    }
    public static class SaveObj {

        private int hp;
        private int[] param;
        public SaveObj(int hp, int t, int x, int y) {
            this.hp = hp;
            param = new int[] {x, y, t};
        }
        public int[] getParam() {
            return param;
        }

        public int getHP() {
            return hp;
        }
    }

    public void fromObjToSaveObj(ArrayList<Obj> levelOvjects){
        HashMap<String, Integer> typeToNumType = new HashMap<>();
        typeToNumType.put("brick", 1);
        typeToNumType.put("tank", 2);
        typeToNumType.put("base", 3);
        typeToNumType.put("player", 4);
        typeToNumType.put("armoredbrick", 5);

        ArrayList<SaveObj> ToSaveObjs = new ArrayList<>();
        for(Obj object : levelOvjects){
            System.out.println(typeToNumType.get(object.getType()));
            ToSaveObjs.add(new SaveObj(object.getHP(), typeToNumType.get(object.getType()), object.getPosX(), object.getPosY()));
        }
        jsonUtil.saveJsonSaveObj(ToSaveObjs, filename);
    }
}