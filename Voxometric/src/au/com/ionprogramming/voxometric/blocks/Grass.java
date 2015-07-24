package au.com.ionprogramming.voxometric.blocks;

import java.awt.Color;

import au.com.ionprogramming.voxometric.Block;

public class Grass extends Block {

	static Color top = new Color(0, 128, 0);
	static Color side = new Color(112, 28, 0);
	
	public Grass(int x, int y, int z) {
		super(x, y, z, top, side);
		
	}

}
