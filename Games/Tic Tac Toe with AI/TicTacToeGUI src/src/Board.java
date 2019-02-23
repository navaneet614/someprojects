import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements MouseListener, ActionListener {
	
	public static boolean winner = false;
	private Timer t;
	
	public static String move = "X";
	public static String winMove = " ";
	public static int difficulty = 3;
	public String[] boardCopy = new String[9];
	public boolean blankScreen = false;
	
	public Board(){
		this.setBackground(Color.WHITE);
		for(int i = 0;i<boardCopy.length;i++) {
			boardCopy[i] = " ";
		}
		this.addMouseListener(this);
		t = new Timer(250, this);
		t.setRepeats(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(blankScreen){
			g.drawRect(0, 0, this.getWidth(), this.getHeight());
			this.setToolTipText("");
			return;
		}
		g.setColor(Color.BLACK);
		g.drawLine(getWidth() / 3, 0, this.getWidth() / 3, this.getHeight());
		g.drawLine(getWidth() / 3 * 2, 0, this.getWidth() / 3 * 2, this.getHeight());
		g.drawLine(0, this.getHeight() / 3, this.getWidth(), this.getHeight() / 3);
		g.drawLine(0, this.getHeight() / 3 * 2, this.getWidth(), this.getHeight() / 3 * 2);
		updateSpaces(g);
		doWinner(g);
		Information.change.setEnabled(Screen.boardIsEmpty());
		if(Screen.boardIsEmpty()&&winner)
			this.setToolTipText("Click anywhere to restart.");
		else this.setToolTipText("Click a space to move.");
	}

	public void updateSpaces(Graphics g) {
		String[] thisBoard = new String[9];
		if(Screen.boardIsEmpty()&&winner){
			thisBoard = this.boardCopy;
		}
		else thisBoard = Screen.board;
		for (int i = 0; i < thisBoard.length; i++) {
			if (!thisBoard[i].equals(" ")) {
				if (thisBoard[i].equals("X"))
					drawX(i, g);
				else if (thisBoard[i].equals("O"))
					drawO(i, g);
			}
		}
	}
	
	public void resetBoardCopy(){
		for(int i = 0;i<boardCopy.length;i++) {
			boardCopy[i] = " ";
		}
	}

	public void drawX(int space, Graphics g) {
		g.setColor(Color.BLUE);
		switch (space) {
		case 0:
			g.drawLine(10, 10, this.getWidth() / 3 - 10, this.getHeight() / 3 - 10);
			g.drawLine(this.getWidth() / 3 - 10, 0 + 10, 0 + 10, this.getHeight() / 3 - 10);
			break;
		case 1:
			g.drawLine(this.getWidth() / 3 + 10, 0 + 10, this.getWidth() / 3 * 2 - 10, this.getHeight() / 3 - 10);
			g.drawLine(this.getWidth() / 3 * 2 - 10, 0 + 10, this.getWidth() / 3 + 10, this.getHeight() / 3 - 10);
			break;
		case 2:
			g.drawLine(this.getWidth() / 3 * 2 + 10, 0 + 10, this.getWidth() - 10, this.getHeight() / 3 - 10);
			g.drawLine(this.getWidth() - 10, 0 + 10, this.getWidth() / 3 * 2 + 10, this.getHeight() / 3 - 10);
			break;
		case 3:
			g.drawLine(0 + 10, this.getHeight() / 3 + 10, this.getWidth() / 3 - 10, this.getHeight() / 3 * 2 - 10);
			g.drawLine(this.getWidth() / 3 - 10, this.getHeight() / 3 + 10, 0 + 10, this.getHeight() / 3 * 2 - 10);
			break;
		case 4:
			g.drawLine(this.getWidth() / 3 + 10, this.getHeight() / 3 + 10, this.getWidth() / 3 * 2 - 10,
					this.getHeight() / 3 * 2 - 10);
			g.drawLine(this.getWidth() / 3 * 2 - 10, this.getHeight() / 3 + 10, this.getWidth() / 3 + 10,
					this.getHeight() / 3 * 2 - 10);
			break;
		case 5:
			g.drawLine(this.getWidth() / 3 * 2 + 10, this.getHeight() / 3 + 10, this.getWidth() - 10,
					this.getHeight() / 3 * 2 - 10);
			g.drawLine(this.getWidth() - 10, this.getHeight() / 3 + 10, this.getWidth() / 3 * 2 + 10,
					this.getHeight() / 3 * 2 - 10);
			break;
		case 6:
			g.drawLine(0 + 10, this.getHeight() / 3 * 2 + 10, this.getWidth() / 3 - 10, this.getHeight() - 10);
			g.drawLine(this.getWidth() / 3 - 10, this.getHeight() / 3 * 2 + 10, 0 + 10, this.getHeight() - 10);
			break;
		case 7:
			g.drawLine(this.getWidth() / 3 + 10, this.getHeight() / 3 * 2 + 10, this.getWidth() / 3 * 2 - 10,
					this.getHeight() - 10);
			g.drawLine(this.getWidth() / 3 * 2 - 10, this.getHeight() / 3 * 2 + 10, this.getWidth() / 3 + 10,
					this.getHeight() - 10);
			break;
		case 8:
			g.drawLine(this.getWidth() / 3 * 2 + 10, this.getHeight() / 3 * 2 + 10, this.getWidth() - 10,
					this.getHeight() - 10);
			g.drawLine(this.getWidth() - 10, this.getHeight() / 3 * 2 + 10, this.getWidth() / 3 * 2 + 10,
					this.getHeight() - 10);
			break;
		}
	}

	public void drawO(int space, Graphics g) {
		g.setColor(Color.RED);
		switch (space) {
		case 0:
			g.drawOval(10, 10, this.getWidth() / 3 - 20, this.getHeight() / 3 - 20);
			break;
		case 1:
			g.drawOval(this.getWidth() / 3 + 10, 0 + 10, this.getWidth() / 3 - 20, this.getHeight() / 3 - 20);
			break;
		case 2:
			g.drawOval(this.getWidth() / 3 * 2 + 10, 0 + 10, this.getWidth() / 3 - 20, this.getHeight() / 3 - 20);
			break;
		case 3:
			g.drawOval(0 + 10, this.getHeight() / 3 + 10, this.getWidth() / 3 - 20, this.getHeight() / 3 - 20);
			break;
		case 4:
			g.drawOval(this.getWidth() / 3 + 10, this.getHeight() / 3 + 10, this.getWidth() / 3 - 20,
					this.getHeight() / 3 - 20);
			break;
		case 5:
			g.drawOval(this.getWidth() / 3 * 2 + 10, this.getHeight() / 3 + 10, this.getWidth() / 3 - 20,
					this.getHeight() / 3 - 20);
			break;
		case 6:
			g.drawOval(0 + 10, this.getHeight() / 3 * 2 + 10, this.getWidth() / 3 - 20, this.getHeight() / 3 - 20);
			break;
		case 7:
			g.drawOval(this.getWidth() / 3 + 10, this.getHeight() / 3 * 2 + 10, this.getWidth() / 3 - 20,
					this.getHeight() / 3 - 20);
			break;
		case 8:
			g.drawOval(this.getWidth() / 3 * 2 + 10, this.getHeight() / 3 * 2 + 10, this.getWidth() / 3 - 20,
					this.getHeight() / 3 - 20);
			break;
		}
	}

	public void doWinner(Graphics g) {
		if(!Screen.boardIsEmpty())
		for(int i = 0;i<this.boardCopy.length;i++)
			this.boardCopy[i] = Screen.board[i];
		
		check(0, 1, 2, g);
		check(3, 4, 5, g);
		check(6, 7, 8, g);

		check(0, 3, 6, g);
		check(1, 4, 7, g);
		check(2, 5, 8, g);
		
		check(0, 4, 8, g);
		check(2, 4, 6, g);


		int j = 0;
		for (int i = 0; i < 9; i++) {
			if (!Screen.board[i].equals(" ")) {
				j++;
			}
		}
		if (j >= 9) {
			System.out.println("It's a tie!");
			Screen.gamesPlayed++;
			Screen.playerXLastMove = -1;
			Screen.playerOLastMove = -1;
			winner = true;
			winMove = " ";
			Main.end();
		}
	}

	private void check(int one, int two, int three, Graphics g) {
		String s1 = boardCopy[one], s2 = boardCopy[two], s3 = boardCopy[three];
		if (s1.equals(" ") && s2.equals(" ") && s3.equals(" "))
			return;
		if (s1.equals(s2) && s1.equals(s3) && s2.equals(s3)) {
			System.out.println(s1 + " wins!");
			if (s1.equals("X")) {
				Screen.playerXScore++;
				Screen.gamesPlayed++;
			}
			else if (s1.equals("O")) {
				Screen.playerOScore++;
				Screen.gamesPlayed++;
			}
			Screen.playerXLastMove = -1;
			Screen.playerOLastMove = -1;
			drawWinnerLine(one, two, three, g);
			winner = true;
			winMove = s1;
			Main.end();
		} else
			return;
	}

	private void drawWinnerLine(int s1, int s2, int s3, Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(5));
		g.setColor(Color.BLACK);
		if (s1 == 0 && s2 == 1 && s3 == 2)
			g2.drawLine(this.getWidth() / 12, this.getHeight() / 6, this.getWidth() / 12 * 11, this.getHeight() / 6);
		else if (s1 == 3 && s2 == 4 && s3 == 5)
			g2.drawLine(this.getWidth() / 12, this.getHeight() / 6 * 3, this.getWidth() / 12 * 11,
					this.getHeight() / 6 * 3);
		else if (s1 == 6 && s2 == 7 && s3 == 8)
			g2.drawLine(this.getWidth() / 12, this.getHeight() / 6 * 5, this.getWidth() / 12 * 11,
					this.getHeight() / 6 * 5);
		else if (s1 == 0 && s2 == 3 && s3 == 6)
			g2.drawLine(this.getWidth() / 6, this.getHeight() / 12, this.getWidth() / 6, this.getHeight() / 12 * 11);
		else if (s1 == 1 && s2 == 4 && s3 == 7)
			g2.drawLine(this.getWidth() / 6 * 3, this.getHeight() / 12, this.getWidth() / 6 * 3,
					this.getHeight() / 12 * 11);
		else if (s1 == 2 && s2 == 5 && s3 == 8)
			g2.drawLine(this.getWidth() / 6 * 5, this.getHeight() / 12, this.getWidth() / 6 * 5,
					this.getHeight() / 12 * 11);
		else if (s1 == 0 && s2 == 4 && s3 == 8)
			g2.drawLine(this.getWidth() / 6, this.getHeight() / 6, this.getWidth() / 6 * 5, this.getHeight() / 6 * 5);
		else if (s1 == 2 && s2 == 4 && s3 == 6)
			g2.drawLine(this.getWidth() / 6 * 5, this.getHeight() / 6, this.getWidth() / 6, this.getHeight() / 6 * 5);
	}

	@Override
	public void mousePressed(MouseEvent m) {
		if(winner&&Screen.boardIsEmpty()){
			winner = false;
			this.resetBoardCopy();
			this.repaint();
			if((int)(Math.random()*2)==1&&Information.stateOfGame.equals("PvC")&&!(Screen.gamesPlayed==0))
				this.actionPerformed(null);
			else if(Information.stateOfGame.equals("PvP"))
				move = "X";
			this.repaint();
			return;
		}
		if(winner||t.isRunning())
			return;
		Screen screen = new Screen();
//		System.out.println("X is:"+m.getX()+" Y is:"+m.getY());
		int xPos = m.getX();
		int yPos = m.getY();
		if (xPos < this.getWidth() / 3) {
			if (yPos < this.getHeight() / 3) {
				if (screen.isValidMove(1))
					screen.changeSpace(1, move);
				else
					return;
			} else if (yPos > this.getHeight() / 3 && yPos < this.getHeight() / 3 * 2) {
				if (screen.isValidMove(4))
					screen.changeSpace(4, move);
				else
					return;
			} else if (yPos > this.getHeight() / 3 * 2) {
				if (screen.isValidMove(7))
					screen.changeSpace(7, move);
				else
					return;
			}
		} else if (xPos > this.getWidth() / 3 && xPos < this.getWidth() / 3 * 2) {
			if (yPos < this.getHeight() / 3) {
				if (screen.isValidMove(2))
					screen.changeSpace(2, move);
				else
					return;
			} else if (yPos > this.getHeight() / 3 && yPos < this.getHeight() / 3 * 2) {
				if (screen.isValidMove(5))
					screen.changeSpace(5, move);
				else
					return;
			} else if (yPos > this.getHeight() / 3 * 2) {
				if (screen.isValidMove(8))
					screen.changeSpace(8, move);
				else
					return;
			}
		} else if (xPos > this.getWidth() / 3 * 2) {
			if (yPos < this.getHeight() / 3) {
				if (screen.isValidMove(3))
					screen.changeSpace(3, move);
				else
					return;
			} else if (yPos > this.getHeight() / 3 && yPos < this.getHeight() / 3 * 2) {
				if (screen.isValidMove(6))
					screen.changeSpace(6, move);
				else
					return;
			} else if (yPos > this.getHeight() / 3 * 2) {
				if (screen.isValidMove(9))
					screen.changeSpace(9, move);
				else
					return;
			}
		}
		else return;
		this.repaint();
		if(Information.stateOfGame.equals("PvC"))
			t.start();
		else if(Information.stateOfGame.equals("PvP")){
			if(move.equals("X"))
				move = "O";
			else if(move.equals("O"))
				move = "X";
		}
		else if(Information.stateOfGame.equals("PvO")){
			while(!Information.m.getOtherMove()){}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(winner)
			return;
		Computer comp = new Computer();
		Screen screen = new Screen();
		if(move.equals("X"))
			screen.changeSpace(comp.move(difficulty, "O"), "O");
		else 
			screen.changeSpace(comp.move(difficulty, "X"), "X");
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent m) {
		// TODO Auto-generated method stub
//		System.out.println("Number of Games Won: "+Screen.player1Score);
//		System.out.println("Number of Games Lost: "+Screen.player2Score);
//		System.out.println("Number of Games Tied: "+(Screen.gamesPlayed-(Screen.player1Score+Screen.player2Score)));
//		System.out.println("Number of Games Played: "+Screen.gamesPlayed);
//		System.out.println();
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent m) {
		// TODO Auto-generated method stub

	}

}
