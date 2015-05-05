public class MinerNotFull extends Miner
{

	public MinerNotFull(String name, Point position, int animation_rate, int rate,
					    int resource_limit)
	{
		super(name, position, animation_rate, rate, resource_limit, 0);
	}
	
	public String entityString()
	{
		return ("miner" + " " + 
			    this.getName() + " " +
			    this.getPosition().getX() + " " + 
			    this.getPosition().getY() + " " +
			    this.getResourceLimit() + " " +
			    this.getRate() + " " +
			    this.getAnimationRate());
	}
	
	public Miner tryTransformMinerNotFull()
	{
		if (this.getResourceCount() < this.getResourceLimit())
		{
			return this;
		}
		else
		{
			MinerFull new_entity = new MinerFull(this.getName(), this.getPosition(),
							                     this.getAnimationRate(), this.getRate(), this.getResourceLimit());
			return new_entity;
		}
	}
}
