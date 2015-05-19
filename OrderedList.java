import java.util.LinkedList;
import java.util.List;


public class OrderedList
{
	private List<OrderedListItem> list = new LinkedList<OrderedListItem>();
	
	public int getSize()
	{
		return list.size();
	}
	
	public void insert(Action action, long ord)
	{
		int size = this.list.size();
		int idx = 0;
		while(idx < size && this.list.get(idx).getTime() < ord)
		{
			idx = idx + 1;
		}
		
		this.list.add(idx, new OrderedListItem(action, ord));
	}
	
	public void remove(Action action) //change action to Action later
	{
		int size = this.list.size();
		int idx = 0;
		while(idx < size && this.list.get(idx).getAction() != action)
		{
			idx = idx + 1;
		}
		
		if (idx < size)
		{
			this.list.remove(idx);
		}
	}
	
	public OrderedListItem head()
	{
		if (!(this.list.isEmpty()))
		{
			return this.list.get(0);
		}
		else
		{
			return null;
		}
	}
	
	public Action pop()
	{
		if(!(this.list.isEmpty()))
		{
			Action popped = this.list.get(0).getAction();
			this.list.remove(0);
			return popped;
		}
		else
		{
			return null;
		}
	}
}

/// at some point go and put in the checks to deal with the other lists are empty