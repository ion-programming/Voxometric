package au.com.ionprogramming.voxometric.blocks;

import org.newdawn.slick.Color;

import au.com.ionprogramming.voxometric.Block;
import au.com.ionprogramming.voxometric.Images;

public class Grass extends Block {
	
	static Color cGrassTop = new Color(0, 128, 0);
	static Color cGrassSides = new Color(100, 0, 0);
	
	public Grass(int x, int y, int z) {
		super(x, y, z);
		setColor(cGrassTop, cGrassSides);
//		setTexture(Images.topGrass, Images.lGrass, Images.rGrass);
		
	}

}
