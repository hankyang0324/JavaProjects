import java.awt.Color;

public class CircleMode extends Mode
{
	public Shape create(Color c,int x,int y, boolean fill,int w, int h)
	{
		return new Circle(c,x,y,fill,w);
	}

}
