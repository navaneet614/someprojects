import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class Square extends JPanel implements MouseListener{

	private Color color;
	private boolean correctBox;
	private Board board;
	
	public Square(Board b) {
		color = Color.BLACK;
		board = b;
		correctBox = false;
		this.addMouseListener(this);
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	public void setCorrect(boolean correct){
		correctBox = correct;
	}
	
	public void changeColor(Color c){
		color = c;
		this.repaint();
	}
	
	public Color getColor(){
		return color;
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		if(!board.playing)
			return;
		if(correctBox){
			board.score++;
			correctBox = false;
		}
		else
			board.lives--;
		Main.restart();
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
