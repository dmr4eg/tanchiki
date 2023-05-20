package game;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.GameClient;
import net.GameServer;
import net.packets.Packet00Login;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Model extends Thread{
    private final String gameMode;
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
    private GameServer socketServer;
    private GameClient socketClient;
    private LevelContainer levelContainer;
    private static final Logger LOGGER = Logger.getLogger(Model.class.getName());
    public Model( GraphicsContext gc) {
        levelContainer = new LevelContainer(this, gc);
        allObjects = levelContainer.getLevelObjects();
        base = levelContainer.getBase();
        tanks = levelContainer.getLevelTanks();
        bricks = levelContainer.getLevelBricks();
        player1 = levelContainer.players.get(0);


        this.gameMode = "offline";
        this.gc = gc;
        this.enemyBrain = new EnemyTanksBrain(base, player1);
//        player1 = new Tanks(100, 1, 25, "player", gc, 1, 100, 100, this);
//
//        allObjects.addAll(bricks.getBricksClasses());
//        base = bricks.getBase();
//        enemyTank = new Tanks(100, 0 , 1, "enemyTank", gc, 1, 100, 400, this);


        FileHandler fhm = null;
        try {
            fhm = new FileHandler("modelLogs.txt");
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


        levelContainer = new LevelContainer(this, gc);
        allObjects = levelContainer.getLevelObjects();
        base = levelContainer.getBase();
        tanks = levelContainer.getLevelTanks();
        bricks = levelContainer.getLevelBricks();
        ArrayList<Tanks> players = levelContainer.getPlayersFromContainer();
        player1 = players.get(1);

        startBackend(name);
        FileHandler fhm = null;
        try {
            fhm = new FileHandler("modelLogs.txt");
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
    public void setPlayer1Orientation(int orientation){
        player1.setOrientation(orientation);
    }

    public Tanks getPlayer1() {
        return player1;
    }

    public Tanks getPlayer2() {
        return player2;
    }


//    public void update(){
//        ArrayList<Bullet> newBuletsArr = new ArrayList<Bullet>();
//        Brick brick;
//        for(Bullet bullet : bullets){
//            if(bullet.isInScreen()) {
//                if((brick = isBulletCollision(bullet))!= null) {
//                    bricks.remove(brick);
//                    continue;
//                }
//                bullet.update();
//                newBuletsArr.add(bullet);
//                continue;
//            }
//        }
//        bullets = newBuletsArr;
//        LOGGER.info(String.format("Updated %d bullets", bullets.size()));
//    }

    public void addBullet(Bullet bullet){
        bullets.add(bullet);
        LOGGER.info("New bullet added");
    }
    //checking if is there collision with brick and bullet
    //---------------------------------------------------------------------------------------------------------------------------------
    public void updateDraw(){
        for(Obj object: allObjects){
            object.draw();
        }
    }


    public void enemy_computingObj(){
        for(Tanks tank : tanks){
            if(bricks != null){
                enemyBrain.computing_to_baseObj(tank, allObjects);
                enemyBrain.move(tank);
            }
        }
    }

    public void isCollision_tankObj(Tanks checking_obj) {
//        if (checking_obj.hasCollided) {
//            return;
//        }
        boolean[] retCollisionArr = new boolean[]{false, false, false, false};
        for (Obj object : allObjects) {
            if (object != checking_obj) {
                //left
                if ((((checking_obj.getPosY() + 1 >= object.getPosY() + 1) && (checking_obj.getPosY() + 1 <= object.getPosY() + 49)) || ((checking_obj.getPosY() + 49 >= object.getPosY() + 1) && (checking_obj.getPosY() + 49 <= object.getPosY() + 49))) &&
                        ((checking_obj.getPosX() <= object.getPosX() + 50) && (checking_obj.getPosX() >= object.getPosX() + 50))) {
                    retCollisionArr[0] = true;
                    LOGGER.info("Tank collided with an object");
                }
                //right
                if ((((checking_obj.getPosY() + 1 >= object.getPosY() + 1) && (checking_obj.getPosY() + 1 <= object.getPosY() + 49)) || ((checking_obj.getPosY() + 49 >= object.getPosY() + 1) && (checking_obj.getPosY() + 49 <= object.getPosY() + 49))) &&
                        ((checking_obj.getPosX() + 50 <= object.getPosX()) && (checking_obj.getPosX() + 50 >= object.getPosX()))) {
                    retCollisionArr[1] = true;
                    LOGGER.info("Tank collided with an object");
                }

                //forward
                if ((((checking_obj.getPosX() + 1 >= object.getPosX() + 1) && (checking_obj.getPosX() + 1 <= object.getPosX() + 49)) || ((checking_obj.getPosX() + 49 >= object.getPosX() + 1) && (checking_obj.getPosX() + 49 <= object.getPosX() + 49))) &&
                        ((checking_obj.getPosY() <= object.getPosY() + 49) && (checking_obj.getPosY() >= object.getPosY() + 49))) {
                    retCollisionArr[2] = true;
                    LOGGER.info("Tank collided with an object");
                }
                //backward
                if ((((checking_obj.getPosX() + 1 >= object.getPosX() + 1) && (checking_obj.getPosX() + 1 <= object.getPosX() + 49)) || ((checking_obj.getPosX() + 49 >= object.getPosX() + 1) && (checking_obj.getPosX() + 49 <= object.getPosX() + 49))) &&
                        ((checking_obj.getPosY() + 49 <= object.getPosY()) && (checking_obj.getPosY() + 49 >= object.getPosY() - 1))) {
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

    public void addToTanks(Tanks tank) {
        tanks.add(tank);
    }
    public void addToAllObjects(Obj object) {
        allObjects.add(object);
    }

    public void setGameIsStart(boolean status){
        gameIsStart = status;
    }

    public boolean isGameIsStart() {
        if(gameMode.equals("online")){
            return player2 != null;
        }
        return true;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------


//    private Brick isBulletCollision(Bullet bullet){
//        for(Brick brick : bricks){
//            int bulletX = bullet.getPosX();
//            int bulletY = bullet.getPosY();
//            int brickX = brick.getPosX();
//            int brickY = brick.getPosY();
//            if(((((bulletX>= brickX) && (bulletX <= brickX + 50)) || ((bulletX + 10>= brickX) && (bulletX + 10 <= brickX + 50)))) &&
//                    (((bulletY >= brickY) && (bulletY <= brickY+50)) || ((bulletY+10 >= brickY)&&(bulletY+10 <= brickY + 50)))){
//                if (brick == base){
//                    isStart = false;
//                    System.out.println(isStart);
//                }
//                return brick;
//            }
//        }
//        return null;
//    }



    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public Parent getGamePane() {
        return gamePane;
    }
    //SERVER side--------------------------------------------------------------------------------------------
    private void startBackend(String name){
        if (!isServerStart()) {
            socketServer = new GameServer(this);
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

    public void UpdateModel(){
        updateDraw();
        isCollision_tankObj(player1);
        player1.update();
        enemy_computingObj();
        if (player2 != null) getPlayer2().updateCooldownAndAnimation();
        if (!getBullets().isEmpty()) {
            updateObj();
        }
    }
}