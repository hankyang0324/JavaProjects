package fiveInARow;

//enhance the defense disposition in the initial stage, improve defense(offense) situation when offense(defense)
public class AI_4 
{
	private int x,y;
	private int result;//win lose or continue
	private int [][]spot;//state of the board, 0 no piece here, 1 black, 2 white
	private long [][]blackScore;//score of black piece on every blank spot
	private long [][]whiteScore;//score of white piece on every blank spot
	private int [][][][]LineLength;//(y,x,direction,color),if place a piece in this spot(y,x), length of the queue of the pieces in this direction and color 
	private int color;
	private PiecesData pieces;
	public AI_4(PiecesData pieces)
	{
		result=0;
		this.pieces=pieces;
		spot=new int [19][19];
		for(int i=0;i<19;i++)
			for(int j=0;j<19;j++)
				spot[i][j]=pieces.getPiece(j, i);
		LineLength=new int [19][19][8][2];//y,x,direction,color
		blackScore=new long [19][19];
		whiteScore=new long [19][19];
		color=pieces.getColorInt();//1 black, 2 white
		LengthOfLine();//get the LineLength[19][19][8][2]
		SetScore();//set blackScore[19][19] and whiteScore[19][19]
		SelectSpot();//select the spot to place the next piece
		result=pieces.addPiece(x, y);
	}
	private void LengthOfLine()//if place a piece in this spot(y,x), length of the queue of the pieces in this direction and color 
	{
		int count;
		int tx,ty;
		int [] dx= {0,1,1,1,0,-1,-1,-1};
		int [] dy= {-1,-1,0,1,1,1,0,-1};//eight direction
		for(int i=0;i<19;i++)
		{
			for(int j=0;j<19;j++)
			{
				if(spot[i][j]==0)// is it a blank spot?
				{
					for(int k=0;k<8;k++)//8 direction
					{
						count=0;
						tx=j;
						ty=i;
						for (int length=0;length<4;length++)  
	                    {  
	                        tx+=dx[k];  
	                        ty+=dy[k];  
	                        if(tx>18||tx<0||ty>18||ty<0) //out of boundary
	                        {
	                        	    count--;
	                        	    break;  
	                        }
	                        if(spot[ty][tx]==1) //if there is a black piece on this adjacent spot in this direction, length +1
	                            count+=2;  
	                        else if(spot[ty][tx]==2)//if one side is blocked
	                        {
	                          	count--;
	                          	break;
	                        }
	                        else
	                        	    break;                       
	                    }
					    LineLength[i][j][k][0]=count;//get the length of the black queue in this direction k
					    count=0;
						tx=j;
						ty=i;
						for (int length=0;length<4;length++)  
	                    {  
	                        tx+=dx[k];  
	                        ty+=dy[k];  
	                        if(tx>18||tx<0||ty>18||ty<0)  
	                        {
	                         	count--;
	                         	break;  
	                        }
	                        if(spot[ty][tx]==2)  
	                            count+=2;  
	                        else if(spot[ty][tx]==1)//if one side is blocked
	                        {
	                          	count--;
	                          	break;
	                        }
	                        else  
	                            break;
	                    }
						LineLength[i][j][k][1]=count;//get the length of the white queue in this direction k
					}
				}
			}
		}
	}
	private void SetScore()
	{
		long score;
		for(int i=0;i<19;i++)
		{
			for(int j=0;j<19;j++)
			{
				if(spot[i][j]==0)// is it a blank spot?
				{
					score=0;
					for(int k=0;k<4;k++)//give score referring to the length of the queue containing this blank spot in 4 different directions, one side blocked situation is considered
					{
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]>=7)//5 pieces
						{
							score=10000000000L;//win
							break;
						}
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==6)//unblocked 4 pieces
						{
							if(LineLength[i][j][k][0]%2!=0)//two sides blocked 5
							{
								score=10000000000L;//win
								break;
							}
							score+=100000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
							continue;
						}
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==5&&score<100000000)//blocked 4 pieces
						{
							score+=10000;
							if(score>=20000)//double blocked 4,1 step to win 
							{
								score=100000000;
								continue;
							}
							if(score>=11000)//one blocked 4 and one unblocked 3,2 step to win 
							{
								score=1000000;
								continue;
							}
						}
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4&&score<100000000)//unblocked 3 pieces
						{
							if(LineLength[i][j][k][0]%2!=0)//two sides blocked 4
								continue;
							score+=1000;
							if(score>=11000)//one blocked 4 and one unblocked 3, 2 step to win 
							{
								score=1000000;
								continue;
							}
							if(score>=2000)
							{
								score=1000000;//two unblocked 3,2 step to win
								continue;
							}
						}
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==3&&score<1000000)//blocked 3 pieces
							score+=100;
					    if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==2&&score<1000000)//unblocked 2 pieces
					    {         
					    	    if(LineLength[i][j][k][0]%2!=0)//two sides blocked 3
								continue;
							score+=10;
					    }
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==1&&score<1000000)//blocked 2 pieces
							score+=1;
					}
					blackScore[i][j]=score;
					score=0;
					for(int k=0;k<4;k++)
					{
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]>=7)
						{
							score=10000000000L;//win
							break;
						}
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==6)
						{
							if(LineLength[i][j][k][1]%2!=0)//two sides blocked 5
							{
								score=10000000000L;//win
								break;
							}
							score+=100000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
							continue;
						}
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==5&&score<100000000)
						{
							score+=10000;
							if(score>=20000)//double blocked 4,1 step to win 
							{
								score=100000000;
								continue;
							}
							if(score>=11000)//one blocked 4 and one unblocked 3,2 step to win 
							{
								score=1000000;
								continue;
							}
						}
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4&&score<100000000)
						{
							if(LineLength[i][j][k][1]%2!=0)//two sides blocked 4
								continue;
							score+=1000;
							if(score>=11000)//one blocked 4 and one unblocked 3, 2 step to win 
							{
								score=1000000;
								continue;
							}
							if(score>=2000)
							{
								score=1000000;//two unblocked 3,2 step to win
								continue;
							}
						}
					    if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==3&&score<1000000)
							score+=100;
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==2&&score<1000000)
						{
							if(LineLength[i][j][k][1]%2!=0)//two sides blocked 3
								continue;
							score+=10;
						}
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==1&&score<1000000)
							score+=1;
					}
					whiteScore[i][j]=score;					
				}
			}
		}
	}
	private void SelectSpot()
	{
		int xb=0,yb=0;
		int xw=0,yw=0;
		for(int i=0;i<19;i++)
		{
			for(int j=0;j<19;j++)//find the highest score for the spot of the next black piece
			{
				if(blackScore[yb][xb]==blackScore[i][j])//if scores of black piece at two spots are equal, judge at which spot the score of white piece is higher.
				{
					if(whiteScore[yb][xb]<whiteScore[i][j])
					{
						yb=i;
						xb=j;
					}					
				}			
				if(blackScore[yb][xb]<blackScore[i][j])
				{
					yb=i;
					xb=j;
				}
			}
		}
		for(int i=0;i<19;i++)//find the highest score for the best spot of the next white piece
		{
			for(int j=0;j<19;j++)
			{
				if(whiteScore[yw][xw]==whiteScore[i][j])
				{
					if(blackScore[yw][xw]<blackScore[i][j])
					{
						yw=i;
						xw=j;
					}
				}
				if(whiteScore[yw][xw]<whiteScore[i][j])
				{
					yw=i;
					xw=j;
				}
			}
		}
		if(color==1)//player is black
		{//if the threat from the the best spot of the next black piece (player) is larger than the chance of the best spot of the next white piece (AI) to win, select the black spot to block the player. Otherwise, select the white spot to get higher possibility to win. 
			if(pieces.getSize()<=10)//in first 5 steps AI is inclined to defend
			{
				if(blackScore[yb][xb]>=whiteScore[yw][xw])
				{
					x=xb;
					y=yb;
				}
				else
				{
					x=xw;
					y=yw;
				}	
			}
			else//after 5 steps AI is inclined to offense
			{
				if(blackScore[yb][xb]>whiteScore[yw][xw]*10)//if AI will be n(blocked) and player will be n, choose spot of n(blocked). Unless n will win
				{
					x=xb;
					y=yb;
				}
				else
				{
					x=xw;
					y=yw;
				}
				if(blackScore[yb][xb]<10000000000L&&whiteScore[yw][xw]>=10000&&whiteScore[yw][xw]<20000)
				{
					x=xw;
					y=yw;
				}
			}
		}
		if(color==2)//player is white
		{
			if(pieces.getSize()<=10)//in first 5 steps AI is inclined to defend
			{
				if(whiteScore[yw][xw]>=blackScore[yw][xw])
				{
					x=xw;
					y=yw;
				}
				else
				{
					x=xb;
					y=yb;
				}
			}
			else
			{
				if(whiteScore[yw][xw]>blackScore[yb][xb]*10)//if AI will be n(blocked) and player will be n, choose spot of n(blocked)
				{
					x=xw;
					y=yw;
				}
				else
				{
					x=xb;
					y=yb;
				}
				if(whiteScore[yw][xw]<10000000000L&&blackScore[yb][xb]>=10000&&blackScore[yb][xb]<20000)
				{
					x=xb;
					y=yb;
				}
			}		
		}
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
