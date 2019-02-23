
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener, KeyListener {

	public static final int[] lanes = { 130, 270, 400 };

	public final double lineMaxSpeed = 20, lineMinSpeed = 7;

	public boolean pause, gameEnd, playSound;
	public PlayerCar player;
	public int roadStart, roadEnd, carWidth, carHeight;

	public AICar[] computers;
	public Image[] carImages;
	public int lineOffset;
	public double otherCarSpeed, lineSpeed, timerDelay, score, mph;
	public long timeUsed, lastTime;
	public Timer timer;
	private MenuScreen menu;

	private boolean drawHitBoxes = false;
	private boolean demo = false;

	public enum gameModes {
		RACE, DESTRUCTION
	};

	public Game(MenuScreen m) {
		menu = m;
		this.setBounds(menu.window.getBounds());
		pause = false;
		gameEnd = false;
		playSound = true;
		roadStart = 100;
		roadEnd = 500;
		carWidth = 60;
		carHeight = 124;
		otherCarSpeed = Car.carMinSpeed;
		lineSpeed = lineMinSpeed;
		carImages = new Image[6];
		for (int i = 0; i < carImages.length; i++) {
			carImages[i] = new ImageIcon(Menu.class.getResource("/resources/car" + (i + 1) + ".png")).getImage()
					.getScaledInstance(carWidth, carHeight, 0);
		}
		if (menu.selectedCarImage != null)
			player = new PlayerCar(menu.selectedCarImage, this);
		else
			player = new PlayerCar(carImages[4], this);
		computers = new AICar[6];
		for (int i = 0; i < computers.length; i++) {
			computers[i] = new AICar(this);
		}
		timerDelay = 16.0;
		timer = new Timer((int) timerDelay, this);
		lastTime = System.currentTimeMillis();
	}

	public void restart() {
		menu.selectedIndex = 0;
//		 pause = false;
		gameEnd = false;
		roadStart = 100;
		roadEnd = 500;
		carWidth = 60;
		carHeight = 124;
		score = 0;
		otherCarSpeed = Car.carMinSpeed;
		lineSpeed = lineMinSpeed;
		player.x = lanes[1];
		computers = new AICar[computers.length];
		for (int i = 0; i < computers.length; i++) {
			computers[i] = new AICar(this);
		}
		lastTime = System.currentTimeMillis();
		timeUsed = 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.scale(this.getWidth() / 600.0, this.getHeight() / 900.0);
		this.drawRoad(g);
		if (!demo)
			this.drawPlayer(g);
		this.drawOtherCars(g);
		if (!demo)
			this.checkForHits(g);

		if (!gameEnd) {
			g.setFont(new Font("Comic Sans MS", Font.ITALIC, 40));
			if (!demo)
				g.drawString("Score: " + (int) score,
						600 - g.getFontMetrics().stringWidth("Score: " + (int) score) - 10, 50);
			else 
				g.drawString("Demo (press . to exit)",
						600 - g.getFontMetrics().stringWidth("Demo (press . to exit)") - 10, 50);
			g.drawString("Speed: " + (int) mph + " mph",
					600 - g.getFontMetrics().stringWidth("Speed: " + (int) mph + " mph") - 10, 100);
			if (menu.modeSelected.equals(MenuScreen.gameModes.DESTRUCTION)) {
				g.drawString("Time Left: " + (30000 - (int) timeUsed) / 1000 + " seconds", 600
						- g.getFontMetrics().stringWidth("Time Left: " + (30000 - (int) timeUsed) / 1000 + " seconds")
						- 10, 150);
			}
		}
		if (gameEnd) {
			timer.stop();
			g.setColor(Color.BLACK);
			g.setFont(new Font("Comic Sans MS", Font.ITALIC, 30));
			g.drawString("Score: " + (int) score, 300 - g.getFontMetrics().stringWidth("Score: " + (int) score) / 2,
					150);
			menu.end(g);
		} else if (pause) {
			timer.stop();
			menu.pause(g);
		} else {
			if (!timer.isRunning()) {
				timer.start();
			}
		}
		if (!menu.window.hasFocus() && timer.isRunning())
			pause = true;
	}

	private void drawPlayer(Graphics g) {
		AffineTransform at = AffineTransform.getTranslateInstance(player.x, player.y);
		at.rotate(Math.toRadians(player.tilt), carWidth / 2, carHeight / 2);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.drawImage(player.getImage(), at, null);

		at = AffineTransform.getRotateInstance(Math.toRadians(player.tilt), player.x + carWidth / 2,
				player.y + carHeight / 2);
		player.hitBox = at.createTransformedShape(player.carBox);
		if (drawHitBoxes) {
			g.drawOval((int) (player.x), (int) player.y, 1, 1);
			g2d.draw(player.hitBox);
		}
	}

	private void drawRoad(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, roadStart, 900);
		g.fillRect(roadEnd, 0, 100, 900);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(100, 0, 400, 900);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(5));
		g.setColor(Color.WHITE);
		g.drawLine(233, -200 + lineOffset, 233, -100 + lineOffset);
		g.drawLine(366, -200 + lineOffset, 366, -100 + lineOffset);
		g.drawLine(233, 0 + lineOffset, 233, 100 + lineOffset);
		g.drawLine(366, 0 + lineOffset, 366, 100 + lineOffset);
		g.drawLine(233, 200 + lineOffset, 233, 300 + lineOffset);
		g.drawLine(366, 200 + lineOffset, 366, 300 + lineOffset);
		g.drawLine(233, 400 + lineOffset, 233, 500 + lineOffset);
		g.drawLine(366, 400 + lineOffset, 366, 500 + lineOffset);
		g.drawLine(233, 600 + lineOffset, 233, 700 + lineOffset);
		g.drawLine(366, 600 + lineOffset, 366, 700 + lineOffset);
		g.drawLine(233, 800 + lineOffset, 233, 900 + lineOffset);
		g.drawLine(366, 800 + lineOffset, 366, 900 + lineOffset);
	}

	private void drawOtherCars(Graphics g) {
		for (int i = 0; i < computers.length; i++) {
			AffineTransform at = AffineTransform.getTranslateInstance((int) computers[i].x, (int) computers[i].y);
			at.rotate(Math.toRadians(computers[i].tilt), carWidth / 2, carHeight / 2);
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.drawImage(computers[i].getImage(), at, null);
			at = AffineTransform.getRotateInstance(Math.toRadians(computers[i].tilt), computers[i].x + carWidth / 2,
					computers[i].y + carHeight / 2);
			computers[i].hitBox = at.createTransformedShape(computers[i].carBox);
			if (drawHitBoxes) {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
				g.drawString(i + "", (int) (computers[i].x + computers[i].carBox.getWidth() / 2),
						(int) (computers[i].y + computers[i].carBox.getHeight() / 2));
				g2d = (Graphics2D) g.create();
				g2d.draw(computers[i].hitBox);
			}
		}
	}

	private void checkForHits(Graphics g) {
		for (int i = 0; i < computers.length; i++) {
			if (computers[i].isHitBy(player)) {
				if (menu.modeSelected.equals(MenuScreen.gameModes.RACE)) {
					gameEnd = true;
					repaint();
				} else if (menu.modeSelected.equals(MenuScreen.gameModes.DESTRUCTION)) {
					score += 25;
					computers[i] = new AICar(this);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (menu.modeSelected.equals(MenuScreen.gameModes.DESTRUCTION)) {
			if ((30000 - (int) timeUsed) / 1000 <= 0)
				gameEnd = true;
			timeUsed += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
		}
		player.updateKeys();
		lineOffset += lineSpeed;
		if (lineOffset > 200) {
			lineOffset = 0;
		}
		for (int i = 0; i < computers.length; i++) {
			computers[i].update();
			if (computers[i].y - 100 > getHeight()) {
				computers[i] = new AICar(this);
			}
		}
		player.carBox.setBounds((int) (player.x + 4), (int) (player.y + 6), carWidth - 8, carHeight - 12);
		if (menu.modeSelected.equals(MenuScreen.gameModes.RACE))
			score += otherCarSpeed / 30.0;
		mph = otherCarSpeed * 8;
		player.keys.add(-1);
		player.updateKeys();
		player.keys.remove(-1);
		this.repaint();
	}

	@Override
	public void keyPressed(KeyEvent k) {
		switch (k.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
		case KeyEvent.VK_P:
			if (pause) {
				menu.resume();
				break;
			}
			pause = !pause;
			break;
		case KeyEvent.VK_SLASH:
			System.out.println("Carspeed:" + otherCarSpeed + " linespeed:" + lineSpeed + " timeUsed:" + timeUsed);
			drawHitBoxes = !drawHitBoxes;
			break;
		case KeyEvent.VK_PERIOD:
			demo = !demo;
			if(!demo)
				restart();
			break;
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
