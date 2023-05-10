package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Model {
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private final ArrayList<Tanks> tanks = new ArrayList<Tanks>();
    private  ArrayList<ArrayList<Integer>> bricsCoords =  new ArrayList<ArrayList<Integer>>(); //[[],[],[]]
    private final Bricks bricks;
    private GraphicsContext gc;
    private final Tanks player1;
    private Tanks enemyTank;
    private final EnemyTanksBrain enemyBrain;
    private final Brick base;
    boolean isStart;
    public Model(boolean isStart, GraphicsContext gc) {
        this.bricks = new Bricks("level1.json", gc);
        this.gc = gc;
        base = bricks.getBase();
        player1 = new Tanks(100, 1, 20, gc, 1, 100, 100, this);
        this.enemyBrain = new EnemyTanksBrain(bricks.getBase(), player1);
        this.isStart = isStart;
        enemyTank = new Tanks(100, 1 , 1, gc, 1, 400, 400, this);
        tanks.add(enemyTank);
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

    }


    public void addBullet(Bullet bullet){
        bullets.add(bullet);
    }


    public ArrayList<Bullet> getBullets(){
        return bullets;
    }

    public boolean bullet;

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
