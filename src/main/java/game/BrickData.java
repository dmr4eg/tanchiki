package game;

import java.util.ArrayList;

public class BrickData {
    private ArrayList<int[]> brickCoords;

    public BrickData(ArrayList<int[]> brickCoords) {
        this.brickCoords = brickCoords;
    }

    public ArrayList<int[]> getBrickCoords() {
        return brickCoords;
    }

    public void setBrickCoords(ArrayList<int[]> brickCoords) {
        this.brickCoords = brickCoords;
    }
}
