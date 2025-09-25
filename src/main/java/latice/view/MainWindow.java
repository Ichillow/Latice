package latice.view;

import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import latice.console.Console;
import latice.controller.Referee;
import latice.player.Player;

public class MainWindow extends Application {
	
	public static SideInfoPanel sideInfoPlayer1;
	public static SideInfoPanel sideInfoPlayer2;

    @Override
    public void start(Stage primaryStage) {
        URL videoUrl = getClass().getResource("/assets/bg_video.mp4");
        if (videoUrl == null) {
            Console.printError("Background video not found!");
            return;
        }
        Media backgroundMedia = new Media(videoUrl.toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(backgroundMedia);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        MediaView backgroundMediaView = new MediaView(mediaPlayer);
        backgroundMediaView.setPreserveRatio(false);
        backgroundMediaView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundMediaView.fitHeightProperty().bind(primaryStage.heightProperty());
        backgroundMediaView.setEffect(new GaussianBlur(10));

        Referee referee = new Referee();
        
        List<Player> players = Referee.playersCycle;
        
        GameBoardPanel gameBoardPanel = new GameBoardPanel();
        
        // Create the player rack panel for the first player
        PlayerRackPanel playerRackPanel = new PlayerRackPanel(players.get(0));


        sideInfoPlayer1 = new SideInfoPanel(players.get(0));

        sideInfoPlayer2 = new SideInfoPanel(players.get(1));


        
        Label currentPlayerLabel = new Label(players.get(0).getName() + "'s Turn");
        currentPlayerLabel.setStyle(
            "-fx-font-size: 24px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;" +
            "-fx-effect: dropshadow(gaussian, black, 4, 0.5, 1, 1);"
        );
        currentPlayerLabel.setAlignment(Pos.CENTER);

        VBox rackContainer = new VBox(playerRackPanel);
        rackContainer.setAlignment(Pos.CENTER);

        VBox rackWithLabel = new VBox(25, currentPlayerLabel, rackContainer);
        rackWithLabel.setAlignment(Pos.TOP_CENTER);

        Button endTurnButton = new Button("Fin de Tour");
        endTurnButton.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-color: linear-gradient(to bottom, #f2c94c, #c98b00);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 24;" +
            "-fx-border-radius: 24;" +
            "-fx-border-color: #fff59d;" +
            "-fx-border-width: 2;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 4);" +
            "-fx-cursor: hand;"
        );
        endTurnButton.setPrefWidth(140);
        endTurnButton.setPrefHeight(60);

        endTurnButton.setOnMouseEntered(e -> {
            endTurnButton.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: linear-gradient(to bottom, #f2d44f, #d9a400);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 24;" +
                "-fx-border-radius: 24;" +
                "-fx-border-color: #fff59d;" +
                "-fx-border-width: 2;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 14, 0, 0, 6);" +
                "-fx-cursor: hand;"
            );
        });
        endTurnButton.setOnMouseExited(e -> {
            endTurnButton.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: linear-gradient(to bottom, #f2c94c, #c98b00);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 24;" +
                "-fx-border-radius: 24;" +
                "-fx-border-color: #fff59d;" +
                "-fx-border-width: 2;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 4);" +
                "-fx-cursor: hand;"
            );
        });
        
        endTurnButton.setOnAction(e -> {
			// End the turn for the current player
        	if (!Referee.gameOver) {
				Referee.turnEnd();
				// Update the current player label
				currentPlayerLabel.setText(Referee.playersCycle.get(Referee.currentPlayerIndex).getName() + "'s Turn");
				
				// Update the side info panels for both players
				sideInfoPlayer1.updateScore(Referee.playersCycle.get(0));
				sideInfoPlayer1.updateCardsLeft(Referee.playersCycle.get(0));
				sideInfoPlayer2.updateScore(Referee.playersCycle.get(1));
				sideInfoPlayer2.updateCardsLeft(Referee.playersCycle.get(1));
				
				// Update the rack panel for the current player
				playerRackPanel.displayRack(Referee.playersCycle.get(Referee.currentPlayerIndex));
        	}

		});

        HBox rackAndButton = new HBox(20, rackWithLabel, endTurnButton);
        rackAndButton.setAlignment(Pos.CENTER);
        HBox.setMargin(endTurnButton, new Insets(55, 0, 0, 0));

        HBox bottomContent = new HBox(rackAndButton);
        bottomContent.setPadding(new Insets(0, 130, 170, 300));
        bottomContent.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        gameBoardPanel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        gameBoardPanel.prefWidthProperty().bind(root.widthProperty().multiply(0.5));
        gameBoardPanel.prefHeightProperty().bind(root.heightProperty());

        HBox boardWrapper = new HBox(gameBoardPanel);
        boardWrapper.setAlignment(Pos.CENTER);
        root.setCenter(boardWrapper);
        root.setBottom(bottomContent);

        StackPane leftStack = new StackPane(sideInfoPlayer1);
        leftStack.setPrefWidth(300);
        leftStack.setAlignment(Pos.CENTER);
        BorderPane.setMargin(leftStack, new Insets(0, 10, 0, 140));
        root.setLeft(leftStack);

        StackPane rightStack = new StackPane(sideInfoPlayer2);
        rightStack.setPrefWidth(300);
        rightStack.setAlignment(Pos.CENTER);
        BorderPane.setMargin(rightStack, new Insets(0, 140, 0, 10));
        root.setRight(rightStack);

        Button fullscreenToggle = new Button("⛶");
        fullscreenToggle.setStyle(
            "-fx-font-size: 64px;" +
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;"
        );

        fullscreenToggle.setOnAction(e -> {
            boolean isFull = primaryStage.isFullScreen();
            primaryStage.setFullScreen(!isFull);
            fullscreenToggle.setText(isFull ? "⛶" : "❌");
        });

        HBox topRightControls = new HBox(fullscreenToggle);
        topRightControls.setAlignment(Pos.TOP_RIGHT);
        topRightControls.setPadding(new Insets(10));

        root.setTop(topRightControls);

        StackPane mainContainer = new StackPane(backgroundMediaView, root);

        Scene scene = new Scene(mainContainer);

        primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.setTitle("Plateau de jeu Latice");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
