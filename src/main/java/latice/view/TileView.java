package latice.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import latice.tile.Tile;

public class TileView extends ImageView {
    
    private final Tile tile;

    // Constructor loads tile image based on tile's name
    public TileView(Tile tile) {
        this.tile = tile;
        Image tileImage = new Image(getClass().getResource("/assets/" + tile.getName() + ".png").toExternalForm());
        setImage(tileImage);
    }

    // Getter for the underlying Tile model
    public Tile getTile() {
        return tile;
    }
}
