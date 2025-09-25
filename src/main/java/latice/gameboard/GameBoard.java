package latice.gameboard;

import java.util.HashMap;
import java.util.Map;

import latice.cell.Cell;
import latice.cell.CellType;
import latice.cell.Position;
import latice.controller.Referee;
import latice.tile.Tile;

public class GameBoard {
	public static HashMap<Position, Cell> cells;
	public static final int ROWS = 9;
	public static final int COLS = 9;
	public static final Integer MID_ROW = Math.round(ROWS / 2);
    public static final Integer MID_COL = Math.round(COLS / 2);
	
	public GameBoard() {
		initializeBoard();
	}
	
	
	private void initializeBoard() {
		cells = new HashMap<Position, Cell>();
		
		// Initialize local variables
		final int GRID_PAD = 1; // Used to align cell positions on the grid of the game when ROWS or COLS are used.
		
		// Initialize the cells with SUN type
		// Diagonal cells
		Cell tempCell;
		for (int i = 0; i < 3; i++) {
			tempCell = new Cell(CellType.SUN, new Position(i, i));
			cells.put(tempCell.getPosition(), tempCell);
			
			tempCell = new Cell(CellType.SUN, new Position(ROWS - i - GRID_PAD, i));
			cells.put(tempCell.getPosition(), tempCell);
			
			tempCell = new Cell(CellType.SUN, new Position(i, COLS - i - GRID_PAD));
			cells.put(tempCell.getPosition(), tempCell);
			
			tempCell = new Cell(CellType.SUN, new Position(ROWS - i - GRID_PAD, COLS - i - GRID_PAD));
			cells.put(tempCell.getPosition(), tempCell);
			
		}
		
		// Middle cells
		tempCell = new Cell(CellType.SUN, new Position(MID_ROW, 0));
		cells.put(tempCell.getPosition(), tempCell);
		
		tempCell = new Cell(CellType.SUN, new Position(MID_ROW, COLS - GRID_PAD));
		cells.put(tempCell.getPosition(), tempCell);
		
		tempCell = new Cell(CellType.SUN, new Position(0, MID_COL));
		cells.put(tempCell.getPosition(), tempCell);
		
		tempCell = new Cell(CellType.SUN, new Position(ROWS - GRID_PAD, MID_COL));
		cells.put(tempCell.getPosition(), tempCell);

		
		
		// Initialize the cell with MOON type
		tempCell = new Cell(CellType.MOON, new Position(Math.round(ROWS / 2), Math.round(COLS / 2)));
		cells.put(tempCell.getPosition(), tempCell);
		
		// Initialize the rest of the cells with SEA type
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				tempCell = new Cell(CellType.SEA, new Position(row, col));
				if (!cells.containsKey(tempCell.getPosition())) {
					cells.put(tempCell.getPosition(), tempCell);
				}
			}
		}
		
	}
	
	public static Map<Position, Cell> getCells() {
		return cells;
	}
	
	public static Cell getCell(Position position) {
		return cells.get(position);
	}
	
	public static void setTile(Position position, Tile tile) {
		if (!cells.containsKey(position)) {
			throw new IllegalArgumentException("Position " + position + " does not exist on the game board.");
			
		} else if (!Referee.isPlacementValid(position, tile)) {
			throw new IllegalArgumentException("Position " + position + " is not valid.");
			
		} else if (tile == null) {
			throw new IllegalArgumentException("Tile cannot be null.");

		}
		
		// Set the tile on the cell at the specified position
		cells.get(position).setTile(tile);
	}
	
	
}
