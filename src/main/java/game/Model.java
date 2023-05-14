package game;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Model {
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private final ArrayList<Tanks> tanks = new ArrayList<Tanks>();
    private ArrayList<Obj> allObjects = new ArrayList<Obj>();
    private  ArrayList<ArrayList<Integer>> bricsCoords =  new ArrayList<ArrayList<Integer>>(); //[[],[],[]]
    private final Bricks bricks;
    private GraphicsContext gc;
    private final Tanks player1;
    private Tanks enemyTank;
    private final EnemyTanksBrain enemyBrain;
    private final Brick base;
    boolean isStart;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());

    public Model(boolean isStart, GraphicsContext gc) {
        this.bricks = new Bricks("level1.json", gc);
        allObjects.addAll(bricks.getBricksClasses());
        base = bricks.getBase();
        player1 = new Tanks(100, 1, 20, "player", gc, 1, 100, 100, this);
        enemyTank = new Tanks(100, 0 , 1, "tank", gc, 1, 100, 400, this);
        this.gc = gc;
        this.enemyBrain = new EnemyTanksBrain(bricks.getBase(), player1);
        this.isStart = isStart;
        allObjects.add(player1);
        allObjects.add(enemyTank);
        tanks.add(enemyTank);
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


    public void enemy_computing(){
        for(Tanks tank : tanks){
            if(bricks.getBricksClasses() != null){
                enemyBrain.computing_to_base(tank, bricks);
                enemyBrain.move(tank);
            }
        }
    }



    public void update(){
        ArrayList<Bullet> newBuletsArr = new ArrayList<Bullet>();
        Brick brick;
        for(Bullet bullet : bullets){
            if(bullet.isInScreen()) {
                if((brick = isBulletCollision(bullet))!= null) {
                    bricks.removeBrick(brick);
                    continue;
                }
                bullet.update();
                newBuletsArr.add(bullet);
                continue;
            }
        }
        bullets = newBuletsArr;
        LOGGER.info(String.format("Updated %d bullets", bullets.size()));
    }


    public void addBullet(Bullet bullet){
        bullets.add(bullet);
        LOGGER.info("New bullet added");

    }


    public boolean bullet;


    //checking if is there collision with brick and bullet
    //---------------------------------------------------------------------------------------------------------------------------------
    public void updateDraw(){
        for(Obj object: allObjects){
            object.draw();
        }
    }
    public void enemy_computingObj(){
        for(Tanks tank : tanks){
            if(bricks.getBricksClasses() != null){
                enemyBrain.computing_to_baseObj(tank, allObjects);
                enemyBrain.move(tank);
            }
        }
    }

    public void isCollision_tankObj(Tanks checking_obj) {
        if (checking_obj.hasCollided) {
            return;
        }
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
                    isStart = false;
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
                    if(!object.getType().equals("brick")) {
                        boolean isDead = object.isDead(bullet.getDMG());
                        if(isDead){
                            System.out.println(object.getType() + " " + object.getHP());
                            allObjects.remove(object);
                            tanks.remove(object);
                        }
                        continue;
                    }
                    if(object.getType().equals("brick"))allObjects.remove(object);
                    continue;
                }
                newBuletsArr.add(bullet);
            }
        }
        bullets = newBuletsArr;
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------


    private Brick isBulletCollision(Bullet bullet){
        for(Brick brick : bricks.getBricksClasses()){
            int bulletX = bullet.getPosX();
            int bulletY = bullet.getPosY();
            int brickX = brick.getPosX();
            int brickY = brick.getPosY();
            if(((((bulletX>= brickX) && (bulletX <= brickX + 50)) || ((bulletX + 10>= brickX) && (bulletX + 10 <= brickX + 50)))) &&
                    (((bulletY >= brickY) && (bulletY <= brickY+50)) || ((bulletY+10 >= brickY)&&(bulletY+10 <= brickY + 50)))){
                if (brick == base){
                    isStart = false;
                    System.out.println(isStart);
                }
                return brick;
            }
        }
        return null;
    }



    public boolean[] isCsollision_tank() {
        boolean[] retCollisionArr = new boolean[]{false, false, false, false};
        for (Brick brick : bricks.getBricksClasses()) {
            //left
            if ((((player1.getPosY() + 1 >= brick.getPosY() + 1) && (player1.getPosY() + 1 <= brick.getPosY() + 49)) || ((player1.getPosY() + 49 >= brick.getPosY() + 1) && (player1.getPosY() + 49 <= brick.getPosY() + 49))) &&
                    ((player1.getPosX() <= brick.getPosX() + 50) && (player1.getPosX() >= brick.getPosX() + 50)))
                retCollisionArr[0] = true;
            //right
            if ((((player1.getPosY() + 1 >= brick.getPosY() + 1) && (player1.getPosY() + 1 <= brick.getPosY() + 49)) || ((player1.getPosY() + 49 >= brick.getPosY() + 1) && (player1.getPosY() + 49 <= brick.getPosY() + 49))) &&
                    ((player1.getPosX() + 50 <= brick.getPosX()) && (player1.getPosX() + 50 >= brick.getPosX())))
                retCollisionArr[1] = true;
            //forward
            if ((((player1.getPosX() + 1 >= brick.getPosX() + 1) && (player1.getPosX() + 1 <= brick.getPosX() + 49)) || ((player1.getPosX() + 49 >= brick.getPosX() + 1) && (player1.getPosX() + 49 <= brick.getPosX() + 49))) &&
                    ((player1.getPosY() <= brick.getPosY() + 49) && (player1.getPosY() >= brick.getPosY() + 49)))
                retCollisionArr[2] = true;
            //backward
            if ((((player1.getPosX() + 1 >= brick.getPosX() + 1) && (player1.getPosX() + 1 <= brick.getPosX() + 49)) || ((player1.getPosX() + 49 >= brick.getPosX() + 1) && (player1.getPosX() + 49 <= brick.getPosX() + 49))) &&
                    ((player1.getPosY() + 49 <= brick.getPosY()) && (player1.getPosY() + 49 >= brick.getPosY() - 1)))
                retCollisionArr[3] = true;
        }
        player1.setIsColision(retCollisionArr);
        return retCollisionArr;
    }

    public Tanks getPlayer1() {
        return player1;
    }
    public ArrayList<Bullet> getBullets(){
        return bullets;
    }


