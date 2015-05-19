import java.io.File;
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

	long next_time;
	public boolean setup_complete = false;

	private Point mouse_pt;
	private Rectangle viewport;
	private WorldModel world;
	private WorldView view;
	
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
		return new Point(pt.getX() - view.viewport.getLeft(), pt.getY() - view.viewport.getTop());
	}
	
	public Rectangle createShiftedViewport(int deltax, int deltay, int num_rows, int num_cols)
	{
		System.out.println(view);
		int new_x = view.clamp(view.viewport.getLeft() + deltax, 0, num_cols - view.viewport.getWidth());
		int new_y = view.clamp(view.viewport.getTop() + deltay, 0, num_rows - view.viewport.getHeight());
		return new Rectangle(new_x, new_y, view.viewport.getWidth(), view.viewport.getHeight());
	}
	
	
	public void setup()
	{
		int num_cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE;
		int num_rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_HEIGHT_SCALE;
		
		Map<String, List<PImage>> i_store = ImageStore.loadImages(this, IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT);
		Background default_background = SaveLoad.createDefaultBackground(ImageStore.getImages(i_store, ImageStore.DEFAULT_IMAGE_NAME));

		world = new WorldModel(num_rows, num_cols, default_background);
		view = new WorldView(SCREEN_WIDTH/TILE_WIDTH, SCREEN_HEIGHT/TILE_HEIGHT, world, TILE_WIDTH, TILE_HEIGHT);
		SaveLoad.loadWorld(world, i_store, new File(WORLD_FILE));
		
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		background(0);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		next_time = System.currentTimeMillis() + 100;
		setup_complete = true;
		//Controller.activityLoop(view, world);
	}
	
	public void updateView(int deltax, int deltay)
	{
		
	}
	public void updateView()
	{
		int deltax = 0;
		int deltay = 0;
		this.viewport = this.createShiftedViewport(deltax, deltay, this.num_rows, this.num_cols);
	}
	
	public void draw()
	{
		System.out.println(world.getActionQueue().getSize());
		if (setup_complete)
		{
			long time = System.currentTimeMillis();
			System.out.println(time);
			if (time >= next_time)
			{
				world.updateOnTime(time);
				//draw background
				for (int y = 0; y < view.viewport.getHeight(); y++)
				{
					for (int x = 0; x < view.viewport.getWidth(); x++)
					{
						Point w_pt = view.viewportToWorld(new Point(x, y));
						PImage img = view.world.getBackgroundImage(w_pt);
						image(img, x*view.tile_width, y*view.tile_height);
					}
				}
				//draw entities
				for (Entity e : view.world.getEntities())
				{
					if (view.viewport.pointInRectangle(e.getPosition()))
					{
						Point v_pt = this.worldToViewport(e.getPosition());
						image(e.getImage(), v_pt.getX() * view.tile_width, v_pt.getY() * view.tile_height);
					}
				}
				next_time = time + 100;
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
