package game;

import java.io.*;
import java.util.ArrayList;

public class Bricks {
    private ArrayList<Brick> bricksClasses;
    private ArrayList<int[]> bricksData;


    public Bricks (String filename) {
        try (FileReader reader = new FileReader("sada.txt")) {
            // читаем посимвольно
            int c;
            while ((c = reader.read()) != -1) {

                System.out.print((char) c);
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }

        private void generate_brick(){
        for (int[] oneBrick: bricksData ) {
            bricksClasses.add(new Brick(oneBrick[0], oneBrick[1], oneBrick[2]));
        }
    }

    public ArrayList<Brick> getBricksClasses() {
        return bricksClasses;
    }
}
