package net;

import structure.Model;
import objects.Tanks;
import net.packets.Packet;
import net.packets.Packet11Update;

import java.io.IOException;
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GameClient extends Thread{

    private static boolean running = true;
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Model model;
    private static final Logger LOGGER = Logger.getLogger(GameClient.class.getName());

    public GameClient(Model model, String ipAddress){
        this.model = model;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
        while(running){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                LOGGER.severe("Error receiving packet: " + e.getMessage());
                throw new RuntimeException(e);
            }
            String message = new String(packet.getData());
            parsePacket(packet);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        socket.close();
        interrupt();
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try {
            socket.send(packet);
        } catch (IOException e) {
            LOGGER.severe("Error sending packet: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void parsePacket(DatagramPacket packet){
        String message = new String(packet.getData()).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0,2));
        Packet11Update packet11Update = new Packet11Update();
        switch (type){
            case SUBMIT :
                String[] allPlayerValues = message.substring(2).split("\\$");
                Tanks player1 = createPlayer(allPlayerValues[0]);
                model.setPlayer1(player1);
                model.addToAllObjects(player1);
                if (allPlayerValues.length>1){
                    Tanks player2 = createPlayer(allPlayerValues[1]);
                    model.setPlayer2(player2);
                    model.addToAllObjects(player2);
                    model.updateEnemyBrain(player2, player1);
                }
                sendData(("11"+packet11Update.parseToData(model.getPlayer1())).getBytes());
                break;

            case UPDATE:
                message = message.substring(2);
                if(model.getPlayer2() == null){
                    Tanks player2 = createPlayer(message);
                    model.setPlayer2(player2);
                    model.addToAllObjects(player2);
                    model.updateEnemyBrain(model.getPlayer1(), player2);
                }else {
                    packet11Update.parseData(message,model.getPlayer2());
                }
                String returnMess = packet11Update.parseToData(model.getPlayer1());
                sendData(("11"+returnMess).getBytes());
                break;
        }
    }

    private Tanks createPlayer(String message){
        String[] player1value = message.split("\\|");
        int orientation = 0;
        switch (player1value[0]){
            case "00" -> orientation = 1;
            case"01"-> orientation = 2;
            case "10" -> orientation = 3;
            case "11" -> orientation = 4;
        }
        int posX = Integer.parseInt(player1value[2]);
        int posY = Integer.parseInt(player1value[3]);
        return new Tanks(100, 1, 50, "player", model.getGc(),orientation, posX, posY, model);
    }

    public static void stopRunning(){
        running = false;
    }

}
