package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Input;

public class InputManager {
	
	public boolean forward = false;
	public boolean back = false;
	public boolean right = false;
	public boolean left = false;
	public boolean up = false;
	public boolean down = false;
	
	public int angle = 0;
	
	public InputManager(){
		
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
	
	public int getViewAngle(){
		return angle;
	}
	public void setViewAngle(int angle){
		this.angle = angle;
	}
	
}
