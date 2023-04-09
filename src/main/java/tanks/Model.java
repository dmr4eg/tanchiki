package tanks;

import java.util.ArrayList;

public class Model {
    private ArrayList<Bullet> bulets;
    private ArrayList<Tanks[]> tanks;
    boolean isStart;

    public Model(ArrayList<Bullet> bulets, ArrayList<Tanks[]> tanks, boolean isStart) {
        this.bulets = bulets;
        this.tanks = tanks;
        this.isStart = isStart;
    }

    public void update(){
    }

    public void addBullet(Bullet bulet){
        bulets.add(bulet);
    }


}
