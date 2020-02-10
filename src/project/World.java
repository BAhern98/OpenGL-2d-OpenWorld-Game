package project;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import collision.AABB;
import entities.Entity;
import entities.Player;
import entities.Rock;
import entities.Transform;
import entities.Tree;
import player.Animations;
import world.Tile;
import world.TileRenderer;

public class World {
	private List<Entity> entities;   //contains all entities 
	private byte[] tiles;
	private int width;
	private int height;

	private int scale;
	private Matrix4f world;
	private final int view =24;
	private AABB[] bounding_boxes;
//rock = 1 rgb
	
	public World() {

		//scale = 20;

		//tiles = new byte[width * height];
		//bounding_boxes = new AABB[width * height];

		//world = new Matrix4f().setTranslation(new Vector3f(0));
		//world.scale(scale);
	}


	public World(String rock) {
		try {
			BufferedImage tile_sheet = ImageIO.read(new File("./levels/" + rock + "/tiles.png"));//takes in level map
			//BufferedImage tile_sheet2 = ImageIO.read(new File("./levels/" + brick + "/tiles.png"));
		//	BufferedImage entity_sheet = ImageIO.read(new File("./levels/" + rock + "/entities.png"));


			width = tile_sheet.getWidth();
			height = tile_sheet.getHeight();
//			width2 = entity_sheet.getWidth();
//			height2 = entity_sheet.getHeight();
			scale = 40;
			
			this.world = new Matrix4f().setTranslation(new Vector3f(0));//puts the top left corner of the world into the center of the screen
			this.world.scale(scale);//scales world
//
			int[] colorTileSheet = tile_sheet.getRGB(0, 0, width, height, null, 0, width);// returns all pixels within
//																							// image,Returns an array of
//																							// integer pixels in the
//																							// default RGB color model
//																							// (TYPE_INT_ARGB) and
//																							// default sRGB color space,
//																							// from a portion of the
//																							// image data.
//			int[] colorEntitySheet = entity_sheet.getRGB(0, 0, width2, height2, null, 0, width2);

			tiles = new byte[width * height];// inalise tiles
			bounding_boxes = new AABB[width * height];// initalise boundig boxes
			entities = new ArrayList<Entity>();// initialise list to new array list

			//Transform transform;// transform for entity


			for(int y = 0; y<height; y++) {
				for (int x = 0; x < width; x++) {
					int red = (colorTileSheet[x + y * width]>> 16) & 0xFF;//gets pixel
					Tile t;
					try {
						t= Tile.tiles[red]; 
						
					} catch (ArrayIndexOutOfBoundsException e) {
						t=null;
						// TODO: handle exception
					}
					
					if(t !=null)
						setTile(t,x,y);
						
				}
			}
			Transform r1 = new Transform();
			r1.pos.x = 4 ;
			r1.pos.y = -4;
			
			Transform r2 = new Transform();
			r2.pos.x = 5 ;
			r2.pos.y = -7;
			
			Transform r3 = new Transform();
			r3.pos.x = 11 ;
			r3.pos.y = -12;
			
			
			Transform r4 = new Transform();
			r4.pos.x = 10;
			r4.pos.y = -8;
			
			Transform t1 = new Transform();
			t1.pos.x = 3 ;
			t1.pos.y = -7;
			
			Transform t2 = new Transform();
			t2.pos.x = 5 ;
			t2.pos.y = -5;
			
			Transform t3 = new Transform();
			t3.pos.x = 13 ;
			t3.pos.y = -13;
			
			
			Transform t4 = new Transform();
			t4.pos.x = 7;
			t4.pos.y = -5;
	
			
			Transform t5 = new Transform();
			t4.pos.x = 8;
			t4.pos.y = -9;
	
			
			Transform t6 = new Transform();
			t4.pos.x = 14;
			t4.pos.y = -14;

			
			//TODO
			entities.add(new Player(new Transform()));//add entity player

			entities.add(new Rock(r1));//add entity rock
			entities.add(new Rock(r3));//add entity rock

			entities.add(new Rock(r2));//add entity rock

			entities.add(new Rock(r4));//add entity rock
			
			entities.add(new Tree(t1));//add entity rock
			entities.add(new Tree(t3));//add entity rock

			entities.add(new Tree(t2));//add entity rock

			entities.add(new Tree(t4));//add entity rock

			entities.add(new Tree(t5));//add entity rock

			entities.add(new Tree(t6));//add entity rock

			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	
	public Matrix4f getWorldMatrix() {//returns world
		return world;
	}
	public static double getTime() {
		return (double)System.nanoTime()/(double)1000000000;
	}

	public void render(TileRenderer render, Shader shader, Camera camera, Window window) {//controls amount of tiles 
																						//on screen,
																						//renders only tiles on the screen

		int posX = ((int) camera.getPosition().x + (window.getWidth() / 2)) / (scale * 2);//gets the x cordiotions for center of screen
		int posY = ((int) camera.getPosition().y - (window.getHeight() / 2)) / (scale * 2);//gets the y cordiotions for center of screen

		for (int i = 0; i < view; i++) {
			for (int j = 0; j < view; j++) {
				Tile t = getTile(i - posX, j + posY);
				if (t != null)
					render.renderTile(t, i - posX, -j - posY, shader, world, camera);

			}
		}

		for (Entity entity : entities) {// itaarate through all the entities in list of entities
			entity.render(shader, camera, this);// render each entity
		}

	}
//
	//
	// entity on entity collision
	//
	public void update(float delta, Window window, Camera camera) {//updates all entities
		for (Entity entity : entities) {// itaarate through all the entities in list of entities
			entity.update(delta, window, camera, this);// update each entity
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).collideWithTiles(this);// get entity at i and collide with tiles this

			for (int j = i + 1; j < entities.size(); j++) {// where we colide with other entities
				entities.get(i).collideWithEntity(entities.get(j));

			}
			entities.get(i).collideWithTiles(this);// where we coliede with tiles again, if you colide with an enttiy
													// and it pushes you into a tile it checks it
		}
	}

	public void correctCamera(Camera camera, Window window) {//stops camera from leaving world
		Vector3f position = camera.getPosition();
		int w = -width * scale * 2;// get exict width or world
		int h = height * scale * 2;// get exict width or world

		if (position.x > -(window.getWidth() / 2) + scale)// stops camera leaving left map
			position.x = -(window.getWidth() / 2) + scale;

		if (position.x < w + (window.getWidth() / 2) + scale)// stops camera leaving right map
			position.x = w + (window.getWidth() / 2) + scale;

		if (position.y < (window.getHeight() / 2) - scale)// stops camera leaving top map
			position.y = (window.getHeight() / 2) - scale;

		if (position.y > h - (window.getHeight() / 2) - scale)// stops camera leaving bottom map
			position.y = h - (window.getHeight() / 2) - scale;
	}

	public void setTile(Tile tile, int x, int y) {//sets indivisual tiles
		tiles[x + y * width] = tile.getId();
		if (tile.isSolid()) {
			bounding_boxes[x + y * width] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1, 1));//enables collision on tiles set to solid
		} else {
			bounding_boxes[x + y * width] = null;
		}
	}

	public Tile getTile(int x, int y) {//gets number of tiles on screen
		try {
			return Tile.tiles[tiles[x + y * width]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;// TODO: handle exception
		}
	}

	public AABB getTileBoundingBox(int x, int y) {//gets solid tiles as bounding boxes
		try {
			return bounding_boxes[x + y * width];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;// TODO: handle exception
		}
	}

	public int getScale() {
		return scale;
	}

}
