package net;

import game.Controller;
import game.Model;
import game.Tanks;
import game.TanksMp;
import net.packets.Packet;
import net.packets.Packet11Update;

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
            parsePacket(packet);
            System.out.println("SERVER["+packet.getAddress()+": "+packet.getPort()+"] > " +message.trim());
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

        private void parsePacket(DatagramPacket packet){
            String message = new String(packet.getData()).trim();
            Packet.PacketTypes type = Packet.lookupPacket(message.substring(0,2));
            switch (type){
                case SUBMIT :
                    String[] allPlayerValues = message.substring(2).split("\\$");
                    String[] player1value = allPlayerValues[0].split("\\|");
                    String[] player2value = allPlayerValues[1].split("\\|");
                    TanksMp player1 = createPlayer(player1value, packet);
                    TanksMp player2 = createPlayer(player2value, packet);
                    model.setPlayer1(player1);
                    model.setPlayer2(player2);
                    sendData(("11"+model.getPlayer1().parseToData(0)).getBytes());
                    break;
                case UPDATE:
                    if(model.getPlayer2() == null);
                    message = message.substring(2);
                    model.getPlayer2().parseData(message);
                    sendData(("11"+model.getPlayer1().parseToData(0)).getBytes());
                    break;
            }
        }

        private TanksMp createPlayer(String[] message, DatagramPacket packet){
            int orientation = 0;
            switch (message[0]){
                case "00" -> orientation = 1;
                case"01"-> orientation = 2;
                case "10" -> orientation = 3;
                case "11" -> orientation = 4;
            }
            int posX = Integer.parseInt(message[2]);
            int posY = Integer.parseInt(message[3]);
            return new TanksMp(100, 1, 50, "player",model.getGc(),orientation, posX, posY, model, packet.getAddress(), packet.getPort());
        }


}
