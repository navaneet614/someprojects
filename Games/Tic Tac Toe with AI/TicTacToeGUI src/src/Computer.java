
import java.util.Random;

public class Computer {

	int lastMove, otherLastMove;

	public int move(int n, String space) {
		String otherSpace = "";
		if (space.equals("X")) {
			lastMove = Screen.playerXLastMove;
			otherLastMove = Screen.playerOLastMove;
			otherSpace = "O";
		} else if (space.equals("O")) {
			lastMove = Screen.playerOLastMove;
			otherLastMove = Screen.playerXLastMove;
			otherSpace = "X";
		}
		int move = 0;
		switch (n) {
		case 1:
			for (int i = 0; i < 9; ++i) {
				if (checkIfPossible(i)) {
					//System.out.println("Computer chooses space#" + (i + 1));
					return ++i;
				}
			}
			return 0;
		case 2:
			n = getRandomValidMove();
			//System.out.println("Computer chooses space#" + (n + 1));
			return ++n;
		case 3:
			move = check2Spaces(space);
			if (move == 100) {
				move = check2Spaces(otherSpace);
				if (move == 100) {
					if (lastMove != -1) {
						move = getBestMove(space);
						//System.out.println("Computer chooses space#" + (move + 1));
						return ++move;
					} else if (otherLastMove != -1) {
//						if (otherLastMove==0||otherLastMove==2||otherLastMove==6||otherLastMove==8)
//							move = this.getOppositeMove(otherLastMove);
						if(checkIfPossible(4))
							move = 4;
						else 
							move = getFirstMove();
						//System.out.println("Computer chooses space#" + (move + 1));
						return ++move;
					} else {
						move = getFirstMove();
						//System.out.println("Computer chooses space#" + (move + 1));
						return ++move;
					}
				} else {
					//System.out.println("Computer chooses space#" + (move + 1));
					return ++move;
				}
			} else {
				//System.out.println("Computer chooses space#" + (move + 1));
				return ++move;
			}
		}
		return -1;
	}

	private int getRandomValidMove() {
		Random rand = new Random();
		int n = 0;
		while (true) {
			n = rand.nextInt(9);
			if (checkIfPossible(n)) {
				return n;
			}
		}
	}

	private boolean checkIfPossible(int space) {
		if (space > 8 || space < 0)
			return false;
		if (Screen.board[space].equals(" "))
			return true;
		else
			return false;
	}

	private boolean checkIfSame(int space1, int space2, String move) {
		if (Screen.board[space1].equals(Screen.board[space2]) && Screen.board[space1].equals(move)
				&& Screen.board[space2].equals(move)) {
			return true;
		} else {
			return false;
		}
	}

	private int check2Spaces(String move) {
		// horizontal
		if (checkIfSame(0, 1, move) && checkIfPossible(2))
			return 2;
		if (checkIfSame(3, 4, move) && checkIfPossible(5))
			return 5;
		if (checkIfSame(6, 7, move) && checkIfPossible(8))
			return 8;

		if (checkIfSame(1, 2, move) && checkIfPossible(0))
			return 0;
		if (checkIfSame(4, 5, move) && checkIfPossible(3))
			return 3;
		if (checkIfSame(7, 8, move) && checkIfPossible(6))
			return 6;

		if (checkIfSame(0, 2, move) && checkIfPossible(1))
			return 1;
		if (checkIfSame(3, 5, move) && checkIfPossible(4))
			return 4;
		if (checkIfSame(6, 8, move) && checkIfPossible(7))
			return 7;

		// vertical
		if (checkIfSame(0, 3, move) && checkIfPossible(6))
			return 6;
		if (checkIfSame(1, 4, move) && checkIfPossible(7))
			return 7;
		if (checkIfSame(2, 5, move) && checkIfPossible(8))
			return 8;

		if (checkIfSame(3, 6, move) && checkIfPossible(0))
			return 0;
		if (checkIfSame(4, 7, move) && checkIfPossible(1))
			return 1;
		if (checkIfSame(5, 8, move) && checkIfPossible(2))
			return 2;

		if (checkIfSame(0, 6, move) && checkIfPossible(3))
			return 3;
		if (checkIfSame(1, 7, move) && checkIfPossible(4))
			return 4;
		if (checkIfSame(2, 8, move) && checkIfPossible(5))
			return 5;

		// diagonal
		if (checkIfSame(0, 4, move) && checkIfPossible(8))
			return 8;
		if (checkIfSame(4, 8, move) && checkIfPossible(0))
			return 0;
		if (checkIfSame(2, 4, move) && checkIfPossible(6))
			return 6;
		if (checkIfSame(4, 6, move) && checkIfPossible(2))
			return 2;
		if (checkIfSame(0, 8, move) && checkIfPossible(4))
			return 4;
		if (checkIfSame(2, 6, move) && checkIfPossible(4))
			return 4;

		return 100;
	}
	
