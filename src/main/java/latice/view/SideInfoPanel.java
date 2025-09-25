package latice.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import latice.player.Player;

public class SideInfoPanel extends VBox {
    private Label scoreLabel;
    private Label cardsLeftLabel;
    private final String FONT_FAMILY = "Segoe UI";

    public SideInfoPanel(Player player) {
        setAlignment(Pos.CENTER);
        setSpacing(18);
        setPadding(new Insets(32));
        setPrefSize(300, 300);
        setMaxSize(300, 300);
        setMinSize(300, 300);

        // Background color, rounded corners and border styling
        setStyle(
            "-fx-background-color: rgba(0,16,34,0.85);" +
            "-fx-background-radius: 24;" +
            "-fx-border-color: #00bfff;" +  // light sky blue border
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 24;"
        );
        setEffect(new DropShadow(18, Color.rgb(0, 0, 0, 0.7))); // subtle shadow effect

        // Player name label styling
        Label nameLabel = new Label(player.getName());
        nameLabel.setFont(Font.font(FONT_FAMILY, 38));
        nameLabel.setTextFill(Color.web("#ffffff"));
        nameLabel.setStyle("-fx-font-weight: bold;");

        // Score label styling
        scoreLabel = new Label("Points : " + player.getPoint());
        scoreLabel.setFont(Font.font(FONT_FAMILY, 28));
        scoreLabel.setTextFill(Color.web("#f8f8f8"));

        // Cards left label styling
        cardsLeftLabel = new Label("Cartes : " + player.getPool().size());
        cardsLeftLabel.setFont(Font.font(FONT_FAMILY, 28));
        cardsLeftLabel.setTextFill(Color.web("#f8f8f8"));

        // Add all labels to the panel
        getChildren().addAll(nameLabel, scoreLabel, cardsLeftLabel);
    }

    public void updateScore(Player player) {
        scoreLabel.setText("Points : " + player.getPoint());
    }

    public void updateCardsLeft(Player player) {
        cardsLeftLabel.setText("Cartes : " + player.getPool().size());
    }
}
