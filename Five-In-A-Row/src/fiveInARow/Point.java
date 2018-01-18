package fiveInARow;

//store information from single piece
public class Point 
{
	int y,x,interval;
	public Point(int y,int x)
	{
		this.y=y;
		this.x=x;
		interval=0;
	}
	public Point(int y,int x,int interval)
	{
		this.y=y;
		this.x=x;
		this.interval=interval;
	}
	public int getY()
	{
		return y;
	}
	public int getX()
	{
		return x;
	}
	public int getInterval()
	{
		return interval;
	}
}
