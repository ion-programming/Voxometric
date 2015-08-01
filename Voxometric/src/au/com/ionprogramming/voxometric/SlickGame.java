package au.com.ionprogramming.voxometric;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SlickGame extends BasicGame{
	
	private static int width = 1000;
	private static int height = 600;
	
	private Chunk chunk = Light.illuminate(Generator.generate());	//TODO: temporary
	
	private boolean forward = false;
	private boolean back = false;
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	private int angle = 0;
	private double speed = 0.2;
	private double cx = 0;
	private double cy = 0;
	private double cz = 0;
	
	
	
	public SlickGame(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Block.setBlockSize(40, 20);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		input(gc.getInput());
		
		move();
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		chunk.render(g, width, height, angle, cx, cy, cz);
		
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
	
			
//			appgc.setShowFPS(false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void input(Input i){
		if(i.isKeyPressed(Input.KEY_Q)){
			if(angle > 0){
				angle--;
			}
			else{
				angle = 3;
			}
		}
		if(i.isKeyPressed(Input.KEY_E)){
			if(angle < 3){
				angle++;
			}
			else{
				angle = 0;
			}
		}
		
		forward = i.isKeyDown(Input.KEY_W);
		
		back = i.isKeyDown(Input.KEY_S);
		
		left = i.isKeyDown(Input.KEY_A);
		
		right = i.isKeyDown(Input.KEY_D);
		
		down = i.isKeyDown(Input.KEY_LSHIFT);
		
		up = i.isKeyDown(Input.KEY_SPACE);
	}
	
	public void move(){
		switch(angle){
			case 0:
				if(left){
					cx -= speed;
					cy += speed;
				}
				if(right){
					cx += speed;
					cy -= speed;
				}
				if(forward){
					cy -= speed;
					cx -= speed;
				}
				if(back){
					cy += speed;
					cx += speed;
				}
				break;
				
			case 1:
				if(left){
					cy += speed;
					cx += speed;
				}
				if(right){
					cy -= speed;
					cx -= speed;
				}
				if(forward){
					cx -= speed;
					cy += speed;
				}
				if(back){
					cx += speed;
					cy -= speed;
				}
				break;
				
			case 2:
				if(left){
					cx += speed;
					cy -= speed;
				}
				if(right){
					cx -= speed;
					cy += speed;
				}
				if(forward){
					cy += speed;
					cx += speed;
				}
				if(back){
					cy -= speed;
					cx -= speed;
				}
				break;
				
			case 3:
				if(left){
					cy -= speed;
					cx -= speed;	
				}
				if(right){
					cy += speed;
					cx += speed;
				}
				if(forward){
					cx += speed;
					cy -= speed;
				}
				if(back){
					cx -= speed;
					cy += speed;
				}
				break;
		}
		
		if(up){
			cz += speed;
		}
		if(down){
			cz -= speed;
		}
	}
	
	public int getW(){
		return width;
	}
	public int getH(){
		return height;
	}
	public double getSpeed(){
		return speed;
	}
	public void setSpeed(double speed){
		this.speed = speed;
	}
	public int getViewAngle(){
		return angle;
	}
	public void setViewAngle(int angle){
		this.angle = angle;
	}
}
