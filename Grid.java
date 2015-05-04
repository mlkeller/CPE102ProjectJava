public class Grid
{
	private int width;
	private int height;
	WorldObject[][] cells = new WorldObject[width][height];
	
	public Grid(int width, int height, WorldObject occupancy_value)
	{
		this.width = width;
		this.height = height;
		
		for (int row = 0; row < height; row++)
		{
			for (int col = 0; col < width; col++)
			{
				this.cells[row][col] = occupancy_value;
			}
		}
	}
	
	public WorldObject getCell(Point pt)
	{
		return this.cells[pt.getY()][pt.getX()];
	}
	
	public void setCell(Point pt, WorldObject value)
	{
		this.cells[pt.getY()][pt.getX()] = value;
	}
}