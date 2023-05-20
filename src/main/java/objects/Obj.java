package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Obj {
    protected int HP = 0;
    protected static int MS = 0;
    public boolean hasCollided;
    protected int PosX;
    protected int PosY;
    protected String type;
    protected String picture;
    protected GraphicsContext gc;

    public String getType() {
        return type;
    }

    public boolean isDead(int damageTaken){
        HP = HP - damageTaken;
        return HP <= 0;
    }

    public Obj(int MS, int HP, int posX, int posY, String type, GraphicsContext gc) {
        this.MS = MS;
        this.HP = HP;
        PosX = posX;
        PosY = posY;
        this.type = type;
        this.gc = gc;
    }

    public Obj(int HP, int MS, int PosX, int PosY, String type){
    }
    public static int getMS() {
        return MS;
    }
    public int getHP() {
        return HP;
    }
    public int getPosX()
    {
        return PosX;
    }

    public int getPosY()
    {
        return PosY;
    }

    public void draw()
    {
        gc.drawImage(new Image(picture), PosX, PosY);
    }
}
