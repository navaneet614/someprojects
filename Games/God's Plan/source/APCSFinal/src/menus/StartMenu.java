package menus;
import java.awt.Color;

import processing.core.PApplet;
import utilities.GameScreen;
import utilities.ImageLoader;

/**
 * 
 * @author Navaneet Kadaba
 * 
 * The StartMenu class is a specific type of Menu that
 * shows up before the user starts playing the game. It 
 * allows the user to pick his or her choice of game 
 * (single-player or multiplayer mode), as well as edit
 * certain game settings.
 *
 */
public class StartMenu extends Menu {

	public StartMenu() {
		super();
		doButtons();
	}
	
	private void doButtons() {
		this.addButton(new Button(325, 200, 150, 50, "Singleplayer", Color.BLACK, Color.ORANGE, Color.YELLOW, Color.BLUE));
		this.addButton(new Button(325, 260, 150, 50, "Multiplayer", Color.BLACK, Color.MAGENTA, Color.PINK, Color.BLUE));
		this.addButton(new Button(325, 320, 150, 50, "Instructions", Color.BLACK, Color.GREEN, Color.CYAN, Color.BLUE));
		this.addButton(new Button(325, 380, 150, 50, "Quit", Color.BLACK, Color.RED, Color.ORANGE, Color.BLUE));
	}

	@Override
	public void doButtonAction(String buttonText, GameScreen gameScreen) {
		if(buttonText.equals("Singleplayer")) {
			gameScreen.changeMenuMode("singleplayer");
		} else if(buttonText.equals("Multiplayer")) {
			gameScreen.changeMenuMode("multiplayer"); 
		}else if(buttonText.equals("Instructions")) {
			gameScreen.changeMenuMode("instructions");
		} else if(buttonText.equals("Quit")) {
			gameScreen.changeMenuMode("quit");
		}
	}
	
	public void draw(PApplet drawer) {
		drawer.background(ImageLoader.startImage);
		super.draw(drawer);
		drawer.textFont(drawer.createFont("Georgia", 20));
		drawer.textSize(38);
		drawer.fill(Color.black.getRGB());
		drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
		drawer.text("God's Plan", 400, 100);
		
		
	}
}
