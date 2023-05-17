package net.packets;

import game.Tanks;
import game.TanksMp;
import net.GameClient;
import net.GameServer;

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
    }

    @Override
    public byte[] getData() {
        return (packetId+data).getBytes();
    }

}
