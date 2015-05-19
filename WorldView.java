import java.util.List;
import java.util.Map;
import java.util.Random;

import processing.core.*;


public class WorldView extends PApplet
{
	public static final String IMAGE_LIST_FILE_NAME = "imagelist";
	public static final String WORLD_FILE = "gaia.sav";
	public static final String DEFAULT_IMAGE_NAME = "background_default";

	public static final int WORLD_WIDTH_SCALE = 2;
	public static final int WORLD_HEIGHT_SCALE = 2;
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 480;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;

	public boolean setup_complete = false;

	private Point mouse_pt;
	private Rectangle viewport;
	private WorldModel world;
	private int tile_width;
	private int tile_height;
	private int num_rows;
	private int num_cols;
	
	
	public WorldView(int view_cols, int view_rows, WorldModel world, int tile_width, int tile_height)
	{
		this.viewport = new Rectangle(0, 0, view_cols, view_rows);
		this.world = world;
		this.tile_width = tile_width;
		this.tile_height = tile_height;
	}
	public WorldView()
	{
		
	}
	
	public int clamp(int v, int low, int high)
	{
		return Math.min(high, Math.max(v, low));
	}
	
	public Point viewportToWorld(Point pt)
	{
		return new Point(pt.getX() + this.viewport.getLeft(), pt.getY() + this.viewport.getTop());
	}
	
	public Point worldToViewport(Point pt)
	{
		return new Point(pt.getX() - this.viewport.getLeft(), pt.getY() - this.viewport.getTop());
	}
	
	public Rectangle createShiftedViewport(int deltax, int deltay, int num_rows, int num_cols)
	{
		int new_x = this.clamp(this.viewport.getLeft() + deltax, 0, num_cols - this.viewport.getWidth());
		int new_y = this.clamp(this.viewport.getTop() + deltay, 0, num_rows - this.viewport.getHeight());
		return new Rectangle(new_x, new_y, this.viewport.getWidth(), this.viewport.getHeight());
	}
	
	
	public void setup()
	{
		int num_cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE;
		int num_rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_HEIGHT_SCALE;
		
		Map<String, List<PImage>> i_store = ImageStore.loadImages(this, IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT);
		Background default_background = SaveLoad.createDefaultBackground(ImageStore.getImages(i_store, ImageStore.DEFAULT_IMAGE_NAME));

		WorldModel world = new WorldModel(num_rows, num_cols, default_background);
		WorldView view = new WorldView(SCREEN_WIDTH/TILE_WIDTH, SCREEN_HEIGHT/TILE_HEIGHT, world, TILE_WIDTH, TILE_HEIGHT);
		SaveLoad.loadWorld(world, i_store, WORLD_FILE);
		
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		background(0);
		
		setup_complete = true;
		//Controller.activityLoop(view, world);
	}
	
	public void draw()
	{
		if (setup_complete)
		{
			long time = System.currentTimeMillis();
			//if (time >= next_time)
		
			//draw background
			for (int y = 0; y < this.viewport.getHeight(); y++)
			{
				for (int x = 0; x < this.viewport.getWidth(); x++)
				{
					Point w_pt = this.viewportToWorld(new Point(x, y));
					PImage img = this.world.getBackgroundImage(w_pt);
					image(img, x*this.tile_width, y*this.tile_height);
				}
			}
			
			//draw entities
			for (Entity e : this.world.getEntities())
			{
				if (this.viewport.pointInRectangle(e.getPosition()))
				{
					Point v_pt = this.worldToViewport(e.getPosition());
					image(e.getImage(), v_pt.getX() * this.tile_width, v_pt.getY() * this.tile_height);
				}
			}
		}
	}
	
	public void keyPressed()
	{
		
	}
	
	public static void main(String[] args)
	{
		Random random_generator = new Random();
		PApplet.main("WorldView");
	}
}