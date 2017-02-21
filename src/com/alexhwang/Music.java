package com.alexhwang;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.SwingUtilities;

public class Music {
	static File soundFile;
	static Clip clip;

	public static void play(final String sf) throws Exception{
		soundFile = new File(sf);
		clip = AudioSystem.getClip();
		final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
        clip.open(stream);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
            }
        });
	}
	
	public static void pause() {
		clip.stop();
	}
	
	public static void resume() {
		clip.start();
	}
	
	public static void changeVolume(final float value) {
		final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(value);
	}
}
