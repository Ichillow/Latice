package latice.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import latice.cell.CellType;
import latice.cell.Position;
import latice.console.Console;
import latice.gameboard.GameBoard;
import latice.player.Player;
import latice.player.Pool;
import latice.player.Rack;
import latice.tile.Color;
import latice.tile.Shape;
import latice.tile.Tile;

public class Referee {
	
	private final GameBoard gameboard;

    public List<Player> players;
    public static List<Player> playersCycle = new ArrayList<>();
    
    
    private static final Integer RACKSIZE = 5;
    private static final Integer NB_PLAYER = 2; // Number of players, can be adjusted as needed
    public static int currentPlayerIndex = 0; // Index of the current player in the cycle
    public static int turnCount = 0; // Counter for the number of turns taken
    public static final int MAX_TURN = 10; // Example maximum number of turns, can be adjusted as needed
    public static boolean gameOver = false; // Flag to indicate if the game is over
    
    // Constructor to initialize the referee with a list of players
    public Referee() {
    	
    	gameboard = new GameBoard();
    	
    	this.players = new ArrayList<>();
    	
    	for (int i = 0; i < NB_PLAYER; i++) {
			this.players.add(new Player("Player " + (i + 1)));
		}
    	
    	createPlayerPool();
    	
    	for (Player player : this.players) {
			player.setRack(new Rack());
			draw(player.getRack(), player.getPool());
		}
    	
    	createCycle();
    	
    }
    
    // Draw tiles from the pool and add them to the player's rack
    public static Rack draw(Rack rack, Pool pool) {
    	
        while (rack.size() < RACKSIZE && !pool.isEmpty()) {
            Tile tile = pool.drawTile();
            rack.addTile(tile);
        }
        return rack;
    }

    
    // Create a pool of tiles for each player
    public void createPlayerPool() {
    	List<Tile> mainTiles = new ArrayList<>();
    	
    	for (Color color : Color.values()) {
    		if (color != Color.RESET && color != Color.WHITE) {
    			for (Shape shape : Shape.values()) {
    				mainTiles.add(new Tile(color, shape));
    				mainTiles.add(new Tile(color, shape));
    			}
    		}
     	}    	
    	
    	Collections.shuffle(mainTiles);

        int tilesPerPlayer = mainTiles.size() / players.size();

        for (Player player : players) {
            List<Tile> playerTiles = new ArrayList<>();

            for (int i = 0; i < tilesPerPlayer; i++) {
                if (!mainTiles.isEmpty()) {
                    playerTiles.add(mainTiles.remove(0));
                }
            }

            Pool playerPool = new Pool(playerTiles);
            player.setPool(playerPool);
        }
    }
    
    
    
    // Choose a random player from the list of players
    public Player choosePlayer() {
    	Random rand = new Random();
    	int randomIndex = rand.nextInt(players.size());
    	Player chosenPlayer = players.get(randomIndex);
    	
    	return chosenPlayer;
    }


    public static boolean isPlacementValid(Position position, Tile tile) {
    	
    	String color = tile.getColor();
    	String shape = tile.getShape();
    	Position centerPosition = new Position(GameBoard.MID_ROW, GameBoard.MID_COL);
    	boolean haveNeighbors = false;
    	
    	if (GameBoard.cells.containsKey(position)) {
    		// Check if the center position is occupied by a tile
    		if (GameBoard.cells.get(centerPosition).getTile() != null) {
    			
    			if (GameBoard.cells.get(position).getTile() != null) {
    				return false; // Position already occupied
    			}
    			else {
    				// Check if the adjacency Tile are the same color or shape
    				for (Position neighbor : position.getNeighbors()) {
						if (GameBoard.cells.containsKey(neighbor)) {
							Tile neighborTile = GameBoard.cells.get(neighbor).getTile();
							if (neighborTile != null) {
								haveNeighbors = true; // At least one neighbor exists
								if (!neighborTile.getColor().equals(color) && !neighborTile.getShape().equals(shape)) {
									return false; // Adjacent tile does not match color or shape
								}
							}
						}
					
					}
    				if (!haveNeighbors) {
						return false; // No adjacent tiles to match with
					}
    			}
    			
    		}
    		else if (!position.equals(centerPosition)) {
					return false; // Center position is empty and the position is not the center
				}
			}
    		
		return true;
    }
    
    public static void pointCalcul(Position position, Tile tile, Player player) {
    	
    	List<Position> neighbors = position.getNeighbors();
    	int validNeighborsCount = 0;
    	
        	if (GameBoard.getCell(position).getType()== CellType.SUN) {
        		player.addPoint(2); // Add 1 point for placing a tile on a SUN cell
        	}
        	


        	for (Position neighbor : neighbors) {
        	    if (GameBoard.cells.containsKey(neighbor)) {
        	        Tile neighborTile = GameBoard.cells.get(neighbor).getTile();
        	        if (neighborTile != null) {
        	            // If same color or shape 
        	            if (neighborTile.getColor().equals(tile.getColor()) || neighborTile.getShape().equals(tile.getShape())) {
        	                validNeighborsCount++;
        	            }
        	        }
        	    }
        	}
        	
            switch (validNeighborsCount) {
                case 2:
                    // Two neighbors, 1 points
                    player.addPoint(1);
                    break;
                case 3:
                    // Three neighbors, 2 points
                    player.addPoint(2);
                    break;
                case 4:
                    // Four neighbors, 4 points
                    player.addPoint(4);
                    break;
                default:
                    // No neighbors or one neighbors, 0 points
                    break;
            }
        }

  

    // Method to end the game and determine the winner(s)
    public static void gameEnd() {
    	gameOver = true; // Set the game over flag 

        Player winner = null;
        int maxTilesPlaced = -1;
        
        for (Player player : playersCycle) {
            int tilesPlaced = player.getTilesPlaced();
            if (tilesPlaced > maxTilesPlaced) {
                maxTilesPlaced = tilesPlaced;
                winner = player;
            }
        }
        
        if (winner != null) {
            Console.println("Game Over! The winner is " + winner.getName() + " with " + maxTilesPlaced + " tiles placed.");
        } else {
            Console.println("Game Over! No winner could be determined.");
        }
    }
    	
    	
   
    
    // Method to end the turn for the current player
    public static void turnEnd() {
    	if (currentPlayerIndex < playersCycle.size() - 1) {
			currentPlayerIndex++;
		} else {
			currentPlayerIndex = 0; // Reset to the first player
		}
    	turnCount++;
		
		if (turnCount == MAX_TURN ) { 
			gameEnd();
		}
    }

    // Method to create a cycle of players 
    public void createCycle() {
    	while (playersCycle.size() != NB_PLAYER){
    		Player currentPlayer = choosePlayer();
    		if (!playersCycle.contains(currentPlayer)) {
				playersCycle.add(currentPlayer);
			}
    	}
    }
    
    public static void doAction(Position position, Tile tile) {
    	// Check if the game is over before performing any action
    	if (gameOver) {
			Console.println("The game is over. No more actions can be performed.");
			return;
		}
    	
		Player currentPlayer = playersCycle.get(currentPlayerIndex);
		GameBoard.setTile(position, tile);
		pointCalcul(position, tile, currentPlayer);
		currentPlayer.playTile(currentPlayer.getRack().getTiles().indexOf(tile));
		currentPlayer.incrementTilesPlaced();
		Referee.draw(currentPlayer.getRack(), currentPlayer.getPool());
	}
	
    
    public GameBoard getGameBoard() {
    	return gameboard;
    }
}