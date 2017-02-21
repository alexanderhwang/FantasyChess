package com.alexhwang;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.SwingUtilities;



public class SoundEffect {
	static File soundFile;
	static Clip clip;

	public static void play(final String sf) throws Exception{
		soundFile = new File(sf);
		clip = AudioSystem.getClip();
		final AudioInputStream stream = AudioSystem.getAudioInputStream(soundFile);
        clip.open(stream);
        clip.start();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
            }
        });
	}
	
}
