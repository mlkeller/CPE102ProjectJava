import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import processing.core.*;

public class WorldModel
{
	Random random_generator = new Random();
	int BLOB_ANIMATION_MIN = 1;
	int BLOB_ANIMATION_MAX = 3;
	int ORE_CORRUPT_MIN = 20000;
	int ORE_CORRUPT_MAX = 30000;
	int QUAKE_ANIMATION_RATE = 100;
	int VEIN_RATE_MIN = 8000;
	int VEIN_RATE_MAX = 17000;
	
	private int num_rows;
	private int num_cols;
	private Grid background;
	private Grid occupancy;
	private List<Entity> entities = new LinkedList<Entity>();
	private OrderedList action_queue = new OrderedList();
	
	public WorldModel(int num_rows, int num_cols, Background background)
	{
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.background = new Grid(num_cols, num_rows, background);
		this.occupancy = new Grid(num_cols, num_rows, null);
	}
	
	public OrderedList getActionQueue()
	{
		return this.action_queue;
	}
	
	public List<Entity> getEntities()
	{
		return this.entities;
	}
	
	public PImage getBackgroundImage(Point pt)
	{
		if (this.withinBounds(pt))
		{
			return this.getImage(pt);
		}
		else
		{
			return null;
		}
	}
	
	public PImage getImage(Point pt)
	{
		return this.background.getCell(pt).getImage();
	}
	
	public Background getBackground(Point pt)
	{
		if (this.withinBounds(pt))
		{
			return (Background)this.background.getCell(pt);
		}
		else
		{
			return null;
		}
	}
	
	public void setBackground(Point pt, Background bgnd)
	{
		if (this.withinBounds(pt))
		{
			this.background.setCell(pt, bgnd);
		}
	}
	
	public Background getTileBackground(Point pt)
	{
		if (withinBounds(pt))
		{
			return (Background) this.background.getCell(pt);
		}
		//remember to deal with null
		return null;
	}
	
	public void setTileBackground(Point pt, Background bgnd)
	{
		if (withinBounds(pt))
		{
			this.background.setCell(pt, bgnd);
		}
	}
	
	public Entity getTileOccupant(Point pt)
	{
		if (withinBounds(pt))
		{
			return (Entity) this.occupancy.getCell(pt);
		}
		//remember to deal with null
		return null;
	}
	
	public void addEntity(Entity entity)
	{
		Point pt = entity.getPosition();
		if (this.withinBounds(pt))
		{
			Entity old_entity = (Entity) this.occupancy.getCell(pt);
			if (old_entity != null)
			{
				((Actionable)old_entity).clearPendingActions();  //it may crash and burn here
			}
			this.occupancy.setCell(pt, entity);
			this.entities.add(entity);
		}
	}
	
	public List<Point> moveEntity(Entity entity, Point new_pt)
	{
		List<Point> tiles = new ArrayList<Point>();
		if (this.withinBounds(new_pt))
		{
			Point old_pt = entity.getPosition();
			this.occupancy.setCell(old_pt, null);
			tiles.add(old_pt);
			this.occupancy.setCell(new_pt, entity);
			tiles.add(new_pt);
			entity.setPosition(new_pt);
		}
		return tiles;
	}
	
	public void removeEntity(Entity entity)
	{
		removeEntityAt(entity.getPosition());
	}
	
	public void removeEntityAt(Point pt)
	{
		//isOccupied - change to later
		if ((this.withinBounds(pt)) && (this.occupancy.getCell(pt) != null))
		{
			Entity entity = (Entity) this.occupancy.getCell(pt);
			Point new_position = new Point(-1, -1);
			entity.setPosition(new_position);
			this.entities.remove(entity);
			this.occupancy.setCell(pt, null);
		}
	}
	
	public boolean withinBounds(Point pt)
	{
		return (pt.getX() >= 0 && pt.getX() < this.num_cols) &&
			   (pt.getY() >= 0 && pt.getY() < this.num_rows);
	}
	
	public boolean isOccupied(Point pt)
	{
		return (this.withinBounds(pt) && (this.occupancy.getCell(pt) != null));
	}
	
	public void scheduleAction(Action action, long time)
	{
		this.action_queue.insert(action, time);  // + System.currentTimeMillis() + 
	}
	
	public void unscheduleAction(Action action)
	{
		this.action_queue.remove(action);
	}
	
	public void updateOnTime(long ticks)
	{
		OrderedListItem next = this.action_queue.head();
		while ((next != null) && (next.getTime() < ticks))
		{
			this.action_queue.pop();
			System.out.println(next.getTime() + " " + ticks + " " + next.getAction());
			next.getAction().execute(ticks);
			next = this.action_queue.head();
		}
	}
	
