package entity;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import collision.AABB;
import collision.Collision;
import player.Animations;
import project.Camera;
import project.Model;
import project.Shader;
import project.Texture;
import project.Window;
import project.World;

public class Player extends Entity {
	private Model model;
	private AABB bounding_box;
	// private Texture texture ;
	private Transform transform;
	private Animations texture;

	public Player(Transform transform) {
		super(new Animations(5,15,"image"),transform);
}
}