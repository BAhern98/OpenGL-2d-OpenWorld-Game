package project;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private long window;
	private int width, height;

	

	private Input input;


	public Window() {
		setSize(640, 480);
		//setFullscreen(false);
		// TODO Auto-generated constructor stub
	}

	public void createWindow(String title) {
		
		window = glfwCreateWindow(width, height, title,glfwGetPrimaryMonitor() ,  0);
		
		
		if (window == 0)
			throw new IllegalStateException("Window was not created");

		glfwShowWindow(window);//displays wndow after being created


		glfwShowWindow(window);//displays wndow after being created

		glfwMakeContextCurrent(window);//allows lwjgl  to create gl capabilities

		input = new Input(window);//set input to new input, passing in long variable

	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window) != false;
	}

	public void swapBuffers() {
		glfwSwapBuffers(window);

	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}


	public void update() {
		input.update();//updates input so...
		glfwPollEvents();//...determins which key is down for the next frame

	}

	////////////////////////////////////////////////////////////////////////
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

//	public boolean isFullscreen() {
//		return fullscreen;
//	}

	public long getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
