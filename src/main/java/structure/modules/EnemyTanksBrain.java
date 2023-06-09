package structure.modules;

import objects.Brick;
import objects.Obj;
import objects.Tanks;

import java.util.ArrayList;

public class EnemyTanksBrain { private final int  DMG = 5;
    private final int MS = 1;
    private final int HP = 10;
    private Brick base;
    private Tanks player;
    private Tanks player2;
    private ArrayList<Obj> targets = new ArrayList<Obj>();

    public EnemyTanksBrain(Brick base, Tanks player) {
        this.base = base;
        this.player = player;
        targets.add(player);
        targets.add(base);
    }

    public EnemyTanksBrain(Brick base, Tanks player, Tanks player2) {
        this.base = base;
        this.player = player;
        this.player2 = player2;
        targets.add(player);
        targets.add(player2);
        targets.add(base);
    }

    public void check_collisionObj(Tanks tank, ArrayList<Obj> allObjects){
        boolean[] retCollisionArr = new boolean[]{false, false, false, false};
        for (Obj brick : allObjects) {
            int distY = Math.abs(tank.getPosY() - brick.getPosY());
            int distX = Math.abs(tank.getPosX() - brick.getPosX());
            if (!(distY < 150 && distX < 150))continue;
            if (brick != tank) {
                //left
                if ((((tank.getPosY() + 1 >= brick.getPosY() + 1) && (tank.getPosY() + 1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY() + 1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                        ((tank.getPosX() <= brick.getPosX() + 50) && (tank.getPosX() >= brick.getPosX() + 40))
            ||(tank.getPosX() < 0)) {
                    retCollisionArr[0] = true;
                    if (brick.getType().equals("brick")  && tank.getOrientation() == 1)
                        tank.fire();
                }
                //right
                if ((((tank.getPosY() + 1 >= brick.getPosY() + 1) && (tank.getPosY() + 1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY() + 1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                        ((tank.getPosX() + 50 <= brick.getPosX()+10) && (tank.getPosX() + 50 >= brick.getPosX())) || (tank.getPosX() > 550)
                ) {
                    retCollisionArr[1] = true;
                    if (brick.getType().equals("brick")  && tank.getOrientation() == 2)
                        tank.fire();
                }
                //forward
                if ((((tank.getPosX() + 1 >= brick.getPosX() + 1) && (tank.getPosX() + 1 <= brick.getPosX() + 49)) || ((tank.getPosX() + 49 >= brick.getPosX() + 1) && (tank.getPosX() + 49 <= brick.getPosX() + 49))) &&
                        ((tank.getPosY() <= brick.getPosY() + 50) && (tank.getPosY() >= brick.getPosY() + 40)) || (tank.getPosY() < 0)
                ) {
                    retCollisionArr[2] = true;
                    if (brick.getType().equals("brick")  && tank.getOrientation() == 3) tank.fire();
                }
                //backward
                if ((((tank.getPosX() + 1 >= brick.getPosX() + 1) && (tank.getPosX() + 1 <= brick.getPosX() + 49)) || ((tank.getPosX() + 49 >= brick.getPosX() + 1) && (tank.getPosX() + 49 <= brick.getPosX() + 49))) &&
                        ((tank.getPosY() + 50 <= brick.getPosY()+10) && (tank.getPosY() + 50 >= brick.getPosY() ))||(tank.getPosY() > 550)) {
                    retCollisionArr[3] = true;

                    if(!brick.getType().equals("tank")) {
                        if (brick.getType().equals("brick")  && tank.getOrientation() == 4) tank.fire();

                    }
                    }
                }
            }
            tank.setIsColision(retCollisionArr);
        }


    public void computing_to_baseObj(Tanks tank, ArrayList<Obj> objects){
        check_collisionObj(tank,objects);
        fire_in_player_or_move_to_base(tank);
    }

    public Obj getPrioritytarget(Tanks tank){
        ArrayList<Double> distance = new ArrayList<Double>();
        double min = 850;
        Obj retTarget = null;
        for(Obj target: targets){
            double lengthX = Math.abs(tank.getPosX() - target.getPosX());
            double lengthY = Math.abs(tank.getPosY() - target.getPosY());
            double currentLen = Math.sqrt(Math.pow(lengthX,2) + Math.pow(lengthY,2));
            distance.add(currentLen);
            if(min > currentLen) {
                retTarget = target;
                min = currentLen;
            }
        };
        return retTarget;
    }

    public void move(Tanks tank){
        tank.update();
    }

    public void fire_in_player_or_move_to_base(Tanks tank) {
        Obj target = getPrioritytarget(tank);
        if (!tank.getType().equals("player")) {
            if (tank.getPosX() == target.getPosX() && tank.getPosX() + 50 == target.getPosX() + 50){
                if (target.getPosY() < tank.getPosY()) {
                    tank.setOrientation(3);
                    tank.fire();
                }
                if (target.getPosY() > tank.getPosY()) {
                    tank.setOrientation(4);
                    tank.fire();
                }
            } else if (tank.getPosY()  == target.getPosY() && tank.getPosY() + 50 == target.getPosY() + 50) {

                if (target.getPosX() > tank.getPosX()) {
                    tank.setOrientation(2);
                    tank.fire();

                }
                if (target.getPosX() < tank.getPosX()) {
                    tank.setOrientation(1);
                    tank.fire();
                }
            } else if (tank.getPosX() == base.getPosX() && tank.getPosX() + 50 == base.getPosX() + 50) {
                if (base.getPosY() < tank.getPosY()) {
                    tank.setOrientation(3);
                    tank.fire();
                }
                if (base.getPosY() > tank.getPosY()) {
                    tank.setOrientation(4);
                    tank.fire();
                }
            } else if (tank.getPosY() == base.getPosY() && tank.getPosY() + 50 == base.getPosY() + 50) {

                if (base.getPosX() > tank.getPosX()) {
                    tank.setOrientation(2);
                    tank.fire();
                }
                if (base.getPosX() < tank.getPosX()) {
                    tank.setOrientation(1);
                    tank.fire();
                }

            } else {
                int distY = Math.abs(tank.getPosY() - target.getPosY());
                int distX = Math.abs(tank.getPosX() - target.getPosX());
                if (distY < 250 && distX < 250) {
                    if (target.getPosX() > tank.getPosX()) tank.setOrientation(1);
                    if (target.getPosX() < tank.getPosX()) tank.setOrientation(2);
                    if (target.getPosY() < tank.getPosY()) tank.setOrientation(3);
                    if (target.getPosY() > tank.getPosY()) tank.setOrientation(4);
                }
            }
        }
    }

    public void setRandomOrientation(Tanks tank){
        int random = (int) (Math.random() * 4);
        int orientation = tank.getOrientation();
        switch (orientation){
            case 4 -> tank.setOrientation(3);
            case 1 -> tank.setOrientation(2);
            case 2 -> tank.setOrientation(1);
            case 3 -> tank.setOrientation(4);
        }
    }

}

