package latice.gameboard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import latice.cell.Cell;
import latice.cell.CellType;
import latice.cell.Position;
import latice.tile.Color;
import latice.tile.Shape;
import latice.tile.Tile;

class GameBoardTest {


    private Map<Position, Cell> cells;

    @BeforeEach
    void setUp() {
        new GameBoard();
        cells = GameBoard.getCells();
    }

    @Test
    void testCenterCellIsMoon() {
        Position center = new Position(4, 4);
        assertTrue(cells.containsKey(center), "The center position should exist in the cells map");
        assertEquals(CellType.MOON, cells.get(center).getType(), "The center cell should be of type MOON");
    }

    @Test
    void testSunCellsDiagonalsAndMiddles() {
        // Sun cells are located at the corners and diagonals of the board
        Position[] diagonals = {
            new Position(0, 0),
            new Position(8, 0),
            new Position(0, 8),
            new Position(8, 8),
            new Position(1, 1),
            new Position(7, 1),
            new Position(1, 7),
            new Position(7, 7),
            new Position(2, 2),
            new Position(6, 2),
            new Position(2, 6),
            new Position(6, 6)
        };

        // Middle cells are located at the sides of the board
        Position[] sides = {
            new Position(4, 0),
            new Position(4, 8),
            new Position(0, 4),
            new Position(8, 4)
        };

        for (Position pos : diagonals) {
            assertTrue(cells.containsKey(pos), "Diagonal position is missing : " + pos);
            assertEquals(CellType.SUN, cells.get(pos).getType(), "The cell at " + pos + " should be SUN");
        }

        for (Position pos : sides) {
            assertTrue(cells.containsKey(pos), "Middle position is missing : " + pos);
            assertEquals(CellType.SUN, cells.get(pos).getType(), "The cell at " + pos + " should be SUN");
        }
    }
    
    @Test
    void testAllCellsAreInitialized() {
        assertEquals(81, cells.size(), "There should be exactly 81 cells on a 9x9 board");
    }

    @Test
    void testSeaCellsAreCorrectlyInitialized() {
        for (int row = 0; row < GameBoard.ROWS; row++) {
            for (int col = 0; col < GameBoard.COLS; col++) {
                Position pos = new Position(row, col);
                Cell cell = cells.get(pos);
                assertTrue(cell != null, "Cell should exist at position: " + pos);

                CellType type = cell.getType();
                if (!pos.equals(new Position(4, 4)) && type != CellType.SUN) {
                    assertEquals(CellType.SEA, type, "Cell at " + pos + " should be SEA");
                }
            }
        }
    }

    @Test
    void testGetCellReturnsCorrectCell() {
        Position pos = new Position(0, 0);
        Cell cell = GameBoard.getCell(pos);
        assertEquals(cells.get(pos), cell, "getCell should return the correct cell instance");
    }

    @Test
    void testSetTileValidOnCenter() {
        Position center = new Position(GameBoard.MID_ROW, GameBoard.MID_COL);
        Tile tile = new Tile(Color.RED, Shape.BIRD);

        assertDoesNotThrow(() -> GameBoard.setTile(center, tile));
        assertEquals(tile, GameBoard.getCell(center).getTile(), "Tile should be placed at center.");
    }

    @Test
    void testSetTileOnAlreadyOccupiedCell() {
        Position center = new Position(GameBoard.MID_ROW, GameBoard.MID_COL);
        Tile tile1 = new Tile(Color.RED, Shape.BIRD);
        Tile tile2 = new Tile(Color.BLUE, Shape.DOLPHIN);

        GameBoard.setTile(center, tile1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            GameBoard.setTile(center, tile2);
        });

        assertTrue(exception.getMessage().contains("not valid"), "Should not allow placing a tile on an occupied cell.");
    }

    @Test
    void testSetTileOnInvalidPosition() {
        Position invalid = new Position(-1, -1);
        Tile tile = new Tile(Color.RED, Shape.BIRD);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            GameBoard.setTile(invalid, tile);
        });

        assertTrue(exception.getMessage().contains("does not exist"), "Should throw on invalid position.");
    }

    @Test
    void testSetTileInvalidPlacementByRules() {
        // Placement loin du centre sans voisins
        Position pos = new Position(0, 0); // SUN mais sans voisin au début
        Tile tile = new Tile(Color.GREEN, Shape.BIRD);

        // S'assurer que le centre n'a pas encore de tuile
        Position center = new Position(GameBoard.MID_ROW, GameBoard.MID_COL);
        GameBoard.getCell(center).setTile(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            GameBoard.setTile(pos, tile);
        });

        assertTrue(exception.getMessage().contains("not valid"), "Should throw on invalid placement according to game rules.");
    }
}