package au.com.ionprogramming.voxometric.entities;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import au.com.ionprogramming.voxometric.InputManager;
import au.com.ionprogramming.voxometric.SlickGame;

public class EntityManager {

	private ArrayList<MovingEntity> moving = new ArrayList<MovingEntity>();
	
	public EntityManager(SlickGame game) {
		moving.add(new Player(0, 0, 0, game));
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < moving.size(); i++){
			moving.get(i).render(g);
		}
	}
	
	public void update(InputManager in, float delta) {
		for(int i = 0; i < moving.size(); i++){
			moving.get(i).update(in, delta);
		}
	}
}
