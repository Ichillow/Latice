package latice.tile;

public enum Shape{
	BIRD("🦅"),
	DOLPHIN("🐬"),
	FEATHER("🪶"),
	FLOWER("🌼"),
	LIZARD("🦎"),
	TURTLE("🐢");

	// Enum constants for different shapes
	private final String form;

	// Constructor to initialize the shape
	private Shape(String shape) {
		this.form = shape;
	}
	
	// Method to get the shape
	public String getShape() {
		return form;
	}
	
	// Method to get the shape as a string
	public String getName() {
		return name().toLowerCase();
	}
}
