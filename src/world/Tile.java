package world;

public class Tile {
	public static Tile tiles[] = new Tile[255];

	public static byte NumOfTiles = 0;

	public static final Tile tile1 = new Tile("grass");

	public static final Tile tile2 = new Tile("rock").setSolid();
	private byte id;
	private boolean solid;
	private String texture;
	
	
	
	

	public Tile(String texture) {
		this.id = NumOfTiles;
		NumOfTiles++;
		this.texture = texture;
		this.solid = false;
		if (tiles[id] != null)
			throw new IllegalStateException("Tiles [" + id + "] is not available");
		tiles[id] = this;
	}

	public Tile setSolid() {
		this.solid = true;
		return this;

	}

	public boolean isSolid() {
		return solid;
	}

	public byte getId() {
		return id;
	}

	public String getTexture() {
		return texture;
	}

}
