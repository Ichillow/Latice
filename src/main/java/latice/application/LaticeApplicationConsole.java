package latice.application;

import latice.cell.Position;
import latice.console.Console;
import latice.controller.Referee;
import latice.gameboard.GameBoard;
import latice.player.Player;
import latice.view.MainWindow;



public class LaticeApplicationConsole {

	
	public static void main(String[] args) {
		Console.title("Latice Game");
		
		Console.println("Welcome to Latice!");
		
		Console.println("Choose your game mode:");
		Console.println("1. Console");
		Console.println("2. GUI");
		
		String choice = "";
		Boolean isValide = false;
		
		// Loop until the user enters a valid choice
		while (Boolean.TRUE.equals(isValide) == false) {
			choice = Console.ask("Enter your choice (1 or 2): ");
			
			// Console if the choice is 1
			if (choice.equals("1")) {
				isValide = true;
				Console.println("You chose Console mode.");
				Console.println("Starting game in console mode...");
				
				// Start the console game
				startConsoleGame();
				
			
			// GUI if the choice is 2
			} else if (choice.equals("2")) {
				isValide = true;
				Console.println("You chose GUI mode.");
				Console.println("Starting game in GUI mode...");
				
				// Start the GUI game
				MainWindow.main(args);
			} else {
				Console.printError("Invalid choice. Please enter 1 or 2.");
			}
		}

	}
	
	
	private static void startConsoleGame() {
		
		// Initialize the referee
		Referee referee = new Referee();
		
		
		Console.title("Latice Game - Console Mode");
		
		Console.println("Game started in console mode.");
		
		gameLoop();
		
	}
	
	private static void gameLoop() {

		
		int playerTileIndex = -1; // Index of the tile the player wants to place
		
		
		String playerChoice = "";
		while (Referee.gameOver == false) {
						
			Player player = Referee.playersCycle.get(Referee.currentPlayerIndex);
				
			Console.printBoard(GameBoard.cells);
			
			Console.println("Current player: " + player.getName());
			Console.printRack(player.getRack());
			
			
			// Ask the player to choose what he wants to do
			Console.println("What would you like to do?");
			Console.println("1. Place a tile");
			Console.println("2. End your turn");
			Console.println("3. Exit the game");
			
			playerChoice = Console.ask("Enter your choice (1, 2, or 3): ");
			
			switch (playerChoice) {
				case "1":
					// Validate the player's choice
					if (player.getRack().getTiles().isEmpty()) {
						Console.printError("You have no tiles to place. Please draw a tile first.");
						continue; // Skip to the next iteration if no tiles are available
					}
					// Player wants to place a tile
					Console.println("You chose to place a tile.");
					placeTile(player);
					break;
				case "2":
					// Player ends their turn
					Console.println("You ended your turn.");
					Referee.turnEnd();
					break;
				case "3":
					// Player wants to exit the game
					Console.println("Exiting the game. Goodbye!");
					Referee.gameOver = false;
					break;
				default:
					Console.printError("Invalid choice. Please enter 1, 2, or 3.");
			}
		}
	}
	
	
	private static void placeTile(Player player) {
		
		// Ask the player for their move
		int playerTileIndexInput = -1;
		String playerMove = "";
				
			
				
		// Ask the player for the tile they want to place
		Console.printRack(player.getRack());
		try {
			playerTileIndexInput = Integer.parseInt(Console.ask("Enter the index of the tile you want to place (1 to " + player.getRack().getTiles().size() + "): "));
		} catch (NumberFormatException e) {
			Console.printError("Invalid input. Please enter a valid index.");
		}
		
	
		// Ask the player for their move
		playerMove = Console.ask("Enter your move (e.g., '11 ; 42 ; 36'):");
		
		if (playerMove.isEmpty()) {
			Console.printError("Move cannot be empty. Please try again.");
		} else if (playerMove.length() != 2 || !Character.isDigit(playerMove.charAt(0)) || !Character.isDigit(playerMove.charAt(1))) {
			Console.printError("Invalid move format. Please enter a valid position (e.g., '11' for row 1, column 1).");
		} else {
			// Process the player's move
			int x = playerMove.charAt(0) - '0'; // Convert char to int
			int y = playerMove.charAt(1) - '0';
			Console.println("Placing tile at position: (" + x + ", " + y + ")");
			Position movePosition = new Position(x - 1, y - 1);
			
			// Try to place the tile
			try {
				Referee.doAction(movePosition, player.getRack().getTiles().get(playerTileIndexInput - 1));
				Console.println("Tile placed successfully at position: (" + x + ", " + y + ")");
				
				// Increment the player's tiles placed count
				player.incrementTilesPlaced();
			} catch (IllegalArgumentException e) {
				Console.printError("Error placing tile: " + e.getMessage());
			}
		}
			
		Console.println("Game loop ended.");
	}

}
