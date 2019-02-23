package obstacles;

import processing.core.PImage;

/**
 * 
 * @author Shaunak Bhandarkar
 * 
 * The FinishHouse is an "Obstacle" that determines whether the
 * user has reached the end of the level based on whether the 
 * Player has reached it.
 *
 */
public class FinishHouse extends Obstacle {

	public FinishHouse(float x, float y, PImage p, double width, double height) {
		super(x, y, p, width, height);
	}

	@Override
	public int getDamage() {
		return 0;
	}

}
