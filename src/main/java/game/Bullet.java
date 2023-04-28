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
        switch (Orientation){
            case 1:
                PosX = posX;
                PosY = posY+ 20;
                break;
            case 2:
                PosX = posX + 50;
                PosY = posY + 20;
                break;
            case 3:
                PosX = posX + 20;
                PosY = posY;
                break;
            case 4:
                PosX = posX + 20;
                PosY = posY + 50;
                break;
        }

        this.Orientation = Orientation;
        this.gc = gc;
        this.ms = ms;
    }

    public boolean isInScreen(){
        return getPosX() >= -5 && getPosX() < 600 && getPosY() >= -5 && getPosY() <= 600;
    }

    private void draw(){
        switch (Orientation){
            case 1:
                gc.drawImage(new Image("bullet2.png"), PosX, PosY);
                break;
            case 2:
                gc.drawImage(new Image("bullet2.png"), PosX, PosY);
                break;
            case 3:
                gc.drawImage(new Image("bullet2.png"), PosX, PosY);
                break;
            case 4:
                gc.drawImage(new Image("bullet2.png"), PosX, PosY);
                break;
            default:
                break;
        }

    }

    public void update(){
        if(isInScreen()) {
            switch (Orientation) {
                case 1 -> PosX -= 2;
                case 2 -> PosX += 2;
                case 3 -> PosY -= 2;
                case 4 -> PosY += 2;
                default -> {
                }
            }
            draw();
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
