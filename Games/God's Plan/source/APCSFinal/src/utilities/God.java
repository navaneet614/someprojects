package utilities;
import java.awt.Color;
import java.util.ArrayList;

import processing.core.*;

/**
 * 
 * @author William Hu
 * 
 * The God class is what enables the dropping and placing
 * of Obstacles that hinder the Player from reaching the 
 * end of the level. However, there is a limit to the number
 * of Obstacles that can be used by the God in a certain amount
 * of time.
 *
 */
public class God 
{
	private PImage character;
	private double xCoord, yCoord;
	private double width, height;
	private int amountOfObstacles, placedObstacles;
	
	public God( double x, double y, double w, double h, int amountOfObstacles ) 
	{
		xCoord = x;
		yCoord = y;
		width = w;
		height = h;
		this.amountOfObstacles = amountOfObstacles;
		placedObstacles = 0;
	}
	
	public void setup(PApplet drawer) {
		character = drawer.loadImage("god.png");
	}
	
	public void draw(PApplet drawer) 
	{	
	}
	
	public void place() {
		placedObstacles++;
	}
	
	public boolean canPlace() {
		return amountOfObstacles > placedObstacles;
	}
	
	public int getAmountOfObstacles() {
		return amountOfObstacles;
	}
	
	public int getPlacedObstacles() {
		return placedObstacles;
	}
	
	public void setObstacleAmount(int x) {
		amountOfObstacles = x;
	}
	
	public void subtractPlacedObstacles() {
		placedObstacles--;
	}
	
	
	
//	public void throwObstacle( Obstacle ob, float startX, float startY ) 
//	{
//		
//	}
//	
//	private void fall( Obstacle o ) {
//		
//		/*	if ( yCoord < 550 )
//				canFall = true;
//			else
//				canFall = false;*/
//		double velocity = 0;
//			
//			if( ( 600 - o.getY() - o.getHeight() ) > 1) {
//			velocity+=0.25;
//			if(velocity >= 15) {
//				velocity = 15;
//			}
//			o.setY((float)o.getY() + (float)velocity);
//			if(o.getY() > 550) {
//				o.setY(550);
//				//canFall = false;
//			}
//			}
//			
//		}
//	
	
	
	
}
