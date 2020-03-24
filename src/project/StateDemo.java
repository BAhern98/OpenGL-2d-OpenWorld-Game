package project;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import entities.Entity;

public class StateDemo {
	public StateDemo(){
		
		Window window = new Window();
	
	double frame_time = 0;
	int frames = 0;

	double frame_cap = 1.0 / 60.0;
	double time = World.getTime();
	double unprocessed = 0;

	{

	while (!window.shouldClose()) {// stops rendering when window closes
		boolean can_render = false;

		double time_2 = World.getTime();
		double passed = time_2 - time;
		unprocessed += passed;
		frame_time += passed;
		time = time_2;
		
		
		
		
		if (window.getInput().isKeyDown(GLFW_KEY_ESCAPE)) {
	
			glfwSetWindowShouldClose(window.getWindow(), true);
		
			
		}
		
		
		
		while (unprocessed >= frame_cap) {// all update code
			unprocessed -= frame_cap;
			can_render = true;


			window.update();
			
			
			
			
			if (frame_time >= 1.0) {
				frame_time = 0;
				System.out.println("Fps:" + frames);
				frames = 0;

			}
		}

		if (can_render) {

			glClear(GL_COLOR_BUFFER_BIT);// clears context



			// player.render(shader, camera, world);
			window.swapBuffers();// swaps 2 contexts
			frames++;
		}

	}
	Entity.deleteEntity();// destroys entity asset
	glfwTerminate();// cleans up data
	}

}
}
