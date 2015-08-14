package au.com.ionprogramming.voxometric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.newdawn.slick.Graphics;

public class ChunkManager {
	private int oX, oY, oZ;
	private Chunk[][][] chunks;
	private String worldFile = "C:/Users/Sam/Desktop/world.vox";
	private int chunkSize;
	private int loadSize;
	private int reloadThreshold = 2;
	private BlockList blockList;
	
	public ChunkManager(int loadSize){
		this.loadSize = loadSize;
		chunks = new Chunk[loadSize][loadSize][loadSize];
		oX = oY = oZ = 0;
		blockList = new BlockList();
	}
	
	public BlockList getBlockList(){
		return blockList;
	}
	
	public void setBlockList(BlockList blockList){
		this.blockList = blockList;
	}
	
	public void illuminate(Light light){
		for(int z = 0; z < loadSize; z++){
			for(int y = 0; y < loadSize; y++){
				for(int x = 0; x < loadSize; x++){
					if(chunks[x][y][z] != null){
						light.illuminate(chunks[x][y][z]);
					}
				}
			}
		}
	}

	public void illuminate(SunLight light){
		for(int z = 0; z < loadSize; z++){
			for(int y = 0; y < loadSize; y++){
				for(int x = 0; x < loadSize; x++){
					if(chunks[x][y][z] != null){
						light.illuminate(chunks[x][y][z]);
					}
				}
			}
		}
	}
	
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz){
		for(int z = 0; z < loadSize; z++){
			switch(angle){
				case 0:
					for(int y = 0; y < loadSize; y++){
						for(int x = 0; x < loadSize; x++){
							if(chunks[x][y][z] != null){
								chunks[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
				case 1:
					for(int x = 0; x < loadSize; x++){
						for(int y = loadSize - 1; y >= 0; y--){
							if(chunks[x][y][z] != null){
								chunks[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
				case 2:
					for(int y = loadSize - 1; y >= 0; y--){
						for(int x = loadSize - 1; x >= 0; x--){
							if(chunks[x][y][z] != null){
								chunks[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
				case 3:
					for(int x = loadSize - 1; x >= 0; x--){
						for(int y = 0; y < loadSize; y++){
							if(chunks[x][y][z] != null){
								chunks[x][y][z].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
			}
		}
	}
	
	public void update(int midX, int midY, int midZ){
		int xOS = midX + oX - loadSize/2;
		int yOS = midY + oY - loadSize/2;
		int zOS = midZ + oZ - loadSize/2;
		if(Math.abs(xOS) >= reloadThreshold || Math.abs(yOS) >= reloadThreshold || Math.abs(zOS) >= reloadThreshold){
			oX -= xOS;
			oY -= yOS;
			oZ -= zOS;
			Chunk[][][] temp = new Chunk[loadSize][loadSize][loadSize];
			int tX;
			int tY;
			int tZ;
			for(int k = 0; k < loadSize; k++){
				for(int j = 0; j < loadSize; j++){
					for(int i = 0; i < loadSize; i++){
						tX = i + xOS;
						tY = j + yOS;
						tZ = k + zOS;
						if(tX < 0 || tY < 0 || tZ < 0 || tX >= loadSize || tY >= loadSize || tZ >= loadSize){
							temp[i][j][k] = loadChunk(i - oX, j - oY, k - oZ);
						}
						else{
							temp[i][j][k] = chunks[tX][tY][tZ];
						}
					}
				}
			}
			for(int j = 0; j < loadSize; j++){
				for(int i = 0; i < loadSize; i++){
					chunks[i][j] = Arrays.copyOf(temp[i][j], loadSize);
				}
			}
		}
	}
	
	public void addChunk(int x, int y, int z){
		int aX = x + oX;
		int aY = y + oY;
		int aZ = z + oZ;
		if(aX < 0 || aX >= loadSize || aY < 0 || aY >= loadSize || aZ < 0 || aZ >= loadSize){
			System.err.println("Cannot load chunk! Out of bounds!");
			return;
		}
		chunks[aX][aY][aZ] = loadChunk(x, y, z);
	}
	
	public Chunk loadChunk(int x, int y, int z){
		try{
			BufferedReader in = new BufferedReader(new FileReader(worldFile));
			try{
				String line;
				String[] lineList;
				if((line = in.readLine()) != null){
					lineList = line.split(" ");
					try{
						chunkSize = Integer.parseInt(lineList[0]);
					}
					catch(NumberFormatException e){
						System.err.println("Unable to load chunk size!");
						in.close();
						return null;
					}
				}
				while((line = in.readLine()) != null){
					lineList = line.split(" ");
					if(lineList[0].equals(x + "") && lineList[1].equals(y + "") && lineList[2].equals(z + "")){
						Block[][][] chunkData = new Block[chunkSize][chunkSize][chunkSize];
						int i, j, k;
						i = j = k = 0;
						for(int m = 3; m < lineList.length; m++){
							if(lineList[m].indexOf(':') != -1){
								String[] word = lineList[m].split(":");
								for(int n = 0; n < Integer.parseInt(word[1]); n++){
									if(i == chunkSize){
										i = 0;
										j++;
										if(j == chunkSize){
											j = 0;
											k++;
											if(k == chunkSize){
												return new Chunk(chunkSize, chunkData, x, y, z);
											}
										}
									}
									try{
										Class[] args = new Class[]{int.class, int.class, int.class};
										chunkData[i][j][k] = (Block) blockList.getClassType(Integer.parseInt(word[0])).getDeclaredConstructor(args).newInstance(i, j, k);
									}
									catch(Exception e){
										chunkData[i][j][k] = null;
										if(blockList.getClassType(Integer.parseInt(word[0])) != null){
											System.err.println("Unable to find class with id: " + word[0]);
										}
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
											return new Chunk(chunkSize, chunkData, x, y, z);
										}
									}
								}
								try{
									Class[] args = new Class[]{int.class, int.class, int.class};
									chunkData[i][j][k] = (Block) blockList.getClassType(Integer.parseInt(lineList[m])).getDeclaredConstructor(args).newInstance(i, j, k);
								}
								catch(Exception e){
									chunkData[i][j][k] = null;
									System.err.println("Unable to find class with id: " + lineList[m]);
								}
								i++;
							}
						}
						return new Chunk(chunkSize, chunkData, x, y, z);
					}
				}
			}
			finally{
				in.close();
			}
		}
		catch(IOException e){
			System.err.println("Unable to find save file: " + worldFile);
		}
		return null;
	}
	
	public void saveChunk(Chunk chunk){
		if(chunk == null){
			return;
		}
		try{
			BufferedReader in = new BufferedReader(new FileReader(worldFile));
			BufferedWriter out = new BufferedWriter(new FileWriter(worldFile + "_"));
			try{
				boolean create = true;
				String line;
				while((line = in.readLine()) != null){
					String[] start = line.split(" ", 4);
					if(start.length >= 3 && start[0].equals(chunk.x + "") && start[1].equals(chunk.y + "") && start[2].equals(chunk.z + "")){
						create = false;
						out.write(chunk.x + " " + chunk.y + " " + chunk.z + " ");
						int id = blockList.getBlockID(chunk.chunkData[0][0][0]);
						int lineCount = 0;
						for(int k = 0; k < chunkSize; k++){
							for(int j = 0; j < chunkSize; j++){
								for(int i = 0; i < chunkSize; i++){
									int nextID = blockList.getBlockID(chunk.chunkData[i][j][k]);
									if(id != nextID){
										out.write(id + ":" + lineCount + " ");
										lineCount = 0;
										id = nextID;
									}
									lineCount++;
								}
							}
						}
						out.write(id + ":" + lineCount + " ");
						out.newLine();
					}
					else{
						out.write(line);
						out.newLine();
					}
				}
				if(create){
					out.write(chunk.x + " " + chunk.y + " " + chunk.z + " ");
					int id = blockList.getBlockID(chunk.chunkData[0][0][0]);
					int lineCount = 0;
					for(int k = 0; k < chunkSize; k++){
						for(int j = 0; j < chunkSize; j++){
							for(int i = 0; i < chunkSize; i++){
								int nextID = blockList.getBlockID(chunk.chunkData[i][j][k]);
								if(id != nextID){
									out.write(id + ":" + lineCount + " ");
									lineCount = 0;
									id = nextID;
								}
								lineCount++;
							}
						}
					}
					out.write(id + ":" + lineCount + " ");
					out.newLine();
				}
			}
			finally{
				in.close();
				out.close();
				Files.delete(Paths.get(worldFile));
				File f = new File(worldFile + "_");
				f.renameTo(new File(worldFile));
			}
		}
		catch(IOException e){
			System.err.println("Unable to find save file: " + worldFile);
		}
	}
}
