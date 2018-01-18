import java.awt.Color;
import java.awt.Graphics;

public class Rect extends Shape
{
	private int width,height;
	public Rect(Color c,int x,int y,boolean fill, int width,int height)
	{
		super(c,x,y,fill);
		this.width=width;
		this.height=height;
	}
	public double area()
	{
		return width*height;
	}
	public double perimeter()
	{
		return 2*(width*height);
	}
	public void setWH(int w, int h)
	{
		width=w;
		height=h;
	}
	public void draw(Graphics g)
	{
		g.setColor(c);
		if(fill==false)
			g.drawRect(x, y, width, height);
		else
		    g.fillRect(x, y, width, height);
	}
}

