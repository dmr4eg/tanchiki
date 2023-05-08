package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick {
    private int PosX;
    private int PosY;
    private int Health;

    private GraphicsContext gc;
    private boolean isBreakable = true;

    private boolean isBase = false;

    public boolean isBreakable() {
        return isBreakable;
    }

    public Brick(int PosX, int  PosY, int health, GraphicsContext gc){
            this.PosX = PosX;
            this.PosY = PosY;
            this.Health = health;
            this.gc = gc;
    }

    public Brick(int PosX, int  PosY, int health, GraphicsContext gc, boolean isBase){
        this.PosX = PosX;
        this.PosY = PosY;
        this.Health = health;
        this.gc = gc;
        this.isBase = isBase;
    }

    public void draw(){
        if(isBase){
            gc.drawImage(new Image("base_block.png"), getPosX(), getPosY());
        }
        else {gc.drawImage(new Image("brickdef.png"), getPosX(), getPosY());}
    }

    public int getPosX(){
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public int getHealth() {
        return Health;
    }
}
