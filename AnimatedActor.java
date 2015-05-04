public abstract class AnimatedActor extends Actionable
{
	private int animation_rate;
	
	public AnimatedActor(String name, Point position, int animation_rate)
	{
		super(name, position);
		this.animation_rate = animation_rate;
	}
	
	public int getAnimationRate()
	{
		return this.animation_rate;
	}

}