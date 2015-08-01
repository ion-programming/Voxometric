package au.com.ionprogramming.voxometric.entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import au.com.ionprogramming.voxometric.InputManager;

public class EntityManager {

	private static ArrayList<MovingEntity> moving = new ArrayList<MovingEntity>();
	
	public static void init(){
		moving.add(new Player(0, 0, 0));
	}
	
	public static void render(Graphics g){
		for(int i = 0; i < moving.size(); i++){
			moving.get(i).render(g);
		}
	}
	
	public static void update(InputManager in, float delta){
		for(int i = 0; i < moving.size(); i++){
			moving.get(i).update(in, delta);
		}
	}
}
