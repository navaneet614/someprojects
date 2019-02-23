package menus;
import java.awt.Color;

import processing.core.PApplet;
import utilities.GameScreen;
import utilities.ImageLoader;
/**
 * 
 * @author Navaneet Kadaba
 * 
 * This class represents the menu that will be displayed when the game is paused
 *
 */
public class PauseMenu extends Menu {
	
	public PauseMenu() {
		doButtons();
	}
	
	private void doButtons() {
		this.addButton(new Button(325, 200, 150, 50, "Resume", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 260, 150, 50, "Instructions", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 320, 150, 50, "Main Menu", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 380, 150, 50, "Quit", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
	}

	@Override
	public void doButtonAction(String buttonText, GameScreen gameScreen) {
		if(buttonText.equals("Resume")) {
			gameScreen.changeMenuMode("resume");
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
	}

}
