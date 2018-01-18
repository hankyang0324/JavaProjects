package fiveInARow;
import java.util.*;

//minimax search up to 6 steps with alpha-beta pruning and heuristic search
//prediction up to 11 steps by successive offense of combo is integrated into it

public class MaxMin 
{
	ArrayList<Point> p;
	ArrayList<Point> p1;
	ArrayList<Point> p2;
	int depth;
	int AI;
	MaxMin(BoardAnalyse ba,int AI,int depth)
	{
		this.depth=depth;
		p=new ArrayList<Point> ();
		p1=new ArrayList<Point> ();
		p2=new ArrayList<Point> ();
		this.AI=AI;
		maxmin(ba,AI,depth);
	}
	
	private void maxmin(BoardAnalyse ba,int player,int depth)
	{
		int best=-1000000000;
		Combo c=new Combo(ba,AI,9);
		boolean mustwin=c.AIWin();
		//System.out.println(mustwin);//
		if(mustwin==true)
		{
			int val=(int) (1000000000*Math.pow(0.8, c.Cost()-1));
			best=val;
			p2.add(new Point(c.GetY(),c.GetX()));
			System.out.println((int)c.Cost()+" steps to win");
		}
		if(mustwin==false)
		{
			LinkedList<Point> ps=ba.PossibleSpotOrder(player);
			for(Iterator<Point> it=ps.iterator();it.hasNext();)
			{
				Point pt=it.next();
				int i=pt.getY();
				int j=pt.getX();
				ba.addPiece(j, i, player);
				int val=min(ba,((player-1)^1)+1,depth-1, 1000000000, best > -1000000000 ? best :-1000000000);
				ba.removePiece(j,i);
				if(val==best)
					p.add(pt);
				if(val>best)
				{
					best=val;
					p.clear();
					p.add(pt);
				}			
			}
			PointScore(ba);
		}
		System.out.println("best:"+best);
	}
	
	private int min(BoardAnalyse ba,int player,int depth,int alpha,int beta)
	{
		if(depth==this.depth-1)
		{
			Combo c=new Combo(ba,player,7);
			if(c.AIWin()==true)//Actually it's player-may-win situation
			{
				System.out.println("eliminate Player-may-win spot");
				return -999999999+(int)c.Cost();
			}
		}
		ba.LineLength();
		ba.SpotScore();
		int result=ba.EvaluateBoard(AI,player);
		if(result>=100000000)
			return 999999999;
		if(result<=-100000000)
			return -999999999;
		if(depth<=0)			
			return result;
		int best=1000000000;
		LinkedList<Point> ps=ba.PossibleSpotOrder(player);
		for(Iterator<Point> it=ps.iterator();it.hasNext();)
		{
			Point pt=it.next();
			int i=pt.getY();
			int j=pt.getX();
			ba.addPiece(j, i, player);
			int val=max(ba,((player-1)^1)+1,depth-1,best < alpha ? best : alpha, beta);
			ba.removePiece(j,i);
			if(val<best)
				best=val;
			if(val<=beta)
				return val;
		}
		return best;
	}
	
	private int max(BoardAnalyse ba,int player,int depth,int alpha,int beta)
	{
		ba.LineLength();
		ba.SpotScore();
		int result=ba.EvaluateBoard(AI,player);
		if(result>=100000000)
			return 999999999;
		if(result<=-100000000)
			return -999999999;
		if(depth<=0)
			return result;
		int best=-1000000000;
		LinkedList<Point> ps=ba.PossibleSpotOrder(player);
		for(Iterator<Point> it=ps.iterator();it.hasNext();)
		{
			Point pt=it.next();
			int i=pt.getY();
			int j=pt.getX();
			ba.addPiece(j, i, player);
			int val=min(ba,((player-1)^1)+1,depth-1,alpha, best > beta ? best : beta);
			ba.removePiece(j,i);
			if(val>best)
				best=val;
			if(val>=alpha)
				return val;
		}
		return best;
	}
	
	private void PointScore(BoardAnalyse ba)
	{
		ba.PossibleSpot();
		ba.NextLineLength();
		ba.NextSpotScore();
		int maxAI=-1000000001;
		int maxPlayer=-1000000001;
		int AIscore=0;
		int Playerscore=0;
		for(int i=0;i<p.size();i++)
		{
		    AIscore=ba.GetAISpotScore(AI, p.get(i));
			if(maxAI<AIscore)
				maxAI=AIscore;
		}
		for(int i=0;i<p.size();i++)
		{
			if(ba.GetAISpotScore(AI, p.get(i))==maxAI)
				p1.add(p.get(i));
		}
		for(int i=0;i<p1.size();i++)
		{
			Playerscore=ba.GetPlayerSpotScore(AI, p1.get(i));
			if(maxPlayer<Playerscore)
				maxPlayer=Playerscore;
		}
		for(int i=0;i<p1.size();i++)
		{
			if(ba.GetPlayerSpotScore(AI, p1.get(i))==maxPlayer)
				p2.add(p1.get(i));
		}
	}
	
	public Point GetPoint()
	{
		Random a=new Random();
		return p2.get(a.nextInt(p2.size()));
	}
}
