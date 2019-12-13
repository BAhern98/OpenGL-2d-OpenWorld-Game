package player;

import project.Texture;
import project.Timer;

public class Animations {


	private Texture[] frames;
	private int pointer;
	
	
	private double elapsedTime;
	private double currentTime;
	private double lastTime;
	private double fps;

	
	
	public Animations(int amount, int fps, String filename) {
	// TODO Auto-generated constructor stub
		this.pointer = 0;
		this.currentTime = 0;
		this.elapsedTime = 0;
		this.lastTime = Timer.getTime();
		this.fps = 1.0/(double)fps;
		
		this.frames = new Texture[amount];
		for(int i = 0; i < amount ; i++) {
			this.frames[i] = new Texture("/animation/"+filename + "_" + i + ".png");
		}
			
	}
	public void bind() {
		bind(0);
	}
	
	public void bind(int sampler) {
		this.currentTime = Timer.getTime();
		this.elapsedTime += currentTime - lastTime;
		
		if(elapsedTime>=fps) {
			elapsedTime -= fps;
			pointer++;
		}
		
		if(pointer >= frames.length) pointer = 0;//0vkeeps array inbounds
		
		this.lastTime = currentTime;
		
		frames[pointer].bind(sampler);
	}
}
