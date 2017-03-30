//package packageName;
import java.io.*;

public class W06Practicalshit {
	
	private static final int SQUARES = 8;    //initializing all the squares for the array dimensions, then all the possibilities for each square
	
	private static final int EMPTY = 0;
	private static final int BLACK_BISHOP = 1;
	private static final int BLACK_ROOK = 2;
	private static final int WHITE_BISHOP = 3;
	private static final int WHITE_ROOK = 4;
	
	public static void printBoard(int[][] board) {          //prints my array with the intended unicode characters for prettiness 
		System.out.println();
		for (int row = 7; row >= 0; row--) {            //counting down on the array itself
			
			for (int col = 0; col < SQUARES; col++) {
				switch (board[row][col]) {
					case EMPTY:
						System.out.print("▢ ");   //prints the different possibilities for each array coordinate
						break;
					case BLACK_BISHOP:
						System.out.print("♗ ");
						break;
					case BLACK_ROOK:
						System.out.print("♖ ");
						break;
					case WHITE_BISHOP:
						System.out.print("♝ ");
						break;
					case WHITE_ROOK:
						System.out.print("♜ ");
						break;
					default:
						System.out.print("  ");
				}
			}
			System.out.print(row+1 + "  ");         //printing the row numbers on the side
			System.out.println();
		}
		System.out.println("a b c d e f g h ");         //printing column letters on the bottom
	}
	
	public static int rowChoose(int[][] board) {    //Y COORDINATES input
		int valid = 1;                          //Gotta initialize them values
		int startingRow = 0;
		
		while (valid == 1) {                   //same purpose as boolean, just more options than true or false
			System.out.println();
			System.out.print("Enter a row: ");
			startingRow = EasyIn.getInt();
			startingRow--;
			if ((startingRow < 0) || (startingRow >= board.length)) {  //ahhhh you didn't put a valid coordinate
				System.out.println("Invalid input");
				}
			else {
				valid = 0;
			}		
		}
		return startingRow;
	}
	
	public static int colChoose(int[][] board) {    //X COORDINATES
		char charCol;                           //the input will be in char but will be converted into int
		int startingCol = 0;
		int valid = 1;
		
		while (valid == 1) {
			System.out.println();
			System.out.print("Enter a column: ");
			charCol = EasyIn.getChar();
			startingCol = Character.getNumericValue(charCol);    //right here it gets converted
			startingCol -= 10;                                   //numerical value for characters starts at 10, so pushes it back 10
			if ((startingCol < 0) || (startingCol >= board.length)) {
				System.out.println("Invalid input");         //ahhhh cheeky ya did it again!!!!
				
			}
			else {
				valid = 0;
			}				
		}
		return startingCol;
	}
	
	public static int checkPiece(int[][] board, int row, int col) {      //defines pieces for use later
		int pieceIdentity=0;
		if (board[row][col] == EMPTY) {
			pieceIdentity = 0;
    	    	}
    	    	if (board[row][col] == BLACK_BISHOP) {
    	    		pieceIdentity = 1;
    	    	}
    	    	if (board[row][col] == BLACK_ROOK) {
    	    		pieceIdentity = 2;
    	    	}
    	    	if (board[row][col] == WHITE_BISHOP) {
    	    		pieceIdentity = 3;
    	    	}
    	    	if (board[row][col] == WHITE_ROOK) {
    	    		pieceIdentity = 4;
    	    	}
    	    	return pieceIdentity;
    	}
	
    	public static boolean bishopValidMove(int[][] board, int targetRow, int targetCol, int startingRow, int startingCol, int color) {
		int totalRowsPositive = 0;            //just trying to initialize some int variables
		int totalColsPositive = 0;
		int totalRows = 0;
		int totalCols = 0;
		boolean validMove=false;
		int rowCheck = 0;
		int colCheck = 0;
		
		totalRows = (targetRow - startingRow);  //simple arithmetic to find values needed later
		totalCols = (targetCol - startingCol);
		totalRowsPositive = Math.abs(totalRows); //absolute value incase bishop is moving negative one direction and positive the other
		totalColsPositive = Math.abs(totalCols);
		
		if (totalRowsPositive == totalColsPositive) {   //if bishop is indeed moving diagonally
			validMove = obstruction(board, targetRow, targetCol, color, rowCheck, colCheck, startingRow, startingCol, validMove);
			
		}
		return validMove;
	}
	
