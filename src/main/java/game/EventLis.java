package game;

import javafx.scene.Scene;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;

public class EventLis {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private Scene scene;
    private Model model;

    public EventLis(Model model, Scene scene) {
        this.scene = scene;
        this.model = model;
    }

    public void setEventLis() {
        FileHandler fhc = null;
        try {
            fhc = new FileHandler("controllerLogs.txt");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open log file", e);
        }
        fhc.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(fhc);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A, LEFT -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(1);
                    LOGGER.log(Level.INFO, "Player1 move left");
                }
//                    if ((!(model.getPlayer1().getPosX() <= 0))&& !model.getPlayer1().getIsColision()[0]){
//                        model.getPlayer1().moveLeft();
//                    }
//                    else model.setPlayer1Orientation(1);
                case D, RIGHT -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(2);
                    LOGGER.log(Level.INFO, "Player1 move right");
                }
//                    if ((!(model.getPlayer1().getPosX() >= WIDTH-TILE)) && !model.getPlayer1().getIsColision()[1]){
//                        model.getPlayer1().moveRight();
//                    }else model.setPlayer1Orientation(2);
                case W, UP -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(3);
                    LOGGER.log(Level.INFO, "Player1 move up");
                }
//                    if (!((model.getPlayer1().getPosY() <= 0)) && !model.getPlayer1().getIsColision()[2]) {
//                        model.getPlayer1().moveUp();
//                    }else model.setPlayer1Orientation(3);
                case S, DOWN -> {
                    model.getPlayer1().setTankIsMove(true);
                    model.setPlayer1Orientation(4);
                    LOGGER.log(Level.INFO, "Player1 move down");
                }
//                    if ((!(model.getPlayer1().getPosY() >= HEIGHT- TILE)) && !model.getPlayer1().getIsColision()[3]){
//                        model.getPlayer1().moveDown();
//                    }else model.setPlayer1Orientation(4);
                case E -> {
                    model.getPlayer1().fire();
                    LOGGER.log(Level.INFO, "Player1 fire");
                }

                default -> model.getPlayer1().setTankIsMove(false);
            }
        });
    }
}
