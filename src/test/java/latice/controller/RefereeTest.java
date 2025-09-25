package latice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.cell.Position;
import latice.gameboard.GameBoard;
import latice.player.Player;
import latice.player.Pool;
import latice.player.Rack;
import latice.tile.Color;
import latice.tile.Shape;
import latice.tile.Tile;

public class RefereeTest {

    private Referee referee;

    @BeforeEach
    public void setUp() {
        referee = new Referee();
    }
    
    
    @Test
    public void testGameBoardInitialization() {
		assertNotNull(referee.getGameBoard(), "Game board should be initialized");
	}

    @Test
    public void testDrawAddsFiveTilesToRack() {
        // Create a pool with more than 5 tiles
        List<Tile> tiles = new ArrayList<>();
        for (Color color : Color.values()) {
            if (color != Color.RESET && color != Color.WHITE) {
                for (Shape shape : Shape.values()) {
                    tiles.add(new Tile(color, shape));
                    tiles.add(new Tile(color, shape)); // Add two copies of each tile
                }
            }
        }

        Pool pool = new Pool(new ArrayList<>(tiles));
        Rack rack = new Rack();

        // Draw 5 tiles
        Referee.draw(rack, pool);

        assertEquals(5, rack.getTiles().size());
        assertEquals(tiles.size() - 5, pool.size());
    }

    @Test
    public void testCreatePlayerPoolDistributesTiles() {
        referee.createPlayerPool();

        for (Player player : referee.players) {
            Pool pool = player.getPool();
            assertNotNull(pool);

            // 6 shapes x 6 colors (excluding RESET and WHITE) x 2 copies = 72 tiles
            int expectedTotalTiles = 6 * 6 * 2;
            int expectedTilesPerPlayer = expectedTotalTiles / 2;

            assertEquals(expectedTilesPerPlayer, pool.size());
        }
    }
    
    // This test verifies that the choosePlayer method selects a player randomly from the list of players.    
    @Test
    public void testChoosePlayer() {
        List<Player> players = referee.players;

        assertFalse(players.isEmpty(), "The list of players should not be empty");

        Player chosenPlayer = referee.choosePlayer();

        assertNotNull(chosenPlayer, "The chosen player should not be null");
        assertTrue(players.contains(chosenPlayer), "The chosen player should be part of the list of players");
    }
    
    @Test
    public void testInitialPlayerSetup() {
        for (Player player : referee.players) {
            assertNotNull(player.getRack(), "Each player should have a rack");
            assertEquals(5, player.getRack().getTiles().size(), "Each rack should have 5 tiles");
            assertNotNull(player.getPool(), "Each player should have a pool");
            assertFalse(player.getPool().isEmpty(), "Each player's pool should not be empty");
        }
    }

    @Test
    public void testDrawWithEmptyPool() {
        Pool emptyPool = new Pool(new ArrayList<>());
        Rack rack = new Rack();
        Referee.draw(rack, emptyPool);
        assertEquals(0, rack.getTiles().size(), "No tiles should be drawn if the pool is empty");
    }

    @Test
    public void testDrawWithLessThanFiveTiles() {
        List<Tile> fewTiles = new ArrayList<>();
        fewTiles.add(new Tile(Color.BLUE, Shape.BIRD));
        fewTiles.add(new Tile(Color.RED, Shape.DOLPHIN));
        Pool smallPool = new Pool(fewTiles);
        Rack rack = new Rack();

        Referee.draw(rack, smallPool);
        assertEquals(2, rack.getTiles().size(), "Rack should contain only available tiles");
        assertTrue(smallPool.isEmpty(), "Pool should be empty after drawing all tiles");
    }

    @Test
    public void testIsPlacementValid_CenterPlacement() {
        Position center = new Position(GameBoard.MID_ROW, GameBoard.MID_COL);
        Tile tile = new Tile(Color.BLUE, Shape.BIRD);

        // The center is empty, so placing on it should be valid
        assertTrue(Referee.isPlacementValid(center, tile), "Should be valid to place a tile at center if it's empty");
    }

    @Test
    public void testIsPlacementValid_InvalidNonCenterFirstPlacement() {
        Position notCenter = new Position(GameBoard.MID_ROW + 1, GameBoard.MID_COL);
        Tile tile = new Tile(Color.BLUE, Shape.BIRD);

        // Center is empty, but we try to place elsewhere — should be invalid
        assertFalse(Referee.isPlacementValid(notCenter, tile), "Cannot place outside center if center is empty");
    }

    @Test
    public void testIsPlacementValid_InvalidDifferentColorAndShapeNeighbor() {
        Position center = new Position(GameBoard.MID_ROW, GameBoard.MID_COL);
        GameBoard.cells.get(center).setTile(new Tile(Color.BLUE, Shape.BIRD)); // manually set tile at center

        Position testPosition = new Position(center.x() + 1, center.y());
        Tile testTile = new Tile(Color.GREEN, Shape.FEATHER); // incompatible

        GameBoard.cells.get(testPosition); // make sure it exists

        assertFalse(Referee.isPlacementValid(testPosition, testTile), "Placement should be invalid due to non-matching neighbor");
    }

    @Test
    public void testIsPlacementValid_ValidMatchingShapeNeighbor() {
        Position center = new Position(GameBoard.MID_ROW, GameBoard.MID_COL);
        GameBoard.cells.get(center).setTile(new Tile(Color.BLUE, Shape.BIRD)); // center occupied

        Position testPosition = new Position(center.x() + 1, center.y());
        Tile testTile = new Tile(Color.YELLOW, Shape.BIRD);

        GameBoard.cells.get(testPosition); // ensure exists

        assertTrue(Referee.isPlacementValid(testPosition, testTile), "Should allow placement if at least one neighbor matches shape");
    }
}
