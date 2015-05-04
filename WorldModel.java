import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WorldModel
{
	private int num_rows;
	private int num_cols;
	private Grid background;
	private Grid occupancy;
	private List<Entity> entities = new LinkedList<Entity>();
	//action_queue
	
	public WorldModel(int num_rows, int num_cols, Grid background, Grid occupancy, List<Entity> entities)
	{
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.background = background;
		this.occupancy = occupancy;
		this.entities = entities;
	}

	Random random_generator = new Random();
	int BLOB_ANIMATION_MIN = 1;
	int BLOB_ANIMATION_MAX = 3;
	int ORE_CORRUPT_MIN = 20000;
	int ORE_CORRUPT_MAX = 30000;
	int QUAKE_ANIMATION_RATE = 100;
	int VEIN_RATE_MIN = 8000;
	int VEIN_RATE_MAX = 17000;
	
	//get_image
	//find_nearest
	public List<Entity> getEntities()
	{
		return this.entities;
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
		if (withinBounds(pt))
		{
			Entity old_entity = (Entity) this.occupancy.getCell(pt);
		}
	}
	
	public List<Point> moveEntity(Entity entity, Point new_pt)
	{
		List<Point> tiles = new ArrayList<Point>();
		if (withinBounds(new_pt))
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
		if ((withinBounds(pt)) && (this.occupancy.getCell(pt) != null))
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
		return (this.withinBounds(pt) && (this.occupancy.getCell(pt) != null)); // and self.occupancy.GetCell(pt) != None
	}
	
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
	
	public Point bloblNextPosition(Point entity_pt, Point dest_pt)
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
		Point new_pt = new Point(pt.getX(), pt.getY());  //ask if I can get rid of the second part
		for (int dy = -distance; dy <= distance; distance++)
		{
			for (int dx = -distance; dx <= distance; distance++)
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
	
	public OreBlob createBlob(String name, Point pt, int rate)
	{
		int animation_rate = random_generator.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN) + BLOB_ANIMATION_MIN;
		OreBlob blob = new OreBlob(name, pt, animation_rate, rate);
		//schedule blob
		return blob;
	}
	
	public Ore createOre(String name, Point pt)
	{
		int rate = random_generator.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN) + ORE_CORRUPT_MIN;
		Ore ore = new Ore(name, pt, rate);
		//schedule ore
		return ore;
	}
	
	public Quake createQuake(Point pt)
	{
		Quake quake = new Quake("quake", pt, QUAKE_ANIMATION_RATE);
		//schedule quake
		return quake;
	}
	
	public Vein createVein(String name, Point pt)
	{
		int rate = random_generator.nextInt(VEIN_RATE_MAX - VEIN_RATE_MIN) + VEIN_RATE_MIN;
		Vein vein = new Vein("vein" + name, pt, rate);
		return vein;
	}
}