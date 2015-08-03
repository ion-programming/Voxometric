package au.com.ionprogramming.voxometric.entities;

import au.com.ionprogramming.voxometric.InputManager;
import au.com.ionprogramming.voxometric.SlickGame;

public class Player extends MovingEntity {

	private SlickGame game;

	public Player(double x, double y, double z, SlickGame game) {
		super(x, y, z);
		this.game = game;
	}

	@Override
	public void update(InputManager i, float delta){
		switch(i.getViewAngle()){
			case 0:
				if(i.left){
					x -= speed*delta;
					y += speed*delta;
				}
				if(i.right){
					x += speed*delta;
					y -= speed*delta;
				}
				if(i.forward){
					y -= speed*delta;
					x -= speed*delta;
				}
				if(i.back){
					y += speed*delta;
					x += speed*delta;
				}
				break;
				
			case 1:
				if(i.left){
					y += speed*delta;
					x += speed*delta;
				}
				if(i.right){
					y -= speed*delta;
					x -= speed*delta;
				}
				if(i.forward){
					x -= speed*delta;
					y += speed*delta;
				}
				if(i.back){
					x += speed*delta;
					y -= speed*delta;
				}
				break;
				
			case 2:
				if(i.left){
					x += speed*delta;
					y -= speed*delta;
				}
				if(i.right){
					x -= speed*delta;
					y += speed*delta;
				}
				if(i.forward){
					y += speed*delta;
					x += speed*delta;
				}
				if(i.back){
					y -= speed*delta;
					x -= speed*delta;
				}
				break;
				
			case 3:
				if(i.left){
					y -= speed*delta;
					x -= speed*delta;	
				}
				if(i.right){
					y += speed*delta;
					x += speed*delta;
				}
				if(i.forward){
					x += speed*delta;
					y -= speed*delta;
				}
				if(i.back){
					x -= speed*delta;
					y += speed*delta;
				}
				break;
		}
		
		if(i.up){
			z += speed*delta;
		}
		if(i.down){
			z -= speed*delta;
		}
		
		game.cx = x;
		game.cy = y;
		game.cz = z;
	}
}

