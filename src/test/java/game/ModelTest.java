package game;

import objects.Bullet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structure.Model;

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
