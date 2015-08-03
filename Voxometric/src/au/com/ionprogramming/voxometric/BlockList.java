package au.com.ionprogramming.voxometric;

import java.util.ArrayList;

public class BlockList {

	private ArrayList<String> names = new ArrayList<String>();

	public BlockList() {
		this(new ArrayList<String>());
	}

	public BlockList(ArrayList<String> names) {
		this.names = names;
	}

	public String getClassName(int id) {
		try {
			return names.get(id);
		}
		catch(Exception e) {
			System.err.println("Unknown Block id!");
			return null;
		}
	}
	
	public int getBlockID(String name) {
		return names.indexOf(name);
	}
	
	public void addBlockType(String name) {
		names.add(name);
	}
	
	public void addBlockType(String name, int index) {
		names.add(index, name);
	}
	
	public void removeBlockType(String name) {
		names.remove("name");
	}
	
	public void removeBlockType(int index) {
		names.remove(index);
	}

	public ArrayList<String> getNames() {
		return names;
	}

	public static BlockList append(BlockList blockListA, BlockList blockListB) {
		ArrayList<String> list = new ArrayList<String>(blockListA.getNames().size() + blockListB.getNames().size());
		list.addAll(blockListA.getNames());
		list.addAll(blockListB.getNames());
		return new BlockList(list);
	}
}
