package project;

import static org.lwjgl.glfw.GLFW.*;

import javax.swing.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window extends JPanel {
	private long window;
	private int width, height;
	public boolean fullscreen;
	

	private Input input;


	public Window() {
		setSize(640, 480);
		//setFullscreen(false);
		// TODO Auto-generated constructor stub
//		JPanel panel = new JPanel();
//		panel.setBackground(java.awt.Color.DARK_GRAY);
//		CustomButton button = new CustomButton("Play Game");
//		panel.add(button);
//		add(panel);
		
	}

	public void createWindow(String title) {
		
		window = glfwCreateWindow(width, height, title,fullscreen ? glfwGetPrimaryMonitor():0 ,  0);
		
		if (window == 0)
			throw new IllegalStateException("Window was not created");
		
		if (!fullscreen) {
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
		}

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



	public long getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}
}
