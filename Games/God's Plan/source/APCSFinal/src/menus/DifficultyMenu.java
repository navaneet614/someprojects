package menus;
import java.awt.Color;

import processing.core.PApplet;
import utilities.GameScreen;
import utilities.ImageLoader;

/**
 * 
 * @author Navaneet Kadaba
 * 
 * This Menu is displayed before the user plays a level for him
 * or her to select the difficulty of the given level.
 *
 */
public class DifficultyMenu extends Menu {
	public DifficultyMenu() {
		doButtons();
	}
	
	private void doButtons() {
		this.addButton(new Button(10, 300, 150, 50, "Really Not Easy", Color.RED, Color.ORANGE, Color.RED, Color.BLUE));
		this.addButton(new Button(375, 450, 150, 50, "Easy", Color.BLACK, Color.CYAN, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(640, 375, 150, 50, "Not Easy", Color.BLACK, Color.GREEN, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 540, 150, 50, "Back", Color.BLACK, Color.YELLOW, Color.LIGHT_GRAY, Color.BLUE));
	}
	
	
	
	public void doButtonAction(String buttonText, GameScreen gameScreen) {
		if(buttonText.equals("Easy")) {
			gameScreen.changeMenuMode("easy");
		} else if(buttonText.equals("Not Easy")) {
			gameScreen.changeMenuMode("medium"); 
		}else if(buttonText.equals("Really Not Easy")) {
			gameScreen.changeMenuMode("hard");
		} else if(buttonText.equals("Back")) {
			gameScreen.changeMenuMode("backtolevelmenu");
		}
	}
	
	public void draw(PApplet drawer) {
		drawer.background(ImageLoader.difficultyImage);
		super.draw(drawer);
		drawer.textFont(drawer.createFont("Georgia", 20));
		drawer.textSize(20);
		drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
		drawer.text("Difficulty Select", 400, 25);
	}
}
