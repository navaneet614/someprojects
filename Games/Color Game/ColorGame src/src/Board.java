import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

public class Board extends JPanel{

	private final Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
	private Color[] useColors, dispColors;
	public int correctBox, score, size, lives;
	public boolean playing;
	public Square[] tiles;
	
	public Board() {
		playing  = false;
		size = 3;
		dispColors = new Color[size*size];
		this.setLayout(new GridLayout(size, size));
		tiles = new Square[size*size];
		for(int i = 0;i<tiles.length;i++){
			tiles[i] = new Square(this);
			this.add(tiles[i]);
		}
		lives = 3;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		updateTiles();
	}
	
	private void updateTiles(){
		for(int i = 0;i<tiles.length;i++){
			tiles[i].changeColor(dispColors[i]);
		}
	}
	
	public Color[] getColors(){
		return dispColors;
	}
	
	public void randomizeColors(){
		useColors = colors.clone();
		for(int i = 0;i<dispColors.length;i++){
			dispColors[i] = getRandomColor();
			this.removeFromUseColors(dispColors[i]);
		}
	}
	
	private Color getRandomColor() {
		if(useColors.length==0)
			useColors = colors.clone();
		return useColors[(int) (Math.random()*useColors.length)];
	}
	
	private void removeFromUseColors(Color c) {
		ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(useColors));
		colorList.removeAll(Arrays.asList(c));
		useColors = colorList.toArray(new Color[0]);
	}
	
}
