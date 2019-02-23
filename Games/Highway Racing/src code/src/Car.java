import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public abstract class Car {

	private Image carImg;
	public double x, y;
	public static final double carMinSpeed = 5, carMaxSpeed = 10, carMaxTilt = 23, carMinTilt = -23;
	public int tilt;
	public Rectangle carBox;
	public Shape hitBox;

	public Car(Image img) {
		carImg = img;
		x = Game.lanes[1];
	}

	public Image getImage() {
		return carImg;
	}

	public void setImage(Image img) {
		carImg = img;
	}

	public abstract void turnLeft();

	public abstract void turnRight();
}
