package au.com.ionprogramming.voxometric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Graphics;

public class ChunkManager {
	private int oX, oY;
	private Chunk[][] chunks;
	private String worldFile;
	private int chunkSize;
	private int chunkHeight;
	private int loadSize;
	private int reloadThreshold;
	private BlockList blockList;
	private ArrayList<SunLight> sunLights = new ArrayList<SunLight>();
	private ArrayList<Light> lights = new ArrayList<Light>();
	
	public ChunkManager(int loadSize, int reloadThreshold, String worldFile){
		this.loadSize = loadSize;
		this.reloadThreshold = reloadThreshold;
		this.worldFile = worldFile;
		chunks = new Chunk[loadSize][loadSize];
		oX = oY = 0;
		blockList = new BlockList();
	}
	
	public void init(int oX, int oY){
		this.oX = oX;
		this.oY = oY;
		for(int j = 0; j < loadSize; j++){
			for(int i = 0; i < loadSize; i++){
				chunks[i][j] = loadChunk(i - oX, j - oY);
				for(int n = 0; n < sunLights.size(); n++){
					sunLights.get(n).illuminate(chunks[i][j]);
				}
				for(int n = 0; n < lights.size(); n++){
					lights.get(n).illuminate(chunks[i][j]);
				}
			}
		}
	}
	
	public BlockList getBlockList(){
		return blockList;
	}
	
	public void setBlockList(BlockList blockList){
		this.blockList = blockList;
	}
	
	public void addLight(Light light){
		lights.add(light);
	}
	