	private int getOppositeMove(int opp){
		switch(opp){
		case 0:
			if(this.checkIfPossible(8))
				return 8;
			break;
		case 2:
			if(this.checkIfPossible(6))
				return 6;
			break;
		case 6:
			if(this.checkIfPossible(2))
				return 2;
			break;
		case 8:
			if(this.checkIfPossible(0))
				return 0;
			break;
		}
		return 100;
	}

	private int getFirstMove() {
		Random rand = new Random();
		while (checkIfPossible(0) || checkIfPossible(2) || checkIfPossible(6)
				|| checkIfPossible(8)) {
			switch (rand.nextInt(4) + 1) {
			case 1:
				if (checkIfPossible(0))
					return 0;
				break;
			case 2:
				if (checkIfPossible(2))
					return 2;
				break;
			case 3:
				if (checkIfPossible(6))
					return 6;
				break;
			case 4:
				if (checkIfPossible(8))
					return 8;
				break;
			}
		}
		return 100;
	}

	private int getBestMove(String move) {
		if((otherLastMove==0||otherLastMove==2||otherLastMove==6||otherLastMove==8)&&(this.getOppositeMove(lastMove))!=otherLastMove){
			if(this.checkIfPossible(getOppositeMove(lastMove))) 
				return getOppositeMove(lastMove);
		}
		String[] board = new String[Screen.board.length];
		for (int i = 0; i < Screen.board.length; i++) {
			board[i] = Screen.board[i];
		}
		int chains[] = new int[9];
		for (int i = 0; i < chains.length; i++) {
			if (checkIfPossible(i)){
				board[i] = move;
				chains[i] = getNumOfChainsCreated(i, move, board);
				board[i] = " ";
			}
			else
				chains[i] = -1;
		}
		
//		for(int i = 0;i<chains.length;i++){
//			System.out.println("chains of "+i+" is:"+chains[i]);
//		}
		int out = 0, j = 0;
		do{
		String s = "";
		for(int i = 0, max = chains[0];i<chains.length;i++){
			if (chains[i] > max) {
				s = Integer.toString(i);
				max = chains[i];
			}
			else if(chains[i]==max)
				s+=Integer.toString(i);
		}
//		System.out.println(s);
		Random rand = new Random();
		out = Integer.parseInt(Character.toString(s.charAt(rand.nextInt(s.length()))));
		j++;
		if(j>=100)
			break;
//		System.out.println(out+"" + isNextTo(out, lastMove));
		} while(isNextTo(out, lastMove)&&!isNextTo(out, otherLastMove)&&(out+lastMove!=8));   //!isNextTo(out, lastMove)&&
		return out;
	}
	
	private boolean isNextTo(int move, int otherMove){
		switch(move){
		case 0:
			if(otherMove==1||otherMove==3||otherMove==4)
				return true;
			else return false;
		case 1:
			if(otherMove==0||otherMove==2||otherMove==4)
				return true;
			else return false;
		case 2:
			if(otherMove==1||otherMove==5||otherMove==4)
				return true;
			else return false;
		case 3:
			if(otherMove==0||otherMove==6||otherMove==4)
				return true;
			else return false;
		case 4:
			return true;
		case 5:
			if(otherMove==2||otherMove==8||otherMove==4)
				return true;
			else return false;
		case 6:
			if(otherMove==7||otherMove==3||otherMove==4)
				return true;
			else return false;
		case 7:
			if(otherMove==6||otherMove==8||otherMove==4)
				return true;
			else return false;
		case 8:
			if(otherMove==5||otherMove==7||otherMove==4)
				return true;
			else return false;
		}
		return false;
	}

