package entity;

import player.Animations;
import project.Camera;
import project.Texture;
import project.Window;
import project.World;

public class Rock extends Entity{
	public static final int ROCK_IDLE = 0;
	public static final int ROCK_SIZE = 1;
	public Rock(Transform transform) {
		// TODO Auto-generated constructor stub
		super(ROCK_SIZE,transform);
		setAnimation(ROCK_IDLE, new Animations(1,1,"world_Entities/rock"));//player standing 
		
	}
	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		// TODO Auto-generated method stub
		
	}

}
