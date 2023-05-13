package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends Obj{
    private int Health;

    private GraphicsContext gc;
    private boolean isBreakable = true;

    private boolean isBase = false;
    private Image basePic = new Image("base_block.png");

    private Image brickPic = new Image("brickdef.png");

    public boolean isBreakable() {
        return isBreakable;
    }

    public Brick(int PosX, int  PosY, int health, GraphicsContext gc, String type){
        super.PosX = PosX;
        super.PosY = PosY;
        this.Health = health;
        this.gc = gc;
        super.type = type;
    }

    public Brick(int PosX, int  PosY, int health, GraphicsContext gc, String type, boolean isBase){
        this.PosX = PosX;
        this.PosY = PosY;
        this.Health = health;
        this.gc = gc;
        this.isBase = isBase;
        super.type = type;
    }
    @Override
    public void draw(){
        if(isBase){
            gc.drawImage(basePic, getPosX(), getPosY());
        }
        else {gc.drawImage(brickPic, getPosX(), getPosY());}
    }

    public int getPosX(){
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }
}
