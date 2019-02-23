package menus;
import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import utilities.GameScreen;
/**
 * 
 * @author Navaneet Kadaba
 * 
 * The Menu class is responsible for providing certain 
 * menus such as the StartMenu, PauseMenu, and DeathMenu 
 * to the user, which are responsible for giving the user
 * a specific list of options.
 *
 */
public abstract class Menu{
	
	private ArrayList<Button> buttons;
	
	public Menu() {
		 buttons = new ArrayList<Button>();
	}


	public void setup() {
		
	}
	
	public void draw(PApplet drawer) {
		//drawer.background(Color.WHITE.getRGB());
		for(Button b:buttons) {
			b.draw(drawer);
		}
	}
	
	public void addButton(Button b) {
		buttons.add(b);
	}
	
	public void removeButton(int index) {
		buttons.remove(index);
	}
	
	public String checkIfButtonsPressed(int mouseX, int mouseY) {
		for(Button b:buttons) {
			if (b.mouseOver(mouseX, mouseY)) {
				return b.getText();
			}
		}
		return null;
	}
	
	public void updateButtons(int mouseX, int mouseY) {
		for(Button b:buttons) {
			b.update(mouseX, mouseY);
		}
	}
	
	public abstract void doButtonAction(String buttonText, GameScreen gameScreen);
}
