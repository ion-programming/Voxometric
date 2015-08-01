package au.com.ionprogramming.voxometric;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ChunkManager {
	private int oX, oY, oZ;
	private Chunk[][][] chunks;
	private String worldFile = "C:/Users/Sam/Desktop/world.vox";
	private int chunkSize;
	
	public ChunkManager(){
		chunks = new Chunk[1][1][1];
		oX = oY = oZ = 0;
	}
	
	public void addChunk(Chunk c, int x, int y, int z){
		int aX = x + oX;
		int aY = y + oY;
		int aZ = z + oZ;
		if(aX < 0){
			
		}
		else if(aX > chunks.length - 1){
			
		}
		if(aY < 0){
			
		}
		else if(aY > chunks[0].length - 1){
			
		}
		if(aZ < 0){
			
		}
		else if(aZ > chunks[0][0].length - 1){
			
		}
	}
	
	private Chunk loadChunk(int x, int y, int z){
		try{
			BufferedReader in = new BufferedReader(new FileReader(worldFile));
			try{
				String[] line;
				if((line = in.readLine().split(" ")) != null){
					try{
						chunkSize = Integer.parseInt(line[0]);
					}
					catch(NumberFormatException e){
						System.err.println("Unable to load chunk size!");
						in.close();
						return null;
					}
				}
				while((line = in.readLine().split(" ")) != null){
					if(line[0].equals(x + "") && line[1].equals(y + "") && line[2].equals(z + "")){
						Block[][][] chunkData = new Block[chunkSize][chunkSize][chunkSize];
						int i, j, k;
						i = j = k = 0;
						for(int m = 3; m < line.length; m++){
							if(line[m].indexOf(':') != -1){
								String[] word = line[m].split(":");
								for(int n = 0; n < Integer.parseInt(word[1]); n++){
									if(i == chunkSize){
										i = 0;
										j++;
										if(j == chunkSize){
											j = 0;
											k++;
											if(k == chunkSize){
												return new Chunk(chunkSize, chunkData);
											}
										}
									}
									try{
										chunkData[i][j][k] = (Block)(Class.forName(BlockList.getClassName(Integer.parseInt(word[0]))).newInstance());
									}
									catch(Exception e){
										chunkData[i][j][k] = null;
										System.err.println("Unable to find class!");
									}
									i++;
								}
							}
							else{
								if(i == chunkSize){
									i = 0;
									j++;
									if(j == chunkSize){
										j = 0;
										k++;
										if(k == chunkSize){
											return new Chunk(chunkSize, chunkData);
										}
									}
								}
								try{
									chunkData[i][j][k] = (Block)(Class.forName(BlockList.getClassName(Integer.parseInt(line[m]))).newInstance());
								}
								catch(Exception e){
									chunkData[i][j][k] = null;
									System.err.println("Unable to find class!");
								}
								i++;
							}
						}
						return new Chunk(chunkSize, chunkData);
					}
				}
			}
			finally{
				in.close();
			}
		}
		catch(IOException e){
		}
		return null;
	}
	
	private void saveChunk(int x, int y, int z){
		
	}
}
