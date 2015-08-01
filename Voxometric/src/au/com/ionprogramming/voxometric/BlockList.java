package au.com.ionprogramming.voxometric;

import java.util.ArrayList;

public class BlockList {
	private static ArrayList<String> names = new ArrayList<String>();
	
	public static String getClassName(int id){
		try{
			return names.get(id);
		}
		catch(Exception e){
			System.err.println("Unknown Block id!");
		}
		return null;
	}
}
