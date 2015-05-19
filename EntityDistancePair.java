
public class EntityDistancePair
{
	private Actionable entity;
	private double distance;
	
	public EntityDistancePair(Actionable entity, double distance)
	{
		this.entity = entity;
		this.distance = distance;
	}
	
	public Actionable getEntity()
	{
		return this.entity;
	}
	
	public double getDistance()
	{
		return this.distance;
	}

}
