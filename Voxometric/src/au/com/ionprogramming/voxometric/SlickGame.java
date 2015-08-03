package au.com.ionprogramming.voxometric;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import au.com.ionprogramming.voxometric.entities.EntityManager;

public class SlickGame extends BasicGame {

	public static final int width = 1280;
	public static final int height = 720;

	/** Camera X */
	public double cx = 0;
	/** Camera Y */
	public double cy = 0;
	/** Camera Z */
	public double cz = 0;

	private ChunkManager chunkManager;
	private InputManager inputManager;
	private EntityManager entityManager;

	public SlickGame(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Images.load();
		chunkManager = new ChunkManager(new SimpleMapGenerator());
		chunkManager.generateChunks(
				0, 1, 0,
				1, 0, 0,
				0,-1, 0,
				-1,0, 0,
				0, 0, 1
				);
		System.out.println(Arrays.toString(chunkManager.getSortedKeys(0)));
		System.out.println(Arrays.toString(chunkManager.getSortedKeys(1)));
		System.out.println(Arrays.toString(chunkManager.getSortedKeys(2)));
		System.out.println(Arrays.toString(chunkManager.getSortedKeys(3)));
		SunLight light = new SunLight(-20, 0, -4, Color.white);
		chunkManager.illuminate(light);
		Block.setBlockSize(40, 20);
		
		entityManager = new EntityManager(this);
		
		inputManager = new InputManager();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		float delta = 60f/gc.getFPS();
		
		if(delta > 10){
			delta = 10;
		}
			
		inputManager.input(gc.getInput());
		
		entityManager.update(inputManager, delta);
		
//		move(delta);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		chunkManager.render(g, width, height, inputManager.getViewAngle(), cx, cy, cz);
		
		entityManager.render(g);
		
		g.setColor(Color.white);
		g.drawString("Coords: " + String.format("%4.2f", cx) + ", " + String.format("%4.2f", cy) + ", " + String.format("%4.2f", cz), 10 , 30);
	}

	public static void main(String[] args) {
		try {
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