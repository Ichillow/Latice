package latice.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.tile.Color;
import latice.tile.Shape;
import latice.tile.Tile;

class PlayerTest {

    private Player playerWithPool;
    private Player playerWithoutPool;
    private Rack rack;
    private Pool pool;
    private Tile tile1;
    private Tile tile2;

    @BeforeEach
    void setUp() {
        tile1 = new Tile(Color.RED, Shape.BIRD);
        tile2 = new Tile(Color.BLUE, Shape.DOLPHIN);

        rack = new Rack();
        pool = new Pool(Arrays.asList(tile1, tile2));

        playerWithPool = new Player("Alice", rack, pool);
        playerWithoutPool = new Player("Bob");
    }

    @Test
    void testConstructorWithAllParams() {
        assertEquals("Alice", playerWithPool.getName());
        assertEquals(rack, playerWithPool.getRack());
        assertEquals(pool, playerWithPool.getPool());
    }

    @Test
    void testConstructorWithNameOnly() {
        assertEquals("Bob", playerWithoutPool.getName());
        assertNotNull(playerWithoutPool.getRack());
        assertNull(playerWithoutPool.getPool());
    }

    @Test
    void testSetAndGetPool() {
        assertNull(playerWithoutPool.getPool());
        playerWithoutPool.setPool(pool);
        assertEquals(pool, playerWithoutPool.getPool());
    }

    @Test
    void testSetAndGetRack() {
        Rack newRack = new Rack();
        playerWithPool.setRack(newRack);
        assertEquals(newRack, playerWithPool.getRack());
    }

    @Test
    void testGetName() {
        assertEquals("Alice", playerWithPool.getName());
    }
    
    @Test
    void testGetPoint() {
		assertEquals(0, playerWithPool.getPoint());

	}
}