package au.com.ionprogramming.voxometric;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

import org.newdawn.slick.Graphics;

public class ChunkManager {

	private HashMap<Vector, Chunk> chunks;
	private BlockList blockList;
	private MapGenerator mapGenerator;

	public ChunkManager(MapGenerator mapGenerator) {
		this.mapGenerator = mapGenerator;
		chunks = new HashMap<Vector, Chunk>();
		generateChunk(0, 0, 0);
		blockList = new BlockList();
	}

	public BlockList getBlockList(){
		return blockList;
	}

	public void setBlockList(BlockList blockList){
		this.blockList = blockList;
	}

	public Vector[] getKeys() {
		return getChunks().keySet().toArray(new Vector[getChunks().keySet().size()]);
	}

	public Vector[] getSortedKeys(final int angle) {
		Vector[] vectors = getKeys();
		Arrays.sort(vectors, new Comparator<Vector>() {
			@Override
			public int compare(Vector v1, Vector v2) {
				return Math.round(v2.getCameraDistance(angle) - v1.getCameraDistance(angle));
			}
		});
		return vectors;
	}

	public void addChunk(Chunk c, int x, int y, int z){
		addChunk(c, new Vector(x, y, z));
	}

	public void addChunk(Chunk c, Vector v) {
		chunks.put(v, c);
	}

	public void generateChunk(int x, int y, int z) {
		generateChunk(new Vector(x, y, z));
	}

	public void generateChunk(Vector v) {
		chunks.put(v, mapGenerator.generate(v));
	}

	public void generateChunks(Vector[] vectors) {
		for (Vector v : vectors) {
			generateChunk(v);
		}
	}

	public void generateChunks(int... ints) {
		for (int i = 2; i < ints.length; i += 3) {
			generateChunk(ints[i-2], ints[i-1], ints[i]);
		}
	}

	public HashMap<Vector, Chunk> getChunks() {
		return chunks;
	}

	public Chunk getChunk(Vector key) {
		return chunks.get(key);
	}

	public Collection<Chunk> getChunkList() {
		return chunks.values();
	}

	public void render(Graphics g, int width, int height, int angle, double cx, double cy, double cz) {
		System.out.println("\n\nChunkManager.render()");
		Vector[] vectors = getSortedKeys(angle);
		for (int i = 0; i < vectors.length; i++) {
			Chunk chunk = chunks.get(vectors[i]);
			System.out.println(vectors[i].equals(new Vector(0, 0, 0)));
			System.out.println(chunk);
			chunk.render(g, width, height, angle, cx+vectors[i].getX()*Chunk.chunkSize, cy+vectors[i].getY()*Chunk.chunkSize, cz+vectors[i].getZ()*Chunk.chunkSize);
		}
	}

	public void illuminate(SunLight light) {
		for (Chunk chunk : getChunks().values()) {
			light.illuminate(chunk);
		}
	}
}