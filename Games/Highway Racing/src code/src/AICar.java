
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.Random;

public class AICar extends Car {

	private Game game;
	public int lane, slotNum, justTurned;
	public double speed;
	private int wantedLane;
	private boolean wantedLaneSet, turningLeft, turningRight;

	public AICar(Game g) {
		super(g.carImages[0]);
		game = g;
		this.setImage(getRandomCarImage());
		getSpawn();
		speed = Math.random() * -5;
		wantedLaneSet = false;
		justTurned = 100;
	}

	private void getSpawn() {
		boolean leave = true;
		while (leave) {
			lane = (int) (Math.random() * 3);
			x = Game.lanes[lane];
			y = Math.random() * -300 + -100;
			carBox = new Rectangle((int) x, (int) y, game.carWidth, game.carHeight);
			hitBox = (Shape) carBox;
			for (int i = 0; i < game.computers.length; i++) {
				leave = hitByForSpawn(game.computers[i]);
				if (leave)
					break;
			}
		}
	}

	private boolean hitByForSpawn(Car otherCar) {
		try {
			Area areaA = new Area(new Rectangle((int) otherCar.carBox.getX() - 5, (int) otherCar.carBox.getY() - 5,
					(int) otherCar.carBox.getWidth() + 10, (int) otherCar.carBox.getHeight() + 10));
			areaA.intersect(new Area(hitBox));
			return !areaA.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	private Image getRandomCarImage() {
		Image img;
		img = game.carImages[(int) (Math.random() * 6)];
		return img;
	}

	public void update() {
		justTurned = 0;
		for (int i = 0; i < game.computers.length; i++) {
			if (aboutToHit(game.computers[i]))
				game.computers[i].speed = speed;
		}
		y += game.otherCarSpeed + speed;
		carBox.setBounds((int) (x + 5), (int) (y + 5), game.carWidth - 10, game.carHeight - 10);
		if (turningRight)
			turnRight();
		else if (turningLeft)
			turnLeft();
		else if (justTurned > 0)
			justTurned-=2;
		else {
			int rand = new Random().nextInt(6);
			if (rand == 0) {
				if (canTurnLeft())
					turnLeft();
			} else if (rand == 5) {
				if (canTurnRight())
					turnRight();
			}
		}
	}

	public boolean aboutToHit(AICar otherCar) {
		if ((lane == otherCar.lane || lane == otherCar.wantedLane)
				&& ((y - (otherCar.y + game.carHeight) < 100) && (y - (otherCar.y + game.carHeight) > -game.carHeight))
				&& otherCar.speed > speed)
			return true;
		else if ((lane == otherCar.lane || lane == otherCar.wantedLane)
				&& ((otherCar.y - (y + game.carHeight) < 100) && (otherCar.y - (y + game.carHeight) > -game.carHeight))
				&& speed > otherCar.speed)
			return true;
		else
			return false;

	}

	public boolean isHitBy(Car otherCar) {
		try {
			Area areaA = new Area(otherCar.hitBox);
			areaA.intersect(new Area(hitBox));
			return !areaA.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean canTurnLeft() {
		for (int i = 0; i < game.computers.length; i++) {
			if ((lane - 1 == game.computers[i].lane || lane - 1 == game.computers[i].wantedLane)
					&& (y > game.computers[i].y - 150 && y < game.computers[i].y + game.carHeight + 150)) {
				return false;
			}
		}
		if (lane != 0) {
			if ((Game.lanes[lane - 1] > game.player.x - 50 && Game.lanes[lane - 1] < game.player.x + game.carWidth + 50)
					&& (y > game.player.y - 150 && y < game.player.y + game.carHeight + 150))
				return false;
		}
		return true;
	}

	public boolean canTurnRight() {
		for (int i = 0; i < game.computers.length; i++) {
			if ((lane + 1 == game.computers[i].lane || lane + 1 == game.computers[i].wantedLane)
					&& (y > game.computers[i].y - 150 && y < game.computers[i].y + game.carHeight + 150)) {
				return false;
			}
		}
		if (lane != 2) {
			if ((Game.lanes[lane + 1] > game.player.x - 50 && Game.lanes[lane + 1] < game.player.x + game.carWidth + 50)
					&& (y > game.player.y - 150 && y < game.player.y + game.carHeight + 150))
				return false;
		}
		return true;
	}

	@Override
	public void turnLeft() {
		if (turningRight)
			return;
		if (lane != 0) {
			if (!turningRight && !turningLeft)
				turningLeft = true;
			if (!wantedLaneSet) {
				wantedLane = lane - 1;
				wantedLaneSet = true;
			}
			if (tilt > carMinTilt)
				tilt -= 2;
			if (x > Game.lanes[wantedLane]) {
				x -= 2;
				if (x < Game.lanes[wantedLane] + 15) {
					if (tilt > 5 || tilt < -5)
						tilt += 5;
					else {
						tilt = 0;
						wantedLaneSet = false;
						lane = wantedLane;
						wantedLane = lane;
						turningLeft = false;
						justTurned = 100;
					}
				}
			}
		}
	}

	@Override
	public void turnRight() {
		if (turningLeft)
			return;
		if (lane != 2) {
			if (!turningRight && !turningLeft)
				turningRight = true;
			if (!wantedLaneSet) {
				wantedLane = lane + 1;
				wantedLaneSet = true;
			}
			if (tilt < carMaxTilt)
				tilt += 2;
			if (x < Game.lanes[wantedLane]) {
				x += 2;
				if (x >= Game.lanes[wantedLane] - 15) {
					if (tilt > 5 || tilt < -5)
						tilt -= 5;
					else {
						tilt = 0;
						wantedLaneSet = false;
						lane = wantedLane;
						wantedLane = lane;
						turningRight = false;
						justTurned = 100;
					}
				}
			}
		}
	}
}
