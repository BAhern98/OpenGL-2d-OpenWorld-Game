package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
	public Vector3f pos;
	public Vector3f scale;
	
	
	public Transform() {//gives size/scale
	 pos = new Vector3f();
	 scale = new Vector3f(1,1,1);
	}
	
	public Matrix4f getProjection(Matrix4f target) {//sets size of entity to 1 by 1 tile, model matrix
		target.translate(pos);//translate the model first
		
		target.scale(scale);// then scale to give correct position for player
		
		return target;
		
	}

}
