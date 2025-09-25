package latice.cell;

import latice.tile.Tile;
import latice.tile.Color;
import latice.tile.Shape;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testGetTypeNormal() {
        Cell cell = new Cell(CellType.SEA, new Position(1, 1));
        assertEquals(CellType.SEA, cell.getType());
    }


    @Test
    public void testSetAndGetTile() {
        Tile tile = new Tile(Color.RED, Shape.TURTLE);
        Cell cell = new Cell(CellType.SEA, new Position(1, 1));
        
        cell.setTile(tile);
        
        assertNotNull(cell.getTile(), "Tile should not be null after setting it");
        assertEquals(tile, cell.getTile(), "Tile returned should be the same as the one set");
        assertEquals(Color.RED.getCode(), cell.getTile().getColor());
        assertEquals(Shape.TURTLE.getShape(), cell.getTile().getShape());
    }
    
    
    @Test
    public void testGetName() {
		Cell cell = new Cell(CellType.SEA, new Position(1, 1));
		assertEquals("sea", cell.getName(), "The name of the cell should be 'sea'");
	}
    
    
    @Test
    public void testGetPosition() {
		Position position = new Position(1, 1);
		Cell cell = new Cell(CellType.SEA, position);
		
		assertNotNull(cell.getPosition(), "Position should not be null");
		assertEquals(position, cell.getPosition(), "Position returned should be the same as the one set");
	}

	@Test
	public void testGetTypeSymbol() {
		Cell cell = new Cell(CellType.SEA, new Position(1, 1));
		assertEquals(CellType.SEA.getSymbol(), cell.getType().getSymbol(), "The symbol of the cell type should match");
	}
}