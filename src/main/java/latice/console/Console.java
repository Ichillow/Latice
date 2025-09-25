package latice.console;

import java.util.Map;
import java.util.Scanner;

import latice.cell.Cell;
import latice.cell.CellType;
import latice.cell.Position;
import latice.gameboard.GameBoard;
import latice.player.Pool;
import latice.player.Rack;

// This class is used to print a String to the console
public class Console {
	
	private Console() {
		// Prevent instantiation
	}
	
	private static final Scanner scanner = new Scanner(System.in);
	
	//Function to print a message without a newline
	public static void print(Object obj) {
		System.out.print(obj);
	}

	//Function to print a message with a newline
	public static void println(Object obj) {
		System.out.println(obj);
	}

	//Function to print an Error with a newline
	public static void printError(Object obj) {
		System.err.println(obj);
	}
	
	//Function to ask a question and return the answer
	public static String ask(String question) {
        System.out.print(question);  // Print the question
        return scanner.nextLine();   // Read the user's input
    }
	
	public static void title (String title) {
		println("========================================");
		println("  "+title);
		println("========================================");
	}
	
	public static void printRack(Rack rack) {
		for (int i = 0; i < 5; i++) {
			
			print("|");
			
			if (i < rack.getTiles().size()) {
				print("  "+rack.getTiles().get(i).toString()+"  ");
			} else {
				print(" ");
			}
		}
		
		println("|");
	
	}
	
	public static void printPool(Pool pool) {
		for (int i = 0; i < pool.size(); i++) {
			print("|");
			
			print("  "+pool.getTile(i).toString()+"  ");
			
		}
		println("|");
	}
	
	public static void printBoard(Map<Position,Cell> board) {
		
		println("   1  2  3  4  5  6  7  8  9");
		
		for (int i = 0; i < GameBoard.ROWS; i++) {
			
			print(i+1+": ");
			
			for (int j = 0; j < GameBoard.COLS; j++) {
				
				
				Cell cell = board.get(new Position(i, j));
				
				
				if (cell != null) {
					
					if (cell.getTile() != null) {
						print(cell.getTile().toString());
					} else {
						print(cell.getType().getSymbol());
					}
				} else {
					print(CellType.SEA.getSymbol());
				}
				print(" ");
			}
			println("");
		}

	}
}