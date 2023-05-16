package net;

import game.Controller;
import game.Model;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{

    private InetAddress ipAddress;
    private DatagramSocket socket;
    private Model model;

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
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String message = new String(packet.getData());
            System.out.println("SERVER["+packet.getAddress()+": "+packet.getPort()+"] > " +message.trim());
            if (message.trim().equalsIgnoreCase("pong"))sendData("ping".getBytes());
        }
    }

        public void sendData(byte[] data){
            DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
            try {
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

}
