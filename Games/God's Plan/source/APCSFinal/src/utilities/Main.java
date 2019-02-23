package utilities;
import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * 
 * @author William Hu
 * 
 * The Main class is from where the program actually executes.
 * It has a GameScreen that controls the rest of the game actions
 * and conditions.
 *
 */
public class Main {

	public static void main(String[] args) {

		PApplet.main("utilities.GameScreen");
		
	}
}