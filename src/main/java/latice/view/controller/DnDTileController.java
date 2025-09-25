package latice.view.controller;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import latice.console.Console;
import latice.controller.Referee;
import latice.view.CellView;
import latice.view.GameBoardPanel;
import latice.view.PlayerRackPanel;
import latice.view.TileView;

public class DnDTileController {

    private DnDTileController() {
        // Private constructor to prevent instantiation
    }

    private static TileView draggedTileView = null;

    public static void makeDraggable(TileView tileView) {
        tileView.setOnDragDetected(event -> {
            draggedTileView = tileView;

            // Start the drag-and-drop operation
            var db = tileView.startDragAndDrop(TransferMode.MOVE);

            // Capture the tile's snapshot
            WritableImage originalSnapshot = tileView.snapshot(null, null);

            // Set the maximum size for the drag view
            final double maxDragSize = 80;

            // Set the scale factor based on the original snapshot size
            double scaleX = maxDragSize / originalSnapshot.getWidth();
            double scaleY = maxDragSize / originalSnapshot.getHeight();
            double scale = Math.min(scaleX, scaleY);

            // Change the snapshot parameters to include scaling
            SnapshotParameters params = new SnapshotParameters();
            params.setTransform(new Scale(scale, scale));
            params.setFill(Color.TRANSPARENT);

            // Create a new writable image with the scaled size	
            WritableImage resizedSnapshot = new WritableImage(
                (int) (originalSnapshot.getWidth() * scale),
                (int) (originalSnapshot.getHeight() * scale)
            );

            // Take the snapshot with the new parameters
            resizedSnapshot = tileView.snapshot(params, resizedSnapshot);

            // Set the drag view to the resized snapshot
            db.setDragView(resizedSnapshot, resizedSnapshot.getWidth() / 2, resizedSnapshot.getHeight() / 2);

            // Create clipboard content (this is necessary for the drag-and-drop operation)
            ClipboardContent content = new ClipboardContent();
            content.putString("tile");
            db.setContent(content);

            event.consume();
        });
    }

    public static void makeDroppable(CellView targetView) {
        targetView.setOnDragOver(event -> {
            if (event.getGestureSource() != targetView && draggedTileView != null) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        targetView.setOnDragEntered(event -> {
            if (event.getGestureSource() != targetView && draggedTileView != null) {
                targetView.setOpacity(0.5);
            }
            event.consume();
        });

        targetView.setOnDragExited(event -> {
            targetView.setOpacity(1.0);
            event.consume();
        });

        targetView.setOnDragDropped(event -> {
            if (draggedTileView != null) {
            	try {
            		// Play the tile on the target cell
            		Referee.doAction(targetView.getCell().getPosition(), draggedTileView.getTile());
            		GameBoardPanel.drawBoard(); // Redraw the board to reflect the new tile placement
            		PlayerRackPanel.deleteTile(draggedTileView); // Remove the tile from the player's rack
            		event.setDropCompleted(true);
            	} catch (Exception e) {
            		Console.printError("Failed to place tile: " + e.getMessage());
            		event.setDropCompleted(false);
            	}
                draggedTileView = null;
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
    }
}
