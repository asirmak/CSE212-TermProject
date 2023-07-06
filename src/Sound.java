import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	// class to hold game sounds
	
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound(int fb) {
			
		if(fb == 0) soundURL[0] = getClass().getResource("sounds/gameSound.wav"); // 0 is refer to main background sound
		
		else { // this else block for sound effects
			soundURL[1] = getClass().getResource("sounds/bulletSE.wav");
			soundURL[2] = getClass().getResource("sounds/gameOver.wav");
			soundURL[3] = getClass().getResource("sounds/hitsound.wav");
			soundURL[4] = getClass().getResource("sounds/highscore.wav");
			soundURL[5] = getClass().getResource("sounds/winSE.wav");
		}
	
	}
	public void setFile(int i) {		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
	
}
