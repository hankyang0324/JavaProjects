package fiveInARow;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

class Drawing extends JPanel
{
	private ArrayList<Circle> drawList;
	private PiecesData pieces;
	private AI_6 ai;
	private int mode;//0 pvp, 1 you first, 2 AI first
	private int result;//0 continue, 1 black wins,  2 white wins,
	public Drawing()
	{
		setSize(800,800);
		
		setBackground(new Color(255,200,0));
		drawList=new ArrayList<Circle>();
		pieces=new PiecesData();
		ai=new AI_6();
		mode=1;
		result=0;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) 
			{
				if(result==0)//continue game
				{
					int x=((e.getX()+20)/40)*40;
					int y=((e.getY()+20)/40)*40;
					if(x<40||x>600)
						return;
					if(y<40||y>600)
						return;	//out of the boundary
					int temp=pieces.addPiece(x/40-1, y/40-1);
					if(temp==3)//this spot is occupied
						return;
					result=temp;
					Circle c=new Circle(x,y,30,pieces.getColor());
					if(drawList.size()>0)
						drawList.get(drawList.size()-1).coverFrame(getGraphics());
					drawList.add(c);	
					c.draw(getGraphics());
					c.drawFrame(getGraphics());
					if(mode==0)
					{
						if(result==1)
							JOptionPane.showMessageDialog(null, "                 Black Wins!",null, JOptionPane.DEFAULT_OPTION); 
						if(result==2)
							JOptionPane.showMessageDialog(null, "                 White Wins!",null, JOptionPane.DEFAULT_OPTION);
						return;
					}
					if(mode==1)
					{
						if(result==1)
						{
							JOptionPane.showMessageDialog(null, "                  You Win!",null, JOptionPane.DEFAULT_OPTION);
							return;
						}
						ai.play(pieces);
						x=(ai.GetX()+1)*40;
						y=(ai.GetY()+1)*40;
					    c=new Circle(x,y,30,pieces.getColor());
					    if(drawList.size()>0)
							drawList.get(drawList.size()-1).coverFrame(getGraphics());
						drawList.add(c);	
						c.draw(getGraphics());
						c.drawFrame(getGraphics());
						result=ai.GetResult();
						if(result==2)
							JOptionPane.showMessageDialog(null, "                 You Lose!",null, JOptionPane.DEFAULT_OPTION);
						return;
					}
					if(mode==2)
					{
						if(result==2)
						{
							JOptionPane.showMessageDialog(null, "                  You Win!",null, JOptionPane.DEFAULT_OPTION);
							return;
						}
						ai.play(pieces);
						x=(ai.GetX()+1)*40;
						y=(ai.GetY()+1)*40;
					    c=new Circle(x,y,30,pieces.getColor());
					    if(drawList.size()>0)
							drawList.get(drawList.size()-1).coverFrame(getGraphics());
						drawList.add(c);		
						c.draw(getGraphics());
						c.drawFrame(getGraphics());
						result=ai.GetResult();
						if(result==1)
							JOptionPane.showMessageDialog(null, "                 You Lose!",null, JOptionPane.DEFAULT_OPTION);
					}				
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) 
			{
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) 
			{
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}});
	}
	
	public void ReStart()
	{
		drawList.clear();
		pieces.clearPiece();
		ai=new AI_6();
		result=0;
		if(mode==2)//AI first, put a black piece on a random spot on the board for AI when beginning
		{
			int x=7;
			int y=7;
			pieces.addPiece(x, y);
			x=(x+1)*40;
			y=(y+1)*40;
		    Circle c=new Circle(x,y,30,pieces.getColor());
			drawList.add(c);		
			c.draw(getGraphics());
		}
		repaint();
	}
	
	public void Retract()
	{
		if(mode==0&&result==0)// PVP, drop the last piece
		{
			if(drawList.size()==0)
				return;
			drawList.remove(drawList.size()-1);
			pieces.removePiece();
			repaint();
		}
		if(mode==1&&result==0)// PVC, you first, drop the last two pieces
		{
			if(drawList.size()==0)
				return;
			drawList.remove(drawList.size()-1);
			drawList.remove(drawList.size()-1);
			pieces.removePiece();
			pieces.removePiece();
			repaint();
		}
		if(mode==2&&result==0)//PVC, AI first, drop the last two pieces
		{
			if(drawList.size()<=1)// Don't drop the first one which belongs to the AI
				return;
			drawList.remove(drawList.size()-1);
			drawList.remove(drawList.size()-1);
			pieces.removePiece();
			pieces.removePiece();
			repaint();
		}
	}
	
	public void SetMode(int i)
	{
		mode=i;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		for(int x=40;x<=600;x+=40)
			g.drawLine(x, 40, x, 600);
		for(int y=40;y<=600;y+=40)
			g.drawLine(40, y, 600, y);
		for(int x=160;x<=480;x+=320)
			for(int y=160;y<=480;y+=320)
			{
				Circle c=new Circle(x,y,10,Color.BLACK);
				c.draw(g);
			}
		Circle c2=new Circle(320,320,10,Color.BLACK);
		c2.draw(g);//draw the board
		for(Circle c:drawList)
			c.draw(g);
		if(drawList.size()>0)
			drawList.get(drawList.size()-1).drawFrame(g);
	}
}
