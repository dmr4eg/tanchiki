package game;


import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tanks extends Nonstatic{

    private GraphicsContext gc;

    private int fireCooldown = 0;
    private int Orientation;
    private Image imageRight = new Image("p1right.png");
    private Image imageRight2 = new Image("p1right2.png");
    private Image imageLeft = new Image("p1left.png");
    private Image imageLeft2 = new Image("p1left2.png");
    private Image imageUp = new Image("p1up.png");
    private Image imageUp2 = new Image("p1up2.png");
    private Image imageDown = new Image("p1down.png");
    private Image imageDown2 = new Image("p1down2.png");

    private boolean switcher = true;

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
                if (switcher){
                    gc.drawImage(imageLeft, PosX, PosY);
                }else {
                    gc.drawImage(imageLeft2, PosX, PosY);
                }
                break;

            case 2:
                if(switcher){
                    gc.drawImage(imageRight, PosX, PosY);
                }else {
                    gc.drawImage(imageRight2, PosX, PosY);
                }
                break;

            case 3:
                if(switcher){
                    gc.drawImage(imageUp, PosX, PosY);
                }else {
                    gc.drawImage(imageUp2, PosX, PosY);
                }
                break;
            case 4:
                if(switcher){
                    gc.drawImage(imageDown, PosX, PosY);
                }else {
                    gc.drawImage(imageDown2, PosX, PosY);
                }
                break;
        }
    }
    private void updateHP(int DMG){
        HP -= DMG;
    }

    public void update(int DMG){
        if (fireCooldown > 0 && fireCooldown <= 100)fireCooldown--;
        updateHP(DMG);
        draw();
    }

    @Override
    public int getPosX() {
        return super.getPosX();
    }

    public int getPosY() {
        return super.getPosY();
    }

    public void fire(){
        if (fireCooldown == 0){
            Bullet bullet = new Bullet(DMG, getPosX(), getPosY(), Orientation, gc, MS);
            model.addBullet(bullet);
            fireCooldown = 100;
        }
    }

    public void moveLeft() {
        Orientation = 1;
        updatePos();
        switcher = !switcher;
    }

    public void moveRight() {
        Orientation = 2;
        updatePos();
        switcher = !switcher;
    }

    public void moveUp() {
        Orientation = 3;
        updatePos();
        switcher = !switcher;
    }

    public void moveDown() {
        Orientation = 4;
        updatePos();
        switcher = !switcher;
    }
}
