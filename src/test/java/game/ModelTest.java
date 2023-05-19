package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ModelTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new Model(null);
    }

    @Test
    public void testAddBullet() {
        Bullet bullet = new Bullet(0, 0, 1, 1, null, 0);
        model.addBullet(bullet);

        ArrayList<Bullet> bullets = model.getBullets();
        Assertions.assertEquals(1, bullets.size());
        Assertions.assertTrue(bullets.contains(bullet));
    }

}
