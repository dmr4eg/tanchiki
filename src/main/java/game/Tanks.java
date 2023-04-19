package game;


import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tanks extends Nonstatic{

    private GraphicsContext gc;
    private int Orientation;
    private Image imageRight = new Image("p1right.png");
    private Image imageLeft = new Image("p1left.png");
    private Image imageUp = new Image("p1up.png");
    private Image imageDown = new Image("p1down.png");

    private Model model;


    public Tanks(int HP, int MS, int DMG, GraphicsContext gc, int Orientation, int PosX, int PosY, Model model) {
        super.HP = HP;
        super.DMG = DMG;
        super.MS = MS;
        this.gc = gc;
        this.Orientation = Orientation;
        this.PosY = PosY;
        this.PosX = PosX;
        this.model = model;
    }

    private void updatePos(){
        if (Orientation != 0){
            if (Orientation == 2){
                PosX += MS;
            }
            if (Orientation == 4){
                PosY += MS;
            }
            if (Orientation == 3){
                PosY -= MS;
            }
            if (Orientation == 1){
                PosX -= MS;
        }
    }
    }
    private void draw(){
        switch (Orientation){
            case 1:
                gc.drawImage(imageLeft, PosX, PosY);
                break;

            case 2:
                gc.drawImage(imageRight, PosX, PosY);
                break;

            case 3:
                gc.drawImage(imageUp, PosX, PosY);
                break;
            case 4:
                gc.drawImage(imageDown, PosX, PosY);
                break;
        }
    }
    private void updateHP(int DMG){
        HP -= DMG;
    }

    public void update(int DMG){
        updateHP(DMG);
        draw();
    }

    private int getPosX(){
        return PosX;
    }

    private int getPosY(){
        return PosY;
    }

    public void fire(){
        Bullet bullet = new Bullet(DMG, getPosX(), getPosY(), Orientation, gc);
        model.addBullet(bullet);
    }

    public void moveLeft() {
        Orientation = 1;
        updatePos();
    }

    public void moveRight() {
        Orientation = 2;
        updatePos();

    }

    public void moveUp() {
        Orientation = 3;
        updatePos();

    }

    public void moveDown() {
        Orientation = 4;
        updatePos();
    }
}
