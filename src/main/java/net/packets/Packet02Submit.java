package net.packets;

import javafx.scene.paint.Stop;
import net.GameClient;
import net.GameServer;

public class Packet02Submit extends Packet{
    byte[] data;
    public Packet02Submit(int packetId) {
        super(02);
        this.data = String.valueOf(packetId).getBytes();
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(data);
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return data;
    }
}
