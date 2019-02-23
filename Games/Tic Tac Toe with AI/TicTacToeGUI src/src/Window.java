import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	
	private JFrame j;
	private JPanel main;
	private Container c;
	public Board board;
	private Information info;
	
	public Window(){
		j = new JFrame("Tic Tac Toe");
		c = j.getContentPane();
		main = new JPanel();
		
		GridBagLayout g = new GridBagLayout();
		c.setLayout(g);
		GridBagConstraints con = new GridBagConstraints();
		con.fill = GridBagConstraints.BOTH;
		
		board = new Board();
		info = new Information(this);
		
		con.weightx = 0.1;
		con.weighty = 0.01;
		con.gridx = 0;
		con.gridy = 0;
		g.setConstraints(info, con);
		c.add(info);
		
		con.weighty = 0.1;
		con.gridx = 0;
		con.gridy = 1;
		g.setConstraints(board, con);
		c.add(board);
		
		j.setBackground(Color.WHITE);
		j.setSize(600, 600);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setVisible(true);
	}
	
	public void boardRepaint(){
		board.repaint();
	}
	
	public void infoRepaint() {
		info.repaint();
	}
	
	public void boardReset() {
		board.resetBoardCopy();
	}
	
}
