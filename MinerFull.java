public class MinerFull extends Miner
{

	public MinerFull(String name, Point position, int animation_rate, int rate,
					 int resource_limit)
	{
		super(name, position, animation_rate, rate, resource_limit, resource_limit);
	}
	
	public MinerNotFull tryTransformMinerFull(WorldModel world)
	{
		MinerNotFull new_entity = new MinerNotFull(this.getName(), this.getPosition(),
												   this.getAnimationRate(), this.getRate(), this.getResourceLimit());
		return new_entity;
	}
}