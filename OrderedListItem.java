
public class OrderedListItem
{
	private Action action;
	private long ord;
	
	public OrderedListItem(Action action, long ord)
	{
		this.action = action;
		this.ord = ord;
	}
	
	public Action getAction()
	{
		return this.action;
	}
	
	public long getTime()
	{
		return this.ord;
	}
}
