public abstract class Entity extends WorldObject
{
	private Point position;
	
	public Entity (String name, Point position)
	{
		super(name);
		this.position = position;
	}
	
	public String getName()
	{
		return this.name;
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