package game;


import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tanks extends Nonstatic{

    private GraphicsContext gc;

    private boolean[] isColision = new boolean[] {false, false, false, false};

    private boolean isTankMove = true;

    public boolean isTankMove() {
        return isTankMove;
    }

    public void setTankIsMove(boolean isTankMove) {
        this.isTankMove = isTankMove;
    }
    public void setIsColision(boolean[] isColision) {
        this.isColision = isColision;
    }

    public boolean[] getIsColision() {
        return isColision;
    }

    private int fireCooldown = 0;

    public void setOrientation(int orientation) {
        Orientation = orientation;
    }

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
//        System.out.println(isColision[0]);
//        System.out.println(isColision[1]);
//        System.out.println(isColision[2]);
//        System.out.println(isColision[3]);
        if (isTankMove){
            switcher = !switcher;
            if (Orientation == 2 && !isColision[1] ){
                PosX += MS;
            }
            if (Orientation == 4 && !isColision[3]){
                PosY += MS;
            }
            if (Orientation == 3 && !isColision[2]){
                PosY -= MS;
            }
            if (Orientation == 1 && !isColision[0]){
                PosX -= MS;
            }
        }
    }

    public void draw(){
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
        updatePos();
        if (fireCooldown > 0 && fireCooldown <= 100)fireCooldown--;
        updateHP(DMG);
        draw();
    }

    public void fire(){
        if (fireCooldown == 0){
            Bullet bullet = new Bullet(DMG, PosX, PosY, Orientation, gc, MS);
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

    public int getOrientation() {
        return Orientation;
    }

    @Override
    public int getPosX() {
        return super.getPosX();
    }

    public int getPosY() {
        return super.getPosY();
    }
}
