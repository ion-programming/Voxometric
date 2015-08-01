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
	
	public static Chunk illuminate(Chunk input){
		for(int lx = 0; lx < input.chunkSize; lx++){
			for(int ly = 0; ly < input.chunkSize; ly++){
				for(int lz = 0; lz < input.chunkSize; lz++){
					if(input.chunkData[lx][ly][lz] != null){
//						input.chunkData[lx][ly][lz].xmax.darker();
					}
				}
			}
		}
		
		return input;
	}
	
//	public double distance(int x1, int y1, int z1, int x2, int y2, int z2){
//		
//		return 
//	}

}
