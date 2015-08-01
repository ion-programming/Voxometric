package au.com.ionprogramming.voxometric.blocks;

import org.newdawn.slick.Color;

import au.com.ionprogramming.voxometric.Block;

public class Water extends Block {

	static Color top = new Color(0, 0, 1f, 0.5f);
	static Color side = top;
	
	public Water(int x, int y, int z) {
		super(x, y, z, top, side);
		setTransparent(true);
		
	}

}
