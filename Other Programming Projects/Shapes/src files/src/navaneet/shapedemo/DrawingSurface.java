package navaneet.shapedemo;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import anantajit.shapes.Circle;
import anantajit.shapes.Rectangle;
import anantajit.shapes.Shape2D;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

/**
 * 
 * @author Navaneet Kadaba
 * @version 10/9/2017
 *
 */
public class DrawingSurface extends PApplet {

	private static final float ORIGINAL_WIDTH = 400, ORIGNAL_HEIGHT = 300, MENUBAR_HEIGHT = 50;

	private ArrayList<PhysicsShape> shapes;
	private ArrayList<PhysicsShape> clickStartedInShape;
	private ArrayList<Shape2D> menu;

	public DrawingSurface() {
		shapes = new ArrayList<PhysicsShape>();
		shapes.add(new PhysicsShape(new Circle(100, 100, 15)));
		shapes.get(0).getBoundingShape().setFillColor(Color.BLUE);
		shapes.add(new PhysicsShape(new Rectangle(200, 200, 30, 40)));
		shapes.get(1).getBoundingShape().setFillColor(Color.GREEN);
		clickStartedInShape = new ArrayList<PhysicsShape>();
		menu = new ArrayList<Shape2D>();
		menu.add(new Rectangle(0, 0, 400, MENUBAR_HEIGHT));
		menu.get(0).setFillColor(Color.LIGHT_GRAY);
		menu.get(0).setSurface(this);
		menu.add(new Circle(20, 25, 15));
		menu.get(1).setSurface(this);
		menu.add(new Rectangle(50, 10, 20, 30));
		menu.get(2).setSurface(this);
	}

	// The statements in the setup() function
	// execute once when the program begins
	public void setup() {

	}

	// The statements in draw() are executed until the
	// program is stopped. Each statement is executed in
	// sequence and after the last line is read, the first
	// line is executed again.
	public void draw() {
		scale(width / ORIGINAL_WIDTH, height / ORIGNAL_HEIGHT);
		background(Color.WHITE.getRGB());
		for (int i = 0; i < menu.size(); i++) {
			menu.get(i).draw();
		}
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).draw(this);
			shapes.get(i).act(new Rectangle(0, MENUBAR_HEIGHT, ORIGINAL_WIDTH, ORIGNAL_HEIGHT));
		}
	}

	public void mousePressed(MouseEvent event) {
	
		if(event.getButton() == CENTER) {
			PhysicsShape.invertGravity();
		}
		
		for (int i = 1; i < menu.size(); i++) {
			if (menu.get(i).isPointInside((int) (event.getX() / (width / ORIGINAL_WIDTH)),
					(int) (event.getY() / (height / ORIGNAL_HEIGHT)))) {
				if (menu.get(i) instanceof Circle) {
					shapes.add(new PhysicsShape(new Circle(20, 80, 15)));
				} else if (menu.get(i) instanceof Rectangle) {
					shapes.add(new PhysicsShape(new Rectangle(50, 60, 30, 40)));
				}
				shapes.get(shapes.size() - 1).getBoundingShape().setFillColor(
						new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
				return;
			}
		}

		if (event.getButton() == RIGHT && event.isControlDown()) {
			shapes.removeAll(shapes);
			return;
		}

		for (int i = shapes.size() - 1; i >= 0; i--) {
			if (shapes.get(i).getBoundingShape().isPointInside((int) (event.getX() / (width / ORIGINAL_WIDTH)),
					(int) (event.getY() / (height / ORIGNAL_HEIGHT)))) {
				if (event.getButton() == LEFT) {
					shapes.get(i).setVelocity(0, 0);
					shapes.get(i).toggleGravity(false);
					clickStartedInShape.add(shapes.get(i));
				} else if (event.getButton() == RIGHT) {
					shapes.remove(i);
				}
				return;
			}
		}

	}

	public void mouseDragged(MouseEvent event) {
		for (int i = 0; i < clickStartedInShape.size(); i++) {
			clickStartedInShape.get(i).moveTo(event.getX() / (width / ORIGINAL_WIDTH),
					event.getY() / (height / ORIGNAL_HEIGHT), new Rectangle(0, MENUBAR_HEIGHT, ORIGINAL_WIDTH, ORIGNAL_HEIGHT));
		}

	}

	public void mouseReleased(MouseEvent event) {
		for (int i = 0; i < clickStartedInShape.size(); i++) {
			clickStartedInShape.get(i).toggleGravity(true);
			clickStartedInShape.get(i).doVelocity((event.getX() - pmouseX) / (width / ORIGINAL_WIDTH),
					(event.getY() - pmouseY) / (height / ORIGNAL_HEIGHT), (1 / frameRate) * 50);
			clickStartedInShape.remove(clickStartedInShape.get(i));
			i--;
		}
	}
	
	public void mouseWheel(MouseEvent event) {
		for (int i = 0; i < shapes.size(); i++) {
			shapes.get(i).chuck();
		}
	}

	public static void main(String args[]) {
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[] { "" }, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame) canvas.getFrame();

		window.setSize(400, 300);
		window.setMinimumSize(new Dimension(100, 100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setVisible(true);
	}
}
