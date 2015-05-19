import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import processing.core.*;

public class OreBlob extends Mover
{
	public OreBlob(String name, Point position, List<PImage> imgs, int animation_rate, int rate)
	{
		super(name, position, imgs, animation_rate, rate);
	}
	
	public Action createOreBlobAction(WorldModel world, Map<String, List<PImage>> i_store)
	{
		Action[] a = { null };
		a[0] = (current_ticks) ->
		{
			this.removePendingAction(a[0]);
			
			Point entity_pt = this.getPosition();
			Vein vein = (Vein)world.findNearest(entity_pt, Vein.class);
			PointBooleanPair tiles_and_found = this.blobToVein(world, vein);
				
			long next_time = current_ticks + this.getRate();
				
			if (tiles_and_found.getBoolean())
			{
				Quake quake = world.createQuake(tiles_and_found.getPoint().get(0), current_ticks, i_store);
				world.addEntity(quake);
				next_time = current_ticks + this.getRate()*2;
			}
			
			this.scheduleAction(world, this.createOreBlobAction(world, i_store), next_time);
				
			return tiles_and_found.getPoint();
		};
		return a[0];
	}
	
	public PointBooleanPair blobToVein(WorldModel world, Vein vein)
	{

		List<Point> return_pts = new ArrayList<Point>();
		Point entity_pt = this.getPosition();
		
		if (vein == null)
		{
			return_pts.add(entity_pt);
			return new PointBooleanPair(return_pts, false);
		}
		Point vein_pt = vein.getPosition();
		if (MathOperations.adjacent(entity_pt, vein_pt))
		{
			vein.removeEntity(world);
			return_pts.add(vein_pt);
			return new PointBooleanPair(return_pts, true);
		}
		else
		{
			Point new_pt = world.blobNextPosition(entity_pt, vein_pt);
			Entity old_entity = world.getTileOccupant(new_pt);
			if (Ore.class.isInstance(old_entity))
			{
				((Ore)old_entity).removeEntity(world);
			}
			return new PointBooleanPair(world.moveEntity(this, new_pt), false);
		}
	}
	
	public void scheduleBlob(WorldModel world, long ticks, Map<String, List<PImage>> i_store)
	{
		this.scheduleAction(world, this.createOreBlobAction(world, i_store), ticks + this.getRate());
		this.scheduleAnimation(world);
	}
}
