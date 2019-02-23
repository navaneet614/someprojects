
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import processing.core.PApplet;

public class DrawingSurface extends PApplet implements ActionListener {

	private Game game;
	private Timer timer;
	private int TIMER_DELAY = Game.EASY_DELAY;

	public DrawingSurface() {
		game = new Game();
		timer = new Timer(TIMER_DELAY, this);
		timer.start();
	}

	// The statements in the setup() function
	// execute once when the program begins
	public void setup() {
		// size(0,0,PApplet.P3D);
	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {
		background(255); // Clear the screen with a white background
		
		fill(0);
		textAlign(LEFT);
		textSize(12);

		text("Up: Rotate Piece\nLeft: Move Piece Left\nRight: Move Piece Right\nDown: Move Piece Down\nSpace: Drop Piece\nP: Pause (Drop time lowers)\nR: Restart\nE, M, H: Changes Difficulty\nCurrent Difficulty: "+(TIMER_DELAY==Game.EASY_DELAY?"Easy":"")+(TIMER_DELAY==Game.MEDIUM_DELAY?"Medium":"")+(TIMER_DELAY==Game.HARD_DELAY?"Hard":""),
				height + 20, 15);
		
		textSize(16);
		text("Rows Cleared: "+game.getRowClears(), height +20, 190);

		textSize(20);
		text("Next Piece:", height + 20, 210);

		
		if (game != null) {
			game.draw(this, 0, 0, height, height);
			game.drawNextPiece(this, height, 0, width - height, height);
		}

	}

	public void mousePressed() {
	}

	public void mouseDragged() {
	}

	public void keyPressed() {
		if (keyCode == KeyEvent.VK_DOWN) {
			game.moveCurrentPieceDown();
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			game.moveCurrentPieceRight();
		} else if (keyCode == KeyEvent.VK_LEFT) {
			game.moveCurrentPieceLeft();
		} else if (keyCode == KeyEvent.VK_SPACE) {
			game.dropCurrentPiece();
		} else if (keyCode == KeyEvent.VK_UP) {
			game.rotateCurrentPiece();
		} else if (keyCode == KeyEvent.VK_P) {
			TIMER_DELAY-=5;
			game.togglePause();
		} else if (keyCode == KeyEvent.VK_R) {
			game.restart();
		} else if (Character.isDigit(key)) {
			int num = Integer.parseInt(key + "");
			game.newNextPiece(num);
		} else if(keyCode == KeyEvent.VK_E) {
			TIMER_DELAY = Game.EASY_DELAY;
			timer.setInitialDelay(TIMER_DELAY);
			game.restart();
		} else if(keyCode == KeyEvent.VK_M) {
			TIMER_DELAY = Game.MEDIUM_DELAY;
			timer.setInitialDelay(TIMER_DELAY);
			game.restart();
		} else if(keyCode == KeyEvent.VK_H) {
			TIMER_DELAY = Game.HARD_DELAY;
			timer.setInitialDelay(TIMER_DELAY);
			game.restart();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		game.moveCurrentPieceDown();
		if(timer.getDelay()!=TIMER_DELAY-(game.getRowClears()*5))
			timer.setDelay(TIMER_DELAY-game.getRowClears()*5);
	}

}
