package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
	public Vector3f position;
	public Vector3f scale;
	
	
	public Transform() {//gives size/scale
	 position = new Vector3f();
	 scale = new Vector3f(1,1,1);
	}
	
	public Matrix4f getProjection(Matrix4f target) {//sets size of entity to 1 by 1 tile, model matrix
		target.translate(position);//translate the model first
		
		target.scale(scale);// then scale to give correct position for player
		
		return target;
		
	}

}
