import java.util.List;

public abstract class Entity
{
	private String name;
	private Point position;
	private List<Integer> imgs;
	private int current_img = 0;
	
	public Entity (String name, Point position, List<Integer> imgs, int current_img)
	{
		this.name = name;
		this.position = position;
		this.imgs = imgs;
		this.current_img = current_img;
	}
	
	
	
	public String getName()
	{
		return this.name;
	}
	
	public Point getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Point new_position)
	{
		this.position = new_position;
	}
	
	public List<Integer> getImages()
	{
		return this.imgs;
	}
	
	public int getImage()
	{
		return this.imgs.get(this.current_img);
	}
}