package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Color;

public class Light {
	
	private int x, y, z;
	private int throwRaduis;
	private Color c;
	
	public Light(int x, int y, int z, int throwRadius, Color c){
		this.x = x;
		this.y = y;
		this.z = z;
		this.throwRaduis = throwRadius;
		this.c = c;
	}
	
	public static Chunk illuminate(Chunk chunk){
		for(int lx = 0; lx < Chunk.chunkSize; lx++){
			for(int ly = 0; ly < Chunk.chunkSize; ly++){
				for(int lz = 0; lz < Chunk.chunkSize; lz++){
					if(chunk.getBlock(lx, ly, lz) != null){
//						input.chunkData[lx][ly][lz].xmax.darker();
					}
				}
			}
		}
		return chunk;
	}
	
//	public double distance(int x1, int y1, int z1, int x2, int y2, int z2){
//		
//		return 
//	}

}
