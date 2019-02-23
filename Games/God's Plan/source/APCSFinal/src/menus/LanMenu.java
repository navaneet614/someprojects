package menus;

import java.awt.Color;

import frontend.NetworkManagementPanel;
import processing.core.PApplet;
import utilities.GameScreen;

public class LanMenu extends Menu {
	
	private NetworkManagementPanel nmp;
	
	
	public LanMenu() {
		this.addButton(new Button(275, 200, 250, 50, "Open Networking Panel", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
		this.addButton(new Button(325, 260, 150, 50, "Main Menu", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));

	}

	@Override
	public void doButtonAction(String buttonText, GameScreen gameScreen) {
		if(buttonText.equals("Open Networking Panel")) {
			if(nmp == null) {
				nmp = new NetworkManagementPanel("Gods Plan", 2, gameScreen);
				this.removeButton(0);
			}
			gameScreen.nullCurrentMenu();
		} else if(buttonText.equals("Main Menu")) {
			gameScreen.changeMenuMode("main");
		}
	}

}
