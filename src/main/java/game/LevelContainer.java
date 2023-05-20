package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class LevelContainer {
    public static void setSaveObjs(ArrayList<SaveObj> saveObjs) {
        LevelContainer.saveObjs = saveObjs;
    }

    private static ArrayList<LevelContainer.SaveObj> saveObjs = new ArrayList<>();
    private static ArrayList<LevelContainer.loadObj> loadObjs = new ArrayList<>();
    private static ArrayList<Obj> levelOvjects = new ArrayList<Obj>();



    private static ArrayList<Tanks> levelTanks = new ArrayList<Tanks>();

    public ArrayList<Tanks> getLevelTanks() {
        return levelTanks;
    }

    public ArrayList<Tanks> players;

    private static ArrayList<Brick> levelBricks = new ArrayList<Brick>();
    private GraphicsContext gc;
    private Model model;

    //data for Json
    private static final JsonUtil jsonUtil = new JsonUtil();
    private static final String filename = "level3.json";

    public LevelContainer(Model model, GraphicsContext gc) {
        this.model = model;
        this.gc = gc;
        loadObjs = (ArrayList<LevelContainer.loadObj>) jsonUtil.loadJsonSaveObj(filename);
        generateFromSaveObj();
        parse_From_Obj_To_Brick(levelOvjects);
        parse_From_Obj_To_Tank(levelOvjects, 25, 1);
        players = getPlayersFromContainer();
        levelOvjects.clear();
        levelOvjects.addAll(levelTanks);
        levelOvjects.addAll(levelBricks);
        levelOvjects.addAll(players);
    }

    public LevelContainer() {
    }
    private void generateFromSaveObj(){
        System.out.println("nigger");
        for(loadObj loadObj: loadObjs){
            System.out.println(loadObj.param[2]);
            if(loadObj.param[2] == 1)levelOvjects.add(new Obj(0, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"brick" , gc));
            if(loadObj.param[2] == 2)levelOvjects.add(new Obj(1, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"tank" , gc));
            if(loadObj.param[2] == 3)levelOvjects.add(new Obj(0, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"base" , gc));
            if(loadObj.param[2] == 4)levelOvjects.add(new Obj(1, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"player" , gc));
            if(loadObj.param[2] == 5)levelOvjects.add(new Obj(0, loadObj.hp,loadObj.param[0] ,loadObj.param[1] ,"armoredbrick", gc));
        }
    }
    //Level Objects methods
    public static void addToLevelObjects(Obj object) {
        levelOvjects.add(object);
    }

    public static ArrayList<Obj> getLevelOvjects() {
        return levelOvjects;
    }
//
//    public void loadData() {
//         levelOvjects = (ArrayList<Obj>) jsonUtil.loadJsonSaveObj(filename);
//         parse_From_Obj_To_Tank(levelOvjects, 25, 10);
//         parse_From_Obj_To_Brick(levelOvjects);
//
//    }

    public void saveData() {
        jsonUtil.saveJsonSaveObj(saveObjs, filename);
    }
    //ya v altTab
    // ок

//Level Tanks methods:

    public void parse_From_Obj_To_Tank(ArrayList<Obj> objects, int PlayerDMG, int EnemyDMG) {
        ArrayList<Tanks> retTanks = new ArrayList<Tanks>();
        for (Obj object : objects) {
            if (object.type.equals("tank"))retTanks.add(new Tanks(object.HP, 1, EnemyDMG, object.type, gc, 3, object.getPosX(), object.getPosY(), model));
        }
        levelTanks = retTanks;
    }

    //Level Bricks methods:

    public void parse_From_Obj_To_Brick(ArrayList<Obj> objects) {
        ArrayList<Brick> retBricks = new ArrayList<Brick>();
        for (Obj object : objects) {
            if(object.type.equals("armoredbrick")) {
                retBricks.add(new Brick(object.getPosX(), object.PosY, 1, gc, object.getType()));
                continue;
            }
            if (object.type.equals("brick")) {
                retBricks.add(new Brick(object.getPosX(), object.PosY, 1, gc, object.getType()));
                continue;
            }
            if (object.type.equals("base"))retBricks.add(new Brick(object.getPosX(), object.PosY, 1, gc, object.getType(),true));
        }
        levelBricks = retBricks;
    }
    //getters
    public ArrayList<Tanks> getPlayersFromContainer() {
        ArrayList<Tanks> players = new ArrayList<Tanks>();
        for (Obj player : getLevelObjects()) {
            if (player.type.equals("player")) players.add(new Tanks(player.HP, player.MS, 25, player.type, gc, 1,player.getPosX() ,player.getPosY() ,model));
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
}