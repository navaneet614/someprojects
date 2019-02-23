package utilities;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Navaneet Kadaba
 * 
 * The ImageLoader class is a static class that
 * provides a convenient and centralized way to 
 * load image (png) files. This helps in executing
 * the program faster, as well as eliminating the
 * issue of lagging.
 *
 */
public class ImageLoader {
	
	public static PImage block, character, glue, health, 
	platform, player_walk, spike, turret,mine, finish, background, startImage, multiplayerImage,deathImage, difficultyImage,levelImage;
	
	public static void loadAllImages(PApplet p, String locationOfResources) {
		block = p.loadImage(locationOfResources + "block.png");
		character = p.loadImage(locationOfResources + "character.png");
		glue = p.loadImage(locationOfResources + "glue.png");
		health = p.loadImage(locationOfResources + "health.png");
		platform = p.loadImage(locationOfResources + "platform.png");
		player_walk = p.loadImage(locationOfResources + "player_walk.gif");
		spike = p.loadImage(locationOfResources + "spike.png");
		turret = p.loadImage(locationOfResources + "turret2.png");
		mine = p.loadImage(locationOfResources + "mine.png");
		finish = p.loadImage(locationOfResources + "finish.png");
		background = p.loadImage(locationOfResources+ "GameBackground.jpg");
		background.resize(800, 600);
		startImage = p.loadImage(locationOfResources + "StartImage.png");
		startImage.resize(800,600);
		multiplayerImage = p.loadImage(locationOfResources+"multiplayerMenu.jpg");
		multiplayerImage.resize(800, 600);
		deathImage = p.loadImage(locationOfResources+"gameOver.png");
		deathImage.resize(800, 600);
		difficultyImage = p.loadImage(locationOfResources + "difficultyImage.jpg");
		difficultyImage.resize(800, 600);
		levelImage = p.loadImage(locationOfResources + "levels.jpg");
		levelImage.resize(800, 600);
				
	}
	
	public static void resizeImages(PApplet x){
		startImage.resize(x.width, x.height);
		background.resize(x.width, x.height);
		multiplayerImage.resize(x.width, x.height);
		deathImage.resize(x.width, x.height);
		difficultyImage.resize(x.width, x.height);
		levelImage.resize(x.width, x.height);
//		System.out.println("hello!");
	}
	

}
