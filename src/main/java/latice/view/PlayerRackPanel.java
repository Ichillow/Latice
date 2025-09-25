package latice.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import latice.player.Player;
import latice.tile.Tile;
import latice.view.controller.DnDTileController;

public class PlayerRackPanel extends HBox {

    private static final int RACK_WIDTH = 520;    // Width of the rack container
    private static final int RACK_HEIGHT = 130;   // Height of the rack container
    private static final int TILE_SIZE = 75;      // Size of each tile image
    private static final int RACK_SIZE = 5;       // Maximum number of tiles in rack

    public PlayerRackPanel(Player player) {
        setSpacing(15); // Space between tiles
        setPadding(new Insets(15, 15, 30, 15)); // Padding around rack

        // Background gradient, border and rounded corners styling
        setStyle(
            "-fx-background-color: linear-gradient(to bottom, #f2c94c, #c98b00);" +
            "-fx-border-color: #fff59d;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 24;" +
            "-fx-border-radius: 24;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 4);"
        );

        // Additional drop shadow effect for depth
        setEffect(new DropShadow(30, 0, 6, Color.rgb(0, 0, 0, 0.6)));

        setAlignment(Pos.CENTER);
        setMaxWidth(RACK_WIDTH);
        setPrefHeight(RACK_HEIGHT);

        displayRack(player); // Populate rack with player's tiles
    }

    // Clear all tiles from the rack UI
    public void clearRack() {
        getChildren().clear();
    }

    // Render the tiles from the player's rack with drag and hover effects
    public void displayRack(Player player) {
        getChildren().clear();

        for (Tile tile : player.getRack().getTiles()) {
            TileView tileView = new TileView(tile);
            tileView.setFitWidth(TILE_SIZE);
            tileView.setFitHeight(TILE_SIZE);

            DropShadow dropShadow = new DropShadow(6, 3, 3, Color.rgb(0, 0, 0, 0.6));
            tileView.setEffect(dropShadow);
            DnDTileController.makeDraggable(tileView);
            tileView.setTranslateY(6); // Slight vertical offset for style

            // Hover effect: highlight with gold glow
            tileView.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                dropShadow.setRadius(10);
                dropShadow.setOffsetX(0);
                dropShadow.setOffsetY(0);
                dropShadow.setColor(Color.rgb(255, 215, 0, 0.85)); // gold color
            });

            // Hover exit: revert shadow to original
            tileView.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                dropShadow.setRadius(6);
                dropShadow.setOffsetX(3);
                dropShadow.setOffsetY(3);
                dropShadow.setColor(Color.rgb(0, 0, 0, 0.6));
            });

            getChildren().add(tileView);
        }
    }

    // Add a new tile image to the rack if not full, with hover effects
    public void addTile(Image tileImage) {
        if (getChildren().size() < RACK_SIZE) {
            ImageView tileView = new ImageView(tileImage);
            tileView.setFitWidth(TILE_SIZE);
            tileView.setFitHeight(TILE_SIZE);

            DropShadow dropShadow = new DropShadow(6, 3, 3, Color.rgb(0, 0, 0, 0.6));
            tileView.setEffect(dropShadow);
            tileView.setTranslateY(6);

            // Hover effect: highlight with blue glow
            tileView.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                dropShadow.setRadius(10);
                dropShadow.setOffsetX(0);
                dropShadow.setOffsetY(0);
                dropShadow.setColor(Color.rgb(60, 180, 255, 0.8)); // blue color
            });

            // Hover exit: revert shadow to original
            tileView.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                dropShadow.setRadius(6);
                dropShadow.setOffsetX(3);
                dropShadow.setOffsetY(3);
                dropShadow.setColor(Color.rgb(0, 0, 0, 0.6));
            });

            getChildren().add(tileView);
        }
    }
    
    public static void deleteTile(TileView tileView) {
		if (tileView != null && tileView.getParent() instanceof PlayerRackPanel) {
			PlayerRackPanel rackPanel = (PlayerRackPanel) tileView.getParent();
			rackPanel.getChildren().remove(tileView);
		}
	}
}