	public void clearPendingActions(Actionable entity)
	{
		for (Action a : entity.getPendingActions())
		{
			this.unscheduleAction(a);
		}
		entity.clearPendingActions();
	}
	
	//update on time goes in draw
	
	public Point nextPosition(Point entity_pt, Point dest_pt)
	{
		int horiz = MathOperations.sign(dest_pt.getX() - entity_pt.getX());
		Point new_pt = new Point(entity_pt.getX() + horiz, entity_pt.getY());
		
		if (horiz == 0 || isOccupied(new_pt))
		{
			int vert = MathOperations.sign(dest_pt.getY() - entity_pt.getY());
			new_pt.setX(entity_pt.getX());
			new_pt.setY(entity_pt.getY() + vert);
			
			if (vert == 0 || isOccupied(new_pt))
			{
				new_pt.setX(entity_pt.getX());
				new_pt.setY(entity_pt.getY());
			}
		}
		
		return new_pt;
	}
	
	public Point blobNextPosition(Point entity_pt, Point dest_pt)
	{
		int horiz = MathOperations.sign(dest_pt.getX() - entity_pt.getX());
		Point new_pt = new Point(entity_pt.getX() + horiz, entity_pt.getY());
		
		if ((horiz == 0 || isOccupied(new_pt))) // and is not an instance of something, fix this later
		{
			int vert = MathOperations.sign(dest_pt.getY() - entity_pt.getY());
			new_pt.setX(entity_pt.getX());
			new_pt.setY(entity_pt.getY() + vert);
			
			if ((vert == 0 || isOccupied(new_pt))) //and is not an instance of something, fix this later
			{
				new_pt.setX(entity_pt.getX());
				new_pt.setY(entity_pt.getY());
			}
		}
		
		return new_pt;
	}
	
	public Point findOpenAround(Point pt, int distance)
	{
		Point new_pt = new Point(pt.getX(), pt.getY());
		for (int dy = -distance; dy <= distance; dy++)
		{
			for (int dx = -distance; dx <= distance; dx++)
			{
				new_pt.setX(pt.getX() + dx);
				new_pt.setY(pt.getY() + dy);
				
				if (withinBounds(new_pt) && !(isOccupied(new_pt)))
				{
					return new_pt;
				}
			}
		}
		
		return null;
	}
	
	public Entity nearestEntity(List<EntityDistancePair> entity_dists)
	{
		Entity nearest;
		if (entity_dists.size() > 0)
		{
			EntityDistancePair shortest_pair = entity_dists.get(0);

			for (EntityDistancePair other_pair : entity_dists)
			{
				if (other_pair.getDistance() < shortest_pair.getDistance())
				{
					shortest_pair = other_pair;
				}
			}
			
			nearest = shortest_pair.getEntity();
		}
		else
		{
			nearest = null;
		}
		
		return nearest;
	}
	
	public Entity findNearest(Point pt, Class type)
	{
		List<EntityDistancePair> oftype = new LinkedList<EntityDistancePair>();
		for (Entity e : this.entities)
		{
			if (type.isInstance(e))
			{
				EntityDistancePair new_pair = new EntityDistancePair(e, MathOperations.distanceSquared(pt, e.getPosition()));
				oftype.add(new_pair);
			}
		}
		
		return nearestEntity(oftype);
	}
	
	public OreBlob createBlob(String name, Point pt, int rate, long ticks, Map<String, List<PImage>> i_store)
	{
		int animation_rate = random_generator.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN) + BLOB_ANIMATION_MIN;
		OreBlob blob = new OreBlob(name, pt, ImageStore.getImages(i_store, "blob"), animation_rate, rate);
		blob.scheduleBlob(this, ticks, i_store);
		return blob;
	}
	
	public Ore createOre(String name, Point pt, long ticks, Map<String, List<PImage>> i_store)
	{
		int rate = random_generator.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN) + ORE_CORRUPT_MIN;
		Ore ore = new Ore(name, pt, ImageStore.getImages(i_store, "ore"), rate);
		ore.scheduleOre(this, ticks, i_store);
		return ore;
	}
	
	public Quake createQuake(Point pt, long ticks, Map<String, List<PImage>> i_store)
	{
		Quake quake = new Quake("quake", pt, ImageStore.getImages(i_store, "quake"), QUAKE_ANIMATION_RATE);
		quake.scheduleQuake(this, ticks);
		return quake;
	}
	
	public Vein createVein(String name, Point pt, long ticks, Map<String, List<PImage>> i_store)
	{
		int rate = random_generator.nextInt(VEIN_RATE_MAX - VEIN_RATE_MIN) + VEIN_RATE_MIN;
		Vein vein = new Vein("vein" + name, pt, ImageStore.getImages(i_store, "vein"), rate);
		return vein;
	}
}
