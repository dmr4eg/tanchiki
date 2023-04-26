package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bullet {
    private int DMG;
    private int PosX;
    private int PosY;
    private GraphicsContext gc;

    private int ms;
    private int Orientation;
    private Image image = new Image("bullet2.png");



    public Bullet(int DMG, int posX, int posY, int Orientation, GraphicsContext gc, int ms) {
        this.DMG = DMG;
        PosX = posX;
        PosY = posY;
        this.Orientation = Orientation;
        this.gc = gc;
        this.ms = ms;
    }

    private void draw(){
        switch (Orientation){
            case 1:
                gc.drawImage(new Image("bullet2.png"), PosX, PosY+20);
                break;
            case 2:
                gc.drawImage(new Image("bullet2.png"), PosX+45, PosY+20);
                break;
            case 3:
                gc.drawImage(new Image("bullet2.png"), PosX+20, PosY);
                break;
            case 4:
                gc.drawImage(new Image("bullet2.png"), PosX+20, PosY+45);
                break;
            default:
                break;
        }

    }

    public void update(){
        switch(Orientation){
            case 1:
                PosX-= 2;
                break;
            case 2:
                PosX+= 2;
                break;
            case 3:
                PosY-= 2;
                break;
            case 4:
                PosY+= 2;
                break;
            default:break;
        }
        draw();

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
