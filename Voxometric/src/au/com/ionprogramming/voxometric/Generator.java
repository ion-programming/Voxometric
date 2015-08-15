package au.com.ionprogramming.voxometric;

import java.util.Random;

import org.newdawn.slick.Color;

import au.com.ionprogramming.voxometric.blocks.Grass;
import au.com.ionprogramming.voxometric.blocks.Water;

public class Generator {

	static Random r = new Random();
	
	public static ChunkManager generateWorld(){
		
		ChunkManager cm = new ChunkManager(8, 1, "C:/vox/world.vox");
		
		BlockList bl = new BlockList();
		bl.addBlockType(new Grass(0, 0, 0));
		bl.addBlockType(new Water(0, 0, 0));
		cm.setBlockList(bl);
		
		Chunk[] chunks = new Chunk[16*16*16];
		int i = 0;
		//GENERATE
		for(int z = 0; z < 16; z++){
			for(int y = 0; y < 16; y++){
				for(int x = 0; x < 16; x++){
					chunks[i] = generate(x, y, z);
					i++;
				}
			}
		}
		cm.saveNewWorld(chunks);
		cm.addLight(new SunLight(-20, 0, -4, Color.darkGray));
		cm.addLight(new Light(20, 20, 20, 30, Color.white));
		cm.addLight(new Light(-10, 0, 10, 20, Color.white));
		cm.init(0, 0, 0);
		
		return cm;
		
	}
	
	
	
	public static Chunk generate(int xx, int yy, int zz){
		int chunkSize = 16;
		Block[][][] blocks = new Block[chunkSize][chunkSize][chunkSize];
		for(int z = 0; z < chunkSize; z++){
			for(int y = 0; y < chunkSize - z; y++){
				for(int x = 0; x < chunkSize - z; x++){
					int rand = r.nextInt(10);
//					if(rand >= 8){
//						blocks[x][y][z] = new Water(x, y, z);
//					}
//					else if(rand <= 1){
//						blocks[x][y][z] = new Emerald(x, y, z);
//					}
//					else if(rand < 8){
						blocks[x][y][z] = new Grass(x, y, z);
//					}
				}
			}
		}
		return new Chunk(chunkSize, blocks, xx, yy, zz);
	}
	
}
