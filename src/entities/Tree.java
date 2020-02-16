package entities;

import player.Animations;
import project.Camera;
import project.Texture;
import project.Window;
import project.World;
public class Tree extends Entity{

	public static final int TREE_IDLE = 0;
	public static final int TREE_SIZE = 1;
	public Tree(Transform transform) {
		// TODO Auto-generated constructor stub
		super(TREE_SIZE,transform);
		setAnimation(TREE_IDLE, new Animations(2,1,"worldEntities/tree"));//player standing 
		useAnimation(TREE_IDLE);
	}

	@Override
	public void update(float delta, Window window, Camera camera, World world) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() {

	}

}
