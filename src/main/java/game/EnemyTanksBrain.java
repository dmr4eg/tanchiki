package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class EnemyTanksBrain {
    private final int  DMG = 5;
    private final int MS = 1;
    private final int HP = 10;
    private Brick base;
    private Tanks player;

    public EnemyTanksBrain(Brick base, Tanks player) {
        this.base = base;
        this.player = player;
    }

    //-------------------------------------------------------------------------------------------------------------------
    //Object project
    private boolean[] check_collisionObj(Tanks tank, ArrayList<Obj> allObjects){
        boolean[] retCollisionArr = new boolean[]{false, false, false, false};
        for (Obj brick : allObjects) {
            //brick.draw();
            if (brick != tank) {
                //left
                if ((((tank.getPosY() + 1 >= brick.getPosY() + 1) && (tank.getPosY() + 1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY() + 1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                        ((tank.getPosX() <= brick.getPosX() + 50) && (tank.getPosX() >= brick.getPosX() + 50))) {
                    retCollisionArr[0] = true;
                    if (brick.getType().equals("brick")  && tank.getOrientation() == 1)
                        tank.fire();
                }
                //right
                if ((((tank.getPosY() + 1 >= brick.getPosY() + 1) && (tank.getPosY() + 1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY() + 1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                        ((tank.getPosX() + 50 <= brick.getPosX()) && (tank.getPosX() + 50 >= brick.getPosX()))) {
                    retCollisionArr[1] = true;
                    if (brick.getType().equals("brick")  && tank.getOrientation() == 2)
                        tank.fire();
                }
                //forward
                if ((((tank.getPosX() + 1 >= brick.getPosX() + 1) && (tank.getPosX() + 1 <= brick.getPosX() + 49)) || ((tank.getPosX() + 49 >= brick.getPosX() + 1) && (tank.getPosX() + 49 <= brick.getPosX() + 49))) &&
                        ((tank.getPosY() <= brick.getPosY() + 49) && (tank.getPosY() >= brick.getPosY() + 49))) {
                    retCollisionArr[2] = true;
                    if (brick.getType().equals("brick" ) && tank.getOrientation() == 3)
                        tank.fire();
                }
                //backward
                if ((((tank.getPosX() + 1 >= brick.getPosX() + 1) && (tank.getPosX() + 1 <= brick.getPosX() + 49)) || ((tank.getPosX() + 49 >= brick.getPosX() + 1) && (tank.getPosX() + 49 <= brick.getPosX() + 49))) &&
                        ((tank.getPosY() + 49 <= brick.getPosY()) && (tank.getPosY() + 49 >= brick.getPosY() - 1))) {
                    retCollisionArr[3] = true;
                    if (brick.getType().equals("brick") && tank.getOrientation() == 4)
                        tank.fire();
                }
            }
        }
        tank.setIsColision(retCollisionArr);
        return retCollisionArr;
    }

    public void computing_to_baseObj(Tanks tank, ArrayList<Obj> objects){
        check_collisionObj(tank,objects);
        fire_in_player_or_move_to_base(tank);
    }

    //-------------------------------------------------------------------------------------------------------------------


    private boolean[] check_collision(Tanks tank, Bricks bricks){
        boolean[] retCollisionArr = new boolean[]{false, false, false, false};
        for (Brick brick : bricks.getBricksClasses()) {
            brick.draw();
            //left
            if ((((tank.getPosY() + 1 >= brick.getPosY() + 1) && (tank.getPosY() + 1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY() + 1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                    ((tank.getPosX() <= brick.getPosX() + 50) && (tank.getPosX() >= brick.getPosX() + 50))){
                retCollisionArr[0] = true;
                if(brick.isBreakable() && tank.getOrientation()==1)tank.fire();
            }
            //right
            if ((((tank.getPosY() + 1 >= brick.getPosY() + 1) && (tank.getPosY() + 1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY() + 1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                    ((tank.getPosX() + 50 <= brick.getPosX()) && (tank.getPosX() + 50 >= brick.getPosX()))){
                retCollisionArr[1] = true;
                if(brick.isBreakable() && tank.getOrientation()==2)tank.fire();
            }
            //forward
            if ((((tank.getPosX() + 1 >= brick.getPosX() + 1) && (tank.getPosX() + 1 <= brick.getPosX() + 49)) || ((tank.getPosX() + 49 >= brick.getPosX() + 1) && (tank.getPosX() + 49 <= brick.getPosX() + 49))) &&
                    ((tank.getPosY() <= brick.getPosY() + 49) && (tank.getPosY() >= brick.getPosY() + 49))){
                retCollisionArr[2] = true;
                if(brick.isBreakable() && tank.getOrientation()==3)tank.fire();
            }
            //backward
            if ((((tank.getPosX() + 1 >= brick.getPosX() + 1) && (tank.getPosX() + 1 <= brick.getPosX() + 49)) || ((tank.getPosX() + 49 >= brick.getPosX() + 1) && (tank.getPosX() + 49 <= brick.getPosX() + 49))) &&
                    ((tank.getPosY() + 49 <= brick.getPosY()) && (tank.getPosY() + 49 >= brick.getPosY() - 1))){
                retCollisionArr[3] = true;
                if(brick.isBreakable() && tank.getOrientation()==4)tank.fire();
            }
        }
        tank.setIsColision(retCollisionArr);
        return retCollisionArr;
    }

    public void move(Tanks tank){
        tank.update();
//        switch (tank.getOrientation()){
//            case 1:
//                tank.moveLeft();
//                break;
//            case 2:
//                tank.moveRight();
//                break;
//            case 3:
//                tank.moveUp();
//                break;
//            case 4:
//                tank.moveDown();
//                break;
//        }
    }
    public void computing_to_base(Tanks tank, Bricks bricks){
        check_collision(tank,bricks);
        fire_in_player_or_move_to_base(tank);
    }

    private void fire_in_player_or_move_to_base(Tanks tank) {
        if (tank.getType() != "player") {
            if (tank.getPosX()+25 >= player.getPosX() && tank.getPosX() + 25 <= player.getPosX() + 50) {
                if (player.getPosY() < tank.getPosY()) {
                    tank.setOrientation(3);
                    tank.fire();
                }
                if (player.getPosY() > tank.getPosY()) {
                    tank.setOrientation(4);
                    tank.fire();
                }
            } else if (tank.getPosY() +25 >= player.getPosY() && tank.getPosY() + 25 <= player.getPosY() + 50) {

                if (player.getPosX() > tank.getPosX()) {
                    tank.setOrientation(2);
                    tank.fire();
                }
                if (player.getPosX() < tank.getPosX()) {
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
                if (player.getPosX() > tank.getPosX()) tank.setOrientation(1);
                if (player.getPosX() < tank.getPosX()) tank.setOrientation(2);
                if (player.getPosY() < tank.getPosY()) tank.setOrientation(3);
                if (player.getPosY() > tank.getPosY()) tank.setOrientation(4);
            }
        }
    }
}
