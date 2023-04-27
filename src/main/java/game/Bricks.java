package game;

import java.io.*;
import java.util.ArrayList;

public class Bricks {
    private ArrayList<Integer> bricksPosX;
    private ArrayList<Integer> bricksPosY;


    public Bricks (String filename){
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("soubor.txt"), "UTF8"));
            out.write(System.lineSeparator());
            out.close();
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }}

}
