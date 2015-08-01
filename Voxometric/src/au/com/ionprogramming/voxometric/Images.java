package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {

	public static Image topGrass = null;
	public static Image lGrass = null;
	public static Image rGrass = null;
	
	public static void load(){
		
		try {
			rGrass = new Image("res/rightGrass.png");
			topGrass = new Image("res/topGrass.png");
			lGrass = new Image("res/leftGrass.png");
			
//			topGrass.setColor(3, 0.5f, 0.5f, 0.5f);
			
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
