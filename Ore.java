public class Ore extends Actionable
{
	int rate = 5000;
	
	public Ore(String name, Point position, int rate)
	{
		super(name, position);
		this.rate = rate;
	}
	
	public Ore(String name, Point position)
	{
		super(name, position);
	}
	
	public String entityString()
	{
		return ("ore" + " " + 
			    this.getName() + " " +
			    this.getPosition().getX() + " " + 
			    this.getPosition().getY() + " " +
			    this.getRate());
	}
	
	public int getRate()
	{
		return this.rate;
	}
}
