package entities;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import collision.AABB;
import collision.Collision;
import entities.Entity;
import player.Animations;
import project.Camera;
import project.Model;
import project.Shader;
import project.Texture;
import project.Window;
import project.World;

public class Player extends Entity {
//	private Model model;
//	protected AABB bounding_box;
//	// private Texture texture ;
//	protected Transform transform;
//	protected Animations texture;
	public static final int ANIM_IDLE = 0;
	public static final int  ANIM_WALK = 1;
public static final int ANIM_SIZE = 2;


	public Player(Transform transform) {
		super(ANIM_SIZE,transform);
		//number of images, fps, file
		setAnimation(ANIM_IDLE, new Animations(4,2,"player/idle"));//player standing 
		setAnimation(ANIM_WALK, new Animations(4,4,"player/walk"));//player walking 
	}
	
	@Override
	public void update (float delta, Window window,Camera camera , World world ) {
		Vector2f movement = new Vector2f();//gets new position
		
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A))
			movement.add(-10 * delta, 0);//move character 10 units fr every frame

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D))
			movement.add(10 * delta, 0);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W))
			movement.add(0, 10 * delta);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S))
			movement.add(0, -10 * delta);
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_LEFT))
			movement.add(-10 * delta, 0);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_RIGHT))
			movement.add(10 * delta, 0); 

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_UP))
			movement.add(0, 10 * delta);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN))
			movement.add(0, -10 * delta);
		
		move(movement);
		if(movement.x != 0 || movement.y !=0)
			useAnimation(ANIM_WALK);
		else
			useAnimation(ANIM_IDLE);

		camera.getPosition().lerp(transform.pos.mul(-world.getScale(), new Vector3f()), 0.15f);//follows the player
//lerp = linear interpolutitation
		
	}
}