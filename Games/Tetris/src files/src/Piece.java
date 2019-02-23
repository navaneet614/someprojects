import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Piece {

	private ArrayList<Point> points;
	private Color color;
	private int x, y, pieceNumber;

	public Piece(int pieceNum) {
		points = new ArrayList<Point>();
		x = 0;
		y = 0;
		if (pieceNum < 0 || pieceNum > 6)
			pieceNumber = (int) (Math.random() * 7);
		else
			pieceNumber = pieceNum;
		// blocks: http://i.imgur.com/9Z0oJXe.png
		// L Block
		if (pieceNumber == 0) {
			points.add(new Point(0, 1));
			points.add(new Point(1, 1));
			points.add(new Point(2, 1));
			points.add(new Point(2, 0));
			color = Color.ORANGE;
		}
		// O Block
		else if (pieceNumber == 1) {
			points.add(new Point(0, 0));
			points.add(new Point(0, 1));
			points.add(new Point(1, 0));
			points.add(new Point(1, 1));
			color = Color.YELLOW;
		}
		// S Block
		else if (pieceNumber == 2) {
			points.add(new Point(0, 1));
			points.add(new Point(1, 1));
			points.add(new Point(1, 0));
			points.add(new Point(2, 0));
			color = Color.GREEN;
		}
		// I Block
		else if (pieceNumber == 3) {
			points.add(new Point(0, 0));
			points.add(new Point(1, 0));
			points.add(new Point(2, 0));
			points.add(new Point(3, 0));
			color = Color.CYAN;
		}
		// J Block
		else if (pieceNumber == 4) {
			points.add(new Point(0, 0));
			points.add(new Point(1, 0));
			points.add(new Point(1, 1));
			points.add(new Point(2, 1));
			color = Color.BLUE;
		}
		// T Block
		else if (pieceNumber == 5) {
			points.add(new Point(0, 1));
			points.add(new Point(1, 0));
			points.add(new Point(1, 1));
			points.add(new Point(2, 1));
			color = new Color(148, 0, 211); // purple
		}
		// Z Block
		else if (pieceNumber == 6) {
			points.add(new Point(0, 0));
			points.add(new Point(1, 0));
			points.add(new Point(1, 1));
			points.add(new Point(2, 1));
			color = Color.RED;
		}

	}

	public ArrayList<Point> points() {
		return points;
	}
	
	public int getPieceNumber() {
		return pieceNumber;
	}

	public int getX() {
		return x;
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setY(int newY) {
		y = newY;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color newColor) {
		color = newColor;
	}

	public int getWidth() {
		int x = 0;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getX() > x)
				x = points.get(i).x;
		}
		return x + 1;
	}

	public int getHeight() {
		int y = 0;
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getY() > y)
				y = points.get(i).y;
		}
		return y + 1;
	}

	private Point getCenter() {
		// int sumX = 0, sumY = 0;
		// for(int i = 0;i<points.size();i++) {
		// sumX+=points.get(i).x;
		// sumY+=points.get(i).y;
		// }
		// return new Point(sumX/points.size(), sumY/points.size());
		return points.get(0);
	}

	public void rotate(int gridWidth, int gridHeight) {
		int cx = (int) getCenter().getX();
		int cy = (int) getCenter().getY();
		for (int i = 0; i < points.size(); i++) {
			Point current = points.get(i);
			int dx = (int) (current.x - cx);
			int dy = (int) (current.y - cy);

			current.x = cx + dy;
			current.y = cy - dx;
		}

		while (!has0InX()) {
			for (int i = 0; i < points.size(); i++) {
				points.set(i, new Point(points.get(i).x - 1, points.get(i).y));
			}
			x++;
		}
		while (hasNegInX()) {
			for (int i = 0; i < points.size(); i++) {
				points.set(i, new Point(points.get(i).x + 1, points.get(i).y));
			}
			x--;
		}
		while (hasNegInY()) {
			for (int i = 0; i < points.size(); i++) {
				points.set(i, new Point(points.get(i).x, points.get(i).y+1));
			}
			y--;
		}

		if (x + getWidth() >= gridWidth)
			x = gridWidth - getWidth();
		if (x < 0)
			x = 0;
		if (y + getHeight() >= gridHeight)
			y = gridHeight - getHeight();
		if (y < 0)
			y = 0;
	}

	public boolean canRotate(Color[][] grid) {
		if (pieceNumber == 1) {
			return false;
		}
		int cx = (int) getCenter().getX();
		int cy = (int) getCenter().getY();
		for (int i = 0; i < points.size(); i++) {
			Point current = points.get(i);
			int dx = (int) (current.x - cx);
			int dy = (int) (current.y - cy);

			int testx = x + cx + dy;
			int testy = y + cy - dx;

			// System.out.println("here2: " + (x) + ", " + (y));

			try {
				if (!grid[testx][testy].equals(Color.WHITE)) {
					return false;
				}
			} catch (Exception e) {
				// System.out.println("here:" + (testx) + ", " + (testy));
//				if(testx<0||testy<0)
//					return false;
				return true;
			}
		}
		return true;
	}

	private boolean has0InX() {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).x == 0)
				return true;
		}
		return false;
	}

	private boolean hasNegInX() {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).x < 0)
				return true;
		}
		return false;
	}
	
	private boolean hasNegInY() {
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).y < 0)
				return true;
		}
		return false;
	}
}
