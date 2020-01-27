package world;

import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import project.Camera;
import project.Model;
import project.Shader;
import project.Texture;

public class TileRenderer {

	private HashMap<String, Texture> tile_textures;//holds all tile textures 
	private Model model;//default tile(square)
	
	public TileRenderer() {
		tile_textures = new HashMap<String, Texture>();//holds all tile textures 
		float[] vertices = new float[] {
				-1f, 1f, 0,   //top left 0
				1f, 1f, 0,   //top right 1
				1f, -1f, 0, //bottom right 2
				-1f, -1f, 0,//bottom left  3
				
			};
			
			float[] texture = new float[] {
				 0,0,
				 1,0,
				 1,1,
				 0,1,
			
			};
			
			int[] indices = new int[] {//pointer to the vertices
					0,1,2,
					2,3,0,
			};
			
			 model = new Model(vertices, texture,indices);
			 
			 for(int i = 0; i< Tile.tiles.length; i++) {//itterates through hash map
				 if(Tile.tiles[i] != null) {
				 if(!tile_textures.containsKey(Tile.tiles[i].getTexture())) {
					String tex = Tile.tiles[i].getTexture();
					 tile_textures.put( tex,new Texture (tex+".png"));
				 }
				 }
			 }
			
	}
	public void renderTile(Tile tile, int x, int y, Shader shader, Matrix4f world, Camera cam) {
		shader.bind();
		if(tile_textures.containsKey(tile.getTexture())) {//tests if texture is there
			tile_textures.get(tile.getTexture()).bind(0);//if it is, binds texture
		}
		Matrix4f tile_pos = new Matrix4f().translate(new Vector3f(x*2, y*2, 0));//tiles position
		Matrix4f target = new Matrix4f();
		
		cam.getprojection().mul(world, target);//get camera projection and multiplying with world and input it into target
		target.mul(tile_pos);//multiply target with tile position
		
		shader.setUniform("sampler", 0);//uses frament shader "sampler", uses 0 attribie because 0 is first sampler
		shader.setUniform("projection", target);
		
		model.render();
	}

}
