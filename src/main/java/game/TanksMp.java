package game;

import javafx.geometry.Orientation;
import javafx.scene.canvas.GraphicsContext;

import java.net.InetAddress;

public class TanksMp extends Tanks {
    public InetAddress ipAddress;
    public int port;
    public TanksMp(int HP, int MS, int DMG, String type, GraphicsContext gc, int Orientation, int PosX, int PosY, Model model, InetAddress ipAddress, int port) {
        super(HP, MS, DMG, type, gc, Orientation, PosX, PosY, model);
        this.ipAddress = ipAddress;
        this.port = port;
    }

//    public String parseToData(int isFire){
//        String message = "";
//        switch (getOrientation()){
//            case 1-> message += "00";
//            case 2-> message += "01";
//            case 3-> message += "10";
//            case 4-> message += "11";
//        }
//        message+="|";
//        switch (isFire){
//            case 1 -> message += "1";
//            case 0 -> message += "0";
//        }
//        message+="|";
//        message+=String.format("%3d", PosX);
//        message+="|";
//        message+=String.format("%3d", PosY);
//        return message;
//    }
//
//    public void parseData(String message){
//        String[] messageValues = message.split("\\|");
//        int orientation = 0;
//        switch(messageValues[0]){
//            case "00" -> orientation = 1;
//            case "01" -> orientation = 2;
//            case "10" -> orientation = 3;
//            case "11" -> orientation = 4;
//        }
//        setOrientation(orientation);
//        if(messageValues[1].equals("1"))fire();
//        setPos(Integer.parseInt(messageValues[2]),Integer.parseInt(messageValues[3]));
//    }
}
