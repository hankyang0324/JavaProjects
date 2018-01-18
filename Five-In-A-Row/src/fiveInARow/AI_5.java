package fiveInARow;

//consider the situation of lines with a blank space
//optimize the offense strategy
public class AI_5 
{
	private int x,y;
	private int result;//win lose or continue
	private int [][]spot;//state of the board, 0 no piece here, 1 black, 2 white
	private int [][]blackScore;//score of black piece on every blank spot
	private int [][]whiteScore;//score of white piece on every blank spot
	private int [][][][]LineLength;//(y,x,direction,color),if place a piece in this spot(y,x), length of the queue of the pieces in this direction and color
	private int [][][][]SpaceLineLength;
	private int color;
	private PiecesData pieces;
	public AI_5(PiecesData pieces)
	{
		result=0;
		this.pieces=pieces;
		spot=new int [15][15];
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++)
				spot[i][j]=pieces.getPiece(j, i);
		LineLength=new int [15][15][8][2];//y,x,direction,color
		SpaceLineLength=new int [15][15][8][2];
		blackScore=new int [15][15];
		whiteScore=new int [15][15];
		color=pieces.getColorInt();//1 black, 2 white
		LengthOfLine();//get the LineLength[19][19][8][2]
		SetScore();//set blackScore[19][19] and whiteScore[19][19]
		SelectSpot();//select the spot to place the next piece
		System.out.println(y+","+x);
		System.out.println(whiteScore[y][x]);
		System.out.println(blackScore[y][x]);
		result=pieces.addPiece(x, y);
	}
	private void LengthOfLine()//if place a piece in this spot(y,x), length of the queue of the pieces in this direction and color 
	{
		int count;
		int tx,ty;
		int [] dx= {0,1,1,1,0,-1,-1,-1};
		int [] dy= {-1,-1,0,1,1,1,0,-1};//eight direction
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
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
	                        if(tx>14||tx<0||ty>14||ty<0) //out of boundary
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
	                        if(tx>14||tx<0||ty>14||ty<0)  
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
		/*-----Line with a black space-----*/
		int spacecount;
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if(spot[i][j]==0)// is it a blank spot?
				{
					for(int k=0;k<8;k++)//8 direction
					{
						count=0;
						spacecount=0;
						tx=j;
						ty=i;
						for (int length=0;length<5;length++)  
						{
						    tx+=dx[k];  
	                        ty+=dy[k];  
	                        if(tx>14||tx<0||ty>14||ty<0) //out of boundary
	                        {
	                        	    if(spacecount==0)
	                        	    {
	                        	    	    count=-10;
	                        	    	    break;
	                        	    }
	                        	    	if(spot[ty-dy[k]][tx-dx[k]]==0)
	                        	    	{
	                        	    		count=-10;
	                        	    		break;
	                        	    	}
	                        	    	count--;
	                        	    break;  
	                        }
	                        if(spot[ty][tx]==1) //if there is a black piece on this adjacent spot in this direction, length +1
	                            count+=2;
	                        if(spot[ty][tx]==0)
	                        {
	                        	    spacecount++;
	                        	    if(spacecount>1)
	                        	    {
	                        	    	     if(spot[ty-dy[k]][tx-dx[k]]==0)
	                        	    	    	     count=-10;
	                        	    	     break;
	                        	    }
	                        }
	                        if(spot[ty][tx]==2)//if one side is blocked
	                        {
	                        	    if(spacecount==0)
	                        	    {
	                        	    	    count=-10;
	                        	    	    break;
	                        	    }
	                        	    if(spot[ty-dy[k]][tx-dx[k]]==0)
	                        	    {
	                        	    	    count=-10;
	                        	    	    break;
	                        	    }
	                          	count--;
	                          	break;
	                        } 
						}                                         					
						if(count>=6)
							count=-10;
					    SpaceLineLength[i][j][k][0]=count;//get the length of the black queue in this direction k
					    count=0;
					    spacecount=0;
						tx=j;
						ty=i;
						for (int length=0;length<5;length++)  
	                    {  
	                        tx+=dx[k];  
	                        ty+=dy[k];  
	                        if(tx>14||tx<0||ty>14||ty<0)  
	                        {
	                        	    if(spacecount==0)
	                        	    {
	                        	    	    count=-10;
	                        	    	    break;
	                        	    }
	                        	    if(spot[ty-dy[k]][tx-dx[k]]==0)
	                        	    {
	                        	    	    count=-10;
	                        	    	    break;
	                        	    }
	                         	count--;
	                         	break;  
	                        }
	                        if(spot[ty][tx]==2)  
	                            count+=2;
	                        if(spot[ty][tx]==0)
	                        {
	                          	spacecount++;
                        	        if(spacecount>1)
                        	        {
                        	        	    if(spot[ty-dy[k]][tx-dx[k]]==0)
                   	    	    	             count=-10;
                   	    	            break;
                        	        }
	                        }
	                        if(spot[ty][tx]==1)//if one side is blocked
	                        {
	                        	    if(spacecount==0)
	                        	    {
	                        	    	    count=-10;
	                        	    	    break;
	                        	    }
	                        	    if(spot[ty-dy[k]][tx-dx[k]]==0)
	                        	    {
	                        	    	    count=-10;
	                        	    	    break;
	                        	    }
	                          	count--;
	                          	break;
	                        }
	                    }
						if(count>=6)
							count=-10;
						SpaceLineLength[i][j][k][1]=count;//get the length of the white queue in this direction k
					}
				}
			}
		}
	}
	private void SetScore()
	{
		int scoreB,scoreW,scoreB2,scoreW2;
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if(spot[i][j]==0)// is it a blank spot?
				{
					scoreB=0;
					for(int k=0;k<4;k++)//give score referring to the length of the queue containing this blank spot in 4 different directions, one side blocked situation is considered
					{
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]>=7)//5 pieces
							scoreB+=100000000;//win
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==6)//unblocked 4 pieces
						{
							if(LineLength[i][j][k][0]%2!=0)//two sides blocked 5
								scoreB+=100000000;//win
							else
								scoreB+=10000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==5)//blocked 4 pieces
						{
							scoreB+=10000;
							if(scoreB>=20000)//double blocked 4,1 step to win 
								scoreB+=10000000-10000;
							else if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB+=1000000-10000;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4)//unblocked 3 pieces
						{
							if(LineLength[i][j][k][0]%2==0)//not two sides blocked 4
							{
								scoreB+=1000;
								if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB+=1000000-1000;
								else if(scoreB>=2000)//two unblocked 3, 2 step to win 
									scoreB+=100000-1000;
							}							
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==3)//blocked 3 pieces
							scoreB+=100;
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==2)//unblocked 2 pieces
					    {         
					    	    if(LineLength[i][j][k][0]%2==0)//not two sides blocked 3
					    	    	    scoreB+=10;
					    }
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==1)//blocked 2 pieces
							scoreB+=1;
					}
					blackScore[i][j]=scoreB;
					scoreW=0;
					for(int k=0;k<4;k++)
					{
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]>=7)
							scoreW+=100000000;//win
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==6)
						{
							if(LineLength[i][j][k][1]%2!=0)//two sides blocked 5
								scoreW+=100000000;//win
							else
								scoreW+=10000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==5)
						{
							scoreW+=10000;
							if(scoreW>=20000)//double blocked 4,1 step to win 
								scoreW+=10000000-10000;
							else if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW+=1000000-10000;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4)
						{
							if(LineLength[i][j][k][1]%2==0)//not two sides blocked 4
							{
								scoreW+=1000;
								if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW+=1000000-1000;
								else if(scoreW>=2000)
									scoreW+=100000-1000;//two unblocked 3,2 step to win
							}						
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==3)
							scoreW+=100;
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==2)
						{
							if(LineLength[i][j][k][1]%2==0)//not two sides blocked 3
								scoreW+=10;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==1)
							scoreW+=1;
					}
					whiteScore[i][j]=scoreW;					
				}
			}
		}
		/*-----score with blank space-----*/
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if(spot[i][j]==0)// is it a blank spot?
				{
					scoreB2=0;
					for(int k=0;k<4;k++)
					{
						if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==6)//unblocked 4 pieces
						{
							if(LineLength[i][j][k][0]%2!=0)//two sides blocked 5
								scoreB2+=100000000;//win
							else
								scoreB2+=10000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
						}
						else if(SpaceLineLength[i][j][k][0]==5||SpaceLineLength[i][j][k+4][0]==5)//blocked 4
						{
							scoreB2+=10000;
							if(scoreB2>=20000)//double blocked 4,1 step to win 
								scoreB2+=10000000-10000;
							else if(scoreB2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB2+=1000000-10000;
						}
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]>=5||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]>=5)//blocked 4
						{
							scoreB2+=10000;
							if(scoreB2>=20000)//double blocked 4,1 step to win 
								scoreB2+=10000000-10000;
							else if(scoreB2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB2+=1000000-10000;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==5)//blocked 4
						{
							scoreB2+=10000;
							if(scoreB2>=20000)//double blocked 4,1 step to win 
								scoreB2+=10000000-10000;
							else if(scoreB2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB2+=1000000-10000;				
						}
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==4)//unblocked 3
						{
							if(SpaceLineLength[i][j][k][0]%2!=0||SpaceLineLength[i][j][k+4][0]%2!=0)//two sides blocked 4 with a blank space
							{
								scoreB2+=10000;
								if(scoreB2>=20000)//double blocked 4,1 step to win 
									scoreB2+=10000000-10000;
								else if(scoreB2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB2+=1000000-10000;
							}
							else
							{
								scoreB2+=1000;
								if(scoreB2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB2+=1000000-1000;
								else if(scoreB2>=2000)//two unblocked 3, 2 step to win 
									scoreB2+=100000-1000;
							}
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4)
						{
							if(LineLength[i][j][k][0]%2==0)//not two sides blocked 4
							{
								scoreB2+=1000;
								if(scoreB2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB2+=1000000-1000;
								else if(scoreB2>=2000)//two unblocked 3, 2 step to win 
									scoreB2+=100000-1000;
							}							
						}	
					}
					if(blackScore[i][j]<scoreB2)
						blackScore[i][j]=scoreB2-1;
					scoreW2=0;
					for(int k=0;k<4;k++)
					{
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==6)
						{
							if(LineLength[i][j][k][1]%2!=0)//two sides blocked 5
								scoreW2+=100000000;//win
							else
								scoreW2+=10000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
						}
						else if(SpaceLineLength[i][j][k][1]==5||SpaceLineLength[i][j][k+4][1]==5)//blocked 4
						{
							scoreW2+=10000;
							if(scoreW2>=20000)//double blocked 4,1 step to win 
								scoreW2+=10000000-10000;
							else if(scoreW2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW2+=1000000-10000;
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]>=5||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]>=5)//blocked 4
						{
							scoreW2+=10000;
							if(scoreW2>=20000)//double blocked 4,1 step to win 
								scoreW2+=10000000-10000;
							else if(scoreW2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW2+=1000000-10000;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==5)//blocked 4
						{
							scoreW2+=10000;
							if(scoreW2>=20000)//double blocked 4,1 step to win 
								scoreW2+=10000000-10000;
							else if(scoreW2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW2+=1000000-10000;						
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==4)//unblocked 3
						{
							if(SpaceLineLength[i][j][k][1]%2!=0||SpaceLineLength[i][j][k+4][1]%2!=0)//two sides blocked 4 with a blank space
							{
								scoreW2+=10000;
								if(scoreW2>=20000)//double blocked 4,1 step to win 
									scoreW2+=10000000-10000;
								else if(scoreW2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW2+=1000000-10000;
							}
							else
							{
								scoreW2+=1000;
								if(scoreW2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW2+=1000000-1000;
								else if(scoreW2>=2000)
									scoreW2+=100000-1000;//two unblocked 3,2 step to win
							}
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4)
						{
							if(LineLength[i][j][k][1]%2==0)//not two sides blocked 4
							{
								scoreW2+=1000;
								if(scoreW2>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW2+=1000000-1000;
								else if(scoreW2>=2000)
									scoreW2+=100000-1000;//two unblocked 3,2 step to win
							}							
						}	
					}
					if(whiteScore[i][j]<scoreW2)
						whiteScore[i][j]=scoreW2-1;					
				}
			}
		}
	}
	private void SelectSpot()
	{
		int xb=0,yb=0;
		int xw=0,yw=0;
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)//find the highest score for the spot of the next black piece
			{
				if(Length(blackScore[yb][xb])==Length(blackScore[i][j])&&blackScore[i][j]<10000000)//if scores of black piece at two spots are equal, judge at which spot the score of white piece is higher.
				{
					if(whiteScore[yb][xb]<whiteScore[i][j]&&whiteScore[i][j]>=1000)
					{
						yb=i;
						xb=j;
						continue;
					}
					if(whiteScore[yb][xb]>whiteScore[i][j]&&whiteScore[yb][xb]>=1000)
						continue;
				}			
				if(blackScore[yb][xb]<blackScore[i][j])
				{
					yb=i;
					xb=j;
				}
			}
		}
		for(int i=0;i<15;i++)//find the highest score for the best spot of the next white piece
		{
			for(int j=0;j<15;j++)
			{
				if(Length(whiteScore[yw][xw])==Length(whiteScore[i][j])&&whiteScore[i][j]<10000000)
				{
					if(blackScore[yw][xw]<blackScore[i][j]&&blackScore[i][j]>=1000)
					{
						yw=i;
						xw=j;
						continue;
					}
					if(blackScore[yw][xw]>blackScore[i][j]&&blackScore[yw][xw]>=1000)
						continue;
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
			if(pieces.getSize()<=8)//in first 3 steps AI is inclined to defend
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
			else//after 4 steps AI is inclined to offense
			{
				if(blackScore[yb][xb]>=99999&&Length(whiteScore[yw][xw])<Length(blackScore[yb][xb]))//if the player can not win, AI tries offense
				{
					x=xb;
					y=yb;
				}
				else
				{
					x=xw;
					y=yw;
				}
				if(blackScore[yb][xb]<100000000&&whiteScore[yw][xw]>=999999)//if the player can not win in 1 step, AI can try a blocked 4 with an unlocked 3
				{
					x=xw;
					y=yw;
				}
				else if(blackScore[yb][xb]<100000000&&whiteScore[yw][xw]>=9999&&whiteScore[yw][xw]<20000)//if the player can not win in 1 step, AI can try a blocked 4
				{
					x=xw;
					y=yw;
				}
//				else if(blackScore[yb][xb]<999999&&whiteScore[yw][xw]>=999)//if the player can not win in 2 step, AI can try an unlocked 3
//				{
//					x=xw;
//					y=yw;
//				}
			}
		}
		if(color==2)//player is white
		{
			if(pieces.getSize()<=10)//in first 4 steps AI is inclined to defend
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
				if(whiteScore[yw][xw]>=99999&&Length(blackScore[yb][xb])<Length(whiteScore[yw][xw]))//if the player can not win, AI tries offense
				{
					x=xw;
					y=yw;
				}
				else
				{
					x=xb;
					y=yb;
				}
				if(whiteScore[yw][xw]<100000000&&blackScore[yb][xb]>=999999)//if the player can not win in 1 step, AI can try a blocked 4 with an unlocked 3
				{
					x=xb;
					y=yb;
				}	
				else if(whiteScore[yw][xw]<100000000&&blackScore[yb][xb]>=9999&&blackScore[yb][xb]<20000)//if the player can not win in 1 step, AI can try a blocked 4
				{
					x=xb;
					y=yb;
				}
//				else if(whiteScore[yw][xw]<999999&&blackScore[yb][xb]>=999)//if the player can not win in 2 step, AI can try an unlocked 3
//				{
//					x=xb;
//					y=yb;
//				}	
			}		
		}
	}
	private int Length(int x)
	{
		return (x+1+"").length();
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
