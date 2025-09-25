package latice.player;

import latice.tile.Tile;
import latice.tile.Color;
import latice.tile.Shape;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PoolTest {

    private List<Tile> tiles;
    private Pool pool;

    @BeforeEach
    void setUp() {
        tiles = new ArrayList<>();
        tiles.add(new Tile(Color.RED, Shape.TURTLE));
        tiles.add(new Tile(Color.BLUE, Shape.FLOWER));
        tiles.add(new Tile(Color.GREEN, Shape.LIZARD));
        pool = new Pool(new ArrayList<>(tiles)); 
    }

    @Test
    void testConstructorShufflesAndStoresTiles() {
        assertEquals(3, pool.size());
    }

    @Test
    void testDrawTileRemovesTile() {
        Tile drawn = pool.drawTile();
        assertNotNull(drawn);
        assertEquals(2, pool.size());
    }

    @Test
    void testDrawTileReturnsNullWhenEmpty() {
        pool.drawTile();
        pool.drawTile();
        pool.drawTile();
        assertTrue(pool.isEmpty());
        assertNull(pool.drawTile());
    }

    @Test
    void testIsEmpty() {
        assertFalse(pool.isEmpty());
        pool.drawTile();
        pool.drawTile();
        pool.drawTile();
        assertTrue(pool.isEmpty());
    }

    @Test
    void testSize() {
        assertEquals(3, pool.size());
        pool.drawTile();
        assertEquals(2, pool.size());
    }

    @Test
    void testGetTile() {
        Tile firstTile = pool.getTile(0);
        assertNotNull(firstTile);
    }

    @Test
    void testGetTilesReturnsList() {
        List<Tile> returnedTiles = pool.getTiles();
        assertEquals(pool.size(), returnedTiles.size());
        assertTrue(returnedTiles.containsAll(tiles));
    }
}
