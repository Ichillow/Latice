package latice.tile;

public enum Color {
	RESET("\u001B[0m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PINK("\u001B[35m"),
	CYAN("\u001B[36m"),
	WHITE("\u001B[37m");
	
	// Enum constants for different colors
	private final String code;

	// Constructor to initialize the color
    private Color(String code) {
        this.code = code;
    }

    // Method to get the color code
    public String getCode() {
        return code;
    }
    
    // Method to get the color name in lowercase
    public String getName() {
		return name().toLowerCase();
	}
}
