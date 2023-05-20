package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends Obj{
    private boolean isBreakable = true;

    private boolean isBase = false;
    private Image img;

    public boolean isBreakable() {
        return isBreakable;
    }

    public Brick(int PosX, int  PosY, int HP, GraphicsContext gc, String type){
        super(0, HP, PosX, PosY, type,  gc);
        if(type.equals("brick"))img = new Image("brickdef.png");
        else if(type.equals("armoredbrick")) img = new Image("solidbrick.png");
        else img = new Image("base_block.png");
    }

    public Brick(int PosX, int  PosY, int HP, GraphicsContext gc, String type, boolean isBase){
        super(0, HP, PosX, PosY, type,  gc);
        this.isBase = isBase;
        img = new Image("base_block.png");
    }
    @Override
    public void draw(){
        super.gc.drawImage(img, getPosX(), getPosY());
    }

    public int getPosX(){
        return super.PosX;
    }

    public int getPosY() {
        return super.PosY;
    }
}
