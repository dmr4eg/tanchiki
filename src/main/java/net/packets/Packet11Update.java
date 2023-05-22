package net.packets;

import objects.Tanks;
import net.TanksMp;
import net.GameClient;
import net.GameServer;
import structure.Model;

public class Packet11Update extends Packet{
    private String data;
    private TanksMp player;
    public Packet11Update() {
        super(11);
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    public String parseToData( Tanks player){
        String message = "";
        switch (player.getOrientation()){
            case 1-> message += "00";
            case 2-> message += "01";
            case 3-> message += "10";
            case 4-> message += "11";
        }
        message+="|";
        boolean isFire = player.isWasFire();
        int i;
        i = isFire? 1 : 0;
        switch (i){
            case 1 ->{
                message += "1";
                player.setWasFire(false);
            }
            case 0 -> message += "0";
        }
        message+="|";
        message+=String.valueOf(player.getPosX());
        message+="|";
        message+=String.valueOf(player.getPosY());
        message+="|";
        message+=String.valueOf(player.getHP());
        return message;
    }

    public void parseData(String message,Tanks player){
        String[] messageValues = message.split("\\|");
        int orientation = 0;
        switch(messageValues[0]){
            case "00" -> orientation = 1;
            case "01" -> orientation = 2;
            case "10" -> orientation = 3;
            case "11" -> orientation = 4;
        }
        player.setOrientation(orientation);
        if(messageValues[1].equals("1"))player.fire();
        player.setPos(Integer.parseInt(messageValues[2]),Integer.parseInt(messageValues[3]));
        player.setHP(Integer.parseInt(messageValues[4]));
    }


//    public void parseDataToOrientation(Model model, String data){
//        String[] messageValues = data.split("\\|");
//        int count = 5;
//        for (Tanks tank: model.getTanks()) {
//            if (!tank.getType().equals("player")) {
//                tank.setOrientation(Integer.parseInt(messageValues[count]));
//                count++;
//            }
//        }
//    }
//
//    public String parseOrientation(Model model){
//        String message = "";
//        for (Tanks tank: model.getTanks()){
//            if(!tank.getType().equals("player"))
//                message += "|" + (String.valueOf(tank.getOrientation()));
//        }
//        return message;
//    }

    @Override
    public byte[] getData() {
        return (packetId+data).getBytes();
    }

}
