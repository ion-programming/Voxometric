package au.com.ionprogramming.voxometric.sound;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SFX {

	Audio sfx;
	
	public SFX(String path){
		try {
			sfx = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void play(){
		sfx.playAsSoundEffect(1.0f, 1.0f, false);
	}
	public void loop(){
		sfx.playAsSoundEffect(1.0f, 1.0f, true);
	}
	public void playAt(float x, float y, float z){
		sfx.playAsSoundEffect(1.0f, 1.0f, false, x, y, z);
	}
	public void loopAt(float x, float y, float z){
		sfx.playAsSoundEffect(1.0f, 1.0f, true, x, y, z);
	}
	public void stop(){
		sfx.stop();
	}
}
