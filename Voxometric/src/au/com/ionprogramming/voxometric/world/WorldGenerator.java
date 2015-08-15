package au.com.ionprogramming.voxometric.world;

import java.util.Random;

import org.newdawn.slick.Color;

import au.com.ionprogramming.voxometric.Block;
import au.com.ionprogramming.voxometric.BlockList;
import au.com.ionprogramming.voxometric.Chunk;
import au.com.ionprogramming.voxometric.ChunkManager;
import au.com.ionprogramming.voxometric.Light;
import au.com.ionprogramming.voxometric.SunLight;
import au.com.ionprogramming.voxometric.blocks.Grass;
import au.com.ionprogramming.voxometric.blocks.Water;

public class WorldGenerator {

static Random r = new Random();
	
	static int chunkSize = 16;
	static int worldSize = 4;
	static int[][] heightmap;

	public static ChunkManager generateWorld(){
		
		heightmap = Noise.generateNoiseMap(chunkSize*worldSize);

	    System.out.println("Height Map Generated!");
	      
		ChunkManager cm = new ChunkManager(16, 4, "C:/vox/world.vox");
		
		BlockList bl = new BlockList();
		bl.addBlockType(new Grass(0, 0, 0));
		bl.addBlockType(new Water(0, 0, 0));
		cm.setBlockList(bl);
		
		Chunk[] chunks = new Chunk[worldSize*worldSize*worldSize];
		System.out.println("Chunk[] Generated!");
		int i = 0;
		
		//GENERATE
		for(int x = 0; x < worldSize; x++){
			for(int y = 0; y < worldSize; y++){
				for(int z = 0; z < worldSize; z++){
					chunks[i] = generate(x, y, z);
					i++;
				}
			}
		}
		
		System.out.println("Chunks Generated!");
		
		cm.saveNewWorld(chunks);
		
		System.out.println("World Saved!");
		
		cm.addLight(new SunLight(-20, 0, -4, Color.white));
		cm.addLight(new Light(20, 20, 20, 30, Color.white));
		cm.addLight(new Light(-10, 0, 10, 20, Color.white));
		System.out.println("Light Map Generated!");
		
		cm.init(0, 0, 0);
		
		return cm;
		
	}

	public static Chunk generate(int xx, int yy, int zz){
		
		Block[][][] blocks = new Block[chunkSize][chunkSize][chunkSize];
		
		for(int x = 0; x < chunkSize; x++){
			for(int y = 0; y < chunkSize; y++){
				for(int z = 0; z < chunkSize; z++){
					
					if(heightmap[x+(xx*chunkSize)][y+(yy*chunkSize)] >= z+(zz*chunkSize)){
						blocks[x][y][z] = new Grass(x, y, z);
					}
					else if(z+(zz*chunkSize) <= 15){
						blocks[x][y][z] = new Water(x, y, z);
					}
					
				}
			}
		}
		return new Chunk(chunkSize, blocks, xx, yy, zz);
	}
}
