package au.com.ionprogramming.voxometric;

import org.newdawn.slick.Input;

import au.com.ionprogramming.voxometric.gui.Minimap;

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
		
		if(i.isKeyPressed(Input.KEY_M)){
			if(!SlickGame.drawMiniMap){
				Minimap.generate(16, 12);
				SlickGame.drawMiniMap = true;
			}
			else{
				SlickGame.drawMiniMap = false;
			}
			
		}
	}
	
	public int getViewAngle(){
		return angle;
	}
	public void setViewAngle(int angle){
		this.angle = angle;
	}
	
}
