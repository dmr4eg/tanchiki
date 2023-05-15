package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private final Server server;
    private final Socket socket;
    private PrintWriter out;
    private String name;

    public Connection(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // setup connection
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            // send first communication to clients: SUBMIT (i.e. request to submit nickname)
            sendToClient(Protocol.SUBMIT, "");
            boolean running = true;
            while (running) {
                // read message from client
                String msg = in.readLine();
                LOGGER.log(Level.INFO, "Server received: >>>{0}<<<", msg);
                if (msg != null) {
                    running = processIncomingMessage(msg);
                } else {
                    // end communication if received null
                    running = false;
                }
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error communicating with client {0}", ex.getMessage());
        } finally {
            quit();
        }
    }

    private boolean processIncomingMessage(String msg) {
        String[] tokens = msg.split("\\|"); // escape pipe in regexp
        Protocol actionCode = Protocol.valueOf(tokens[0]);
        String actionPayload = tokens.length > 1 ? tokens[1] : "";
        switch (actionCode) {
            // incoming message arrived, decide what to do
            case NAME:
                if (server.addConnection(this, actionPayload)) {
                    // name sent, add connection to the list maintained by server
                    name = actionPayload;
                    sendToClient(Protocol.ACCEPTED, "");
                    server.broadcast(name + " joined the conversation.");
                } else {
                    // connection with this name already there, reject
                    sendToClient(Protocol.REJECTED, "");
                }
                break;
            case BROADCAST:
                server.broadcast(actionPayload);
                break;
            case QUIT:
                server.broadcast(name + " left the conversation.");
                server.removeConnection(name);
                return false;
        }
        return true;
    }

    public void sendToClient(Protocol code, String payload) {
        String msg = code.toString() + '|' + payload;
        LOGGER.log(Level.INFO, "Sending >>>{0}<<< to {1}", new Object[]{msg, name});
        out.println(msg);
    }

    private void quit() {
        LOGGER.info("Quitting connection.");
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    public String getName() {
        return name;
    }
}
