package game;


import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tanks extends Nonstatic{

    private GraphicsContext gc;
    private int Orientation;
    private Image imageRight = new Image("player1_tank_right.png");
    private Image imageLeft = new Image("player1_tank_left.png");
    private Image imageUp = new Image("player1_tank_up.png");
    private Image imageDown = new Image("player1_tank_down.png");


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
        if (x==1){
            PosX += MS;
            Orientation = 1;
            gc.drawImage( imageRight, PosX, PosY);
        }
        if (y==1){
            PosY += MS;
            Orientation = 2;
            gc.drawImage( imageDown, PosX, PosY);
        }
        if (y == -1){
            PosY -= MS;
            Orientation = 3;
            gc.drawImage( imageUp, PosX, PosY);
        }
        if (x == -1){
            PosX -= MS;
            Orientation = 4;
            gc.drawImage( imageLeft, PosX, PosY);
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

    public void moveLeft() {
        updatePos(-1, 0);
    }

    public void moveRight() {
        updatePos(1, 0);
    }

    public void moveUp() {
        updatePos(0, -1);
    }

    public void moveDown() {
        updatePos(0, 1);
    }
}
