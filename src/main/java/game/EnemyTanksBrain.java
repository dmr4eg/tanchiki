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

    private boolean[] check_collision(Tanks tank, Brick brick){
        boolean[] retCollisionArr = new boolean [] {false, false, false, false};
            //left
            if ((((tank.getPosY()+1 >= brick.getPosY()+1) && (tank.getPosY()+1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY()+1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                    ((tank.getPosX() <= brick.getPosX() + 50) && (tank.getPosX() >= brick.getPosX() + 50))) retCollisionArr[0] = true;
            //right
            if ((((tank.getPosY()+1 >= brick.getPosY()+1) && (tank.getPosY()+1 <= brick.getPosY() + 49)) || ((tank.getPosY() + 49 >= brick.getPosY()+1) && (tank.getPosY() + 49 <= brick.getPosY() + 49))) &&
                    ((tank.getPosX()+ 50 <= brick.getPosX()) && (tank.getPosX() + 50 >= brick.getPosX()))) retCollisionArr[1] = true;
            //forward
            if ((((tank.getPosX()+1 >= brick.getPosX()+1) && (tank.getPosX()+1 <= brick.getPosX()+49)) || ((tank.getPosX() + 49 >= brick.getPosX()+1) && (tank.getPosX() + 49 <= brick.getPosX()+49))) &&
                    ((tank.getPosY() <= brick.getPosY()+49) && (tank.getPosY() >= brick.getPosY()+ 49)))retCollisionArr[2] = true;
            //backward
            if ((((tank.getPosX()+1 >= brick.getPosX()+1) && (tank.getPosX()+1 <= brick.getPosX()+49)) || ((tank.getPosX() + 49 >= brick.getPosX()+1) && (tank.getPosX() + 49 <= brick.getPosX()+49))) &&
                    ((tank.getPosY()+49 <= brick.getPosY()) && (tank.getPosY() + 49 >= brick.getPosY() - 1))) retCollisionArr[3] = true;
            return retCollisionArr;
    }

    private void ifCollision_fire(Tanks tank, Brick brick){
        if (check_collision(tank, brick)[0] && (tank.getOrientation() == 1) && brick.isBreakable()) {
            tank.fire();
        }

        if (check_collision(tank, brick)[1] && (tank.getOrientation() == 2) && brick.isBreakable()) {
            tank.fire();
        }

        if (check_collision(tank, brick)[2] && (tank.getOrientation() == 3) && brick.isBreakable()) {
            tank.fire();
        }

        if (check_collision(tank, brick)[3] && (tank.getOrientation() == 4) && brick.isBreakable()) {
            tank.fire();
        }

    }

    public void move(Tanks tank){
        switch (tank.getOrientation()){
            case 1:
                tank.moveLeft();
                break;
            case 2:
                tank.moveRight();
                break;
            case 3:
                tank.moveUp();
                break;
            case 4:
                tank.moveDown();
                break;
        }
    }
    public void computing_to_base(Tanks tank, Brick brick){
        ifCollision_fire(tank, brick);
        fire_in_player_or_move_to_base(tank);
    }

    private void fire_in_player_or_move_to_base(Tanks tank){
        tank.draw();
        if(tank.getPosX() == base.getPosX() && tank.getPosX() +50 == base.getPosX() + 50){
            if (base.getPosY() < tank.getPosY()) {
                tank.setOrientation(3);
                tank.draw();
                tank.fire();
            }
            if (base.getPosY() > tank.getPosY()){
                tank.setOrientation(4);
                tank.draw();
                tank.fire();
            }
        }else if (tank.getPosY() == base.getPosY() && tank.getPosY() +50 == base.getPosY() + 50) {

            if (base.getPosX() > tank.getPosX()) {
                tank.setOrientation(2);
                tank.draw();
                tank.fire();
            }
            if (base.getPosX() < tank.getPosX()){
                tank.setOrientation(1);
                tank.draw();
                tank.fire();
            }
        }
        else if((tank.getPosX() >= player.getPosX() && tank.getPosX() <= player.getPosX() + 50)||(tank.getPosX()+50 >= player.getPosX() && tank.getPosX()+50 <= player.getPosX() + 50)) {
            if (player.getPosY() < tank.getPosY()) {
                tank.setOrientation(3);
                tank.draw();
                tank.fire();
            }
            if (player.getPosY() > tank.getPosY()){
                tank.setOrientation(4);
                tank.draw();
                tank.fire();
            }
        } else if ((tank.getPosY() >= player.getPosY() && tank.getPosY() <= player.getPosY() + 50)||(tank.getPosY()+50 >= player.getPosY() && tank.getPosY()+50 <= player.getPosY() + 50)) {

            if (player.getPosX() > tank.getPosX()) {
                tank.setOrientation(1);
                tank.draw();
                tank.fire();
            }
            if (player.getPosX() < tank.getPosX()){
                tank.setOrientation(2);
                tank.draw();
                tank.fire();
            }
        }
        else{
            if (base.getPosX() > tank.getPosX()) tank.setOrientation(1);
            if (base.getPosX() < tank.getPosX()) tank.setOrientation(2);
            if (base.getPosY() < tank.getPosY()) tank.setOrientation(3);
            if (base.getPosY() > tank.getPosY()) tank.setOrientation(4);
        }
    }
}
