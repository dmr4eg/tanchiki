package structure;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import net.GameClient;
import net.GameServer;
import net.packets.Packet00Login;
import objects.Brick;
import objects.Bullet;
import objects.Obj;
import objects.Tanks;
import structure.modules.EnemyTanksBrain;
import structure.modules.LevelContainer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Model extends Thread{
    public final String gameMode;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private final ArrayList<Tanks> tanks;
    private ArrayList<Obj> allObjects = new ArrayList<Obj>();
    private final ArrayList<Brick> bricks;
    private GraphicsContext gc;
    private Tanks player1;
    private Tanks player2;
    private Pane gamePane = new Pane();
    private boolean gameIsStart = true;
    public void setPlayer2(Tanks player2) {
        this.player2 = player2;
    }

    public void setPlayer1(Tanks player1) {
        this.player1 = player1;
    }

    private Tanks enemyTank;
    private EnemyTanksBrain enemyBrain;
    private Brick base;
    boolean isStart;
    public boolean bullet;
    GameServer socketServer;
    GameClient socketClient;
    private LevelContainer levelContainer;
    private static final Logger LOGGER = Logger.getLogger(Model.class.getName());
    private int enemyCount = 0;
    public Model(GraphicsContext gc) {
        //construct game storage for handling objects
        this.gameMode = "offline";
        levelContainer = new LevelContainer(this, gc, gameMode);
        allObjects = levelContainer.getLevelObjects();
        base = levelContainer.getBase();
        tanks = levelContainer.getLevelTanks();
        bricks = levelContainer.getLevelBricks();
        player1 = levelContainer.players.get(0);
        allObjects.add(player1);

        this.gc = gc;
        //Declare Enemy Logic
        this.enemyBrain = new EnemyTanksBrain(base, player1);

        FileHandler fhm = null;
        try {
            fhm = new FileHandler("modelLogs.txt", true);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open log file", e);
        }
        fhm.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fhm);
        LOGGER.info("Model instantiated");
    }
    public Model(GraphicsContext gc, String name) {
        this.gc = gc;
        this.gameMode = "online";
        //construct game storage for handling objects
        levelContainer = new LevelContainer(this, gc, gameMode);
        allObjects = levelContainer.getLevelObjects();
        base = levelContainer.getBase();
        tanks = levelContainer.getLevelTanks();
        bricks = levelContainer.getLevelBricks();
        ArrayList<Tanks> players = levelContainer.players;

        //start server and client
        startBackend(name);
        FileHandler fhm = null;
        try {
            fhm = new FileHandler("modelLogs.txt", true);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open log file", e);
        }
        fhm.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fhm);
        LOGGER.info("Model instantiated");
    }
    public void startGame(){
        isStart = true;
    }

    public void setPlayer1Orientation(int orientation){ // changing player orientation
        player1.setOrientation(orientation);
    }

    public Tanks getPlayer1() {
        return player1;
    }

    public Tanks getPlayer2() {
        return player2;
    }

    public void addBullet(Bullet bullet){ //add to game storage bullet
        bullets.add(bullet);
        LOGGER.info("New bullet added");
    }

    //---------------------------------------------------------------------------------------------------------------------------------
    public void updateDraw(){
        for(Obj object: allObjects){
            object.draw();
        }
    }
    //method for computing enemy
    public void enemy_computingObj(){
        for(Tanks tank : tanks){
            if(bricks != null){
                enemyBrain.computing_to_baseObj(tank, allObjects);
                enemyBrain.move(tank);
            }
        }
    }
    //checking if is there collision with brick and bullet
    public void isCollision_tankObj(Tanks checking_obj) {
//        if (checking_obj.hasCollided) {
//            return;
//        }
        boolean[] retCollisionArr = new boolean[]{false, false, false, false};
        for (Obj object : allObjects) {
            if (object != checking_obj) {
                //left
                if ((((checking_obj.getPosY() + 1 >= object.getPosY() + 1) && (checking_obj.getPosY() + 1 <= object.getPosY() + 49)) ||
                        ((checking_obj.getPosY() + 49 >= object.getPosY() + 1) && (checking_obj.getPosY() + 49 <= object.getPosY() + 49))) &&
                        ((checking_obj.getPosX() <= object.getPosX() + 50) && (checking_obj.getPosX() >= object.getPosX() + 50))||
                        (checking_obj.getPosX() < 0)) {
                    retCollisionArr[0] = true;
                    LOGGER.info("Tank collided with an object");
                }
                //right
                if ((((checking_obj.getPosY() + 1 >= object.getPosY() + 1) && (checking_obj.getPosY() + 1 <= object.getPosY() + 49)) ||
                        ((checking_obj.getPosY() + 49 >= object.getPosY() + 1) && (checking_obj.getPosY() + 49 <= object.getPosY() + 49))) &&
                        ((checking_obj.getPosX() + 50 <= object.getPosX()) && (checking_obj.getPosX() + 50 >= object.getPosX())) ||
                        (checking_obj.getPosX() > 550)) {
                    retCollisionArr[1] = true;
                    LOGGER.info("Tank collided with an object");
                }

                //forward
                if ((((checking_obj.getPosX() + 1 >= object.getPosX() + 1) && (checking_obj.getPosX() + 1 <= object.getPosX() + 49)) ||
                        ((checking_obj.getPosX() + 49 >= object.getPosX() + 1) && (checking_obj.getPosX() + 49 <= object.getPosX() + 49))) &&
                        ((checking_obj.getPosY() <= object.getPosY() + 49) && (checking_obj.getPosY() >= object.getPosY() + 49)) ||
                        (checking_obj.getPosY() < 0)) {
                    retCollisionArr[2] = true;
                    LOGGER.info("Tank collided with an object");
                }
                //backward
                if ((((checking_obj.getPosX() + 1 >= object.getPosX() + 1) && (checking_obj.getPosX() + 1 <= object.getPosX() + 49)) ||
                        ((checking_obj.getPosX() + 49 >= object.getPosX() + 1) && (checking_obj.getPosX() + 49 <= object.getPosX() + 49))) &&
                        ((checking_obj.getPosY() + 49 <= object.getPosY()) && (checking_obj.getPosY() + 49 >= object.getPosY() - 1)) ||
                        (checking_obj.getPosY() > 550)) {
                    retCollisionArr[3] = true;
                    LOGGER.info("Tank collided with an object");
                }
            }
        }

        checking_obj.setIsColision(retCollisionArr);
    }

    private Obj isBulletCollisionObj(Bullet bullet){
        for(Obj object : allObjects){
            int bulletX = bullet.getPosX();
            int bulletY = bullet.getPosY();
            int objectX = object.getPosX();
            int objectY = object.getPosY();
            if((((bulletX>= objectX) && (bulletX <= objectX + 50)) || ((bulletX + 10>= objectX) && (bulletX + 10 <= objectX + 50))) &&
                    (((bulletY >= objectY) && (bulletY <= objectY+50)) || ((bulletY+10 >= objectY)&&(bulletY+10 <= objectY + 50)))){
                if (object == base){
//                    isStart = false;
                    System.out.println(isStart);
                }
                return object;
            }
        }
        return null;
    }

    public void updateObj(){
        ArrayList<Bullet> newBuletsArr = new ArrayList<Bullet>();
        Obj object;
        for(Bullet bullet : bullets){
            if(bullet.isInScreen()) {
                bullet.update();
                if((object = isBulletCollisionObj(bullet))!= null) {
                    if(object.getType().equals("armoredbrick")){
//                        allObjects.remove(object);
                        continue;
                    }
//                    if(object.getType().equals("brick")) {
//                        if(object.getType().equals("player"))continue;
                    boolean isDead = object.isDead(bullet.getDMG());
                    if(isDead){
                        System.out.println(object.getType() + " " + object.getHP());
                        allObjects.remove(object);
                        tanks.remove(object);
//                        }
                    }
                    continue;
                }
                newBuletsArr.add(bullet);
            }
        }
        bullets = newBuletsArr;
    }

    public GraphicsContext getGc() {
        return gc;
    }
    //опа, гс удаляет))

    public void addToTanks(Tanks tank) {
        tanks.add(tank);
    }
    public void addToAllObjects(Obj object) {
        allObjects.add(object);
    }

    public void setGameIsStart(boolean status){
        gameIsStart = status;
    }

    public boolean isOnlineStart() {
        if(gameMode.equals("online")){
            return player2 != null;
        }
        return true;
    }

    public boolean getGameIsStart(){
        return gameIsStart;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------


    public ArrayList<Bullet> getBullets(){
        return bullets;
    }
    //SERVER side--------------------------------------------------------------------------------------------
    private void startBackend(String name){
        if (!isServerStart()) {
            socketServer = new GameServer(this, levelContainer);
            socketServer.start();
        }
        socketClient = new GameClient(this, "localhost");
        socketClient.start();
        Packet00Login loginPacket = new Packet00Login(name.getBytes());
        loginPacket.writeData(socketClient);
    }

    private boolean isServerStart(){
        try {
            DatagramSocket socket = new DatagramSocket(1331);
            socket.close();
            System.out.println("Server is not running");
            return false;
        } catch (IOException ex) {
            System.out.println("Server already running");
            return true;
        }
    }

    public void updateEnemyBrain(Tanks player1, Tanks player2){
        this.enemyBrain = new EnemyTanksBrain(base, player1, player2);
    }

    //update
    public void UpdateModel(){
        if(player1.getHP() == 0) {
            gameIsStart = false;
        }
        updateDraw();
        isCollision_tankObj(player1);
        player1.update();
//        if(socketServer != null)
        enemy_computingObj();
        if (player2 != null) getPlayer2().updateCooldownAndAnimation();
        if (!getBullets().isEmpty()) {
            updateObj();
        }
    }

    public int getEnemies(){
        ArrayList<Tanks> a = levelContainer.getLevelTanks();
        return a.size();
    }

    public ArrayList<Tanks> getTanks() {
        return tanks;
    }

    public GameServer getSocketServer() {
        return socketServer;
    }
}