//    public boolean isCollisionForward(){
//        for(Brick brick : bricks.getBricksClasses()){
//            if ((((player1.getPosX() >= brick.getPosX()-1) && (player1.getPosX() <= brick.getPosX()+51)) || ((player1.getPosX() + 50 >= brick.getPosX()-1) && (player1.getPosX() + 50 <= brick.getPosX()+51))) &&
//                    ((player1.getPosY() <= brick.getPosY()+51) && (player1.getPosY() >= brick.getPosY()+ 49)))return true;//((player1.getPosY() <= brick.getPosY()+51) && (player1.getPosY() >= brick.getPosY()))
//        }
//        return false;
//    }
//
//    public boolean isCollisionBackward(){
//        for(Brick brick : bricks.getBricksClasses()){
//            if ((((player1.getPosX() >= brick.getPosX()-1) && (player1.getPosX() <= brick.getPosX()+51)) || ((player1.getPosX() + 50 >= brick.getPosX()-1) && (player1.getPosX() + 50 <= brick.getPosX()+51))) &&
//                    ((player1.getPosY()+50 <= brick.getPosY()) && (player1.getPosY() +50 >= brick.getPosY() - 1)))return true;//((player1.getPosY() <= brick.getPosY()+51) && (player1.getPosY() >= brick.getPosY()))
//        }
//        return false;
//    }

//    public boolean isCollisionLeft() {
//        for (Brick brick : bricks.getBricksClasses()) {
//            if ((((player1.getPosY() >= brick.getPosY()) && (player1.getPosY() <= brick.getPosY() + 50)) || ((player1.getPosY() + 50 >= brick.getPosY()) && (player1.getPosY() + 50 <= brick.getPosY() + 50))) &&
//                    ((player1.getPosX() <= brick.getPosX() + 51) && (player1.getPosX() >= brick.getPosX() + 49)))
//                return true;//((player1.getPosY() <= brick.getPosY()+51) && (player1.getPosY() >= brick.getPosY()))
//        }
//        return false;
//    }
//
//    public boolean isCollisionRight() {
//        for (Brick brick : bricks.getBricksClasses()) {
//            if ((((player1.getPosY() >= brick.getPosY()) && (player1.getPosY() <= brick.getPosY() + 50)) || ((player1.getPosY() + 50 >= brick.getPosY()) && (player1.getPosY() + 50 <= brick.getPosY() + 50))) &&
//                    ((player1.getPosX()+ 50 <= brick.getPosX()+1) && (player1.getPosX() + 50 >= brick.getPosX() -1)))
//                return true;//((player1.getPosY() <= brick.getPosY()+51) && (player1.getPosY() >= brick.getPosY()))
//        }
//        return false;
//    }




}
