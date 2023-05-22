package net;

import net.packets.Packet;
import net.packets.Packet00Login;
import net.packets.Packet11Update;
import objects.Tanks;
import structure.modules.LevelContainer;
import structure.Model;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class GameServer extends Thread{
    private DatagramSocket socket;
    private Model model;
    private LevelContainer levelContainer;
    private ArrayList<TanksMp> connectedPlayers = new ArrayList<TanksMp>();
    private Packet11Update packet11Update;
    private static boolean running = true;
    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());


    public GameServer(Model model , LevelContainer levelContainer){
        this.levelContainer = levelContainer;
        this.model = model;
        packet11Update = new Packet11Update();
        try {
            this.socket = new DatagramSocket(1331);
        } catch (SocketException e) {
            LOGGER.severe("Error creating socket: " + e.getMessage());
            e.printStackTrace();
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
            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
        socket.close();
        interrupt();
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
                LOGGER.info("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has connected");
                System.out.println("["+ address.getHostAddress() + ":" + port+ "] " + packet.getUsername() + " has connected");
                if (connectedPlayers.isEmpty()){
                    Tanks p1 = levelContainer.players.get(0);
                    TanksMp player1 = new TanksMp(p1.getHP(), p1.getMS(),  p1.getDMG(), p1.getType(), model.getGc(), p1.getOrientation(), p1.getPosX(), p1.getPosY(), model, address, port);
                    connectedPlayers.add(player1);
                    byte[] messageToPlayer1 = ("02"+packet11Update.parseToData(player1) + "$"+ " ").getBytes();
                    sendData(messageToPlayer1, address, port);
                }else{
                    Tanks p2 = levelContainer.players.get(1);
                    TanksMp player2 = new TanksMp(p2.getHP(), p2.getMS(),  p2.getDMG(), p2.getType(), model.getGc(), p2.getOrientation(), p2.getPosX(), p2.getPosY(), model, address, port);
                    connectedPlayers.add(player2);
                    byte[] messageToPlayer2 = ("02"+packet11Update.parseToData( player2) + "$" + packet11Update.parseToData( connectedPlayers.get(0))).getBytes();
                    sendData(messageToPlayer2, address, port);
                }
                break;
            case DISCONNECT:
                break;

            case UPDATE:
                message = message.substring(2);
                for (TanksMp player: connectedPlayers){
                    LOGGER.info("Sending update to player at " + player.ipAddress.getHostAddress() + ":" + player.port);
                    System.out.println();
                    if(port == player.port)continue;

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

    public static void stopRunning(){
        running = false;
    }
}

