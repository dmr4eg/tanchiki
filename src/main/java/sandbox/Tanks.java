package sandbox;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tanks extends Nonstatic {

    private Group root;
    private int Orientation;


    public Tanks(int HP,int MS,int DMG, Group root, int Orientation, int PosX, int PosY) {
        super.HP = HP;
        super.DMG = DMG;
        super.MS = MS;
        this.root = root;
        this.Orientation = Orientation;
        this.PosY = PosY;
        this.PosX = PosX;
        Rectangle brick = new Rectangle(PosX, PosY, 16, 16);
        brick.setFill(Color.BLUE);
        this.root.getChildren().add(brick);
    }

    private void updatePos(int x,int  y){
        if (x==1){
            PosX += MS;
            Orientation = 1;
        }
        if (y==1){
            PosY += MS;
            Orientation = 2;
        }
        if (y == -1){
            PosY -= MS;
            Orientation = 3;
        }
        if (x == -1){
            PosX -= MS;
            Orientation = 4;
        }
    }

    private void updateHP(int DMG){
        HP -= DMG;
    }

    public void update(int x,int y, int DMG){
        updatePos(x,y);
        updateHP(DMG);
    }

    private int getPosX(){
        return PosX;
    }

    private int getPosY(){
        return PosY;
    }

    private void Fire(){
        Bullet bullet = new Bullet(DMG, getPosX(), getPosY(), Orientation);
//        model.addBullet(bullet);
    }
}
