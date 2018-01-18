import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DrawingArea extends JPanel
{
	private ArrayList<Shape> drawList;
	private Mode mode;
	private Shape current;
	private Color color;
	private int x,y;
	private boolean fill=false,eraser=false,pen=true;
	private int width;
	public DrawingArea()
	{
		mode=new LineMode();
		color=Color.BLACK;
		drawList=new ArrayList<Shape>();
		setBackground(Color.WHITE);
		addMouseListener(new MouseListener() 
		{

			@Override
			public void mouseClicked(MouseEvent e) 
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) 
			{
				// TODO Auto-generated method stub
				if(eraser==false&&pen==false)
				{
					Graphics g=getGraphics();
					x=e.getX(); 
					y=e.getY();
				    current=mode.create(color,x,y,false,0,0);
					current.draw(g);
				}
				if(eraser==true)	
				{
					current=new Circle(Color.WHITE,e.getX()-width,e.getY()-width,true,width*2);
					drawList.add(current);
					current.draw(getGraphics());
				}
				if(pen==true)	
				{
					current=new Circle(color,e.getX()-width,e.getY()-width,true,width*2);
					drawList.add(current);
					current.draw(getGraphics());
				}
				
		        //repaint(); repaint everything, slow
			}

			@Override
			public void mouseReleased(MouseEvent e) 
			{
				if(eraser==false&&pen==false)
				{
					int w=e.getX()-x;
					int h=e.getY()-y;
					current=mode.create(color,x,y,fill,w,h);
					drawList.add(current);
				    current.draw(getGraphics());
				}
				if(eraser==true)	
				{
					current=new Circle(Color.WHITE,e.getX()-width,e.getY()-width,true,width*2);
					drawList.add(current);
					current.draw(getGraphics());
				}
				if(pen==true)	
				{
					current=new Circle(color,e.getX()-width,e.getY()-width,true,width*2);
					drawList.add(current);
					current.draw(getGraphics());
				}
					
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) 
			{
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) 
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				// TODO Auto-generated method stub
				
			}
		});
		addMouseMotionListener(new MouseMotionListener()
		{

			@Override
			public void mouseDragged(MouseEvent e)
			{
				if(eraser==false&&pen==false)
				{
					Graphics g=getGraphics();				
					int w=e.getX()-x;
					int h=e.getY()-y;
					g.setXORMode(Color.WHITE);
					current.draw(g);
					current=mode.create(color,x,y,false,w,h);
					current.draw(g);
				}
				if(eraser==true)	
				{
					current=new Circle(Color.WHITE,e.getX()-width,e.getY()-width,true,width*2);
					drawList.add(current);
					current.draw(getGraphics());
				}
				if(pen==true)	
				{
					current=new Circle(color,e.getX()-width,e.getY()-width,true,width*2);
					drawList.add(current);
					current.draw(getGraphics());
				}
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
	public void setPen(boolean p)
	{
		pen=p;
	}
	public void setMode(Mode m) 
	{
		mode=m;
	}
	public void setFill(boolean f)
	{
		fill=f;
	}
	public void setColor(Color c)
	{
		color=c;
	}
	public void setEraser(boolean e)
	{
		eraser=e;
	}
	public void setStrokeWeight(int w)
	{
		width=w;
	}
	public void clear()
	{
		drawList.clear();
		repaint();
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		for(Shape s:drawList)
		    s.draw(g);	    	    
	}
}

