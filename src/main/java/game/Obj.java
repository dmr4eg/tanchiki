package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Obj {
    protected int MS;
    protected int HP;
    protected int PosX;
    protected int PosY;

    public String getType() {
        return type;
    }

    protected String type;
    protected String picture;
    protected GraphicsContext gc;

    public int getPosX() {
        return PosX;
    }
    public int getPosY() {
        return PosY;
    }
    public void draw(){
        gc.drawImage(new Image(picture), PosX, PosY);
    }
}
