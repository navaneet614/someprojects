package menus;
import java.awt.Color;

import processing.core.PApplet;
import utilities.GameScreen;
import utilities.ImageLoader;

/**
 * 
 * @author Navaneet Kadaba
 * 
 * This Menu allows the user to choose between local and online multiplayer modes.
 *
 */
public class MultiplayerMenu extends Menu {

	public MultiplayerMenu() {
		doButtons();
	}
	
	private void doButtons() {
		this.addButton(new Button(325, 200, 150, 50, "Local", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 260, 150, 50, "Online", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 320, 150, 50, "Back", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
	}
	
	public void doButtonAction(String buttonText, GameScreen gameScreen) {
		if(buttonText.equals("Local")) {
			gameScreen.changeMenuMode("localmultiplayer");
		} else if(buttonText.equals("Online")) {
			gameScreen.changeMenuMode("onlinemultiplayer"); 
		}else if(buttonText.equals("Back")) {
			gameScreen.changeMenuMode("main");
		}

	}
	

	public void draw(PApplet drawer) {
		drawer.background(ImageLoader.multiplayerImage);
		super.draw(drawer);
	}

}
