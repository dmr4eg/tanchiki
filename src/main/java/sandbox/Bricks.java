package sandbox;

import com.google.gson.Gson;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.io.FileReader;
import java.util.Map;

public class Bricks {
    int[] bricksXPos;
    int[] bricksYPos;
    int[] solidBricksXPos;
    int[] solidBricksYPos;
    private int[] brickON;
    private Image breakBrickImage = new Image("break_brick.jpg");
    private Image solidBrickImage = new Image("solid_brick.jpg");

    public Bricks() {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("game/brrr.json")) {
            Map<String, int[]> data = gson.fromJson(reader, Map.class);
            this.bricksXPos = data.get("bricksXPos");
            this.bricksYPos = data.get("bricksYPos");
            this.solidBricksXPos = data.get("solidBricksXPos");
            this.solidBricksYPos = data.get("solidBricksYPos");
            this.brickON = new int[this.bricksXPos.length];
            brick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void brick() {
        for (int i = 0; i < this.brickON.length; ++i) {
            this.brickON[i] = 1;
        }
    }


    public void draw(GraphicsContext gc) {
        for (int i = 0; i < this.brickON.length; ++i) {
            if (this.brickON[i] == 1) {
                gc.drawImage(this.breakBrickImage, this.bricksXPos[i], this.bricksYPos[i]);
            }
        }
    }

    public void drawSolids(GraphicsContext gc) {
        for (int i = 0; i < this.solidBricksXPos.length; ++i) {
            gc.drawImage(this.solidBrickImage, this.solidBricksXPos[i], this.solidBricksYPos[i]);
        }
    }

    public boolean checkCollision(int x, int y) {
        boolean collided = false;

        for (int i = 0; i < this.brickON.length; ++i) {
            if (this.brickON[i] == 1) {
                Rectangle brick = new Rectangle(this.bricksXPos[i], this.bricksYPos[i], 50, 50);
                Rectangle ball = new Rectangle(x, y, 20, 20);
                if (brick.intersects(ball.getBoundsInLocal())) {
                    this.brickON[i] = 0;
                    collided = true;
                }
            }
        } // end for
        return collided;
    }
}