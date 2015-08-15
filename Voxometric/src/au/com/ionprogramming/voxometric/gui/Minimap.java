package au.com.ionprogramming.voxometric.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import au.com.ionprogramming.voxometric.SlickGame;
import au.com.ionprogramming.voxometric.world.ChunkGenerator;
import au.com.ionprogramming.voxometric.world.SimplexNoise;

public class Minimap {

	static int[][] minimap;
	
	public static void generate(int xChunks, int yChunks){
		minimap = new int[xChunks*ChunkGenerator.CHUNK_SIZE][yChunks*ChunkGenerator.CHUNK_SIZE];
		
		int startX = (int) (SlickGame.cx/ChunkGenerator.CHUNK_SIZE - (xChunks / 2));
		int startY = (int) (SlickGame.cy/ChunkGenerator.CHUNK_SIZE - (yChunks / 2));
		System.out.println(startX + ", " + startY);
		for(int x = 0; x < xChunks; x++){
			for(int y = 0; y < yChunks; y++){
				int[][] heightMap = SimplexNoise.generateNoiseMap(x-startX, y-startY, ChunkGenerator.CHUNK_SIZE, ChunkGenerator.CHUNK_HEIGHT);
				
				for(int cx = 0; cx < ChunkGenerator.CHUNK_SIZE; cx++){
					for(int cy = 0; cy < ChunkGenerator.CHUNK_SIZE; cy++){
						
						minimap[(x*ChunkGenerator.CHUNK_SIZE) + cx][(y*ChunkGenerator.CHUNK_SIZE) + cy] = heightMap[cx][cy];
					}
				}
				
			}
		}
		
	}
	public static void render(Graphics g){
		
		g.setColor(Color.black);
		
		g.fillRect(SlickGame.width/2 - minimap.length/2 - 10, SlickGame.height/2 - minimap[0].length/2 - 10, minimap.length +20, minimap[0].length + 20);
		
		for(int x = 0; x < minimap.length; x++){
			for(int y = 0; y < minimap[0].length; y++){
				if(minimap[x][y] < ChunkGenerator.WATER_THRESHOLD){
					g.setColor(Color.blue);
					g.fillRect(SlickGame.width/2 - minimap.length/2 + x, SlickGame.height/2 - minimap[0].length/2 +y, 1, 1);
					//DRAW BLUE AT X Y
					
				}
				else{
					g.setColor(Color.green);
					g.fillRect(SlickGame.width/2 - minimap.length/2 + x, SlickGame.height/2 - minimap[0].length/2 +y, 1, 1);
					//DRAW GREEN AT X Y
					
				}
			}
		}
	
	
	}
	
	
}
