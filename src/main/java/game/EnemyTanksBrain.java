package game;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class EnemyTanksBrain {
    private ArrayList<Tanks> EnemyTanks = new ArrayList<Tanks>();
    private GraphicsContext gc;
    private final int  DMG = 5;
    private final int MS = 1;
    private final int HP = 10;
    private Model model;

    public EnemyTanksBrain(ArrayList<int[]> enemyTanksStartData, GraphicsContext gc, Model model) {
        this.gc = gc;
        this.model = model;
        for (int[] enemyPos: enemyTanksStartData){
            EnemyTanks.add(new Tanks(HP, MS, DMG, gc,  enemyPos[0], enemyPos[1], enemyPos[2], this.model));
        }
    }



}
