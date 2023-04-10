package game;


import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tanks extends Nonstatic{

    private GraphicsContext gc;
    private int Orientation;
    Image image = new Image("player1_tank_right.png");


    public Tanks(int HP, int MS, int DMG, GraphicsContext gc, int Orientation, int PosX, int PosY) {
        super.HP = HP;
        super.DMG = DMG;
        super.MS = MS;
        this.gc = gc;
        this.Orientation = Orientation;
        this.PosY = PosY;
        this.PosX = PosX;

    }

    private void updatePos(int x,int  y){

        gc.drawImage( image, PosX, PosY);
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
