package project;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	private Vector3f position;//move camera around world eaiser
	private Matrix4f projection;

	public Camera(int width, int height) {
		
		position = new Vector3f();//gets x y z coords
		projection = new  Matrix4f().setOrtho2D(-width/2, width/2, -height/2, height/2);//center of window, setortho2d makes 1 matrix
		// TODO Auto-generated constructor stub
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	


	
	public Matrix4f getprojection() {//view matrix
		
		
		return projection.translate(position, new Matrix4f());//allocates new target and position
	}
}
