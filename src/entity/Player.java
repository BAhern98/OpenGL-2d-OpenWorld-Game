package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import collision.AABB;
import collision.Collision;
import player.Animations;
import project.Camera;
import project.Model;
import project.Shader;
import project.Texture;
import project.Window;
import project.World;
import entity.Entity;

public class Player extends Entity {
	private Model model;
	private AABB bounding_box;
	// private Texture texture ;
	private Transform transform;
	private Animations texture;

	public Player(Transform transform) {
		super(new Animations(5,15,"image"),transform);
}
	
	@Override
	public void update (float delta, Window window,Camera camera , World world ) {
		Vector2f movement = new Vector2f();
		
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A))
			movement.add(-20 * delta, 0);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D))
			movement.add(20 * delta, 0);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W))
			movement.add(0, 20 * delta);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S))
			movement.add(0, -20 * delta);
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_LEFT))
			movement.add(-20 * delta, 0);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_RIGHT))
			movement.add(20 * delta, 0);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_UP))
			movement.add(0, 20 * delta);

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN))
			movement.add(0, -20 * delta);
		
		move(movement);
		
		super.update(delta, window, camera, world);
		
	}
}