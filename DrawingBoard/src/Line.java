import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape
{
	private int x2,y2;
	public Line(Color c,int x,int y,boolean fill, int x2,int y2)
	{
		super(c,x,y,fill);
		this.x2=x2;
		this.y2=y2;
	}
	public void draw(Graphics g)
	{
		g.setColor(c);
		g.drawLine(x, y, x2, y2);
	}
	@Override
	public double area() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double perimeter() {
		// TODO Auto-generated method stub
		return 0;
	}
}

