package au.com.ionprogramming.voxometric.sound;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Music {

	Audio music;
	
	public Music(String path){
		try {
			music = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void toggle(boolean loop){
		if(music.isPlaying()){
			music.stop();
		}
		else{
			if(loop){
				loop();
			}
			else{
				play();
			}
		}
	}
	public void play(){
		music.playAsMusic(1.0f, 1.0f, false);
	}
	public void loop(){
		music.playAsMusic(1.0f, 1.0f, true);
	}
	public void stop(){
		music.stop();
	}
}
