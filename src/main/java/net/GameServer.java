package net;

import game.Controller;
import game.Model;
import game.TanksMp;
import net.packets.Packet;
import net.packets.Packet00Login;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread{
    private DatagramSocket socket;
    private Model model;
    private List<TanksMp> connectedPlayers = new ArrayList<TanksMp>();

    public GameServer(Model model){
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
        switch (type){
            default:
            case INVALID:
                break;
            case LOGIN:
                Packet00Login packet = new Packet00Login(data);
                System.out.println("["+ address.getHostAddress() + ":" + port+ "] " + packet.getUsername()
                        + " has connected");
                if(packet.getUsername().equals("player1")) {
                    TanksMp player = new TanksMp(100, 1, 50, "player", model.getGc(), 3, 100, 100, model, address, port);
                    model.addToAllObjects(player);
                    model.setPlayer1(player);
                }
                if(packet.getUsername().equals("player2")) {
                    TanksMp player = new TanksMp(100, 1, 50, "player", model.getGc(), 3, 100, 100, model, address, port);
                    model.addToAllObjects(player);
                    model.setPlayer2(player);
                }
                break;
            case DISCONNECT:
                break;
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
