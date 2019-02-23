package obstacles;
import java.awt.Rectangle;
import java.io.Serializable;

import processing.core.*;


/**
 * 
 * @author Shaunak Bhandarkar
 *
 * The Bullet class is essential to Obstacles such as Turret, as
 * it enables bullets to be fired from the given source. A Bullet
 * starts at a specified location, and fire at a constant velocity,
 * going in a straight line indefinitely.
 * 
 */
public class Bullet implements Serializable
{

	private static final long serialVersionUID = -2882183503421892520L;
	
	private double xCoord, yCoord;
	private Rectangle boundingRectangle;
	//private String direction;
	private double angle; // in radians
	
	public Bullet(double X, double Y, /*String direction*/ double a) {
		xCoord = X;
		yCoord = Y;
		//this.direction = direction;
		angle = a;
		boundingRectangle = new Rectangle((int)xCoord,(int)yCoord, 10,10);
	}
	
	public void shoot() {
		/*String d = direction.toLowerCase();
		if(d.equals("left")) {
			xCoord -= 10;
		}else if(d.equals("right")) {
			xCoord += 10;
		}else if(d.equals("up")) {
			yCoord -= 10;
		}else if(d.equals("down")) {
			yCoord += 10;
		}*/
		xCoord += 1*Math.cos(angle);
		yCoord -= 1*Math.sin(angle);
		
		boundingRectangle.setLocation((int)xCoord, (int)yCoord);
	}

	public double getAngle() 
	{
		return angle;
	}
	
	public Rectangle getBoundingRect() 
	{
		return boundingRectangle;
	}
	
	public void draw(PApplet drawer) {
		drawer.fill(250,250,210);
		drawer.rect((int)xCoord,(int) yCoord, 10, 10);
	}
	
	public double getX() {
		return xCoord;
	}
	
	public double getY() {
		return yCoord;
	}
	
	public void setX( float x ) 
	{
		xCoord = x;
	}
	
	public void setY( float y ) 
	{
		yCoord = y;
	}
	
	public int getDamage() {
		return 1;
	}
}
