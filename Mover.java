public abstract class Mover extends AnimatedActor
{
	private int rate;
	
	public Mover(String name, Point position, int animation_rate, int rate) {
		super(name, position, animation_rate);
		this.rate = rate;
	}
	
	public int getRate()
	{
		return this.rate;
	}
}