package navaneet.shapedemo;

import java.awt.Color;
import java.util.Random;
import anantajit.shapes.Circle;
import anantajit.shapes.Rectangle;
import anantajit.shapes.Shape2D;
import processing.core.PApplet;


/**
 * 
 * @author Navaneet Kadaba
 * @version 10/9/2017
 *
 */
public class PhysicsShape {

	private static final double GRAVITY = .5, FRICTION = 1.002;

	private Shape2D boundingShape;
	private double vx, vy;
	private boolean gravity;
	private static boolean invertGravity;

	public PhysicsShape(Shape2D s) {
		boundingShape = s;
		vx = 0;
		vy = 0;
		gravity = true;
	}

	public void draw(PApplet drawer) {
		boundingShape.setSurface(drawer);
		boundingShape.draw();
	}

	public Shape2D getBoundingShape() {
		return boundingShape;
	}

	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}

	public double[] getVelocity() {
		return new double[] { vx, vy };
	}

	public void act(Rectangle window) {

		bounceOffWindowEdge(window);

		boundingShape.setX(boundingShape.getX() + vx);
		boundingShape.setY(boundingShape.getY() + vy);

		// if(vx-FRICTION<0) {
		// vx=0;
		// }
		// else {
		// vx-=FRICTION;
		// }
		// if(vy-FRICTION<0) {
		// vy=0;
		// }
		// else {
		// vy-=FRICTION;
		// }
		// if(gravity) {
		// vy+=GRAVITY;
		// }

		vx /= FRICTION;
		vy /= FRICTION;
		if (Math.abs(vx) < FRICTION / 2) {
			vx = 0;
		}
		if (gravity) {
			if (invertGravity) {
				vy -= GRAVITY;
			} else {
				vy += GRAVITY;
			}
		}
	}

	public void toggleGravity(boolean on) {
		gravity = on;
	}

	public static void invertGravity() {
		invertGravity = !invertGravity;
	}

	public void doVelocity(double deltaX, double deltaY, double time) {
		vx = deltaX / (time);
		vy = deltaY / (time);
	}

	public void moveTo(double x, double y, Rectangle window) {

		if (boundingShape instanceof Circle) {
			Circle c = (Circle) boundingShape;

			if (x - (c.getCircumference() / (2 * Math.PI)) < window.getX()) {
				x = window.getX() + (c.getCircumference() / (2 * Math.PI));
			}
			if (y - (c.getCircumference() / (2 * Math.PI)) < window.getY()) {
				y = window.getY() + (c.getCircumference() / (2 * Math.PI));
			}
			if (x + (c.getCircumference() / (2 * Math.PI)) > window.getWidth()) {
				x = window.getWidth() - (c.getCircumference() / (2 * Math.PI));
			}
			if (y + (c.getCircumference() / (2 * Math.PI)) > window.getHeight()) {
				y = window.getHeight() - (c.getCircumference() / (2 * Math.PI));
			}

		}

		else if (boundingShape instanceof Rectangle) {

			Rectangle r = (Rectangle) boundingShape;

			if (x - r.getWidth() / 2 < window.getX()) {
				x = window.getX();
			}
			if (y - r.getHeight() / 2 - window.getY() < 0) {
				y = window.getY();
			}
			if (x + r.getWidth() > window.getWidth()) {
				x = window.getWidth() - r.getWidth();
			}
			if (y + r.getHeight() > window.getHeight()) {
				y = window.getHeight() - r.getHeight();
			}
		}

		else {
			if (x < window.getX()) {
				x = window.getX();
			}
			if (y - window.getY() < 0) {
				y = 0 + window.getY();
			}
			if (x > window.getWidth()) {
				x = window.getWidth();
			}
			if (y > window.getHeight()) {
				y = window.getHeight();
			}
		}
		boundingShape.setX(x);
		boundingShape.setY(y);
	}

	private void bounceOffWindowEdge(Rectangle window) {
		if (boundingShape instanceof Circle) {
			Circle c = (Circle) boundingShape;
			if ((c.getX() + (c.getCircumference() / (2 * Math.PI)) + vx >=  (window.getWidth()))
					|| (c.getX() - (c.getCircumference() / (2 * Math.PI)) + vx <= window.getX())) {
				vx = -vx / 2;
			}
			if ((c.getY() + (c.getCircumference() / (2 * Math.PI)) + vy >= window.getHeight())
					|| (c.getY() - (c.getCircumference() / (2 * Math.PI)) + vy <= window.getY())) {
				if (Math.abs(vy) <= GRAVITY + 1) {
					vy = 0;
				} else {
					vy = -vy / 2;
				}
			}
		}

		else if (boundingShape instanceof Rectangle) {
			Rectangle r = (Rectangle) boundingShape;
			if ((r.getX() + r.getWidth() + vx >= window.getWidth()) || (r.getX() + vx <= window.getX())) {
				vx = -vx / 2;
			}
			if ((r.getY() + r.getHeight() + vy >= window.getHeight())
					|| (r.getY() + vy <= window.getY())) {
				if (Math.abs(vy) <= GRAVITY + 1) {
					vy = 0;
				} else {
					vy = -vy / 2;
				}
			}
		}

		else {
			if ((boundingShape.getX() + vx >= window.getWidth())
					|| (boundingShape.getX() + vx <= window.getX())) {
				vx = -vx / 2;
			}
			if ((boundingShape.getY() + vy >= window.getHeight())
					|| (boundingShape.getY() + vy <= window.getY())) {
				if (Math.abs(vy) <= GRAVITY + 1) {
					vy = 0;
				} else {
					vy = -vy / 2;
				}
			}
		}
	}

	public void chuck() {
		setVelocity(new Random().nextInt(30) - 15, new Random().nextInt(50) - 25);
		boundingShape.setFillColor(
				new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
	}
}
