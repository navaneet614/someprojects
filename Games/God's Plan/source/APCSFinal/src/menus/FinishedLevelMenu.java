package menus;

import java.awt.Color;

import processing.core.PApplet;
import utilities.GameScreen;
import utilities.ImageLoader;

/**
 * 
 * @author Navaneet Kadaba
 * 
 * This menu is displayed when the Player reaches the end of a given level.
 *
 */
public class FinishedLevelMenu extends Menu {

	public FinishedLevelMenu() {
		doButtons();
	}
	
	private void doButtons() {
		this.addButton(new Button(325, 200, 150, 50, "Restart", Color.BLACK, Color.CYAN, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 260, 150, 50, "Instructions", Color.BLACK, Color.CYAN, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 320, 150, 50, "Main Menu", Color.BLACK, Color.CYAN, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 380, 150, 50, "Quit", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
	}

	@Override
	public void doButtonAction(String buttonText, GameScreen gameScreen) {
		if(buttonText.equals("Restart")) {
			gameScreen.changeMenuMode("restart");
		} else if(buttonText.equals("Instructions")) {
			gameScreen.changeMenuMode("instructions"); 
		}else if(buttonText.equals("Main Menu")) {
			gameScreen.changeMenuMode("main");
		} else if(buttonText.equals("Quit")) {
			gameScreen.changeMenuMode("quit");
		}
	}
	
	public void draw(PApplet drawer) {
		drawer.background(ImageLoader.background);
		super.draw(drawer);
		drawer.textFont(drawer.createFont("Georgia", 20));
		drawer.textSize(20);
		drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
		drawer.text("Congratulations! You finished the level!", 400, 100);
	}

}
