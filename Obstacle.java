public class Obstacle extends Entity
{
	public Obstacle(String name, Point position)
	{
		super(name, position);
	}
	
	public String entityString()
	{
		return ("obstacle" + " " +
				this.getName() + " " +
				Integer.toString(this.getPosition().getX()) + " " +
				Integer.toString(this.getPosition().getY()));
	}
}