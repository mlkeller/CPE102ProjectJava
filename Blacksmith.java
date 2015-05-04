public class Blacksmith extends Entity
{
	public Blacksmith(String name, Point position)
	{
		super(name, position);
	}

	public String entityString()
	{
	return ("blacksmith" + " " +
			this.getName() + " " +
			Integer.toString(this.getPosition().getX()) + " " +
			Integer.toString(this.getPosition().getY()));
	}
}
	