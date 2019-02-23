
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Information extends JPanel {
	
	private JPanel title, top, bottom;

	public static JButton change, reset;
	private JComboBox<String> combo;
	private JComboBox<Integer> difficulty;
	private JTextPane loseWinDisplay, tiedPlayedDisplay;
	private String loseWinText, tiedPlayedText;
	
	private static int playerScore;
	private static int compScore;
	public static Multiplayer m;
	

	public static String stateOfGame = "PvC";

	public Information(Window w) {
		this.setBackground(Color.WHITE);
		title = new JPanel();
		JLabel titleText = new JLabel("Tic Tac Toe");
		titleText.setFont(new Font("Comic Sans MS", 1, 30));
		title.add(titleText);
		title.setBackground(Color.WHITE);
		title.addMouseListener(new MouseListener() {   
			private Color[] color = {Color.RED,Color.ORANGE,Color.YELLOW, Color.GREEN, Color.BLUE, new Color(138, 43, 226)};
			private int num = 0;
			Timer t = new Timer(250, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					title.setBackground(color[num]);
					num++;
					if(num==color.length)
						num = 0;
				}
			});
			@Override
			public void mousePressed(MouseEvent e) {
				if(t.isRunning()){
					t.stop();
					title.setBackground(Color.WHITE);
					num = 0;
					return;
				}
				t.setRepeats(true);
				t.start();
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		top = new JPanel();
		top.setBackground(Color.WHITE);
		bottom = new JPanel();
		bottom.setBackground(Color.WHITE);
		top.setLayout(new GridLayout(1, 2));
		
		change = new JButton("Change to O");
		change.setBackground(Color.WHITE);
		change.setEnabled(true);
		change.setVisible(true);
		change.setToolTipText("This changes your move.");
		change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Board.move.equals("X")) {
					Board.move = "O";
					change.setText("Change to X");
				} else {
					Board.move = "X";
					change.setText("Change to O");
				}
				Main.end();
				w.boardRepaint();
			}
		});
		bottom.setLayout(new GridLayout(1, 0, 10 ,10));
		bottom.add(change);

		difficulty = new JComboBox<Integer>();
		difficulty.setBackground(Color.WHITE);
		difficulty.setFocusable(false);
		difficulty.addItem(1);
		difficulty.addItem(2);
		difficulty.addItem(3);
		difficulty.setSelectedIndex(2);
		difficulty.setToolTipText("This sets the difficulty for the computer.");
		difficulty.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent i) {
				Board.difficulty = difficulty.getSelectedIndex() + 1;
			}

		});
		bottom.add(difficulty);

		combo = new JComboBox<String>();
		combo.setBackground(Color.WHITE);
		combo.setFocusable(false);
		combo.addItem(new String("PvC"));
		combo.addItem(new String("PvP"));
		combo.addItem(new String("PvO"));
		combo.setToolTipText("This changes the gamemode.");
		combo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent i) {
				switch (combo.getSelectedIndex()) {
				case 0:
					stateOfGame = "PvC";
					difficulty.setEnabled(true);
					break;
				case 1:
					stateOfGame = "PvP";
					difficulty.setEnabled(false);
					break;
				case 2:
					stateOfGame = "PvO";
					difficulty.setEnabled(false);
					m = new Multiplayer(w.board);
					break;
				}
				Board.move = "X";
				Main.end();
				Information.reset();
				w.boardReset();
				w.infoRepaint();
				w.boardRepaint();
			}

		});
		bottom.add(combo);
		
		reset = new JButton("Reset Game");
		reset.setBackground(Color.WHITE);
		reset.setEnabled(true);
		reset.setVisible(true);
		reset.setToolTipText("This resets the game.");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				Main.end();
				Screen.playerXLastMove = -1;
				Screen.playerOLastMove = -1;
				Screen.gamesPlayed = 0;
				Screen.playerOScore = 0;
				Screen.playerXScore = 0;
				playerScore = 0;
				compScore = 0;
				w.board.resetBoardCopy();
				w.boardRepaint();
				w.infoRepaint();
			}
		});
		
		bottom.add(reset);
		
		loseWinDisplay = new JTextPane();
		loseWinDisplay.setText(loseWinText);
		loseWinDisplay.setBackground(Color.WHITE);
		loseWinDisplay.setEditable(false);
		loseWinDisplay.setSize(top.getWidth(), top.getHeight());
		loseWinDisplay.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		StyledDocument doc = loseWinDisplay.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		top.add(loseWinDisplay);
		
		tiedPlayedDisplay = new JTextPane();
		tiedPlayedDisplay.setText(tiedPlayedText);
		tiedPlayedDisplay.setBackground(Color.WHITE);
		tiedPlayedDisplay.setEditable(false);
		tiedPlayedDisplay.setSize(top.getWidth(), top.getHeight());
		tiedPlayedDisplay.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		doc = tiedPlayedDisplay.getStyledDocument();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		top.add(tiedPlayedDisplay);
		
		this.setLayout(new GridLayout(3, 0, 0, 0));
		this.add(title);
		this.add(top);
		this.add(bottom);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (Information.stateOfGame.equals("PvC") || Information.stateOfGame.equals("PvO")) {

			if (!Board.winMove.equals(" ")) {
				if (Board.move.equals(Board.winMove))
					playerScore++;
				else
					compScore++;
			}

			loseWinText = "Number of Games Won: " + playerScore +"\n"+
			"Number of Games Lost: " + compScore;
			tiedPlayedText = "Number of Games Tied: " + (Screen.gamesPlayed - (playerScore + compScore)) + "\n" +
			"Number of Games Played: " + Screen.gamesPlayed;
			Board.winMove = " ";
		} else if (Information.stateOfGame.equals("PvP")) {
			loseWinText = "Player X won: " + Screen.playerXScore + "\n" +
			"Player O won: " + Screen.playerOScore;
			tiedPlayedText = "Number of Games Tied: " + (Screen.gamesPlayed - (Screen.playerXScore + Screen.playerOScore)) + "\n" + 
			"Number of Games Played: " + Screen.gamesPlayed;
			Board.winMove = " ";
		}
		loseWinDisplay.setText(loseWinText);
		tiedPlayedDisplay.setText(tiedPlayedText);
	}

	public static void reset() {
		playerScore = 0;
		compScore = 0;
		Screen.gamesPlayed = 0;
		Screen.playerOScore = 0;
		Screen.playerXScore = 0;
	}
}
