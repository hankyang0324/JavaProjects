import java.awt.Color;

public class LineMode extends Mode
{
	public Shape create(Color c,int x,int y,boolean fill, int w, int h)
	{
		return new Line(c,x,y,fill,x+w,y+h);
	}
}
