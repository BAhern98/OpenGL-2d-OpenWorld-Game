package entities;


import java.awt.Rectangle;

import org.joml.Matrix4f;
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

public abstract  class Entity {
	
	
	
	private static Model model;
	protected AABB boundingBox;

	// private Texture texture ;
	protected Transform transform;
	
	
	
	protected Animations[] animations;//array for player animations
	private int useAnimation;// which frame to start at
	
	
	
	public boolean active = true;
	
	
	
	protected int x, y;
	protected int width, height;
	protected Rectangle bounds;

	
	//
	// changed to protected so any child class will have access to that
	//
	protected int health; 
	public static final int default_health = 10;
	
	public Entity(int maxAnimations, Transform transform, int x, int y, int width, int height) {
		
		
		bounds = new Rectangle(0, 0, width, height);
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height= height;
		
		this.animations= new Animations[maxAnimations];
	
		this.useAnimation = 0;

		this.transform = transform;
		boundingBox = new AABB(new Vector2f(transform.position.x, transform.position.y), new Vector2f(transform.scale.x, transform.scale.y));//scales bounding box
		health = default_health;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setAnimation(int index, Animations animation) {//sets animation
		animations[index] = animation;
	}
	
	public void useAnimation(int index) {//select animation
		this.useAnimation = index;
	}
public Rectangle getCollisionBounds(int xOffset, int yOffset){
		
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	public void move(Vector2f direction) {//tells if player has moved
		transform.position.add(new Vector3f(direction, 0));
		boundingBox.getCenter().set(transform.position.x, transform.position.y);// updates center of bounding box
	}

	public abstract void update(float delta, Window window, Camera camera, World world);

	public void collideWithTiles(World world) {// put in new method because we want the world to handle every entity

		AABB[] boxes = new AABB[25];//surrounds entity with 5*5 bounding boxes

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boxes[i + j * 5] = world.getTileBoundingBox(

						(int) (((transform.position.x / 2) + 0.5) - (5 / 2)) + i,
						(int) (((-transform.position.y / 2) + 0.5) - (5 / 2)) + j);
				// gets everything around player for
				// bounding boxes
			}
		}	
		AABB box = null;
		//
		// getting closest box
		//
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] != null) {
				if (box == null)
					box = boxes[i];
				Vector2f length1 = box.getCenter().sub(transform.position.x, transform.position.y, new Vector2f());
				Vector2f length2 = boxes[i].getCenter().sub(transform.position.x, transform.position.y, new Vector2f());

				if (length1.lengthSquared() > length2.lengthSquared()) {// used squared to save cpu power
					box = boxes[i];
				}
			}
		}
		//
		// colliding with it
		//
		if (box != null) {
			Collision data = boundingBox.getCollision(box);
			if (data.isIntersecting) {
				boundingBox.correctPosition(box, data);
				transform.position.set(boundingBox.getCenter(), 0);
			}
			

			//
			// next closest box
			//

			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) {
					if (box == null)
						box = boxes[i];

					Vector2f length1 = box.getCenter().sub(transform.position.x, transform.position.y, new Vector2f());
					Vector2f length2 = boxes[i].getCenter().sub(transform.position.x, transform.position.y, new Vector2f());

					if (length1.lengthSquared() > length2.lengthSquared()) {// used squared to save cpu power
						box = boxes[i];
					}
				}

			}
			// coliding with it
			data = boundingBox.getCollision(box);
			if (data.isIntersecting) {
				boundingBox.correctPosition(box, data);
				transform.position.set(boundingBox.getCenter(), 0);
			}

		}
	}
	


