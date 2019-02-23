package menus;

import java.awt.Color;

import obstacles.Block;
import obstacles.FinishHouse;
import obstacles.Glue;
import obstacles.LandMine;
import obstacles.Spike;
import obstacles.Turret;
import processing.core.PApplet;
import utilities.GameScreen;
import utilities.ImageLoader;

/**
 * 
 * @author Shaunak Bhandarkar
 * 
 * The InstructionPanel provides instructions on how to control
 * both the Player and God (in multiplayer mode).
 *
 */
public class InstructionMenu extends Menu
{

	public InstructionMenu() 
	{
		super();
		doButtons();
	}
	
	public void doButtonAction(String buttonText, GameScreen gameScreen) 
	{
		if(buttonText.equals("Main Menu")) 
		{
			gameScreen.changeMenuMode("main");
		}
	}
	
	public void doButtons() 
	{
		this.addButton(new Button(325, 380, 150, 50, "Main Menu", Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.BLUE));
	}
	
	public void draw(PApplet drawer) 
	{	
		drawer.background(ImageLoader.background);
		super.draw(drawer);
		Block b1 = new Block( 500, 400, 50, 50 );
		Block b2 = new Block( 550, 400, 50, 50 );
		Block b3 = new Block( 620, 330, 50, 50 );
		Block b4 = new Block( 430, 470, 50, 50 );
		Spike s1 = new Spike( 0, 500, 50, 50 );
		Spike s2 = new Spike( 50, 500, 50, 50 );
		Turret t = new Turret( 620, 280, 50, 50, 0 );
		Glue g = new Glue( 300, 540, 400, 10 );
		LandMine l = new LandMine( 525, 380, 20, 20 );
		FinishHouse f = new FinishHouse( 730, 480, ImageLoader.finish , 70, 70 );
		Block[] blocks = new Block [ 16 ];
		for ( int i = 0; i < blocks.length; i++ ) 
		{
			blocks[i] = new Block( 50*i, 550, 50, 50 );
		}
		b1.draw(drawer);
		b2.draw(drawer);
		b3.draw(drawer);
		b4.draw(drawer);
		for ( Block b : blocks ) 
		{
			b.draw(drawer);
		}
		t.draw(drawer);
		g.draw(drawer);
		f.draw(drawer);
		s1.draw(drawer);
		s2.draw(drawer);
		l.draw(drawer);
		drawer.image(ImageLoader.character, 100, 350);
		drawer.textFont(drawer.createFont("Georgia", 20));
		drawer.textSize(20);
		drawer.textAlign(PApplet.CENTER, PApplet.CENTER);
		drawer.text("In this action-packed adventure, your goal is\n to reach the end of each stage, avoiding God's wrath"
				+ "\n along the way. To move the (memester) character,\n you may use either the up, right, and left arrows,"
				+ "\n or the W, A, and D keys. In case you need to pause your\n marvelous journey, feel free to press the"
				+ "\n 'p' key. Now, if YOU are playing as the one and only\n God, experience the once-in-a-lifetime opportunity"
				+ "\n to wield SO MUCH power as you use the mouse (or touchpad)\n to drag and place obstacles in your very own"
				+ "\n stage!" , 400, 200);
	}

}
