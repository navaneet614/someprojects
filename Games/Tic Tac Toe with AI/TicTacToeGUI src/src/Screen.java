
public class Screen {

	static String[] board = new String[9];
	static int playerXLastMove = -1, playerOLastMove = -1, playerXScore = 0, playerOScore = 0, gamesPlayed = 0;

	public static void makeBoard() {
		for (int i = 0; i < 9; i++) {
			board[i] = " ";
		}
	}

	public void drawBoard() {
		System.out.println(board[0] + "|" + board[1] + "|" + board[2]);
		System.out.println("-----");
		System.out.println(board[3] + "|" + board[4] + "|" + board[5]);
		System.out.println("-----");
		System.out.println(board[6] + "|" + board[7] + "|" + board[8]);
	}
	
	public static void clearBoard(){
		for (int i = 0; i < 9; i++) {
			board[i] = " ";
		}
	}
	
	public static boolean boardIsEmpty(){
		for (int i = 0; i < 9; i++) {
			if(!board[i].equals(" "))
				return false;
		}
		return true;
	}

	public void changeSpace(int spaceNum, String move) {
		spaceNum--;
		if (board[spaceNum].equals(" ")) {
			board[spaceNum] = move;
			if (move == "X")
				playerXLastMove = spaceNum;
			else if(move=="O")
				playerOLastMove = spaceNum;
		}
	}
	
	public boolean isValidMove(int space){
		space--;
		if(board[space].equals(" "))
			return true;
		else return false;
	}

	public void checkForWinner() {
		check(board[0], board[1], board[2]);
		check(board[3], board[4], board[5]);
		check(board[6], board[7], board[8]);

		check(board[0], board[3], board[6]);
		check(board[1], board[4], board[7]);
		check(board[2], board[5], board[8]);

		check(board[0], board[4], board[8]);
		check(board[2], board[4], board[6]);

		int j = 0;
		for (int i = 0; i < 9; i++) {
			if (!board[i].equals(" ")) {
				j++;
			}
		}
		if (j >= 9) {
			System.out.println("It's a tie!");
			gamesPlayed++;
			playerXLastMove = -1;
			playerOLastMove = -1;
//			Main.end(false);
		}
	}

	private void check(String s1, String s2, String s3) {
		if (s1.equals(" ") && s2.equals(" ") && s3.equals(" "))
			return;
		if (s1.equals(s2) && s1.equals(s3) && s2.equals(s3)) {
			System.out.println(s1 + " wins!");
			if (s1.equals("X")) {
				playerXScore++;
				gamesPlayed++;
			}
			else if (s1.equals("O")) {
				playerOScore++;
				gamesPlayed++;
			}
			playerXLastMove = -1;
			playerOLastMove = -1;
//			Main.end(false);
		}
	}
	
	public static boolean isBoardFull(){
		int j = 0;
		for (int i = 0; i < 9; i++) {
			if (!board[i].equals(" ")) {
				j++;
			}
		}
		if (j >= 9) 
			return true;
		else 
			return false;
	}
}
