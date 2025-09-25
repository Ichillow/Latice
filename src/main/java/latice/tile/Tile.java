package latice.tile;


public class Tile {
	private Color color;
	private Shape shape;
	
	
	public Tile(Color color, Shape shape) {
		this.color = color;
		this.shape = shape;
	}
	
	
	//Methods to get the color and shape of the tile
	public String getColor() {
		return color.getCode();
	}
	
	// Method to get the shape of the tile
	public String getShape() {
		return shape.getShape();
	}
	
	@Override
	public String toString() {
		return getColor() + getShape() + Color.RESET.getCode();
	}
	
	// Method to get the name of the tile
	public String getName() {
		return shape.getName() + "_" + color.getName();
	}

}
