import java.awt.*;

public abstract class Shape 
{
    protected int x,y;
    protected Color c;
    protected boolean fill;
    public Shape(Color c,int x,int y,boolean fill) 
    {
    	    this.c=c;
    	    this.x=x;
    	    this.y=y;
    	    this.fill=fill;
    }
	public abstract double area();
	public abstract double perimeter();
	public abstract void draw(Graphics g);
}
