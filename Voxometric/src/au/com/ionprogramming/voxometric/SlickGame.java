package au.com.ionprogramming.voxometric;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import au.com.ionprogramming.voxometric.entities.EntityManager;

public class SlickGame extends BasicGame{
	
	private static int width = 1280;
	private static int height = 720;
	
	private Chunk chunk;	//TODO: temporary
	
	public static double cx = 0;
	public static double cy = 0;
	public static double cz = 0;
	
	private InputManager input;
		
	
	public SlickGame(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Images.load();
		chunk = Generator.generate();
		Block.setBlockSize(40, 20);
		
		EntityManager.init();
		
		input = new InputManager();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		
		float delta = 60f/gc.getFPS();
		
		if(delta > 10){
			delta = 10;
		}
			
		input.input(gc.getInput());
		
		EntityManager.update(input, delta);
		
//		move(delta);
		
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		chunk.render(g, width, height, input.getViewAngle() , cx, cy, cz);
		
		EntityManager.render(g);
		
		g.setColor(Color.white);
		g.drawString("Coords: " + String.format("%4.2f", cx) + ", " + String.format("%4.2f", cy) + ", " + String.format("%4.2f", cz), 10 , 30);
	}

	public static void main(String[] args){
		try{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SlickGame("Voxometric"));
			appgc.setDisplayMode(width, height, false);
			appgc.setTargetFrameRate(60);
			appgc.setAlwaysRender(true);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public int getW(){
		return width;
	}
	public int getH(){
		return height;
	}

	
}
