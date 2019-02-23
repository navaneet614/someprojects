import java.awt.Color;
import java.awt.Point;

import processing.core.PApplet;

/*

	Represents a Game Of Life grid.

	Coded by: Navaneet Kadaba
	Modified on: 1/11/18

*/

public class Game {

	public static final int EASY_DELAY = 1000, MEDIUM_DELAY = 500, HARD_DELAY = 300;

	private Color[][] grid;
	private Piece currentPiece, nextPiece, shadowPiece;
	private boolean lost, pause;
	private int rowClears;

	private int WIDTH = 10, HEIGHT = 20;

	// Constructs an empty grid
	public Game() {
		grid = new Color[WIDTH][HEIGHT];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = Color.WHITE;
			}
		}
		currentPiece = new Piece(-1);
		currentPiece.setX(WIDTH / 2 - 1);
		shadowPiece = new Piece(currentPiece.getPieceNumber());
		shadowPiece.setX(currentPiece.getX());
		shadowPiece.setY(currentPiece.getY());
		shadowPiece.setColor(Color.GRAY);
		newNextPiece(-1);
		lost = false;
		pause = false;
		rowClears = 0;
	}

	public int getRowClears() {
		return rowClears;
	}

	public void togglePause() {
		pause = !pause;
	}

	public boolean pause() {
		return pause;
	}

	public void moveCurrentPieceLeft() {
		if (pause)
			return;
		if (currentPiece.getX() > 0 && !pieceLeft())
			currentPiece.setX(currentPiece.getX() - 1);
		// System.out.println(currentPiece.getX());
	}

	public void moveCurrentPieceRight() {
		if (pause)
			return;
		if (currentPiece.getX() + currentPiece.getWidth() < WIDTH && !pieceRight())
			currentPiece.setX(currentPiece.getX() + 1);
	}

	public void moveCurrentPieceDown() {
		if (pause)
			return;
		if (currentPiece.getY() + currentPiece.getHeight() < HEIGHT && !pieceUnder(currentPiece))
			currentPiece.setY(currentPiece.getY() + 1);
		else {
			addPieceToGrid();
			newNextPiece(-1);
		}
	}

	private boolean pieceUnder(Piece piece) {
		for (int i = 0; i < piece.points().size(); i++) {
			int currentX = (int) (piece.points().get(i).getX());
			int currentY = (int) (piece.points().get(i).getY());
			if (grid[currentX + piece.getX()][Math.min(currentY + piece.getY() + 1, HEIGHT - 1)] != Color.WHITE
					&& !piece.points().contains(new Point(currentX, currentY + 1))) {
				return true;
			}
		}
		return false;
	}

	private boolean pieceLeft() {
		for (int i = 0; i < currentPiece.points().size(); i++) {
			int currentX = (int) (currentPiece.points().get(i).getX());
			int currentY = (int) (currentPiece.points().get(i).getY());
			if (grid[Math.max(currentX + currentPiece.getX() - 1, 0)][currentY + currentPiece.getY()] != Color.WHITE
					&& !currentPiece.points().contains(new Point(currentX - 1, currentY))) {
				return true;
			}
		}
		return false;
	}

	private boolean pieceRight() {
		for (int i = 0; i < currentPiece.points().size(); i++) {
			int currentX = (int) (currentPiece.points().get(i).getX());
			int currentY = (int) (currentPiece.points().get(i).getY());
			if (grid[Math.min(currentX + currentPiece.getX() + 1, WIDTH - 1)][currentY
					+ currentPiece.getY()] != Color.WHITE
					&& !currentPiece.points().contains(new Point(currentX + 1, currentY))) {
				return true;
			}
		}
		return false;
	}

	public void dropCurrentPiece() {
		if (pause)
			return;
		while (currentPiece.getY() + currentPiece.getHeight() < HEIGHT && !pieceUnder(currentPiece))
			currentPiece.setY(currentPiece.getY() + 1);
		addPieceToGrid();
		newNextPiece(-1);
	}

	public void rotateCurrentPiece() {
		if (pause)
			return;
		if (currentPiece.canRotate(grid)) {
			// currentPiece.rotate();
			currentPiece.rotate(WIDTH, HEIGHT);
			shadowPiece.rotate(WIDTH, HEIGHT);
		}
	}

	private void addPieceToGrid() {
		try {
			for (int i = 0; i < currentPiece.points().size(); i++) {
				grid[(int) (currentPiece.points().get(i).getX() + currentPiece.getX())][(int) (currentPiece.points()
						.get(i).getY() + currentPiece.getY())] = currentPiece.getColor();
			}
		} catch (Exception e) {
			lost = true;
		}
		try {
			for (int i = 0; i < nextPiece.points().size(); i++) {
				while (!grid[(int) (nextPiece.points().get(i).getX() + nextPiece.getX())][(int) (nextPiece.points()
						.get(i).getY() + nextPiece.getY())].equals(Color.WHITE))
					nextPiece.setY(nextPiece.getY() - 1);
			}
		} catch (Exception e) {
			lost = true;
		}
		currentPiece = nextPiece;
		shadowPiece = new Piece(currentPiece.getPieceNumber());
		shadowPiece.setX(currentPiece.getX());
		shadowPiece.setY(currentPiece.getY());
		shadowPiece.setColor(Color.GRAY);
	}

	public void newNextPiece(int n) {
		nextPiece = new Piece(n < 0 || n > 6 ? -1 : n);
		nextPiece.setX(WIDTH / 2 - 1);
	}

	private void checkForFullRow() {
		int count = 0;
		for (int i = 0; i < grid[0].length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (!grid[j][i].equals(Color.WHITE)) {
					count++;
				}
			}
			// System.out.println(i+", "+count);
			if (count == WIDTH) {
				removeRow(i);
			}
			count = 0;
		}
	}

	private void removeRow(int row) {
		for (int i = row; i > 0; i--) {
			for (int j = 0; j < grid.length; j++) {
				grid[j][i] = grid[j][i - 1];
			}
		}
		for (int i = 0; i < grid.length; i++) {
			grid[i][0] = Color.WHITE;
		}
		rowClears++;
	}

	public void restart() {
		rowClears = 0;
		lost = false;
		grid = new Color[WIDTH][HEIGHT];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = Color.WHITE;
			}
		}
		currentPiece = nextPiece;
		shadowPiece = new Piece(currentPiece.getPieceNumber());
		shadowPiece.setX(currentPiece.getX());
		shadowPiece.setY(currentPiece.getY());
		shadowPiece.setColor(Color.GRAY);
		newNextPiece(-1);
	}

	public void draw(PApplet marker, float x, float y, float width, float height) {
		checkForFullRow();
		shadowPiece.setX(currentPiece.getX());
		shadowPiece.setY(currentPiece.getY());
		while (shadowPiece.getY() + shadowPiece.getHeight() < HEIGHT && !pieceUnder(shadowPiece))
			shadowPiece.setY(shadowPiece.getY() + 1);
		if (lost) {
			marker.background(255);
			marker.fill(Color.RED.getRGB());
			marker.text("You Lose. Press R to Restart.", width / 2, height / 2);
		} else {
			float boxWidth = (width - x) / grid.length;
			float boxHeight = (height - y) / grid[0].length;
			marker.stroke(0);
			for (int i = 0; i < grid[0].length; i++) {
				for (int j = 0; j < grid.length; j++) {
					marker.fill(grid[j][i].getRGB());
					marker.rect(x + j * boxWidth, y + i * boxHeight, boxWidth, boxHeight);
				}
			}
			for (int i = 0; i < shadowPiece.points().size(); i++) {
				marker.fill(shadowPiece.getColor().getRGB());
				marker.rect(x + (int) (shadowPiece.points().get(i).getX() + shadowPiece.getX()) * boxWidth,
						y + (int) (shadowPiece.points().get(i).getY() + shadowPiece.getY()) * boxHeight, boxWidth,
						boxHeight);
			}
			for (int i = 0; i < currentPiece.points().size(); i++) {
				marker.fill(currentPiece.getColor().getRGB());
				marker.rect(x + (int) (currentPiece.points().get(i).getX() + currentPiece.getX()) * boxWidth,
						y + (int) (currentPiece.points().get(i).getY() + currentPiece.getY()) * boxHeight, boxWidth,
						boxHeight);
			}
		}
	}

	public void drawNextPiece(PApplet marker, float x, float y, float width, float height) {
		if (lost)
			return;
		float boxWidth = 20;
		float boxHeight = 20;
		marker.stroke(0);
		for (int i = 0; i < nextPiece.points().size(); i++) {
			marker.fill(nextPiece.getColor().getRGB());
			marker.rect(x + (int) (nextPiece.points().get(i).getX() + nextPiece.getX()) * boxWidth,
					y + 225 + (int) (nextPiece.points().get(i).getY() + nextPiece.getY()) * boxHeight, boxWidth,
					boxHeight);
		}
	}
}
