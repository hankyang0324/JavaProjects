import java.awt.Color;

public class RectMode extends Mode
{
	public Shape create(Color c,int x,int y,boolean fill, int w, int h)
	{
		return new Rect(c,x,y,fill,w,h);
	}
}
