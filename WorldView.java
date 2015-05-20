import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import processing.core.*;


public class WorldView
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
		this.num_rows = view_rows;
		this.num_cols = view_cols;
	}
	
	public WorldModel getWorld()
	{
		return this.world;
	}
	
	public Rectangle getViewport()
	{
		return this.viewport;
	}
	
	public int getTileWidth()
	{
		return this.tile_width;
	}
	
	public int getTileHeight()
	{
		return this.tile_height;
	}
	
	public int clamp(int v, int low, int high)
	{
		return Math.min(high, Math.max(v, low));
	}
	
	public Point viewportToWorld(Point pt)
	{
		//System.out.println("ahax" + pt.getX() + " " + this.viewport.getLeft());
		//System.out.println("ahay" + pt.getY() + " " + this.viewport.getTop());
		Point rm_pt = new Point(pt.getX() + this.viewport.getLeft(), pt.getY() + this.viewport.getTop());
		//System.out.println("oho" + rm_pt.getX() + " " + rm_pt.getY());
		return rm_pt;
	}
	
	public Point worldToViewport(Point pt)
	{
		return new Point(pt.getX() - this.viewport.getLeft(), pt.getY() - this.viewport.getTop());
	}
	
	public Rectangle createShiftedViewport(int deltax, int deltay, int num_rows, int num_cols)
	{
		int new_x = this.clamp(this.viewport.getLeft() + deltax, 0, num_cols - this.viewport.getWidth());
		System.out.println("x " + new_x);
		System.out.println(this.viewport.getLeft());
		System.out.println(deltax);
		System.out.println(num_cols);
		System.out.println(this.viewport.getWidth());
		int new_y = this.clamp(this.viewport.getTop() + deltay, 0, num_rows - this.viewport.getHeight());
		System.out.println("y " + new_y);
		return new Rectangle(new_x, new_y, this.viewport.getWidth(), this.viewport.getHeight());
	}
	
	public void updateView(int deltax, int deltay)
	{
		this.viewport = new Rectangle(this.viewport.getLeft() + deltax,
									  this.viewport.getTop() + deltay,
									  this.viewport.getWidth(),
									  this.viewport.getHeight());
	}
}