	public static boolean rookValidMove(int[][] board, int targetRow, int targetCol, int startingRow, int startingCol, int color) {
		int totalRowsPositive = 0;
		int totalColsPositive = 0;
		int totalRows = 0;
		int totalCols = 0;
		boolean validMove=false;
		int rowCheck = 0;
		int colCheck = 0;
		
		totalRows = (targetRow - startingRow);  //rook's got easy arithmetic to find values needed later
		totalCols = (targetCol - startingCol);

		if ((totalRows != 0) && (totalCols == 0) || (totalRows == 0) && (totalCols != 0)) { //can either move in vertically or horizontally, not both simultaneously 
			
			validMove = obstruction(board, targetRow, targetCol, color, rowCheck, colCheck, startingRow, startingCol, validMove);
		}
		return validMove;
	}
	    
    	    
	public static boolean obstruction(int[][] board, int targetRow, int targetCol, int color, int rowCheck, int colCheck, int startingRow, int startingCol, boolean validMove) {
		boolean tsValid = false;   //essentially booleans that tell when to go on to next step
		boolean obsValid = true;
		boolean whileStop = true;  //tells when to continue checking for obstructing pieces or not, will continue (true) until told not to (false)
		
		int pieceIdentity = checkPiece(board, targetRow, targetCol);
		if ((color == 0) && ((pieceIdentity == 0) || (pieceIdentity == 3) || (pieceIdentity == 4))) {  //white can only get in the way of white
			tsValid=true;
		}
		if ((color == 1) && ((pieceIdentity == 0) || (pieceIdentity == 1) || (pieceIdentity == 2))) {  //black can only get in the way of black
			tsValid=true;
		}
		if (tsValid = true) {
			rowCheck = targetRow;
			colCheck = targetCol;
			while (whileStop == true) {
				if ((board[startingRow][startingCol] == 2) || (board[startingRow][startingCol] == 4)) {  //if rook, only needs either obstructing row OR column to block path
					if ((board[rowCheck][colCheck] != 0) && (((rowCheck != startingRow) || (colCheck != startingCol)) && ((rowCheck != targetRow) || (colCheck != targetCol)))) { //if a piece is there, and it's not the moving piece or the target piece, the path is blocked
						System.out.println("Invalid move, path blocked");
						obsValid = false;
						rowCheck = startingRow;
						colCheck = startingCol;
						whileStop = false;
					}
				}
				if ((board[startingRow][startingCol] == 1) || (board[startingRow][startingCol] == 3)) {  //if bishop, needs both row AND column to block path
					if ((board[rowCheck][colCheck] != 0) && (((rowCheck != startingRow) && (colCheck != startingCol)) && ((rowCheck != targetRow) && (colCheck != targetCol)))) { //same thing as that long one right up there, only applicable to bishop
						System.out.println("Invalid move, path blocked");
						obsValid = false;
						rowCheck = startingRow;
						colCheck = startingCol;
						whileStop = false;
					}
				}
				if (targetRow > startingRow) {    //checking sequence, makes sure computer is checking in correct direction
					rowCheck--;
				}
				if (targetRow < startingRow) {
					rowCheck++;
				}
				if (targetCol > startingCol) {
					colCheck--;
				}
				if (targetCol < startingCol) {
					colCheck++;
				}
				if ((rowCheck == startingRow) && (colCheck == startingCol)) {  //you don't need to check any further than the current place
					whileStop = false;					
				}
				
			}
			if (obsValid==true) {
				validMove=true;
			}
		}
		return validMove;
	}
	
	
	public static int move(int[][] board, int color) {
		boolean bishopValid=false;   //initializing a lot of booleans and ints needed for later
		boolean rookValid=false;
		boolean validMove=false;
		boolean rightColorPiece = false;
		int startingRow = rowChoose(board);
		int startingCol = colChoose(board);
		int pieceIdentity = checkPiece(board, startingRow, startingCol);
		int targetRow=0;
		int targetCol=0;
		
		if ((color == 1) && ((pieceIdentity == 3) || (pieceIdentity == 4))) { //white can only move white
			rightColorPiece = true;
		}
		if ((color == 0) && ((pieceIdentity == 1) || (pieceIdentity == 2))) { //black can only move black
			rightColorPiece = true;
		}
		if (rightColorPiece == true) {
			targetRow = rowChoose(board);      //returns row and column choices again this time as the target coordinate
			targetCol = colChoose(board);
			
			switch (pieceIdentity) {
			case 0:                          //if square is empty, you can't move it can you?!
				
				System.out.println("Empty square selected");
				validMove=false;
				break;
			case 1:                          //if square contains bishop or rook, and if you can move it (if it's your turn), then checks to see if move can be done
				if (color == 0) {
					validMove = bishopValidMove(board, targetRow, targetCol, startingRow, startingCol, color);
				}
				break;
			case 2:
				if (color ==0) {
					validMove = rookValidMove(board, targetRow, targetCol, startingRow, startingCol, color);
				}
				break;
			case 3:
				if (color == 1) {
					validMove = bishopValidMove(board, targetRow, targetCol, startingRow, startingCol, color);
				}
				break;
			case 4:
				if (color == 1) {
					validMove = rookValidMove(board, targetRow, targetCol, startingRow, startingCol, color);
				}
				break;
			}
		}
		else {
			System.out.println("Wrong color piece selected");          //if you selected the other team's piece
		}
		if (validMove == true) {                                           //ACTUAL PIECE MOVEMENT, replaces square with moved piece, sets original square to empty
			board[targetRow][targetCol] = pieceIdentity;
			board[startingRow][startingCol] = EMPTY;
			color = switchColor(color);
		}
		else {
			System.out.println("Please try again");                    //you must've done something wrong
		}
		return color;
	}
	
