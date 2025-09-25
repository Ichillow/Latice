package latice.view;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import latice.cell.Cell;
import latice.cell.Position;
import latice.gameboard.GameBoard;
import latice.view.controller.DnDTileController;

public class GameBoardPanel extends StackPane {
    private static final double CELL_SIZE = 64;
    private static final double Y_OFFSET = -30;


    private static GridPane grid;

    public GameBoardPanel() {

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(0);
        grid.setHgap(0);
        grid.setTranslateY(Y_OFFSET);

        // Set the size of the grid
        setPadding(new Insets(60, 0, 0, 0)); // top, right, bottom, left

        drawBoard();

        getChildren().add(grid);
    }

    public static void drawBoard() {
    	// Clear the grid and redraw the cells
        Map<Position, Cell> cells = GameBoard.getCells();
        grid.getChildren().clear();
        
        

        // Loop through each cell in the grid
        for (Position pos : cells.keySet()) {
			int row = pos.x();
			int col = pos.y();

			// Create a CellView for each cell
			CellView cellView = new CellView(cells.get(pos));
			
			// Set the size of the CellView
			cellView.setFitWidth(CELL_SIZE);
			cellView.setFitHeight(CELL_SIZE);
			
			// Make the ImageView droppable
			DnDTileController.makeDroppable(cellView);

			// Add the CellView to the grid
			StackPane cellPane = new StackPane(cellView);
			cellPane.setPrefSize(CELL_SIZE, CELL_SIZE);
			grid.add(cellPane, col, row);
		}
    }
}
