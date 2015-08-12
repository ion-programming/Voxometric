package au.com.ionprogramming.voxometric;

import java.util.ArrayList;

public class BlockList {
	private ArrayList<Class> types = new ArrayList<Class>();
	
	public Class getClassType(int id){
		if(id != -1){
			try{
				return types.get(id);
			}
			catch(Exception e){
				System.err.println("Unknown Block id!");
			}
		}
		return null;
	}
	
	public int getBlockID(Block type){
		if(type == null){
			return -1;
		}
		return types.indexOf(type.getClass());
	}
	
	public void addBlockType(Block type){
		types.add(type.getClass());
	}
	
	public void addBlockType(Block type, int index){
		types.add(index, type.getClass());
	}
	
	public void removeBlockType(Block type){
		for(int n = 0; n < types.size(); n++){
			if(types.get(n).equals(type.getClass())){
				types.remove(type);
				return;
			}
		}
		System.err.println("Unknown block type: " + type.getClass().getName());
	}
	
	public void removeBlockType(int index){
		types.remove(index);
	}
}
