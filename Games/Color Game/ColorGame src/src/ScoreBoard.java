import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.Timer;

public class ScoreBoard extends JPanel implements ActionListener, MouseListener{
	
	public static Color displayColor, trickColor;
	private Board board;
	private Map<Color, String> colorMap;
	public Timer t;
	public int timeLeft = 10;

	public ScoreBoard(Board b){
		this.setBackground(Color.BLACK);
		this.addMouseListener(this);
		board = b;
		colorMap = new HashMap<Color, String>();
	    colorMap.put(Color.BLACK, "Black");   
	    colorMap.put(Color.BLUE, "Blue");
	    colorMap.put(Color.CYAN, "Cyan");
	    colorMap.put(Color.DARK_GRAY, "Dark Gray");
	    colorMap.put(Color.GRAY, "Gray");
	    colorMap.put(Color.GREEN, "Green");
	    colorMap.put(Color.LIGHT_GRAY, "Light Gray");
	    colorMap.put(Color.MAGENTA, "Magenta");
	    colorMap.put(Color.ORANGE, "Orange");
	    colorMap.put(Color.PINK, "Pink");
	    colorMap.put(Color.RED, "Red");
	    colorMap.put(Color.WHITE, "White");
	    colorMap.put(Color.YELLOW, "Yellow");
	    this.pickRandomColor();
	    t = new Timer(1000, this);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
		g.setColor(trickColor);
		g.drawString(colorMap.get(displayColor), this.getWidth()/2, this.getHeight()/2);
		g.setColor(Color.WHITE);
		g.drawString("Score: "+board.score, this.getWidth()/2+50, this.getHeight()/2+50);
		g.drawString("Lives left: "+board.lives, this.getWidth()/2+50, this.getHeight()/2+100);
		g.drawString("Time Left: "+timeLeft, this.getWidth()/10, this.getHeight()/2+30);
		if(!board.playing){
			g.drawString("Game Over!", this.getWidth()/3, this.getHeight()/8);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			g.drawString("Press Anywhere to restart.", this.getWidth()/3, this.getHeight()/8+20);
		}
		if(board.lives==0){
			Main.end();
			this.repaint();
		}
	}
	
	public void update(){
		this.pickRandomColor();
		this.repaint();
	}
	
	private void pickRandomColor(){
		int rand = (int) (Math.random()*board.getColors().length);
		displayColor = board.getColors()[rand];
		board.tiles[rand].setCorrect(true);
//		for(int i = 0;i<board.tiles.length;i++){
//			if(board.tiles[i].getColor().equals(board.tiles[rand].getColor())){
//				board.tiles[i].setCorrect(true);
//			}
//		}
		trickColor = getTrickColor();
	}
	
	private Color getTrickColor(){
		Color returnColor;
		do{
			returnColor = board.getColors()[(int) (Math.random()*board.getColors().length)];
		} while(!(!returnColor.equals(displayColor)&&!returnColor.equals(Color.BLACK)));
		return returnColor;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timeLeft--;
		if(timeLeft==0){
			Main.end();
			timeLeft=10;
		}
		this.repaint();
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
	public void mousePressed(MouseEvent e) {
		if(board.playing)
			return;
		else if(!board.playing){
			board.playing = true;
			Main.restart();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
