package menus;
import java.awt.Color;

import processing.core.PApplet;

/**
 * 
 * @author Navaneet Kadaba
 * 
 * The Button class is a convenient element of GUI that 
 * enables the user in a game to choose their game mode,
 * adjust the game settings, pause, and quit. 
 *
 */
public class Button {

	private float x, y, width, height;
	private String text;
	private Color edgeColor, fillColor, highlightFillColor, textColor, currentColor;

	public Button(float x, float y, float width, float height, String text, Color edgeColor, Color fillColor,
			Color highlightFillColor, Color textColor) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.edgeColor = edgeColor;
		this.fillColor = fillColor;
		this.highlightFillColor = highlightFillColor;
		this.textColor = textColor;
		currentColor = fillColor;
	}

	public void draw(PApplet drawer) {
		drawer.rectMode(PApplet.CORNER);
		drawer.fill(currentColor.getRGB());
		drawer.stroke(edgeColor.getRGB());
		drawer.rect(x, y, width, height);
		drawer.fill(textColor.getRGB());
		drawer.textFont(drawer.createFont("Georgia", 20));
		drawer.textSize(20);
		drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
		drawer.text(text, x + width / 2, y + height / 2);
	}

	public boolean mouseOver(int mouseX, int mouseY) {
		return (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height);
	}
	
	public void setFill( Color fill ) 
	{
		fillColor  = fill;
	}

	public String getText() {
		return text;
	}

	public void update(int mouseX, int mouseY) {
		if (mouseOver(mouseX, mouseY)) {
			currentColor = highlightFillColor;
		} else {
			currentColor = fillColor;
		}
	}

}
