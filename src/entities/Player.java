package entities;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

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
	ArrayList<Entity> entities = (ArrayList<Entity>) World.getEntities();
	// public ArrayList<Entity> entities; // contains all entities
	public static final int IDLE = 0;
	public static final int RUN = 1;

	public Player(Transform transform) {
		super(4, transform, 22, 44, 19, 19);
		// number of images, fps, file
		setAnimation(IDLE, new Animations(4, 2, "player/idle"));// player standing
		setAnimation(RUN, new Animations(4, 4, "player/run"));// player walking

	}

	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		Vector2f movement = new Vector2f();// gets new position

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A))
			movement.add(-10 * delta, 0);// move character 10 units fr every frame

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D))
			movement.add(10 * delta, 0);// move character 10 units fr every frame

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W))
			movement.add(0, 10 * delta);// move character 10 units fr every frame

		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S))
			movement.add(0, -10 * delta);// move character 10 units fr every frame

		Rectangle collisionBounds = getCollisionBounds(0, 0);
		Rectangle attackRectangle = new Rectangle();
		int attackRectangleSize = 20;
		attackRectangle.width = attackRectangleSize;
		attackRectangle.height = attackRectangleSize;


		if (window.getInput().isKeyPressed(GLFW.GLFW_KEY_UP))
			attackRectangle.x = collisionBounds.x + collisionBounds.width / 2 - attackRectangleSize / 2;
		attackRectangle.y = collisionBounds.y - attackRectangleSize;

		if (window.getInput().isKeyPressed(GLFW.GLFW_KEY_DOWN))
			attackRectangle.x = collisionBounds.x + collisionBounds.width / 2 - attackRectangleSize / 2;
		attackRectangle.y = collisionBounds.y + collisionBounds.height;

		if (window.getInput().isKeyPressed(GLFW.GLFW_KEY_LEFT))
			attackRectangle.x = collisionBounds.x - attackRectangleSize;
		attackRectangle.y = collisionBounds.y + collisionBounds.height / 2 - attackRectangleSize / 2;
		if (window.getInput().isKeyPressed(GLFW.GLFW_KEY_RIGHT))
			attackRectangle.x = collisionBounds.x + collisionBounds.width;
		attackRectangle.y = collisionBounds.y + collisionBounds.height / 2 - attackRectangleSize / 2;

		for (Entity entity : World.entities) {// itaarate through all the entities in list of entities
			if (entity.equals(this))
				continue;
			if (entity.getCollisionBounds(0, 0).intersects(attackRectangle)) {// checks if the attack hits entity
				entity.hurt(2);//damages entity
				return;
			}

		}

		move(movement);
		if (movement.x != 0 || movement.y != 0)
			useAnimation(RUN);
		else
			useAnimation(IDLE);

		camera.getPosition().lerp(transform.position.mul(-world.getScale(), new Vector3f()), 0.1f);// follows the player

//lerp = linear interpolutitation

	}
//	public void attack() {
//		AABB[] boxes = new AABB[9];//surrounds entity with 5*5 bounding boxes
//	
//	
//	}

	@Override
	public void destroy() {
		System.out.println("Game Over");
	}

//	private void checkAttack() {
//		Rectangle cb = new Rectangle();
//		Rectangle ar = new Rectangle();
//		int arSize = 20 ;
//		ar.width = arSize;
//		ar.height = arSize;
//		
//
//		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_UP)) 
//			
//		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_LEFT))
//			
//			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_RIGHT))
//
//
//				
//
//			if (window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN))
//		
//		
//		
//	}
}