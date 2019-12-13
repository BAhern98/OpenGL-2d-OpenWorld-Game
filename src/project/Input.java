package project;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFW;

public class Input {

	private long window;
	private boolean keys[];

	public Input(long window) {
		// TODO Auto-generated constructor stub
		this.window = window;//get window long 
		this.keys = new boolean[GLFW_KEY_LAST];
		for (int i = 0; i < GLFW_KEY_LAST; i++)
			keys[i] = false;
	}

	public boolean isKeyDown(int key) {//
		return glfwGetKey(window, key) == 1;//return boolean, if 1 = true
	}

	public boolean isKeyPressed(int key) {
		return (isKeyDown(key) && !keys[key]);

	}

	public boolean isKeyReleased(int key) {

		return (!isKeyDown(key) && keys[key]);

	}

	public boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(window, button) == 1;
	}

	public void update() {
		for (int i = 32; i < GLFW.GLFW_KEY_LAST; i++) 
			  keys[i] = isKeyDown(i);
	}

}
