package fiveInARow;

//a stupid random garbage AI
import java.util.*;

public class AI_0
{
	private int x,y,result;//result: win lose or continue
	public AI_0(PiecesData pieces)
	{
		result=0;
		Random a=new Random();
		int temp;
		do 
		{
			x=a.nextInt(19);
			y=a.nextInt(19);
          temp=pieces.addPiece(x, y);
		}while(temp==3);//if this spot is occupied, choose another spot.
	    result=temp;		
	}
	public int GetResult()
	{
		return result;
	}
	public int GetX()
	{
		return x;
	}
	public int GetY()
	{
		return y;
	}
}
