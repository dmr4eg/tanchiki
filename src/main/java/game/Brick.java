package game;

public class Brick {
    private int PosX;
    private int PosY;
    private int Health;

    public Brick(int PosX,int  PosY, int health){
            this.PosX = PosX;
            this.PosY = PosY;
            this.Health = health;
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
