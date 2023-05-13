package game;

import javafx.scene.Scene;

public class EventLis {
    private Scene scene;
    private Model model;
    private final int WIDTH;
    private final int HEIGHT;
    private final int TILE;
    public EventLis(Scene scene, Model model,int WIDTH, int HEIGHT, int TILE) {
        this.scene = scene;
        this.model = model;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.TILE = TILE;
    }

    public void eventLis(){
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    if ((!(model.getPlayer1().getPosX() <= 0))&& !model.getPlayer1().getIsColision()[0])model.getPlayer1().moveLeft();else model.setPlayer1Orientation(1);
                    break;
                case RIGHT:
                    if ((!(model.getPlayer1().getPosX() >= WIDTH-TILE)) && !model.getPlayer1().getIsColision()[1])model.getPlayer1().moveRight();else model.setPlayer1Orientation(2);
                    break;
                case UP:
                    if (!((model.getPlayer1().getPosY() <= 0)) && !model.getPlayer1().getIsColision()[2]) model.getPlayer1().moveUp();else model.setPlayer1Orientation(3);
                    break;
                case DOWN:
                    if ((!(model.getPlayer1().getPosY() >= HEIGHT- TILE)) && !model.getPlayer1().getIsColision()[3])model.getPlayer1().moveDown();else model.setPlayer1Orientation(4);
                    break;
                case E:
                    model.getPlayer1().fire();
            }    });}
}
