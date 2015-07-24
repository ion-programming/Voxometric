package au.com.ionprogramming.voxometric;

import java.awt.Graphics;

public class Chunk {

	int chunkSize;
	Block[][][] chunkData;
	
	public Chunk(int chunkSize, Block[][][] chunkData){
		this.chunkSize = chunkSize;
		this.chunkData = chunkData;
	}
	
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz){
		for(int z = 0; z < chunkSize; z++){
			switch(angle){
				case 0:
					for(int y = 0; y < chunkSize; y++){
						for(int x = 0; x < chunkSize; x++){
							if(chunkData[x][y][z] != null){
								chunkData[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
				case 1:
					for(int x = 0; x < chunkSize; x++){
						for(int y = chunkSize - 1; y >= 0; y--){
							if(chunkData[x][y][z] != null){
								chunkData[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
				case 2:
					for(int y = chunkSize - 1; y >= 0; y--){
						for(int x = chunkSize - 1; x >= 0; x--){
							if(chunkData[x][y][z] != null){
								chunkData[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
				case 3:
					for(int x = chunkSize - 1; x >= 0; x--){
						for(int y = 0; y < chunkSize; y++){
							if(chunkData[x][y][z] != null){
								chunkData[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
			}
		}
	}
}
