package latice.player;

import java.util.List;

import latice.console.Console;
import latice.tile.Tile;

public class Rack {
    private List<Tile> tiles;

    public Rack() {
		this.tiles = new java.util.ArrayList<>();
	}
    
    public Rack(List<Tile> tiles) {
        this.tiles = tiles;
    }
    
    public void addTile(Tile tile) {
        if (tile != null) {
            tiles.add(tile);
        }
    }

    public void displayRack() {
        Console.printRack(this);
        
    }

    public List<Tile> getTiles() {
        return tiles;
    }
    
    public int size() {
		return tiles.size();
	}
    
    public Tile removeTile(int index) {
		if (index >= 0 && index < tiles.size()) {
			return tiles.remove(index);
		} else {
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		}
	}
}
