package au.com.ionprogramming.voxometric;

import java.util.Random;

import au.com.ionprogramming.voxometric.blocks.Grass;

public class Generator {

	public static Chunk generate(){
		Random r = new Random();
		int chunkSize = 3;
		Block[][][] blocks = new Block[chunkSize][chunkSize][chunkSize];
		for(int z = 0; z < chunkSize; z++){
			for(int y = 0; y < chunkSize; y++){
				for(int x = 0; x < chunkSize; x++){
					if(z < 5 /*|| (z == 5 && r.nextInt(3) == 0)*/){
						blocks[x][y][z] = new Grass(x, y, z);
					}
				}
			}
		}
		return new Chunk(chunkSize, blocks);
	}
	
}
