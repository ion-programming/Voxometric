package au.com.ionprogramming.voxometric.world;

import au.com.ionprogramming.voxometric.Block;
import au.com.ionprogramming.voxometric.Chunk;
import au.com.ionprogramming.voxometric.blocks.Grass;
import au.com.ionprogramming.voxometric.blocks.Water;

public class ChunkGenerator {

	public static int CHUNK_SIZE = 16;
	public static int CHUNK_HEIGHT = 32;
	public static int WATER_THRESHOLD = 12;
	
	
	public static Chunk getChunk(int cx, int cy){
		int[][] heightMap = SimplexNoise.generateNoiseMap(cx, cy, CHUNK_SIZE, CHUNK_HEIGHT);
		
		Block[][][] blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_HEIGHT];
		
		for(int x = 0; x < CHUNK_SIZE; x++){
			for(int y = 0; y < CHUNK_SIZE; y++){
				for(int z = 0; z < CHUNK_HEIGHT; z++){
					if(heightMap[x][y] >= z){
						blocks[x][y][z] = new Grass(x, y, z);
					}
					else if(z <= WATER_THRESHOLD){
						blocks[x][y][z] = new Water(x, y, z);
					}
					
				}
			}
		}
		return new Chunk(CHUNK_SIZE, CHUNK_HEIGHT, blocks, cx, cy); 
	}
}
