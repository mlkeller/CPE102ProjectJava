import java.util.List;
import processing.core.*;

public abstract class Entity extends WorldObject
{
	private Point position;
	
	public Entity (String name, Point position, List<PImage> imgs)
	{
		super(name, imgs);
		this.position = position;
	}
	
	public Point getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Point new_position)
	{
		this.position = new_position;
	}
}