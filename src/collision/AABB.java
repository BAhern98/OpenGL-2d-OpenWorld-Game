package collision;

import org.joml.Vector2f;

//axis aligned boundng box
public class AABB {

	private Vector2f center, half_extent;

	public AABB(Vector2f center, Vector2f half_extent) {
		this.center = center;
		this.half_extent = half_extent;
	}

	public Collision getCollision(AABB box2) {// checks if its colliding
		Vector2f distance = box2.center.sub(center, new Vector2f());// gets distance
		distance.x = (float) Math.abs(distance.x); //absolute value of x
		distance.y = (float) Math.abs(distance.y);// absolute value of y

		distance.sub(half_extent.add(box2.half_extent, new Vector2f()));// gets distance between 2 half extents

		return new Collision(distance, distance.x < 0 && distance.y < 0);// returns new collision if colliding
	}

	// collision responce
	public void correctPosition(AABB box2, Collision data) {// stops boxes from inter secting
		Vector2f correction = box2.center.sub(center, new Vector2f());
		if (data.distance.x > data.distance.y) {
			if (correction.x > 0) {
				center.add(data.distance.x, 0);
			} else {
				center.add(-data.distance.x, 0);
			}

		} else {
			if (correction.y > 0) {
				center.add(0, data.distance.y);
			} else {
				center.add(0, -data.distance.y);
			}

		}
	}

	public Vector2f getCenter() {
		return center;
	}

	public Vector2f getHalfExtent() {
		return half_extent;
	}
	

}
