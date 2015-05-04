public abstract class Miner extends Mover
{
	private int resource_limit;
	private int resource_count;

	public Miner(String name, Point position, int animation_rate, int rate, int resource_limit, int resource_count) {
		super(name, position, animation_rate, rate);
		this.resource_limit = resource_limit;
		this.resource_count = resource_count;
	}
	
	public int getResourceCount()
	{
		return this.resource_count;
	}
	
	public void setResourceCount(int n)
	{
		this.resource_count = n;
	}
	
	public int getResourceLimit()
	{
		return this.resource_limit;
	}
}