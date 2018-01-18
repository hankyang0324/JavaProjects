package fiveInARow;

import java.util.*;
//successive offense by using combo of unblocked 3 to 5, test if it can win within 11 steps
//also use minimax search with alpha-beta pruning

public class Combo 
{
	private int AI;
	private IfWin ifwin;
	int x;
	int y;
	public Combo(BoardAnalyse ba,int AI,int depth)
	{
		this.AI=AI;
		ifwin=new IfWin();
		ifwin=ComboMaxmin(ba,AI,depth);
	}
	
	private IfWin ComboMaxmin(BoardAnalyse ba,int player,int depth)
	{
		int level=0;
		ifwin.level=level;
		ifwin.win=false;
		int bestlevel=20;
		LinkedList<Point> ps=ba.PossibleSpotOrderCombo(AI,player);
		if(ps.size()==0)
		{
			ifwin.win=false;
			return ifwin;
		}
		for(Iterator<Point> it=ps.iterator();it.hasNext();)
		{
			Point pt=it.next();
			int i=pt.getY();
			int j=pt.getX();
			ba.addPiece(j, i, player);
			ifwin=ComboMin(ba,((player-1)^1)+1,depth-1,level+1,0,bestlevel<20? bestlevel: 20);
			ba.removePiece(j,i);
			if(ifwin.win==true&&ifwin.level<bestlevel)
			{
				bestlevel=ifwin.level;
				y=i;
				x=j;
			}
		}
		ifwin.level=bestlevel;
		if(bestlevel<20)
			ifwin.win=true;
		return ifwin;
	}
	
	private IfWin ComboMin(BoardAnalyse ba,int player,int depth,int level,int alpha,int beta)
	{
		ba.LineLength();
		ba.SpotScore();
		int result=ba.EvaluateBoard(AI,player);
		ifwin.level=level;
		ifwin.win=false;
		if(result>=100000000)
		{
			ifwin.win=true;
			return ifwin;
		}
		if(result<=-100000000)
		{
			ifwin.win=false;
			return ifwin;
		}
		if(depth<=0)	
		{
			ifwin.win=false;
			return ifwin;
		}
		int bestlevel=0;
		LinkedList<Point> ps=ba.PossibleSpotOrderCombo(AI,player);
		if(ps.size()==0)
		{
			ifwin.win=false;
			return ifwin;
		}
		for(Iterator<Point> it=ps.iterator();it.hasNext();)
		{
			Point pt=it.next();
			int i=pt.getY();
			int j=pt.getX();
			ba.addPiece(j, i, player);
			ifwin=ComboMax(ba,((player-1)^1)+1,depth-1,level+1,bestlevel>alpha? bestlevel: alpha,beta);
			ba.removePiece(j,i);
			if(ifwin.win==false)
				return ifwin;
			if(ifwin.win==true&&ifwin.level>bestlevel)
				bestlevel=ifwin.level;
			if(ifwin.level>=beta)
			{
				//ifwin.win=false;
				return ifwin;
			}
		}
		ifwin.level=bestlevel;
		return ifwin;
	}
	
	private IfWin ComboMax(BoardAnalyse ba,int player,int depth,int level,int alpha,int beta)
	{
		ba.LineLength();
		ba.SpotScore();
		int result=ba.EvaluateBoard(AI,player);
		ifwin.win=false;
		ifwin.level=level;
		if(result>=100000000)
		{
			ifwin.win=true;
			return ifwin;
		}
		if(result<=-100000000)
		{
			ifwin.win=false;
			return ifwin;
		}
		if(depth<=0)
		{
			ifwin.win=false;
			return ifwin;
		}
		int bestlevel=20;
		LinkedList<Point> ps=ba.PossibleSpotOrderCombo(AI,player);
		if(ps.size()==0)
		{
			ifwin.win=false;
			return ifwin;
		}
		for(Iterator<Point> it=ps.iterator();it.hasNext();)
		{
			Point pt=it.next();
			int i=pt.getY();
			int j=pt.getX();
			ba.addPiece(j, i, player);
			ifwin=ComboMin(ba,((player-1)^1)+1,depth-1,level+1,alpha,bestlevel<beta ? bestlevel : beta);
			ba.removePiece(j,i);
			if(ifwin.win==true&&ifwin.level<bestlevel)
				bestlevel=ifwin.level;
			if(ifwin.level<=alpha)
			{
				//ifwin.win=false;
				return ifwin;
			}
		}
		ifwin.level=bestlevel;
		if(bestlevel<20)
			ifwin.win=true;
		return ifwin;
	}
	
	public boolean AIWin()
	{
		return ifwin.win;
	}
	public double Cost()
	{
		return (double)ifwin.level;
	}
	public int GetY()
	{
		return y;
	}
	public int GetX()
	{
		return x;
	}
	private class IfWin	
	{
		public boolean win;
		public int level;
	}
}

