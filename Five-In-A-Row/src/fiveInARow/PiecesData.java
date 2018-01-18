package fiveInARow;

import java.awt.Color;
import java.util.*;

class PiecesData
{
	private BoardData board;
	private ArrayList<Integer> prevX;
	private ArrayList<Integer> prevY;
	private int color;
	private void ChangeColor()
	{
		if(color==1)
		{
			color=2;
			return;
		}
		if(color==2)
		{
			color=1;
			return;
		}
	}
	public PiecesData()
	{
		board=new BoardData();
		prevX=new ArrayList<Integer>();
		prevY=new ArrayList<Integer>();
		color=2;
	}
	public int getPiece(int x,int y)
	{
		return board.GetSpot(x, y);
	}
	public void removePiece()
	{
		int x=prevX.get(prevX.size()-1);
		int y=prevY.get(prevY.size()-1);
		prevX.remove(prevX.size()-1);
		prevY.remove(prevY.size()-1);
		ChangeColor();
		board.SetSpot(x, y, 0);
	}
	public int addPiece(int x,int y)
	{
		if(board.GetSpot(x, y)!=0)
			return 3;//this spot is occupied
		prevX.add(x);
		prevY.add(y);
		ChangeColor();
		board.SetSpot(x, y, color);
		return board.Check(x, y);//who wins? or continue
	}
	public void clearPiece()
	{
		prevX.clear();
		prevY.clear();
		color=2;
		board.BoardClear();
	}
	public int getPrevX(int n)
	{
		return prevX.get(n);
	}
	public int getPrevY(int n)
	{
		return prevY.get(n);
	}
	public int getSize()
	{
		return prevX.size();
	}
	public int getColorInt()
	{
		return color;
	}
	public Color getColor()
	{
		if(color==1)
			return Color.black;
		if(color==2)
			return Color.white;
		return null;
	}
	public BoardData GetBoard()
	{
		return board;
	}
}
