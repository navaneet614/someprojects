package utilities;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import java.io.*;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author William Hu
 * 
 *         The Player class is the main class used to control the actual main
 *         character of the game. This involves simulating gravity, as on Earth,
 *         moving around, and jumping. It also enables one to check the
 *         "boundaries" of a Player to identify whether it is intersecting or
 *         overlapping with another graphical object.
 *
 */
public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6984109521635763440L; // just in case ID is needed

	public final double GRAVITY = 1.05, JUMP_HEIGHT = 20;

	private double xCoord;
	private double yCoord;
	private int health, count;
	private double width;
	private double height;
	private transient PImage character, heart;
	private boolean alive, jumping, slow, tintRed;
	private double vy;
	private Rectangle boundingRectangle;

	public Player(double x, double y, double w, double h) {
		xCoord = x;
		yCoord = y;
		width = w;
		height = h;
		alive = true;
		vy = 0;
		boundingRectangle = new Rectangle((int) x + 5, (int) y, (int) w - 5, (int) h);
		slow = false;
		health = 3;
		tintRed = false;
		count = 0;
	}

	public void setup(PApplet drawer) {
		character = ImageLoader.character;
		heart = ImageLoader.health;
	}

	public void draw(PApplet drawer) {
		float xSpot = (float) (xCoord);
		float ySpot = (float) yCoord - 25;
		for (int i = 0; i < health; i++) {
			drawer.image(heart, xSpot, ySpot, 20, 20);
			xSpot += 20;
		}
		drawer.pushStyle();
		if (tintRed) {
			drawer.tint(Color.RED.getRGB(), 128);
			count++;
		}
		if (count > 25) {
			count = 0;
			drawer.noTint();
			tintRed = false;
		}
//		drawer.point((float) xCoord, (float) yCoord);
//		drawer.rect(boundingRectangle.x, boundingRectangle.y, boundingRectangle.width, boundingRectangle.height);
		drawer.image(character, (float) xCoord, (float) yCoord, (float) width, (float) height);
		drawer.popStyle();

	}

	public int hearts() {
		return health;
	}

	public void moveDirection(double x) {
		xCoord += x * 5;
	}

	public void jump() {
		if (!jumping)
			jumping = true;
	}

	public void cancelJump() {
		if (jumping) {
			jumping = false;
		}
	}

	public void takeDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			alive = false;
		}
	}

	public void setTintRed() {
		tintRed = true;
	}

	public boolean getStatus() {
		return alive;
	}

	public double getX() {
		return xCoord;
	}

	public double getY() {
		return yCoord;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public void setX(double x) {
		xCoord = x;
	}

	public void setY(double y) {
		yCoord = y;
	}

	public double getVY() {
		return vy;
	}

	public void setVY(double newVY) {
		vy = GRAVITY + newVY;
	}

	private void moveLeft() {
		if (slow) {
			moveDirection(-.5);
		} else
			moveDirection(-1);
	}

	private void moveRight() {
		if (slow) {
			moveDirection(.5);
		} else
			moveDirection(1);
	}

	public void update(HashSet<Integer> keys, GameScreen gameScreen) {
		for (int key : keys) {
			if (key == PApplet.UP || key == KeyEvent.VK_W) {
				jump();
			} else if (key == PApplet.DOWN) {

			} else if ((key == PApplet.LEFT || key == KeyEvent.VK_A) && getX() > 0) {
				if (xCoord > gameScreen.ORIGINAL_WIDTH * 1 / 4) {
					moveLeft();
				} else {
					if (!gameScreen.translate(-7))
						moveLeft();
				}
			} else if ((key == PApplet.RIGHT || key == KeyEvent.VK_D) && getX() + width < gameScreen.width) {
				if (xCoord + width < gameScreen.ORIGINAL_WIDTH * 1 / 2) {
					moveRight();
				} else {
					if (!gameScreen.translate(7))
						moveRight();
				}

			}
		}
		act();
		gameScreen.sendInfoToEveryone();
	}

	private void act() {
		boundingRectangle.setLocation((int) xCoord, (int) yCoord);
		if (jumping) {
			if (slow) {
				yCoord -= JUMP_HEIGHT * .8;
			} else {
				yCoord -= JUMP_HEIGHT;
			}
		}
		vy += GRAVITY;
		yCoord += vy;
		if (vy > 35) {
			vy = 35;
		}
		boundingRectangle.setLocation((int) xCoord, (int) yCoord);
	}

	public Rectangle getBoundingRect() {
		return boundingRectangle;
	}

	public void setSlow(boolean x) {
		slow = x;
	}
	
	public void doImages() {
		character = ImageLoader.character;
		heart = ImageLoader.health;
	}

}