	public static int switchColor(int color) {                        //tells you whos turn it is, decides who can move, what can be moved, and more!!!
		if (color==0) {
			System.out.println();
			System.out.println("It is White's turn");
			color=1;
		}
		else {
			System.out.println();
			System.out.println("It is Black's turn");
			color=0;
		}
		return color;
	}
	
	public static void play(int[][] board) {                   //the essential method that has everything and nothing, the one and only, the game
		int color=1;
		int isGameOver=0;
		printBoard(board);
		System.out.println();
		System.out.println("Enter command: move or quit");
		String command = "";
		command = EasyIn.getString();
		if (command.equals("move")) {
			System.out.println();
			System.out.println("White may begin");
			System.out.println();
			while (isGameOver == 0) {
				color = move(board, color);
				printBoard(board);
				isGameOver = whoWins(board, color);
			}
		}
		
		else {
			System.out.println("Ok, don't follow directions");
		}
		if (command.equals("quit")) {
			System.out.println("Thanks for NOT playing");
		}
	}
	
	public static int whoWins(int[][] board, int color) {           //decides the victor based on who doesn't have any pieces left
		int blackPieces = 0;
		int whitePieces = 0;
		int whoWins = 0;
		int pieceIdentity = 0;
		int row = 0;
		int col = 0;
		
		for (row = 7; row >= 0; row--) {                                      //checks rows and columns for any pieces each turn, same for other player's turn
			for (col = 0; col < SQUARES; col++) {
				pieceIdentity = checkPiece(board, row, col);
				if ((pieceIdentity == 1) || (pieceIdentity == 2)) {
					blackPieces=1;
				}
			}
		}
		if (blackPieces == 0) {
			System.out.println();
			System.out.println("WHITE WINS");
			whoWins = 1;
		}
		for (row = 7; row >= 0; row--) {
			for (col = 0; col < SQUARES; col++) {
				pieceIdentity = checkPiece(board, row, col);
				if ((pieceIdentity == 3) || (pieceIdentity == 4)) {
					whitePieces=1;
				}
			}
		}
		if (whitePieces == 0) {
			System.out.println();
			System.out.println("BLACK WINS");
			whoWins = 2;
		}
		return whoWins;
	}
	
	public static void main(String[] args) {                                //finally the main method, actually quite simple really, everything is a method in a method almost
		int[][] board = new int[SQUARES][SQUARES];
		for (int row = 7; row >= 0; row--) {
			for (int col = 0; col < SQUARES; col++) {
				
				board[0][0] = BLACK_ROOK;
				board[0][2] = BLACK_BISHOP;
				board[0][5] = BLACK_BISHOP;
				board[0][7] = BLACK_ROOK;
				board[7][0] = WHITE_ROOK;
				board[7][2] = WHITE_BISHOP;
				board[7][5] = WHITE_BISHOP;
				board[7][7] = WHITE_ROOK;
			}
		}
		play(board);
	}
}