	private int getNumOfChainsCreated(int space, String move, String[] board) {
		int out = 0;
		switch (space) {
		case 0:
			if (checkIfSame(0, 1, move, board) && checkIfPossible(2))
				out++;
			if (checkIfSame(0, 2, move, board) && checkIfPossible(1))
				out++;
			if (checkIfSame(0, 3, move, board) && checkIfPossible(6))
				out++;
			if (checkIfSame(0, 6, move, board) && checkIfPossible(3))
				out++;
			if (checkIfSame(0, 4, move, board) && checkIfPossible(8))
				out++;
			if (checkIfSame(0, 8, move, board) && checkIfPossible(4))
				out++;
			break;
		case 1:
			if (checkIfSame(1, 0, move, board) && checkIfPossible(2))
				out++;
			if (checkIfSame(1, 2, move, board) && checkIfPossible(0))
				out++;
			if (checkIfSame(1, 4, move, board) && checkIfPossible(7))
				out++;
			if (checkIfSame(1, 7, move, board) && checkIfPossible(4))
				out++;
			break;
		case 2:
			if (checkIfSame(2, 1, move, board) && checkIfPossible(0))
				out++;
			if (checkIfSame(2, 0, move, board) && checkIfPossible(1))
				out++;
			if (checkIfSame(2, 5, move, board) && checkIfPossible(8))
				out++;
			if (checkIfSame(2, 8, move, board) && checkIfPossible(5))
				out++;
			if (checkIfSame(2, 4, move, board) && checkIfPossible(6))
				out++;
			if (checkIfSame(2, 6, move, board) && checkIfPossible(4))
				out++;
			break;
		case 3:
			if (checkIfSame(3, 0, move, board) && checkIfPossible(6))
				out++;
			if (checkIfSame(3, 6, move, board) && checkIfPossible(0))
				out++;
			if (checkIfSame(3, 4, move, board) && checkIfPossible(5))
				out++;
			if (checkIfSame(3, 5, move, board) && checkIfPossible(4))
				out++;
			break;
		case 4:
			if (checkIfSame(4, 0, move, board) && checkIfPossible(8))
				out++;
			if (checkIfSame(4, 1, move, board) && checkIfPossible(7))
				out++;
			if (checkIfSame(4, 2, move, board) && checkIfPossible(6))
				out++;
			if (checkIfSame(4, 3, move, board) && checkIfPossible(5))
				out++;
			if (checkIfSame(4, 5, move, board) && checkIfPossible(3))
				out++;
			if (checkIfSame(4, 6, move, board) && checkIfPossible(2))
				out++;
			if (checkIfSame(4, 7, move, board) && checkIfPossible(1))
				out++;
			if (checkIfSame(4, 8, move, board) && checkIfPossible(0))
				out++;
			break;
		case 5:
			if (checkIfSame(5, 2, move, board) && checkIfPossible(8))
				out++;
			if (checkIfSame(5, 8, move, board) && checkIfPossible(2))
				out++;
			if (checkIfSame(5, 4, move, board) && checkIfPossible(3))
				out++;
			if (checkIfSame(5, 3, move, board) && checkIfPossible(4))
				out++;
			break;
		case 6:
			if (checkIfSame(6, 3, move, board) && checkIfPossible(0))
				out++;
			if (checkIfSame(6, 0, move, board) && checkIfPossible(3))
				out++;
			if (checkIfSame(6, 7, move, board) && checkIfPossible(8))
				out++;
			if (checkIfSame(6, 8, move, board) && checkIfPossible(7))
				out++;
			if (checkIfSame(6, 4, move, board) && checkIfPossible(2))
				out++;
			if (checkIfSame(6, 2, move, board) && checkIfPossible(4))
				out++;
			break;
		case 7:
			if (checkIfSame(7, 6, move, board) && checkIfPossible(8))
				out++;
			if (checkIfSame(7, 8, move, board) && checkIfPossible(6))
				out++;
			if (checkIfSame(7, 4, move, board) && checkIfPossible(1))
				out++;
			if (checkIfSame(7, 1, move, board) && checkIfPossible(4))
				out++;
			break;
		case 8:
			if (checkIfSame(8, 5, move, board) && checkIfPossible(2))
				out++;
			if (checkIfSame(8, 2, move, board) && checkIfPossible(5))
				out++;
			if (checkIfSame(8, 7, move, board) && checkIfPossible(6))
				out++;
			if (checkIfSame(8, 6, move, board) && checkIfPossible(7))
				out++;
			if (checkIfSame(8, 4, move, board) && checkIfPossible(0))
				out++;
			if (checkIfSame(8, 0, move, board) && checkIfPossible(4))
				out++;
			break;
		}
		return out;
	}

	private boolean checkIfSame(int space1, int space2, String move, String[] board) {
		if (board[space1].equals(board[space2]) && board[space1].equals(move) && board[space2].equals(move))
			return true;
		else
			return false;
	}
}
