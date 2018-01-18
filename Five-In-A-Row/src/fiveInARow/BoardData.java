package fiveInARow;

public class BoardData
{
	private int [][] spot;
	public BoardData()
	{
		spot=new int [19][19];
		for(int i=0;i<19;i++)
			for(int j=0;j<19;j++)
				spot[i][j]=0;//0 no piece here, 1 black, 2 white
	}	
	public void SetSpot(int x,int y,int color)
	{
			spot[y][x]=color;
	}
	public int GetSpot(int x,int y)
	{
		return spot[y][x];
	}
	public void BoardClear()
	{
		for(int i=0;i<19;i++)
			for(int j=0;j<19;j++)
				spot[i][j]=0;
	}
	public int Check(int x,int y)//check who wins, or continue the game
	{
		int result=CheckHorizontal(x,y);
		if(result!=0)
			return result;
		result=CheckVertical(x,y);
		if(result!=0)
			return result;
		result=CheckDiagonal1(x,y);
		if(result!=0)
			return result;
		return CheckDiagonal2(x,y);		
	}
	
	private int CheckHorizontal(int x,int y)
	{
		int count=0;
		for(int i=x;i>=0;i--)
		{
			if(spot[y][x]==spot[y][i])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		for(int i=x+1;i<19;i++)
		{
			if(spot[y][x]==spot[y][i])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		return 0;			
	}
	
	private int CheckVertical(int x,int y)
	{
		int count=0;
		for(int i=y;i>=0;i--)
		{
			if(spot[y][x]==spot[i][x])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		for(int i=y+1;i<19;i++)
		{
			if(spot[y][x]==spot[i][x])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		return 0;			
	}
	
	private int CheckDiagonal1(int x,int y)
	{
		int count=0;
		for(int i=y,j=x;i>=0&&j>=0;i--,j--)
		{
			if(spot[y][x]==spot[i][j])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		for(int i=y+1,j=x+1;i<19&&j<19;i++,j++)
		{
			if(spot[y][x]==spot[i][j])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		return 0;
	}
	
	private int CheckDiagonal2(int x,int y)
	{
		int count=0;
		for(int i=y,j=x;i>=0&&j<19;i--,j++)
		{
			if(spot[y][x]==spot[i][j])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		for(int i=y+1,j=x-1;i<19&&j>=0;i++,j--)
		{
			if(spot[y][x]==spot[i][j])
			{
				count++;
				if(count==5)
					return spot[y][x];
			}
			else
				break;
		}
		return 0;
	}
}
