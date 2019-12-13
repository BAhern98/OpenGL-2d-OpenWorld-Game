package project;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private long window;
	private int width, height;

	private boolean fullscreen;

	private Input input;

	public static void setCallbacks() {
		glfwSetErrorCallback(new GLFWErrorCallback() {

			@Override
			public void invoke(int error, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}

		});

	}

	public Window() {
		setSize(1000, 600);
		setFullscreen(false);
		// TODO Auto-generated constructor stub
	}

	public void createWindow(String title) {
		window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
		if (window == 0)
			throw new IllegalStateException("Failed to create window");
		if (!fullscreen) {
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
		}

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

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;

	}

	public void update() {
		input.update();
		glfwPollEvents();

	}

	////////////////////////////////////////////////////////////////////////
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public long getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
