package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Model {
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Tanks[]> tanks;
    boolean isStart;
    private Bricks bricks;

    private GraphicsContext gc;

    public Model(boolean isStart, GraphicsContext gc) {
        this.bricks = new Bricks("", gc);
        this.isStart = isStart;
    }

    public void drawWalls(){
        bricks.draw();
    }
    public void update(){
        ArrayList<Bullet> newBuletsArr = new ArrayList<Bullet>(
        );
        for(Bullet bullet : bullets){
            if(bullet.isInScreen()) {
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

    private void calculateCollision(){

    }

}
