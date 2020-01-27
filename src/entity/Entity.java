package entity;

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
	protected AABB bounding_box;
	// private Texture texture ;
	protected Transform transform;
	protected Animations[] animations;//array for player animations
	private int use_animation;

	//
	// changed to protected so any child class will have access to that
	//
	public Entity(int max_animations, Transform transform) {

		this.animations= new Animations[max_animations];
		this.transform = transform;
		this.use_animation = 0;

		bounding_box = new AABB(new Vector2f(transform.pos.x, transform.pos.y), new Vector2f(transform.scale.x, transform.scale.y));//scales bounding box
	}
	protected void setAnimation(int index, Animations animation) {//sets animation
		animations[index] = animation;
	}
	
	public void useAnimation(int index) {//select animation
		this.use_animation = index;
	}

	public void move(Vector2f direction) {//tells if player has moved
		transform.pos.add(new Vector3f(direction, 0));
		bounding_box.getCenter().set(transform.pos.x, transform.pos.y);// updates center of bounding box
	}

	public abstract void update(float delta, Window window, Camera camera, World world);

	public void collideWithTiles(World world) {// put in new method because we want the world to handle every entity

		AABB[] boxes = new AABB[25];//sorounds emtity with 25 bounding boxes
//
		//
		//  sets 5 * 5 bounding boxes around the entity
		//
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boxes[i + j * 5] = world.getTileBoundingBox(

						(int) (((transform.pos.x / 2) + 0.5f) - (5 / 2)) + i,
						(int) (((-transform.pos.y / 2) + 0.5f) - (5 / 2)) + j);// gets everything around player for
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

				Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
				Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

				if (length1.lengthSquared() > length2.lengthSquared()) {// used squared to save cpu power
					box = boxes[i];
				}
			}

		}

		//
		// colliding with it
		//

		if (box != null) {
			Collision data = bounding_box.getCollision(box);
			if (data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(), 0);
			}

			//
			// next closest box
			//

			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) {
					if (box == null)
						box = boxes[i];

					Vector2f length1 = box.getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());
					Vector2f length2 = boxes[i].getCenter().sub(transform.pos.x, transform.pos.y, new Vector2f());

					if (length1.lengthSquared() > length2.lengthSquared()) {// used squared to save cpu power
						box = boxes[i];
					}
				}

			}
			// coliding with it
			data = bounding_box.getCollision(box);
			if (data.isIntersecting) {
				bounding_box.correctPosition(box, data);
				transform.pos.set(bounding_box.getCenter(), 0);
			}

		}
	}

	public void render(Shader shader, Camera camera, World world) {
		Matrix4f target = camera.getprojection(); 
		target.mul(world.getWorldMatrix());
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(target));
		animations[use_animation].bind(0);//binds texture at animation
		model.render();
	}

	public static void IntiAsset() {// initialise the model
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

	public static void DelAsset() {// get rid of model
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

//	public void collideWithEntity(Entity entity) {// method for coliding with just entites
//		Collision collision = bounding_box.getCollision(entity.bounding_box);// get all data so we can colide with the entity
//																				
//		if (collision.isIntersecting) { // test if it is intersecting
////			collision.distance.x/=2;//leaves smaller gap when moving entity objects
////			collision.distance.y/=2;
//			
//			bounding_box.correctPosition(entity.bounding_box, collision);// correct the position
//			transform.pos.set(bounding_box.getCenter().x, bounding_box.getCenter().y, 0);// setting the transform
//		
//			entity.bounding_box.correctPosition(bounding_box, collision);//corect entities bounding box with our bounding box
//			entity.transform.pos.set(entity.bounding_box.getCenter().x, entity.bounding_box.getCenter().y, 0);//set its transformation position
//		}
//	}
}
