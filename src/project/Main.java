package project;

import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;

import java.io.FileInputStream;

import javax.imageio.stream.FileImageInputStream;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import audio.MusicPlayer;
import collision.AABB;
import entities.Entity;
//import entities.Player;
import entities.Transform;
import javazoom.jl.player.Player;
import world.Tile;
import world.TileRenderer;

public class Main {

	
	public Main() {
		
		

		
		
		
		ThreadPool pool = new ThreadPool(1);
		MusicPlayer musicplayer = new MusicPlayer("Ignition");
		
		pool.runTask(musicplayer);

		if (glfwInit() != true) {
			System.err.println("glfw failed to initialize !");// if glfw is working
			System.exit(1);
		}

		Window window = new Window();
	
		window.createWindow("game");

		GL.createCapabilities();// creates a context,image that is on graphics card that opengl draws on
		//
		// allows for transperency
		//
		glEnable(GL_BLEND);// ENABLE BLEND
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);// TELL OPENGL WHAT WE WANT BLENDED, BLENDS ALPHA WITH COLOURS

		Camera camera = new Camera(window.getWidth(), window.getHeight());// take in height and width of window
		//glEnable(GL_TEXTURE_2D);

		TileRenderer tiles = new TileRenderer();
		Entity.initialiseEntity();// initialise entity asset


		
		
		Shader shader = new Shader("shader");

		// Texture tex = new Texture("./res/test.png");

		World world = new World("test_level");

//		
//		Transform t = new Transform();
//		t.scale.x = 1;
//		t.scale.y = 1;
//		Player player = new Player(t);


///
///			FPS counter
///
		
		
		
	//world.setTile(Tile.tile2, 4, 4);
	
	
		
		
		
		double frame_time = 0;
		int frames = 0;

		double frame_cap = 1.0 / 60.0;
		double time = World.getTime();
		double unprocessed = 0;

		

		while (!window.shouldClose()) {// stops rendering when window closes
			boolean can_render = false;

			double time_2 = World.getTime();
			double passed = time_2 - time;
			unprocessed += passed;
			frame_time += passed;
			time = time_2;
			
			
			
			
			if (window.getInput().isKeyDown(GLFW_KEY_ESCAPE)) {
		
				pool.close();
				glfwSetWindowShouldClose(window.getWindow(), true);
			
				
			}
			
			
			
			while (unprocessed >= frame_cap) {// all update code
				unprocessed -= frame_cap;
				can_render = true;


				world.update((float) frame_cap, window, camera);//updates everything in the world and entities
				world.correctCamera(camera, window);

				window.update();
				
				
				
				
				if (frame_time >= 1.0) {
					frame_time = 0;
					System.out.println("Fps:" + frames);
					frames = 0;

				}
			}

			if (can_render) {

				glClear(GL_COLOR_BUFFER_BIT);// clears context



				world.render(tiles, shader, camera, window);
				// player.render(shader, camera, world);
				window.swapBuffers();// swaps 2 contexts
				frames++;
			}

		}
		Entity.deleteEntity();// destroys entity asset
		glfwTerminate();// cleans up data

	}

	public static void main(String[] args) {
	
		
		new Main();
	}
}
