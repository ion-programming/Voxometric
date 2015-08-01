package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Graphics;

public class Chunk {

	int chunkSize;
	
	Block[][][] chunkData;
	
	public Chunk(int chunkSize, Block[][][] chunkData){
		this.chunkSize = chunkSize;
		this.chunkData = chunkData;
		computeCoveredBlocks();
	}
	
	public void computeCoveredBlocks(){
		for(int z = 0; z < chunkSize; z++){
			for(int y = 0; y < chunkSize; y++){
				for(int x = 0; x < chunkSize; x++){
					if(chunkData[x][y][z] != null){
						
						if(x > 0 && chunkData[x - 1][y][z] != null){
							if((!chunkData[x - 1][y][z].isTransparent()) 
								|| (chunkData[x - 1][y][z].isTransparent() && chunkData[x][y][z].isTransparent() 
								&& chunkData[x - 1][y][z].getClass().equals(chunkData[x][y][z].getClass()))){
								chunkData[x][y][z].coveredFaces += 2;
							}
						}
						if(x + 1 < chunkSize && chunkData[x + 1][y][z] != null){
							if((!chunkData[x + 1][y][z].isTransparent()) 
								|| (chunkData[x + 1][y][z].isTransparent() && chunkData[x][y][z].isTransparent() 
								&& chunkData[x + 1][y][z].getClass().equals(chunkData[x][y][z].getClass()))){
								chunkData[x][y][z].coveredFaces += 4;
							}
						}
						if(y > 0 && chunkData[x][y - 1][z] != null){
							if((!chunkData[x][y - 1][z].isTransparent()) 
								|| (chunkData[x][y - 1][z].isTransparent() && chunkData[x][y][z].isTransparent() 
								&& chunkData[x][y - 1][z].getClass().equals(chunkData[x][y][z].getClass()))){
								chunkData[x][y][z].coveredFaces += 8;
							}
						}
						if(y + 1 < chunkSize && chunkData[x][y + 1][z] != null){
							if((!chunkData[x][y + 1][z].isTransparent()) 
								|| (chunkData[x][y + 1][z].isTransparent() && chunkData[x][y][z].isTransparent() 
								&& chunkData[x][y + 1][z].getClass().equals(chunkData[x][y][z].getClass()))){
								chunkData[x][y][z].coveredFaces += 16;
							}
						}
						if(z + 1 < chunkSize && chunkData[x][y][z + 1] != null){
							if((!chunkData[x][y][z + 1].isTransparent()) 
								|| (chunkData[x][y][z + 1].isTransparent() && chunkData[x][y][z].isTransparent() 
								&& chunkData[x][y][z + 1].getClass().equals(chunkData[x][y][z].getClass()))){
								chunkData[x][y][z].coveredFaces += 1;
							}
						}
					}
				}
			}
		}
	}
	
	private void cull0(int i, int j, int k){
		while(i >= 0 && j >= 0 && k >= 0){
			if(chunkData[i][j][k] != null){
				chunkData[i][j][k].setRenderTag(true);
				if(!chunkData[i][j][k].isTransparent()){
					return;
				}
			}
			i--;
			j--;
			k--;
		}
	}
	private void cull1(int i, int j, int k){
		while(i >= 0 && j < chunkSize && k >= 0){
			if(chunkData[i][j][k] != null){
				chunkData[i][j][k].setRenderTag(true);
				if(!chunkData[i][j][k].isTransparent()){
					return;
				}
			}
			i--;
			j++;
			k--;
		}
	}
	private void cull2(int i, int j, int k){
		while(i < chunkSize && j < chunkSize && k >= 0){
			if(chunkData[i][j][k] != null){
				chunkData[i][j][k].setRenderTag(true);
				if(!chunkData[i][j][k].isTransparent()){
					return;
				}
			}
			i++;
			j++;
			k--;
		}
	}
	private void cull3(int i, int j, int k){
		while(i < chunkSize && j >= 0 && k >= 0){
			if(chunkData[i][j][k] != null){
				chunkData[i][j][k].setRenderTag(true);
				if(!chunkData[i][j][k].isTransparent()){
					return;
				}
			}
			i++;
			j--;
			k--;
		}
	}
	private void setRenderTags(int angle){
		switch(angle){
			case 0:
				for(int y = 0; y < chunkSize - 1; y++){
					for(int x = 0; x < chunkSize - 1; x++){
						cull0(x, y, chunkSize - 1);
					}
				}
				for(int z = 0; z < chunkSize; z++){
					for(int x = 0; x < chunkSize - 1; x++){
						cull0(x, chunkSize - 1, z);
					}
					for(int y = 0; y < chunkSize; y++){
						cull0(chunkSize - 1, y, z);
					}
				}
				break;
			case 1:
				for(int x = 0; x < chunkSize - 1; x++){
					for(int y = chunkSize - 2; y >= 0; y--){
						cull1(x, y, chunkSize - 1);
					}
				}
				for(int z = 0; z < chunkSize; z++){
					for(int y = chunkSize - 1; y > 0; y--){
						cull1(chunkSize - 1, y, z);
					}
					for(int x = 0; x < chunkSize; x++){
						cull1(x, 0, z);
					}
				}
				break;
			case 2:
				for(int y = chunkSize - 1; y > 0; y--){
					for(int x = chunkSize - 1; x > 0; x--){
						cull2(x, y, chunkSize - 1);
					}
				}
				for(int z = 0; z < chunkSize; z++){
					for(int x = 0; x < chunkSize - 1; x++){
						cull2(x, 0, z);
					}
					for(int y = 0; y < chunkSize; y++){
						cull2(0, y, z);
					}
				}
				break;
			case 3:
				for(int x = chunkSize - 1; x > 0; x--){
					for(int y = 0; y < chunkSize - 1; y++){
						cull3(x, y, chunkSize - 1);
					}
				}
				for(int z = 0; z < chunkSize; z++){
					for(int y = 0; y < chunkSize - 1; y++){
						cull3(0, y, z);
					}
					for(int x = chunkSize - 1; x >= 0; x--){
						cull3(x, chunkSize - 1, z);
					}
				}
				break;
		}
	}
	int counter = 0;
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz){
		counter++;
		
		if(counter > 1){
			
			for(int z = 0; z < chunkSize; z++){
				for(int y = 0; y < chunkSize; y++){
					for(int x = 0; x < chunkSize; x++){
						if(chunkData[x][y][z] != null){
							chunkData[x][y][z].setRenderTag(false);
						}
					}
				}
			}
			
			setRenderTags(angle); //TODO: call from chunk update method
			counter = 0;
		}
			
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
					break;
				case 1:
					for(int x = 0; x < chunkSize; x++){
						for(int y = chunkSize - 1; y >= 0; y--){
							if(chunkData[x][y][z] != null){
								chunkData[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
				case 2:
					for(int y = chunkSize - 1; y >= 0; y--){
						for(int x = chunkSize - 1; x >= 0; x--){
							if(chunkData[x][y][z] != null){
								chunkData[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
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
