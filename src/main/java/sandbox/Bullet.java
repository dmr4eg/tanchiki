package sandbox;

public class Bullet {
    private int DMG;
    private int PosX;
    private int PosY;

    private int Orientation;

    public Bullet(int DMG, int posX, int posY, int Orientation) {
        this.DMG = DMG;
        PosX = posX;
        PosY = posY;
        this.Orientation = Orientation;
    }

    public void update(){
        switch(Orientation){
            case 1:
                PosX++;
                break;
            case 2:
                PosX--;
                break;
            case 3:
                PosY++;
                break;
            case 4:
                PosY--;
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
        //TODO
    }
}
