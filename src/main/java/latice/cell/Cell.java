package latice.cell;

import latice.tile.Tile;

public class Cell {
	private final CellType type;
	private Tile tile;
	private Position position;
	
	public Cell(CellType type, Position position) {
		this.type = type;
		this.position = position;
	}
	
	public CellType getType() {
		return type;
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public String getName() {
		return type.getName();
	}
	
	public Position getPosition() {
		return position;
	}
}
