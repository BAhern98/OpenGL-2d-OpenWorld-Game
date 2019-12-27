package project;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import collision.AABB;
import entity.Entity;
import entity.Player;
import entity.Transform;
import world.Tile;
import world.TileRenderer;

public class Main {

	public Main() {
		Window.setCallbacks();
		
//		AABB box1 =new AABB(new Vector2f(0,0), new Vector2f(1,1));
//		AABB box2 =new AABB(new Vector2f(1,0), new Vector2f(1,1));
//		
//		if(box1.isIntersecting(box2))
//			System.out.println("these boxes are intersecting");
//		
		if (glfwInit() != true) {
			System.err.println("glfw failed to initialize !");// if glfw is working
			System.exit(1);
		}

		Window window = new Window();
		window.setSize(640, 480);
		window.setFullscreen(false);
		window.createWindow("game");

		GL.createCapabilities();
		
		glEnable(GL_BLEND);//ENABLE BLEND
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//TELL OPENGL WHAT WE WANT  BLENDED, BLENDS ALPHA WITH COLOURS

		Camera camera = new Camera(window.getWidth(), window.getHeight());// take in height and width of window
		glEnable(GL_TEXTURE_2D);

		TileRenderer tiles = new TileRenderer();
Entity.IntiAsset();//initialise entity asset


//	float[] vertices = new float[] {
//		-0.5f, 0.5f, 0,   //top left 0
//		0.5f, 0.5f, 0,   //top right 1
//		0.5f, -0.5f, 0, //bottom right 2
//		-0.5f, -0.5f, 0,//bottom left  3
//		
//	};
//	
//	float[] texture = new float[] {
//		 0,0,
//		 1,0,
//		 1,1, 
//		 0,1,
//	
//	};
//	
//	int[] indices = new int[] {
//			0,1,2,
//			2,3,0,
//	};
//	
//	Model model = new Model(vertices, texture,indices);
//	

		Shader shader = new Shader("shader");

		// Texture tex = new Texture("./res/test.png");

		World world = new World("test_level");
		
		
	
//		
//		Transform t = new Transform();
//		t.scale.x = 1;
//		t.scale.y = 1;
//		Player player = new Player(t);
//		world.setTile(Tile.test2, 7, 1);
//		world.setTile(Tile.test2, 7, 2);
//		
//		world.setTile(Tile.test2, 7, 6);
		

		double frame_time = 0;
		int frames = 0;

		double frame_cap = 1.0 / 60.0;
		double time = Timer.getTime();
		double unprocessed = 0;

		while (!window.shouldClose()) {
			boolean can_render = false;

			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			time = time_2;

			while (unprocessed >= frame_cap) {
				unprocessed -= frame_cap;
				can_render = true;

				if (window.getInput().isKeyReleased(GLFW_KEY_ESCAPE)) {
					glfwSetWindowShouldClose(window.getWindow(), true);

				}
				
				world.update((float) frame_cap, window, camera);
				world.correctCamera(camera, window);

				window.update();
				if (frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("Fps:" + frames);
					frames = 0;

				}
			}       
			

			if (can_render) {

				glClear(GL_COLOR_BUFFER_BIT);

//				
//				shader.bind();
//				shader.setUniform("sampler", 0);
//				shader.setUniform("projection", camera.getprojection().mul(target));//camera projection with the position multiplied with what were using now
				// model.render();
				// tex.bind(0);

				world.render(tiles, shader, camera, window);
				//player.render(shader, camera, world);
				window.swapBuffers();
				frames++;
			}

		}
		Entity.DelAsset();// destroys entity asset
		glfwTerminate();

	}

	public static void main(String[] args) {
		new Main();
	}
}
