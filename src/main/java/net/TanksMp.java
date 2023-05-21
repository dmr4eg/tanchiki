package net;

import javafx.scene.canvas.GraphicsContext;
import objects.Tanks;
import structure.Model;

import java.net.InetAddress;

public class TanksMp extends Tanks {
    public InetAddress ipAddress;
    public int port;
    public TanksMp(int HP, int MS, int DMG, String type, GraphicsContext gc, int Orientation, int PosX, int PosY, Model model, InetAddress ipAddress, int port) {
        super(HP, MS, DMG, type, gc, Orientation, PosX, PosY, model);
        this.ipAddress = ipAddress;
        this.port = port;
    }
}