	public void addLight(SunLight light){
		sunLights.add(light);
	}
	
	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz){
			switch(angle){
				case 0:
					for(int y = 0; y < loadSize; y++){
						for(int x = 0; x < loadSize; x++){
							if(chunks[x][y] != null){
								chunks[x][y].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
				case 1:
					for(int x = 0; x < loadSize; x++){
						for(int y = loadSize - 1; y >= 0; y--){
							if(chunks[x][y] != null){
								chunks[x][y].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
				case 2:
					for(int y = loadSize - 1; y >= 0; y--){
						for(int x = loadSize - 1; x >= 0; x--){
							if(chunks[x][y] != null){
								chunks[x][y].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
					break;
				case 3:
					for(int x = loadSize - 1; x >= 0; x--){
						for(int y = 0; y < loadSize; y++){
							if(chunks[x][y] != null){
								chunks[x][y].render(g, width, height, angle, cx, cy, cz);
							}
						}
					}
			}
	}
	
	public void update(int playerX, int playerY, int playerZ){
		int xOS = playerX/chunkSize + oX - loadSize/2;
		int yOS = playerY/chunkSize + oY - loadSize/2;
		if(Math.abs(xOS) >= reloadThreshold || Math.abs(yOS) >= reloadThreshold){
			oX -= xOS;
			oY -= yOS;
			Chunk[][] temp = new Chunk[loadSize][loadSize];
			int tX;
			int tY;
			for(int j = 0; j < loadSize; j++){
				for(int i = 0; i < loadSize; i++){
					tX = i + xOS;
					tY = j + yOS;
					if(tX < 0 || tY < 0 || tX >= loadSize || tY >= loadSize){
						temp[i][j] = loadChunk(i - oX, j - oY);
						for(int n = 0; n < sunLights.size(); n++){
							sunLights.get(n).illuminate(temp[i][j]);
						}
						for(int n = 0; n < lights.size(); n++){
							lights.get(n).illuminate(temp[i][j]);
						}
					}
					else{
						temp[i][j] = chunks[tX][tY];
					}
				}
			}
			for(int i = 0; i < loadSize; i++){
				chunks[i] = Arrays.copyOf(temp[i], loadSize);
			}
		}
	}
	
	public Chunk[] loadChunks(ArrayList<Integer> x, ArrayList<Integer> y){
		try{
			BufferedReader in = new BufferedReader(new FileReader(worldFile));
			try{
				String line;
				String[] lineList;
				if((line = in.readLine()) != null){
					lineList = line.split(" ");
					try{
						chunkSize = Integer.parseInt(lineList[0]);
						chunkHeight = Integer.parseInt(lineList[1]);
					}
					catch(Exception e){
						System.err.println("Unable to load chunk size!");
						in.close();
						return null;
					}
				}
				Chunk[] newChunks = new Chunk[x.size()];
				int a = 0;
				while((line = in.readLine()) != null){
					lineList = line.split(" ");
					for(int t = 0; t < x.size(); t++){
						if(lineList[0].equals(x.get(t) + "") && lineList[1].equals(y.get(t) + "")){
							Block[][][] chunkData = new Block[chunkSize][chunkSize][chunkHeight];
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
												if(k == chunkHeight){
													newChunks[a] = new Chunk(chunkSize, chunkHeight, chunkData, x.get(t), y.get(t));
													a++;
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
											if(k == chunkHeight){
												newChunks[a] = new Chunk(chunkSize, chunkHeight, chunkData, x.get(t), y.get(t));
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
							newChunks[a] = new Chunk(chunkSize, chunkHeight, chunkData, x.get(t), y.get(t));
						}
					}
				}
				return newChunks;
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
	
	public Chunk loadChunk(int x, int y){
		try{
			BufferedReader in = new BufferedReader(new FileReader(worldFile));
			try{
				String line;
				String[] lineList;
				if((line = in.readLine()) != null){
					lineList = line.split(" ");
					try{
						chunkSize = Integer.parseInt(lineList[0]);
						chunkHeight = Integer.parseInt(lineList[1]);
					}
					catch(Exception e){
						System.err.println("Unable to load chunk size!");
						in.close();
						return null;
					}
				}
				while((line = in.readLine()) != null){
					lineList = line.split(" ");
					if(lineList[0].equals(x + "") && lineList[1].equals(y + "")){
						Block[][][] chunkData = new Block[chunkSize][chunkSize][chunkHeight];
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
											if(k == chunkHeight){
												return new Chunk(chunkSize, chunkHeight, chunkData, x, y);
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
										if(k == chunkHeight){
											return new Chunk(chunkSize, chunkHeight, chunkData, x, y);
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
						return new Chunk(chunkSize, chunkHeight, chunkData, x, y);
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
	
	public void saveNewWorld(Chunk[] world){
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(worldFile));
			try{
				chunkSize = world[0].chunkSize;
				chunkHeight = world[0].chunkHeight;
				out.write(chunkSize + chunkHeight + "");
				out.newLine();
				for(int n = 0; n < world.length; n++){
					out.write(world[n].x + " " + world[n].y + " ");
					int id = blockList.getBlockID(world[n].chunkData[0][0][0]);
					int lineCount = 0;
					for(int k = 0; k < chunkHeight; k++){
						for(int j = 0; j < chunkSize; j++){
							for(int i = 0; i < chunkSize; i++){
								int nextID = blockList.getBlockID(world[n].chunkData[i][j][k]);
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
				out.close();
			}
		}
		catch(IOException e){
			System.err.println("Unable to save world file");
		}
	}
	
	public void saveChunks(ArrayList<Chunk> chunkList){
		try{
			BufferedReader in = new BufferedReader(new FileReader(worldFile));
			BufferedWriter out = new BufferedWriter(new FileWriter(worldFile + "_"));
			try{
				String line;
				while((line = in.readLine()) != null){
					String[] start = line.split(" ", 3);
					for(int t = 0; t < chunkList.size(); t++){
						if(start.length >= 2 && start[0].equals(chunkList.get(t).x + "") && start[1].equals(chunkList.get(t).y + "")){
							out.write(chunkList.get(t).x + " " + chunkList.get(t).y + " ");
							int id = blockList.getBlockID(chunkList.get(t).chunkData[0][0][0]);
							int lineCount = 0;
							for(int k = 0; k < chunkHeight; k++){
								for(int j = 0; j < chunkSize; j++){
									for(int i = 0; i < chunkSize; i++){
										int nextID = blockList.getBlockID(chunkList.get(t).chunkData[i][j][k]);
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
							chunkList.remove(t);
							t--;
						}
						else{
							out.write(line);
							out.newLine();
						}
					}
				}
				for(int t = 0; t < chunkList.size(); t++){
					out.write(chunkList.get(t).x + " " + chunkList.get(t).y + " ");
					int id = blockList.getBlockID(chunkList.get(t).chunkData[0][0][0]);
					int lineCount = 0;
					for(int k = 0; k < chunkHeight; k++){
						for(int j = 0; j < chunkSize; j++){
							for(int i = 0; i < chunkSize; i++){
								int nextID = blockList.getBlockID(chunkList.get(t).chunkData[i][j][k]);
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
					String[] start = line.split(" ", 3);
					if(start.length >= 2 && start[0].equals(chunk.x + "") && start[1].equals(chunk.y + "")){
						create = false;
						out.write(chunk.x + " " + chunk.y + " ");
						int id = blockList.getBlockID(chunk.chunkData[0][0][0]);
						int lineCount = 0;
						for(int k = 0; k < chunkHeight; k++){
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
					out.write(chunk.x + " " + chunk.y + " ");
					int id = blockList.getBlockID(chunk.chunkData[0][0][0]);
					int lineCount = 0;
					for(int k = 0; k < chunkHeight; k++){
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
