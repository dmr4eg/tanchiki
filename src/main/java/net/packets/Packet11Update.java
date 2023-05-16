package net.packets;

import game.Tanks;
import game.TanksMp;
import net.GameClient;
import net.GameServer;

public class Packet11Update extends Packet{
    private String data;
    private TanksMp player;
    public Packet11Update( TanksMp player) {
        super(11);
        this.player = player;
    }

    @Override
    public void writeData(GameClient client) {
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return (packetId+data).getBytes();
    }

}
