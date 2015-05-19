import java.util.List;
import processing.core.*;

public abstract class Mover extends AnimatedActor
{
	private int rate;
	
	public Mover(String name, Point position, List<PImage> imgs, int animation_rate, int rate) {
		super(name, position, imgs, animation_rate);
		this.rate = rate;
	}
	
	public int getRate()
	{
		return this.rate;
	}
}
