package net;

import net.packets.Packet;
import net.packets.Packet00Login;
import net.packets.Packet11Update;
import structure.modules.LevelContainer;
import structure.Model;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class GameServer extends Thread{
    private DatagramSocket socket;
    private Model model;
    private LevelContainer levelContainer;
    private ArrayList<TanksMp> connectedPlayers = new ArrayList<TanksMp>();

    public GameServer(Model model , LevelContainer levelContainer){
        this.levelContainer = levelContainer;
        this.model = model;
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //sendData(packet.getData(), packet.getAddress(), packet.getPort());
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());


//            String message = new String(packet.getData());
//            System.out.println("CLIENT["+packet.getAddress()+": "+packet.getPort()+"] > " +message);
//            if(message.trim().equalsIgnoreCase("ping"));{
//                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
//
//            }
        }
    }

    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
        Packet11Update packet11Update = new Packet11Update();
        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:
                Packet00Login packet = new Packet00Login(data);
                System.out.println("["+ address.getHostAddress() + ":" + port+ "] " + packet.getUsername()
                        + " has connected");

                TanksMp player1 = (TanksMp) levelContainer.players.get(0);
                if (connectedPlayers.isEmpty()){
                    connectedPlayers.add(player1);
                    byte[] messageToPlayer1 = ("02"+packet11Update.parseToData(player1) + "$"+ " ").getBytes();
                    sendData(messageToPlayer1, address, port);
//                    model.addToAllObjects(player1);
//                    model.setPlayer1(player1);
                }else{
                    TanksMp player2 = (TanksMp) levelContainer.players.get(1);
                    connectedPlayers.add(player2);
                    byte[] messageToPlayer2 = ("02"+packet11Update.parseToData( player2) + "$" + packet11Update.parseToData( connectedPlayers.get(0))).getBytes();
                    sendData(messageToPlayer2, address, port);
//                    model.addToAllObjects(player2);
//                    model.setPlayer2(player2);
                }
                break;
            case DISCONNECT:
                break;

            case UPDATE:
                message = message.substring(2);
                //System.out.println("UPDATE: " + message);
                for (TanksMp player: connectedPlayers){
                    System.out.println();
                    if(port == player.port)continue;
//                        packet11Update.parseData(message, player);

                    else sendData(("11" + message).getBytes(), player.ipAddress, player.port);
                }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendDataToAllClients(byte[] data) {
        for (TanksMp player: connectedPlayers)
            sendData(data, player.ipAddress, player.port);
    }
}
