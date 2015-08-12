package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Graphics;

public class Chunk {

	int chunkSize;
	int prevAng = -1;	//TODO: implement proper updating
	Block[][][] chunkData;
	int x, y, z;
	
	public Chunk(int chunkSize, Block[][][] chunkData, int chunkX, int chunkY, int chunkZ){
		this.chunkSize = chunkSize;
		this.chunkData = chunkData;
		computeCoveredBlocks();
		x = chunkX;
		y = chunkY;
		z = chunkZ;
	}
	
	public void computeCoveredBlocks(){
		for(int k = 0; k < chunkSize; k++){
			for(int j = 0; j < chunkSize; j++){
				for(int i = 0; i < chunkSize; i++){
					if(chunkData[i][j][k] != null){
						if(i > 0 && chunkData[i - 1][j][k] != null){
							if((!chunkData[i - 1][j][k].isTransparent()) 
								|| (chunkData[i - 1][j][k].isTransparent() && chunkData[i][j][k].isTransparent() 
								&& chunkData[i - 1][j][k].getClass().equals(chunkData[i][j][k].getClass()))){
								chunkData[i][j][k].coveredFaces += 2;
							}
						}
						if(i + 1 < chunkSize && chunkData[i + 1][j][k] != null){
							if((!chunkData[i + 1][j][k].isTransparent()) 
								|| (chunkData[i + 1][j][k].isTransparent() && chunkData[i][j][k].isTransparent() 
								&& chunkData[i + 1][j][k].getClass().equals(chunkData[i][j][k].getClass()))){
								chunkData[i][j][k].coveredFaces += 4;
							}
						}
						if(j > 0 && chunkData[i][j - 1][k] != null){
							if((!chunkData[i][j - 1][k].isTransparent()) 
								|| (chunkData[i][j - 1][k].isTransparent() && chunkData[i][j][k].isTransparent() 
								&& chunkData[i][j - 1][k].getClass().equals(chunkData[i][j][k].getClass()))){
								chunkData[i][j][k].coveredFaces += 8;
							}
						}
						if(j + 1 < chunkSize && chunkData[i][j + 1][k] != null){
							if((!chunkData[i][j + 1][k].isTransparent()) 
								|| (chunkData[i][j + 1][k].isTransparent() && chunkData[i][j][k].isTransparent() 
								&& chunkData[i][j + 1][k].getClass().equals(chunkData[i][j][k].getClass()))){
								chunkData[i][j][k].coveredFaces += 16;
							}
						}
						if(k + 1 < chunkSize && chunkData[i][j][k + 1] != null){
							if((!chunkData[i][j][k + 1].isTransparent()) 
								|| (chunkData[i][j][k + 1].isTransparent() && chunkData[i][j][k].isTransparent() 
								&& chunkData[i][j][k + 1].getClass().equals(chunkData[i][j][k].getClass()))){
								chunkData[i][j][k].coveredFaces += 1;
							}
						}
					}
				}
			}
		}
	}
	
