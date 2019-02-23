import java.awt.GridLayout;
import javax.swing.JFrame;

public class Main {
	
	private static JFrame w;
	private static Board board;
	private static ScoreBoard score;
	
	public static void main(String[] args){
		w = new JFrame("Game");
		w.setBounds(100, 100, 800, 600);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setLayout(new GridLayout(2, 0));
		board = new Board();
		board.randomizeColors();
		w.add(board);
		score = new ScoreBoard(board);
		w.add(score);
		w.setResizable(true);
		w.setVisible(true);
	}
	
	public static void restart(){
		if(!score.t.isRunning()){
			board.score = 0;
			board.lives = 3;
			score.timeLeft = 10;
			score.t.start();
		}
		board.randomizeColors();
		board.repaint();
		score.update();
		score.repaint();
	}

	public static void end() {
		score.t.stop();
		board.playing = false;
	}
}
