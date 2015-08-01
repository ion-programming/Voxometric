package au.com.ionprogramming.voxometric.entities;

import org.newdawn.slick.Graphics;

import au.com.ionprogramming.voxometric.InputManager;

public class MovingEntity {
	
	protected double x = 0;
	protected double y = 0;
	protected double z = 0;
	
	protected double speed = 0.2;
	
	public MovingEntity(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void render(Graphics g){
		
	}
	
	public void update(InputManager i, float delta){
		
	}
	
	public double getSpeed(){
		return speed;
	}
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
}
