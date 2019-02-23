package menus;
import java.awt.Color;

import menus.Button;
import obstacles.Block;
import obstacles.Glue;
import obstacles.LandMine;
import obstacles.Obstacle;
import obstacles.Spike;
import obstacles.Turret;
import processing.core.PApplet;
import utilities.ImageLoader;

/**
 * 
 * @author William Hu
 * 
 * The ClickableObstacle is a Button that represents an Obstacle,
 * which helps with placing Obstacles in the mulitplayer mode.
 *
 */
public class ClickableObstacle extends Button {
	private Obstacle obstacle;
	private float x,y,width,height;
	private String text;
	
	public ClickableObstacle(float x, float y, float width, float height, Obstacle obstacle, String text) {
		super(x,y,width,height,text, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE);
		this.obstacle = obstacle;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	public ClickableObstacle( float x, float y, float width, float height, Obstacle obstacle, String text, Color fill ) 
	{
		super(x,y,width,height,text, Color.BLACK, fill, Color.LIGHT_GRAY, Color.BLUE);
		this.obstacle = obstacle;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	

	public void draw(PApplet drawer) {
		drawer.rectMode(PApplet.CORNER);
		
		drawer.stroke(Color.BLACK.getRGB());
		drawer.rect(x, y, width, height);
		if(obstacle instanceof Spike) {
		drawer.image(ImageLoader.spike, x+5, y+5,width-15,height-15);
		}else if (obstacle instanceof Turret) {
			drawer.image(ImageLoader.turret, x+5, y+5,width-15,height-15);
		}else if(obstacle instanceof Glue) {
			drawer.image(ImageLoader.glue, x+5, y+5,width-15,height-15);
		}else if(obstacle instanceof LandMine) {
			drawer.image(ImageLoader.mine, x+5, y+5,width-15,height-15);
		}else if(obstacle instanceof Block) {
			drawer.image(ImageLoader.block, x+5, y+5,width-15,height-15);
		}
	}
	
	
	

}
