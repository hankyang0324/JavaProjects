import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape
{
	private int r;
	public Circle(Color c, int x,int y,boolean fill,int r)
	{
		super(c,x,y,fill);
		this.r=r;
	}
	public double area()
	{
		return Math.PI*r*r;
	}
	public double perimeter()
	{
		return Math.PI*2*r;
	}
	public void setRadius(int r)
	{
		this.r=r;
	}
	public void draw(Graphics g)
	{
		g.setColor(c);
		if(fill==false)
			g.drawOval(x, y, r, r);
		else
		    g.fillOval(x, y, r, r);
	}
}
