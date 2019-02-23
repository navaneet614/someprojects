package obstacles;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import processing.core.*;
import utilities.GameScreen;
import utilities.ImageLoader;

import javax.swing.Timer;

/**
 * 
 * @author Shaunak Bhandarkar
 * 
 * The Turret class is an Obstacle that is capable of 
 * shooting bullets in a given direction indefinitely.
 * While it does not do much damage to the Player, it 
 * greatly restricts the paths the Player can take to
 * get past the stream of Bullets.
 *
 */
public class Turret extends Obstacle implements ActionListener
{
	private ArrayList<Bullet> bullets;
	private Timer t;
	double angle;
	int count;
	
	public Turret(float x, float y, double width, double height, double a) 
	{
		super(x, y, ImageLoader.turret, width, height);
		bullets = new ArrayList<Bullet>();
		t = new Timer( 10, this );
		t.start();
		angle = a;
		count = 0;
	}
	
	public void startClock() {
		t.start();
	}

	public int getDamage() 
	{	
		super.hit();
//		if(canDamage()) {
//			return 1;
//		}
		return 0;
	}
	
	
	public void shoot() 
	{
		bullets.add(new Bullet(getX()-getWidth()/8 - 15, getY()+getHeight()/2, angle));
		//angle+=0.7;
	}
	
	public void draw( PApplet drawer ) 
	{
		super.draw(drawer);
		GameScreen gs = (GameScreen)drawer;
		
		this.getBoundRect().setBounds((int)getX(), (int)getY() + 10, (int)getWidth(), (int)getHeight() - 10);

		
		for(int j = 0;j<bullets.size();j++) {
			Bullet b = bullets.get(j);
			if(b.getY()<0||b.getY()>gs.ORIGINAL_HEIGHT) {
				bullets.remove(j);
			} else {
				b.draw(drawer);
			}

		}
	}
	
	public ArrayList<Bullet> bullets() 
	{
		return bullets;
	}

	public void actionPerformed(ActionEvent e) 
	{

		if ( count % 300 == 0) 
		{
			shoot();
		}
		for ( Bullet b : bullets ) 
		{
			b.shoot();
		}
		count++;

		
	}

}
