package fiveInARow;

import java.awt.*;

class Circle
{
	private int x,y,w;
	private Color c;
	public Circle(int x,int y,int w,Color c)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.c=c;
	}
	public void draw(Graphics g)
	{
		g.setColor(c);
		g.fillOval(x-w/2, y-w/2, w, w);
	}
	public void drawFrame(Graphics g)
	{
		g.setColor(Color.red);
		g.drawOval(x-w/2, y-w/2, w, w);
	}
	public void coverFrame(Graphics g)
	{
		g.setColor(c);
		g.drawOval(x-w/2, y-w/2, w, w);
	}
}

