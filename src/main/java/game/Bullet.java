package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bullet {
    private int DMG;
    private int PosX;
    private int PosY;
    private GraphicsContext gc;
    private int Orientation;
    private Image image = new Image("bullet2.png");



    public Bullet(int DMG, int posX, int posY, int Orientation, GraphicsContext gc) {
        this.DMG = DMG;
        PosX = posX;
        PosY = posY+25;
        this.Orientation = Orientation;
        this.gc = gc;
    }


    public void update(){
        switch(Orientation){
            case 1:
                PosY--;
                break;
            case 2:
                PosY++;
                break;
            case 3:
                PosX--;
                break;
            case 4:
                PosX++;
                break;
            default:break;
        }

    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public void die(){

    }

}
