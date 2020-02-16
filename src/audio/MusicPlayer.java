package audio;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;

public class MusicPlayer implements Runnable{
	private ArrayList<String> musicFiles;
	private int SongIndex;

	public MusicPlayer(String file) {
		musicFiles = new ArrayList<String>();
		
			musicFiles.add("./resource/media/"+file+ ".wav");
		// TODO Auto-generated constructor stub
	}
	private void playSound(String fileName) {
		try {
			File soundFile = new File(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais); 
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		playSound(musicFiles.get(SongIndex));
	
		
		
	}

}
