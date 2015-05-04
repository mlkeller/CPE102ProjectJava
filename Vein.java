public class Vein extends Actionable
{
	private int rate;
	private int resource_distance;
	
	public Vein(String name, Point position, int rate)
	{
		super(name, position);
		this.rate = rate;
		this.resource_distance = 1;
	}
	
	public int getRate()
	{
		return this.rate;
	}

	public int getResourceDistance()
	{
		return this.resource_distance;
	}
}
