package latice.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import latice.cell.Cell;

public class CellView extends ImageView {
    
    private final Cell cell;
    private final String pathToImage;

    // Constructor determines image based on whether cell contains a tile or not
    public CellView(Cell cell) {
        this.cell = cell;
        if (cell.getTile() != null) {
            pathToImage = cell.getTile().getName();  // Use tile image if present
        } else {
            pathToImage = "bg_" + cell.getName();    // Use background image otherwise
        }
        Image cellImage = new Image(getClass().getResource("/assets/" + pathToImage + ".png").toExternalForm());
        setImage(cellImage);
    }

    // Getter for the underlying Cell model
    public Cell getCell() {
        return cell;
    }
}