//public void Attack(World world) {// put in new method because we want the world to handle every entity
//
//	AABB[] boxes = new AABB[25];//surrounds entity with 5*5 bounding boxes
//
//	for (int i = 0; i < 5; i++) {
//		for (int j = 0; j < 5; j++) {
//			boxes[i + j * 5] = world.getTileBoundingBox(
//
//					(int) (((transform.position.x / 2) + 0.5) - (5 / 2)) + i,
//					(int) (((-transform.position.y / 2) + 0.5) - (5 / 2)) + j);
//			// gets everything around player for
//			// bounding boxes
//		}
//	}	
//	AABB box = null;
//	//
//	// getting closest box
//	//
//	for (int i = 0; i < boxes.length; i++) {
//		if (boxes[i] != null) {
//			if (box == null)
//				box = boxes[i];
//			Vector2f length1 = box.getCenter().sub(transform.position.x, transform.position.y, new Vector2f());
//			Vector2f length2 = boxes[i].getCenter().sub(transform.position.x, transform.position.y, new Vector2f());
//
//			if (length1.lengthSquared() > length2.lengthSquared()) {// used squared to save cpu power
//				box = boxes[i];
//				
//			}
//		}
//	}
//	//
//	// colliding with it
//	//
//	if (box != null) {
//	
//		}
//		}
	

	public abstract void destroy();
	
	
	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getprojection(); 
		target.mul(world.getWorldMatrix());
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		animations[useAnimation].bind(0);//binds texture at animation
		model.render();
	}

	public static void initialiseEntity() {// initialise the model
		float[] vertices = new float[] { -1f, 1f, 0, // top left 0
				1f, 1f, 0, // top right 1
				1f, -1f, 0, // bottom right 2
				-1f, -1f, 0,// bottom left 3

		};

		float[] texture = new float[] { 0, 0, 1, 0, 1, 1, 0, 1,

		};

		int[] indices = new int[] { 0, 1, 2, 2, 3, 0, };

		model = new Model(vertices, texture, indices);
	}

	public static void deleteEntity() {// get rid of model
		float[] vertices = new float[] { -1f, 1f, 0, // top left 0
				1f, 1f, 0, // top right 1
				1f, -1f, 0, // bottom right 2
				-1f, -1f, 0,// bottom left 3

		};

		float[] texture = new float[] { 0, 0, 1, 0, 1, 1, 0, 1,

		};

		int[] indices = new int[] { 0, 1, 2, 2, 3, 0, };

		model = new Model(vertices, texture, indices);
	}
	

	public void collideWithEntity(Entity entity) {// method for coliding with just entites
		Collision collision = boundingBox.getCollision(entity.boundingBox);// get all data so we can colide with the entity using aabb
																				
		if (collision.isIntersecting) { // test if it is intersecting
			collision.distance.x/=10;//leaves smaller gap when moving entity objects
			collision.distance.y/=10;
			//return entity.active(false);
 			
			boundingBox.correctPosition(entity.boundingBox, collision);// correct the position
			transform.position.set(boundingBox.getCenter().x, boundingBox.getCenter().y, 0);// setting the transform
		
			entity.boundingBox.correctPosition(boundingBox, collision);//corect entities bounding box with our bounding box
			entity.transform.position.set(entity.boundingBox.getCenter().x, entity.boundingBox.getCenter().y, 0);//set its transformation position
		
		//collision.h
		}
	}
//		public void collideWithEntityDecrementHealth(Entity entity) {// method for coliding with just entites
//			Collision collision = boundingBox.getCollision(entity.boundingBox);// get all data so we can colide with the entity using aabb
//																					
//			if (collision.isIntersecting) { // test if it is intersecting
//				collision.distance.x/=10;//leaves smaller gap when moving entity objects
//				collision.distance.y/=10;
//				//return entity.active(false);
//	 			
//				boundingBox.correctPosition(entity.boundingBox, collision);// correct the position
//				transform.position.set(boundingBox.getCenter().x, boundingBox.getCenter().y, 0);// setting the transform
//			
//				entity.boundingBox.correctPosition(boundingBox, collision);//corect entities bounding box with our bounding box
//				entity.transform.position.set(entity.boundingBox.getCenter().x, entity.boundingBox.getCenter().y, 0);//set its transformation position
//			entity.hurt();
//			//collision.h
//			}
//	} 
	
	

	public void hurt(int damage) {
		health -= damage;
		if(health<= 0) {
			active = false;
			destroy();
		
		}
		
		
	}
	
	
}
