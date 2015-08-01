package au.com.ionprogramming.voxometric.blocks;

import au.com.ionprogramming.voxometric.Block;
import au.com.ionprogramming.voxometric.Images;

public class Grass extends Block {
	
	public Grass(int x, int y, int z) {
		super(x, y, z);
	
		setTexture(Images.topGrass, Images.lGrass, Images.rGrass);
		
	}

}
