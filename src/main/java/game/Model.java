package game;

import java.util.ArrayList;

public class Model {
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Tanks[]> tanks;
    boolean isStart;

    public Model(boolean isStart) {
        this.isStart = isStart;
    }

    public void update(){
        for(Bullet bullet : bullets){
            bullet.update();
        }
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
