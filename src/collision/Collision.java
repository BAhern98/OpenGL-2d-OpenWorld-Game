package collision;

import org.joml.Vector2f;
//class for collision resolution
public class Collision {

	public Vector2f distance;
	public boolean isIntersecting;
	
	public Collision(Vector2f distance, boolean isIntersecting) {
		// TODO Auto-generated constructor stub#
		this.distance = distance;
		this.isIntersecting =isIntersecting;
		
	}

}
