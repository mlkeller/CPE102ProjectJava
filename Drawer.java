import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

import processing.core.*;

public class Drawer extends PApplet
	{
	public static final int WORLD_WIDTH_SCALE = 2;
	public static final int WORLD_HEIGHT_SCALE = 2;
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 480;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	private WorldModel world;
	private WorldView view;
	
	public static final String IMAGE_LIST_FILE_NAME = "imagelist";
	public static final String WORLD_FILE = "gaia.sav";
	public static final String DEFAULT_IMAGE_NAME = "background_default";
	private static final int COLOR_MASK = 0xffffff;

	long next_time;
	

	public static void main(String[] args)
	{
		Random random_generator = new Random();
		PApplet.main("Drawer");
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

		next_time = System.currentTimeMillis() + 100;
		
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		background(0);

		//Controller.activityLoop(view, world);
	}
	
	public void draw()
	{
		System.out.println(world.getActionQueue().getSize());
		long time = System.currentTimeMillis();
		//System.out.println(time);
		
		System.out.println(time);
		System.out.println(next_time);
		System.out.println("-");
		if (time >= next_time)
		{
			world.updateOnTime(time);
			//draw background
			for (int y = 0; y < view.getViewport().getHeight(); y++)
			{
				for (int x = 0; x < view.getViewport().getWidth(); x++)
				{
					//Point w_pt = view.viewportToWorld(new Point(x, y));
					//System.out.println(w_pt.getX() + " " + w_pt.getY());
					Point w_pt = new Point(x + view.getViewport().getLeft(),
										   y + view.getViewport().getTop());
					PImage img = world.getBackgroundImage(w_pt);
					image(img, x*view.getTileWidth(), y*view.getTileHeight());
				}
			}
			
			//draw entities
			for (Entity e : world.getEntities())
			{
				if (view.getViewport().pointInRectangle(e.getPosition()))
				{
					Point v_pt = view.worldToViewport(e.getPosition());
					image(e.getImage(), v_pt.getX() * view.getTileWidth(), v_pt.getY() * view.getTileHeight());
				}
			}
			next_time = time + 100;
		}
	}
	
	public void keyPressed()
	{
		switch (key)
		{
			case 'w':
				if (view.getViewport().getTop() > 0)
				{
					view.updateView(0, -1);
				}
				break;
			case 'a':
				if (view.getViewport().getLeft() > 0)
				{
					view.updateView(-1, 0);
				}
				break;
			case 's':
				if (view.getViewport().getTop() + view.getViewport().getHeight() < SCREEN_HEIGHT/TILE_HEIGHT*WORLD_HEIGHT_SCALE)
				{
					view.updateView(0, 1);
				}
				break;
			case 'd':
				if (view.getViewport().getLeft() + view.getViewport().getWidth() < SCREEN_WIDTH/TILE_WIDTH*WORLD_WIDTH_SCALE)
				{
					view.updateView(1, 0);
				}
				break;

		}
	}
	
	public static PImage setAlpha(PImage img, int maskColor, int alpha)
	   {
	      int alphaValue = alpha << 24;
	      int nonAlpha = maskColor & COLOR_MASK;
	      img.format = PApplet.ARGB;
	      img.loadPixels();
	      for (int i = 0; i < img.pixels.length; i++)
	      {
	         if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
	         {
	            img.pixels[i] = alphaValue | nonAlpha;
	         }
	      }
	      img.updatePixels();
	      return img;
	   }
}
