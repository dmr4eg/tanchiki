package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends Obj{
    private boolean isBreakable = true;

    private boolean isBase = false;
    private Image basePic = new Image("base_block.png");

    private Image brickPic = new Image("brickdef.png");

    public boolean isBreakable() {
        return isBreakable;
    }

    public Brick(int PosX, int  PosY, int HP, GraphicsContext gc, String type){
        super(0, HP, PosX, PosY, type,  gc);
    }

    public Brick(int PosX, int  PosY, int HP, GraphicsContext gc, String type, boolean isBase){
        super(0, HP, PosX, PosY, type,  gc);
        this.isBase = isBase;
    }
    @Override
    public void draw(){
        if(isBase){
            super.gc.drawImage(basePic, getPosX(), getPosY());
        }
        else {
            super.gc.drawImage(brickPic, getPosX(), getPosY());
        }
    }

    public int getPosX(){
        return super.PosX;
    }

    public int getPosY() {
        return super.PosY;
    }
}
