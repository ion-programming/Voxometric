package au.com.ionprogramming.voxometric;

import java.awt.EventQueue;

public class SetUp {
	
	private static int width = 1208;
	private static int height = 808;
	@SuppressWarnings("unused")
	private static Window frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					init();
					frame = new Window(width, height);
					frame.setTitle("Voxometric");
					frame.setFPS(60);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void init(){
		Block.setBlockSize(50, 32);
	}

}