	private void cull0(int i, int j, int k, int angle){
		while(i >= 0 && j >= 0 && k >= 0){
			if(chunkData[i][j][k] != null && !chunkData[i][j][k].isCovered(angle)){
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
	private void cull1(int i, int j, int k, int angle){
		while(i >= 0 && j < chunkSize && k >= 0){
			if(chunkData[i][j][k] != null && !chunkData[i][j][k].isCovered(angle)){
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
	private void cull2(int i, int j, int k, int angle){
		while(i < chunkSize && j < chunkSize && k >= 0){
			if(chunkData[i][j][k] != null && !chunkData[i][j][k].isCovered(angle)){
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
	private void cull3(int i, int j, int k, int angle){
		while(i < chunkSize && j >= 0 && k >= 0){
			if(chunkData[i][j][k] != null && !chunkData[i][j][k].isCovered(angle)){
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
				for(int j = 0; j < chunkSize - 1; j++){
					for(int i = 0; i < chunkSize - 1; i++){
						cull0(i, j, chunkSize - 1, angle);
					}
				}
				for(int k = 0; k < chunkSize; k++){
					for(int i = 0; i < chunkSize - 1; i++){
						cull0(i, chunkSize - 1, k, angle);
					}
					for(int j = 0; j < chunkSize; j++){
						cull0(chunkSize - 1, j, k, angle);
					}
				}
				break;
			case 1:
				for(int i = 0; i < chunkSize - 1; i++){
					for(int j = chunkSize - 1; j > 0; j--){
						cull1(i, j, chunkSize - 1, angle);
					}
				}
				for(int k = 0; k < chunkSize; k++){
					for(int j = chunkSize - 1; j > 0; j--){
						cull1(chunkSize - 1, j, k, angle);
					}
					for(int i = 0; i < chunkSize; i++){
						cull1(i, 0, k, angle);
					}
				}
				break;
			case 2:
				for(int j = chunkSize - 1; j > 0; j--){
					for(int i = chunkSize - 1; i > 0; i--){
						cull2(i, j, chunkSize - 1, angle);
					}
				}
				for(int k = 0; k < chunkSize; k++){
					for(int i = chunkSize - 1; i > 0; i--){
						cull2(i, 0, k, angle);
					}
					for(int j = chunkSize - 1; j >= 0; j--){
						cull2(0, j, k, angle);
					}
				}
				break;
			case 3:
				for(int i = chunkSize - 1; i > 0; i--){
					for(int j = 0; j < chunkSize - 1; j++){
						cull3(i, j, chunkSize - 1, angle);
					}
				}
				for(int k = 0; k < chunkSize; k++){
					for(int j = 0; j < chunkSize - 1; j++){
						cull3(0, j, k, angle);
					}
					for(int i = chunkSize - 1; i >= 0; i--){
						cull3(i, chunkSize - 1, k, angle);
					}
				}
				break;
		}
	}
	
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz){
		if(angle != prevAng){ //TODO: implement proper chunk updates
			prevAng = angle;
			for(int k = 0; k < chunkSize; k++){
				for(int j = 0; j < chunkSize; j++){
					for(int i = 0; i < chunkSize; i++){
						if(chunkData[i][j][k] != null){
							chunkData[i][j][k].setRenderTag(false);
						}
					}
				}
			}
			setRenderTags(angle);
		}
			
		for(int k = 0; k < chunkSize; k++){
			switch(angle){
				case 0:
					for(int j = 0; j < chunkSize; j++){
						for(int i = 0; i < chunkSize; i++){
							if(chunkData[i][j][k] != null){
								chunkData[i][j][k].render(g, width, height, angle, cx, cy, cz, x*chunkSize, y*chunkSize, z*chunkSize);
							}
						}
					}
					break;
				case 1:
					for(int i = 0; i < chunkSize; i++){
						for(int j = chunkSize - 1; j >= 0; j--){
							if(chunkData[i][j][k] != null){
								chunkData[i][j][k].render(g, width, height, angle, cx, cy, cz, x*chunkSize, y*chunkSize, z*chunkSize);
							}
						}
					}
					break;
				case 2:
					for(int j = chunkSize - 1; j >= 0; j--){
						for(int i = chunkSize - 1; i >= 0; i--){
							if(chunkData[i][j][k] != null){
								chunkData[i][j][k].render(g, width, height, angle, cx, cy, cz, x*chunkSize, y*chunkSize, z*chunkSize);
							}
						}
					}
					break;
				case 3:
					for(int i = chunkSize - 1; i >= 0; i--){
						for(int j = 0; j < chunkSize; j++){
							if(chunkData[i][j][k] != null){
								chunkData[i][j][k].render(g, width, height, angle, cx, cy, cz, x*chunkSize, y*chunkSize, z*chunkSize);
							}
						}
					}
			}
		}
	}
}
