package objects;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import structure.Model;

public class Tanks extends Obj {
    private final int DMG;
    private boolean[] isColision = new boolean[] {false, false, false, false};
    private boolean isTankMove = true;
    private int fireCooldown = 0;
    private boolean wasFire = false;
    private int Orientation;
    Image imageRight, imageRight2, imageLeft, imageLeft2, imageUp, imageUp2, imageDown, imageDown2;
    private boolean switcher = true;
    private Model model;

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

    public void setOrientation(int orientation) {
        Orientation = orientation;
    }

    public Tanks(int HP, int MS, int DMG,String type, GraphicsContext gc, int Orientation, int PosX, int PosY, Model model) {
        super(MS, HP, PosX, PosY, type,  gc);
        this.DMG = DMG;
        this.Orientation = Orientation;
        this.model = model;
        getTankType(type);
    }
    public int getDMG() {
        return DMG;
    }
    private void updatePos(){
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

    public void getTankType(String type) {
        switch (type) {
            case "player":
                imageRight = new Image("p1right.png");
                imageRight2 = new Image("p1right2.png");
                imageLeft = new Image("p1left.png");
                imageLeft2 = new Image("p1left2.png");
                imageUp = new Image("p1up.png");
                imageUp2 = new Image("p1up2.png");
                imageDown = new Image("p1down.png");
                imageDown2 = new Image("p1down2.png");
                break;
            case "player2":
                imageRight = new Image("p2right.png");
                imageRight2 = new Image("p2right2.png");
                imageLeft = new Image("p2left.png");
                imageLeft2 = new Image("p2left2.png");
                imageUp = new Image("p2up.png");
                imageUp2 = new Image("p2up2.png");
                imageDown = new Image("p2down.png");
                imageDown2 = new Image("p2down2.png");
                break;
            case "tank":
                imageRight = new Image("enright.png");
                imageRight2 = new Image("enright2.png");
                imageLeft = new Image("enleft.png");
                imageLeft2 = new Image("enleft2.png");
                imageUp = new Image("enup.png");
                imageUp2 = new Image("enup2.png");
                imageDown = new Image("endown.png");
                imageDown2 = new Image("endown2.png");
                break;
            default:
                throw new IllegalArgumentException("Invalid tank type: " + type);
        }
    }

    @Override
    public void draw() {
        switch (Orientation) {
            case 1:
                if (switcher) {
                    super.gc.drawImage(imageLeft, PosX, PosY);
                } else {
                    super.gc.drawImage(imageLeft2, PosX, PosY);
                }
                break;
            case 2:
                if (switcher) {
                    super.gc.drawImage(imageRight, PosX, PosY);
                } else {
                    super.gc.drawImage(imageRight2, PosX, PosY);
                }
                break;
            case 3:
                if(switcher){
                    super.gc.drawImage(imageUp, PosX, PosY);
                }else {
                    super.gc.drawImage(imageUp2, PosX, PosY);
                }
                break;
            case 4:
                if(switcher){
                    super.gc.drawImage(imageDown, PosX, PosY);
                }else {
                    super.gc.drawImage(imageDown2, PosX, PosY);
                }
                break;
        }
    }

    public void update(){
        updatePos();
        updateCooldownAndAnimation();
        draw();
    }

    public void fire(){
        if (fireCooldown == 0 ){
            System.out.println("fire");
            Bullet bullet = new Bullet(DMG, super.PosX, super.PosY, Orientation, super.gc, super.MS);
            model.addBullet(bullet);
            fireCooldown = 100;
            setWasFire(true);
        }
    }

    public void updateCooldownAndAnimation(){
        if (fireCooldown > 0 && fireCooldown <= 100)fireCooldown--;
    }

    public void setPos(int x, int y){
        super.PosX = x;
        super.PosY = y;
    }

    public int getOrientation() {
        return Orientation;
    }

    @Override
    public int getPosX() {
        return super.getPosX();
    }

    @Override
    public int getPosY() {
        return super.getPosY();
    }

    public boolean isWasFire() {
        return wasFire;
    }

    public void setWasFire(boolean wasFire) {
        this.wasFire = wasFire;
    }
}