package latice.cell;

import java.util.List;
import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    
    @Override
    public String toString() {
		return "Position{" + "x=" + x + ", y=" + y + '}';
	}
    
    public List<Position> getNeighbors() {
		return List.of(
			new Position(x - 1, y), // Left
			new Position(x + 1, y), // Right
			new Position(x, y - 1), // Up
			new Position(x, y + 1)  // Down
		);
	}
}
