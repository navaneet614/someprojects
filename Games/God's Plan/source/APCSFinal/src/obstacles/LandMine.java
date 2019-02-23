package obstacles;

import java.awt.Rectangle;

import processing.core.PApplet;
import utilities.ImageLoader;

/**
 * 
 * @author Shaunak Bhandarkar
 * 
 * The LandMine is an Obstacle that can kill the Player if
 * he steps on it.
 *
 */
public class LandMine extends Obstacle {
	private boolean setOff;
	public LandMine(float x, float y, double width, double height) {
		super(x,y,ImageLoader.mine,25,25);
		setOff = false;
	}

	@Override
	public int getDamage() {
		return 3;
	}
	
	public void turnOn() {
		setOff = false;
	}
	
	public void setOff() {
		setOff = true;
	}
	
	public void draw(PApplet drawer) {
		if(setOff) {
			
		}else {
			super.draw(drawer);
		}
	}
	
	public Rectangle getBoundRect() 
	{
		return new Rectangle( (int)super.getX()+5, (int)super.getY()+5, (int)super.getWidth()-5, (int)super.getHeight()-5 );
	}
	
	
}
