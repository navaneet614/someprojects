
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import javax.swing.ImageIcon;

public class PlayerCar extends Car implements KeyListener {

	private Game game;
	public HashSet<Integer> keys = new HashSet<Integer>();

	public PlayerCar(Image img, Game g) {
		super(img);
		game = g;
		y = 760;
		carBox = new Rectangle((int) x, (int) y, game.carWidth, game.carHeight);
		hitBox = (Shape) carBox;
	}

	public void updateKeys() {
		if (keys.contains(-1)) {
			if (game.lineSpeed < game.lineMinSpeed + (game.score / 300.0))
				game.lineSpeed = game.lineMinSpeed + (game.score / 300.0);
			if (game.otherCarSpeed < carMinSpeed + (game.score / 300.0))
				game.otherCarSpeed = carMinSpeed + (game.score / 300.0);
		}
		if (keys.contains(KeyEvent.VK_W) || keys.contains(KeyEvent.VK_UP)) {
			if (game.lineSpeed <= game.lineMaxSpeed + (game.score / 100) - 0.1)
				game.lineSpeed += 0.03;
			if (game.otherCarSpeed <= carMaxSpeed + (game.score / 100) - 0.1)
				game.otherCarSpeed += 0.03;
		}
		if (keys.contains(KeyEvent.VK_S) || keys.contains(KeyEvent.VK_DOWN)) {
			if (game.lineSpeed >= game.lineMinSpeed + (game.score / 100) + 0.1)
				game.lineSpeed -= 0.1;
			if (game.otherCarSpeed >= carMinSpeed + (game.score / 100) + 0.1)
				game.otherCarSpeed -= 0.1;
		}
		if (keys.contains(KeyEvent.VK_A) || keys.contains(KeyEvent.VK_LEFT)) {
			turnLeft();
		}

		if (keys.contains(KeyEvent.VK_D) || keys.contains(KeyEvent.VK_RIGHT)) {
			turnRight();
		}

		if ((keys.contains(KeyEvent.VK_A) || keys.contains(KeyEvent.VK_LEFT))
				&& (keys.contains(KeyEvent.VK_D) || keys.contains(KeyEvent.VK_RIGHT))
				|| !((keys.contains(KeyEvent.VK_A) || keys.contains(KeyEvent.VK_LEFT))
						|| (keys.contains(KeyEvent.VK_D) || keys.contains(KeyEvent.VK_RIGHT)))
				|| (int) x <= game.roadStart + 5 || (int) x + game.carWidth >= game.roadEnd - 5) {
			if (tilt > -5 && tilt < 5)
				tilt = 0;
			if (tilt < 0)
				tilt += 5;
			else if (tilt > 0)
				tilt -= 5;
		}

		// System.out.println(tilt);
	}

	@Override
	public void turnLeft() {
		if (!(x - 5 < game.roadStart)) {
			x -= 3;
			if (tilt > 0)
				tilt -= 5;
			if (tilt > Car.carMinTilt)
				tilt -= 2;
		}
	}

	@Override
	public void turnRight() {
		if (!(x + 5 + game.carWidth > game.roadEnd)) {
			x += 3;
			if (tilt < 0)
				tilt += 5;
			if (tilt < Car.carMaxTilt)
				tilt += 2;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!game.timer.isRunning())
			game.timer.restart();
		keys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
