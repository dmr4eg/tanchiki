package game;

import objects.Brick;
import objects.Obj;
import objects.Tanks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import structure.modules.EnemyTanksBrain;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTanksBrainTest {

    private EnemyTanksBrain enemyTanksBrain;
    private Brick base;
    private Tanks player;
    private Tanks player2;

    @BeforeEach
    void setUp() {
        base = new Brick(0, 0, "base");
        player = new Tanks(0, 0, "player", 4, 4);
        player2 = new Tanks(0, 0, "player2", 54, 54);
        enemyTanksBrain = new EnemyTanksBrain(base, player, player2);
    }

    @Test
    void checkCollisionObj_shouldSetCollisionFlagsCorrectly() {
        ArrayList<Obj> objects = new ArrayList<>();
        Tanks tank = new Tanks(10, 10, "enemy", 10, 10);

        Obj brick1 = new Brick(5, 10, "brick");
        Obj brick2 = new Brick(15, 10, "brick");
        Obj brick3 = new Brick(10, 5, "brick");
        Obj brick4 = new Brick(10, 15, "brick");

        objects.add(brick1);
        objects.add(brick2);
        objects.add(brick3);
        objects.add(brick4);

        enemyTanksBrain.check_collisionObj(tank, objects);

        boolean[] expectedCollisionArr = {false, false, false, false};
        assertArrayEquals(expectedCollisionArr, tank.getIsColision());
    }

    @Test
    void fireInPlayerOrMoveToBase_shouldFireInPlayerIfPossible() {
        ArrayList<Obj> objects = new ArrayList<>();
        Tanks tank = new Tanks(10, 10, "enemy", 4, 4);

        Obj brick = new Brick(5, 10, "brick");
        objects.add(brick);

        enemyTanksBrain.fire_in_player_or_move_to_base(tank);
    }

    @Test
    void computingToBaseObj_shouldCallCheckCollisionObjAndFireInPlayerOrMoveToBase() {
        ArrayList<Obj> objects = new ArrayList<>();
        Tanks tank = new Tanks(10, 10, "enemy", 4, 4);

        Obj brick = new Brick(5, 10, "brick");
        objects.add(brick);

        enemyTanksBrain.computing_to_baseObj(tank, objects);
        enemyTanksBrain.fire_in_player_or_move_to_base(tank);
    }

    @Test
    void getPriorityTarget_shouldReturnPlayerIfPlayerIsCloser() {
        Tanks tank = new Tanks(10, 10, "enemy", 4, 4);
        Tanks player = new Tanks(5, 5, "player", 10, 10);
//      Tanks player2 = new Tanks(20, 20, "player2", 4, 4);

        Obj expectedPlayer = enemyTanksBrain.getPrioritytarget(player);
        assertEquals(expectedPlayer, player);
    }

}

