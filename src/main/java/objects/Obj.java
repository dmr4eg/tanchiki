package objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Obj {
    public boolean hasCollided;
    protected int MS;
    protected int HP;
    protected int PosX;
    protected int PosY;
    protected String type;
    protected String picture;
    protected GraphicsContext gc;

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getType() {
        return type;
    }

    public boolean isDead(int damageTaken){
        HP = HP - damageTaken;
        if (HP <= 0)HP = 0;
        return HP <= 0;
    }

    public int getMS() {
        return MS;
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
