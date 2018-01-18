package fiveInARow;

import java.util.Iterator;
import java.util.LinkedList;

//consider the situation of lines with a blank space
//optimize the offense strategy
public class BoardAnalyse 
{
	private int x,y;
	private int [][]spot;//state of the board, 0 no piece here, 1 black, 2 white
	private int [][]blackScore;//score of black piece on every blank spot
	private int [][]whiteScore;//score of white piece on every blank spot
	private int [][]NextblackScore;
	private int [][]NextwhiteScore;
	private int [][][][]LineLength;//(y,x,direction,color),if place a piece in this spot(y,x), length of the queue of the pieces in this direction and color
	private int [][][][]SpaceLineLength;
	private int [][]Pspot;//possible spot for next step
	private int color;
	private PiecesData pieces;
	private BoardData bd;
	public BoardAnalyse(PiecesData pieces)
	{
		this.pieces=pieces;
		this.bd=new BoardData();
		spot=new int [15][15];
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++)
			{
				spot[i][j]=pieces.getPiece(j,i);
				bd.SetSpot(j, i, spot[i][j]);
			}
		LineLength=new int [15][15][8][2];//y,x,direction,color
		SpaceLineLength=new int [15][15][8][2];
		blackScore=new int [15][15];
		whiteScore=new int [15][15];
		NextblackScore=new int [15][15];
		NextwhiteScore=new int [15][15];
		Pspot=new int [15][15];
		color=pieces.getColorInt();//1 black, 2 white
	    PossibleSpot();
	}
	
	public void LineLength()
	{
		int count;
		int tx,ty;
		int [] dx= {0,1,1,1,0,-1,-1,-1};
		int [] dy= {-1,-1,0,1,1,1,0,-1};//eight direction
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if(spot[i][j]==1)// is it a black spot?
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
					}					
				}
				if(spot[i][j]==2)
				{
					for(int k=0;k<8;k++)
					{
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
				if(spot[i][j]==1)// is it a black spot?
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
							count=6;
					    SpaceLineLength[i][j][k][0]=count;//get the length of the black queue in this direction k
					}
				}
				if(spot[i][j]==2)
				{
					for(int k=0;k<8;k++)
					{						    
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
							count=6;
						SpaceLineLength[i][j][k][1]=count;//get the length of the white queue in this direction k
					}
				}
			}
		}		
	}
	
	public void PossibleSpot()
	{
		int [] dx= {0,1,1,1,0,-1,-1,-1};
		int [] dy= {-1,-1,0,1,1,1,0,-1};//eight direction 
		int tx=0,ty=0;
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++)
				Pspot[i][j]=0;
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if(spot[i][j]==0)
					continue;
				Pspot[i][j]=3;
				for(int k=0;k<8;k++)
				{
				    ty=i;
					tx=j;
					ty+=dy[k];
					tx+=dx[k];
					if(ty>14)
						ty=14;
					if(ty<0)
						ty=0;
					if(tx>14)
						tx=14;
					if(tx<0)
						tx=0;
					if(spot[ty][tx]==0&&Pspot[ty][tx]<3)
					{
						Pspot[ty][tx]=2;
						continue;
					}
				}
				for(int k=0;k<8;k++)
				{
				    ty=i;
					tx=j;
					ty+=dy[k]*2;
					tx+=dx[k]*2;
					if(ty>14)
						ty=14;
					if(ty<0)
						ty=0;
					if(tx>14)
						tx=14;
					if(tx<0)
						tx=0;
					if(spot[ty][tx]==0&&Pspot[ty][tx]==0)
						Pspot[ty][tx]=1;
				}					
			}
		}
	}
	
	public void NextLineLength()//if place a piece in this spot(y,x), length of the queue of the pieces in this direction and color 
	{
		int count;
		int tx,ty;
		int [] dx= {0,1,1,1,0,-1,-1,-1};
		int [] dy= {-1,-1,0,1,1,1,0,-1};//eight direction
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if(Pspot[i][j]==1||Pspot[i][j]==2)// is it a possible spot?
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
				if(Pspot[i][j]==1||Pspot[i][j]==2)// is it a blank spot?
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
							count=6;
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
							count=6;
						SpaceLineLength[i][j][k][1]=count;//get the length of the white queue in this direction k
					}
				}
			}
		}
	}
	
	public void SpotScore()
	{
		int scoreB,scoreW;
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				blackScore[i][j]=0;
				whiteScore[i][j]=0;
				if(spot[i][j]==1)// is it a blank spot?
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
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]>=6||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]>=6)//blocked 4
						{
							scoreB+=40000;
							if(scoreB>=50000)//double blocked 4,1 step to win 
								scoreB+=10000000-50000;
							else if(scoreB>=41000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB+=1000000-41000;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==5)//blocked 4 pieces
						{
							scoreB+=15000;
							if(scoreB>=25000)//double blocked 4,1 step to win 
								scoreB+=10000000-25000;
							else if(scoreB>=16000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB+=1000000-16000;
						}
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==5||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==5)//blocked 4
						{
							scoreB+=10000;
							if(scoreB>=20000)//double blocked 4,1 step to win 
								scoreB+=10000000-20000;
							else if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB+=1000000-11000;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4)//unblocked 3 pieces
						{
							if(LineLength[i][j][k][0]%2==0)//not two sides blocked 4
							{
								scoreB+=1500;
								if(scoreB>=11500)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB+=1000000-1500;
								else if(scoreB>=2500)//two unblocked 3, 2 step to win 
									scoreB+=100000-2500;
							}							
						}
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==4)//unblocked 3
						{
							if(SpaceLineLength[i][j][k][0]%2!=0||SpaceLineLength[i][j][k+4][0]%2!=0)//two sides blocked 4 with a blank space
							{
								scoreB+=10000;
								if(scoreB>=20000)//double blocked 4,1 step to win 
									scoreB+=10000000-10000;
								else if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB+=1000000-11000;
							}
							else
							{
								scoreB+=1000;
								if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB+=1000000-11000;
								else if(scoreB>=2000)//two unblocked 3, 2 step to win 
									scoreB+=100000-2000;
							}
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==3)//blocked 3 pieces
							scoreB+=150;
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==3||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==3)//blocked 4
							scoreB+=100;
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==2)//unblocked 2 pieces
					    {         
					    	    if(LineLength[i][j][k][0]%2==0)//not two sides blocked 3
					    	    	    scoreB+=15;
					    }
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==2||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==2)//unblocked 2
						{
							if(SpaceLineLength[i][j][k][0]%2==0||SpaceLineLength[i][j][k+4][0]%2==0)
								scoreB+=10;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==1)//blocked 2 pieces
							scoreB+=2;
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==1||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==1)
							scoreB+=1;
					}
					blackScore[i][j]=scoreB;
				}
				if(spot[i][j]==2)
				{
					scoreW=0;
					for(int k=0;k<4;k++)//give score referring to the length of the queue containing this blank spot in 4 different directions, one side blocked situation is considered
					{
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]>=7)//5 pieces
							scoreW+=100000000;//win
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==6)//unblocked 4 pieces
						{
							if(LineLength[i][j][k][1]%2!=0)//two sides blocked 5
								scoreW+=100000000;//win
							else
								scoreW+=10000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]>=6||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]>=6)//blocked 4
						{
							scoreW+=40000;
							if(scoreW>=50000)//double blocked 4,1 step to win 
								scoreW+=10000000-50000;
							else if(scoreW>=41000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW+=1000000-41000;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==5)//blocked 4 pieces
						{
							scoreW+=15000;
							if(scoreW>=25000)//double blocked 4,1 step to win 
								scoreW+=10000000-25000;
							else if(scoreW>=16000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW+=1000000-16000;
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==5||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==5)//blocked 4
						{
							scoreW+=10000;
							if(scoreW>=20000)//double blocked 4,1 step to win 
								scoreW+=10000000-20000;
							else if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW+=1000000-11000;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4)//unblocked 3 pieces
						{
							if(LineLength[i][j][k][1]%2==0)//not two sides blocked 4
							{
								scoreW+=1500;
								if(scoreW>=11500)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW+=1000000-1500;
								else if(scoreW>=2500)//two unblocked 3, 2 step to win 
									scoreW+=100000-2500;
							}							
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==4)//unblocked 3
						{
							if(SpaceLineLength[i][j][k][1]%2!=0||SpaceLineLength[i][j][k+4][1]%2!=0)//two sides blocked 4 with a blank space
							{
								scoreW+=10000;
								if(scoreW>=20000)//double blocked 4,1 step to win 
									scoreW+=10000000-10000;
								else if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW+=1000000-11000;
							}
							else
							{
								scoreW+=1000;
								if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW+=1000000-11000;
								else if(scoreW>=2000)//two unblocked 3, 2 step to win 
									scoreW+=100000-2000;
							}
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==3)//blocked 3 pieces
							scoreW+=150;
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==3||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==3)//blocked 4
							scoreW+=100;
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==2)//unblocked 2 pieces
					    {         
					    	    if(LineLength[i][j][k][1]%2==0)//not two sides blocked 3
					    	    	    scoreW+=15;
					    }
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==2||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==2)//unblocked 2
						{
							if(SpaceLineLength[i][j][k][1]%2==0||SpaceLineLength[i][j][k+4][1]%2==0)
								scoreW+=10;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==1)//blocked 2 pieces
							scoreW+=2;
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==1||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==1)
							scoreW+=1;
					}
					whiteScore[i][j]=scoreW;					
				}
			}
		}
	}
	
	public void NextSpotScore()
	{
		int scoreB,scoreW;
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				NextblackScore[i][j]=0;
				NextwhiteScore[i][j]=0;
			}
		}
		for(int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if(Pspot[i][j]==1||Pspot[i][j]==2)// is it a possible spot?
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
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]>=6||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]>=6)//blocked 4
						{
							scoreB+=40000;
							if(scoreB>=50000)//double blocked 4,1 step to win 
								scoreB+=10000000-50000;
							else if(scoreB>=41000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB+=1000000-41000;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==5)//blocked 4 pieces
						{
							scoreB+=15000;
							if(scoreB>=25000)//double blocked 4,1 step to win 
								scoreB+=10000000-25000;
							else if(scoreB>=16000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB+=1000000-16000;
						}
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==5||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==5)//blocked 4
						{
							scoreB+=10000;
							if(scoreB>=20000)//double blocked 4,1 step to win 
								scoreB+=10000000-20000;
							else if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreB+=1000000-11000;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4)//unblocked 3 pieces
						{
							if(LineLength[i][j][k][0]%2==0)//not two sides blocked 4
							{
								scoreB+=1500;
								if(scoreB>=11500)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB+=1000000-1500;
								else if(scoreB>=2500)//two unblocked 3, 2 step to win 
									scoreB+=100000-2500;
							}							
						}
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==4||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==4)//unblocked 3
						{
							if(SpaceLineLength[i][j][k][0]%2!=0||SpaceLineLength[i][j][k+4][0]%2!=0)//two sides blocked 4 with a blank space
							{
								scoreB+=10000;
								if(scoreB>=20000)//double blocked 4,1 step to win 
									scoreB+=10000000-10000;
								else if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB+=1000000-11000;
							}
							else
							{
								scoreB+=1000;
								if(scoreB>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreB+=1000000-11000;
								else if(scoreB>=2000)//two unblocked 3, 2 step to win 
									scoreB+=100000-2000;
							}
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==3)//blocked 3 pieces
							scoreB+=150;
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==3||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==3)//blocked 4
							scoreB+=100;
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==2)//unblocked 2 pieces
					    {         
					    	    if(LineLength[i][j][k][0]%2==0)//not two sides blocked 3
					    	    	    scoreB+=15;
					    }
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==2||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==2)//unblocked 2
						{
							if(SpaceLineLength[i][j][k][0]%2==0||SpaceLineLength[i][j][k+4][0]%2==0)
								scoreB+=10;
						}
						else if(LineLength[i][j][k][0]+LineLength[i][j][k+4][0]==1)//blocked 2 pieces
							scoreB+=2;
						else if(SpaceLineLength[i][j][k][0]+LineLength[i][j][k+4][0]==1||SpaceLineLength[i][j][k+4][0]+LineLength[i][j][k][0]==1)
							scoreB+=1;
					}
					NextblackScore[i][j]=scoreB;
					scoreW=0;
					for(int k=0;k<4;k++)//give score referring to the length of the queue containing this blank spot in 4 different directions, one side blocked situation is considered
					{
						if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]>=7)//5 pieces
							scoreW+=100000000;//win
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==6)//unblocked 4 pieces
						{
							if(LineLength[i][j][k][1]%2!=0)//two sides blocked 5
								scoreW+=100000000;//win
							else
								scoreW+=10000000;//unblocked 4 (may be with 4 or unblocked 3), 1 step to win
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]>=6||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]>=6)//blocked 4
						{
							scoreW+=40000;
							if(scoreW>=50000)//double blocked 4,1 step to win 
								scoreW+=10000000-50000;
							else if(scoreW>=41000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW+=1000000-41000;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==5)//blocked 4 pieces
						{
							scoreW+=15000;
							if(scoreW>=25000)//double blocked 4,1 step to win 
								scoreW+=10000000-25000;
							else if(scoreW>=16000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW+=1000000-16000;
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==5||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==5)//blocked 4
						{
							scoreW+=10000;
							if(scoreW>=20000)//double blocked 4,1 step to win 
								scoreW+=10000000-20000;
							else if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
								scoreW+=1000000-11000;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4)//unblocked 3 pieces
						{
							if(LineLength[i][j][k][1]%2==0)//not two sides blocked 4
							{
								scoreW+=1500;
								if(scoreW>=11500)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW+=1000000-1500;
								else if(scoreW>=2500)//two unblocked 3, 2 step to win 
									scoreW+=100000-2500;
							}							
						}
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==4||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==4)//unblocked 3
						{
							if(SpaceLineLength[i][j][k][1]%2!=0||SpaceLineLength[i][j][k+4][1]%2!=0)//two sides blocked 4 with a blank space
							{
								scoreW+=10000;
								if(scoreW>=20000)//double blocked 4,1 step to win 
									scoreW+=10000000-10000;
								else if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW+=1000000-11000;
							}
							else
							{
								scoreW+=1000;
								if(scoreW>=11000)//one blocked 4 and one unblocked 3, 1.5 step to win 
									scoreW+=1000000-11000;
								else if(scoreW>=2000)//two unblocked 3, 2 step to win 
									scoreW+=100000-2000;
							}
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==3)//blocked 3 pieces
							scoreW+=150;
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==3||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==3)//blocked 4
							scoreW+=100;
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==2)//unblocked 2 pieces
					    {         
					    	    if(LineLength[i][j][k][1]%2==0)//not two sides blocked 3
					    	    	    scoreW+=15;
					    }
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==2||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==2)//unblocked 2
						{
							if(SpaceLineLength[i][j][k][1]%2==0||SpaceLineLength[i][j][k+4][1]%2==0)
								scoreW+=10;
						}
						else if(LineLength[i][j][k][1]+LineLength[i][j][k+4][1]==1)//blocked 2 pieces
							scoreW+=2;
						else if(SpaceLineLength[i][j][k][1]+LineLength[i][j][k+4][1]==1||SpaceLineLength[i][j][k+4][1]+LineLength[i][j][k][1]==1)
							scoreW+=1;
					}
					NextwhiteScore[i][j]=scoreW;					
				}
			}
		}
	}
	
	public int EvaluateBoard(int AI,int player)
	{
		int xb=0,yb=0;
		int xw=0,yw=0;
		if(AI==1)
		{
			for(int i=0;i<15;i++)//find the highest score for the best spot of the next piece
			{
				for(int j=0;j<15;j++)
				{
					if(whiteScore[yw][xw]<whiteScore[i][j])
					{
						yw=i;
						xw=j;
					}
					if(blackScore[yb][xb]<blackScore[i][j])
					{
						yb=i;
						xb=j;
					} 
				}
			}
			if(player==2) //player's turn
			{
				if(whiteScore[yw][xw]>=1000000)
					whiteScore[yw][xw]=10000000;
				if(whiteScore[yw][xw]>=10000&&whiteScore[yw][xw]<50000)
					whiteScore[yw][xw]=10000000;
				if(blackScore[yb][xb]>=100000000)
					return 100000000;
				else if(whiteScore[yw][xw]>=10000000)
					return -100000000;
				else if(blackScore[yb][xb]>=10000000)
					return 100000000;
				
				else
					return blackScore[yb][xb]-whiteScore[yw][xw];
			}
			else //AI's turn
			{
				if(blackScore[yb][xb]>=1000000)
					blackScore[yb][xb]=10000000;
				if(blackScore[yb][xb]>=10000&&blackScore[yb][xb]<50000)
					blackScore[yb][xb]=10000000;
				if(whiteScore[yw][xw]>=100000000)
					return -100000000;
				else if(blackScore[yb][xb]>=10000000)
					return 100000000;
				else if(whiteScore[yw][xw]>=10000000)
					return -100000000;	
				else
					return blackScore[yb][xb]-whiteScore[yw][xw];
			}
		}
		else
		{			
			for(int i=0;i<15;i++)
			{
				for(int j=0;j<15;j++)
				{		
					if(blackScore[yb][xb]<blackScore[i][j])
					{
						yb=i;
						xb=j;
					}
					if(whiteScore[yw][xw]<whiteScore[i][j])
					{
						yw=i;
						xw=j;
					}
				}
			}
			if(player==2) //AI's turn
			{
				if(whiteScore[yw][xw]>=1000000)
					whiteScore[yw][xw]=10000000;
				if(whiteScore[yw][xw]>=10000&&whiteScore[yw][xw]<50000)
					whiteScore[yw][xw]=10000000;
				if(blackScore[yb][xb]>=100000000)
					return -100000000;
				else if(whiteScore[yw][xw]>=10000000)
					return 100000000;
				else if(blackScore[yb][xb]>=10000000)
					return -100000000;
				else
					return whiteScore[yw][xw]-blackScore[yb][xb];
			}
			else //player's turn
			{
				if(blackScore[yb][xb]>=1000000)
					blackScore[yb][xb]=10000000;
				if(blackScore[yb][xb]>=10000&&blackScore[yb][xb]<50000)
					blackScore[yb][xb]=10000000;
				if(whiteScore[yw][xw]>=100000000)
					return 100000000;
				else if(blackScore[yb][xb]>=10000000)
					return -100000000;
				else if(whiteScore[yw][xw]>=10000000)
					return 100000000;	
				else
					return whiteScore[yw][xw]-blackScore[yb][xb];
			}
		}
	}
	
	public LinkedList<Point> PossibleSpotOrder(int player)//Heuristic search
	{
		PossibleSpot();
		NextLineLength();
		NextSpotScore();
		LinkedList<Point> five=new LinkedList<Point> ();
		LinkedList<Point> four=new LinkedList<Point> ();
		LinkedList<Point> doublethree=new LinkedList<Point> ();
		LinkedList<Point> three=new LinkedList<Point> ();
		LinkedList<Point> PSpot=new LinkedList<Point> ();
		if(player==1)
		{
			for(int i=0;i<15;i++)
			{
				for(int j=0;j<15;j++)
				{
					if(NextblackScore[i][j]>=100000000)
					{
						if(Pspot[i][j]==2)
							five.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							five.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=100000000)
					{
						if(Pspot[i][j]==2)
							five.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							five.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=1000000)
					{
						if(Pspot[i][j]==2)
							four.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=1000000)
					{
						if(Pspot[i][j]==2)
							four.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=40000&&NextblackScore[i][j]<50000)
					{
						if(Pspot[i][j]==2)
							four.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=40000&&NextwhiteScore[i][j]<50000)
					{
						if(Pspot[i][j]==2)
							four.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=100000)
					{
						if(Pspot[i][j]==2)
							doublethree.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							doublethree.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=100000)
					{
						if(Pspot[i][j]==2)
							doublethree.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							doublethree.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=1000)
					{
						if(Pspot[i][j]==2)
							three.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							three.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=1000)
					{
						if(Pspot[i][j]==2)
							three.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							three.addLast(new Point(i,j));
					}
					else 
					{
						if(Pspot[i][j]==2)
							PSpot.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							PSpot.addLast(new Point(i,j));
					}				
				}
			}
		}
		if(player==2)
		{
			for(int i=0;i<15;i++)
			{
				for(int j=0;j<15;j++)
				{
					if(NextwhiteScore[i][j]>=100000000)
					{
						if(Pspot[i][j]==2)
							five.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							five.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=100000000)
					{
						if(Pspot[i][j]==2)
							five.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							five.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=1000000)
					{
						if(Pspot[i][j]==2)
							four.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=1000000)
					{
						if(Pspot[i][j]==2)
							four.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=40000&&NextwhiteScore[i][j]<50000)
					{
						if(Pspot[i][j]==2)
							four.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=40000&&NextblackScore[i][j]<50000)
					{
						if(Pspot[i][j]==2)
							four.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							four.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=100000)
					{
						if(Pspot[i][j]==2)
							doublethree.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							doublethree.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=100000)
					{
						if(Pspot[i][j]==2)
							doublethree.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							doublethree.addLast(new Point(i,j));
					}
					else if(NextwhiteScore[i][j]>=1000)
					{
						if(Pspot[i][j]==2)
							three.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							three.addLast(new Point(i,j));
					}
					else if(NextblackScore[i][j]>=1000)
					{
						if(Pspot[i][j]==2)
							three.addLast(new Point(i,j));
						if(Pspot[i][j]==1)
							three.addLast(new Point(i,j));
					}
					else 
					{
						if(Pspot[i][j]==2)
							PSpot.addFirst(new Point(i,j));
						if(Pspot[i][j]==1)
							PSpot.addLast(new Point(i,j));
					}				
				}
			}
		}
		if(five.size()>0)
			return five;
		if(four.size()>0)
			return four;
		if(doublethree.size()>0)
		{
			for(Iterator<Point> it=three.iterator();it.hasNext();)
				doublethree.add(it.next());
			return doublethree;
		}
		if(three.size()>0)
		{
			for(Iterator<Point> it=PSpot.iterator();it.hasNext();)
				three.add(it.next());
			return three;
		}
		return PSpot;
	}	
	
	public LinkedList<Point> PossibleSpotOrderCombo(int AI,int player) //Heuristic search, for continuous offense through line of 3 to 5
	{
		PossibleSpot();
		NextLineLength();
		NextSpotScore();
		LinkedList<Point> five=new LinkedList<Point> ();
		LinkedList<Point> four=new LinkedList<Point> ();
		LinkedList<Point> three=new LinkedList<Point> ();
		if(AI==player)
		{
			if(AI==1)
			{
				for(int i=0;i<15;i++)
				{
					for(int j=0;j<15;j++)
					{
						if(NextblackScore[i][j]>=100000000)
							five.addLast(new Point(i,j));
						else if(NextblackScore[i][j]>=10000000)
							four.addLast(new Point(i,j));
						else if(NextblackScore[i][j]>=100000)
							three.addFirst(new Point(i,j));
						else if(NextblackScore[i][j]>=1000)
							three.addLast(new Point(i,j));
					}
				}
			}
			if(AI==2)
			{
				for(int i=0;i<15;i++)
				{
					for(int j=0;j<15;j++)
					{
						if(NextwhiteScore[i][j]>=100000000)
							five.addLast(new Point(i,j));
						else if(NextwhiteScore[i][j]>=10000000)
							four.addLast(new Point(i,j));
						else if(NextwhiteScore[i][j]>=100000)
							three.addFirst(new Point(i,j));
						else if(NextwhiteScore[i][j]>=1000)
							three.addLast(new Point(i,j));
					}
				}
			}
		}
		if(AI!=player)
		{
			for(int i=0;i<15;i++)
			{
				for(int j=0;j<15;j++)
				{
					if(NextblackScore[i][j]>=100000000)
						five.addLast(new Point(i,j));
					else if(NextwhiteScore[i][j]>=100000000)
						five.addFirst(new Point(i,j));
					else if(NextblackScore[i][j]>=1000000)
						four.addLast(new Point(i,j));
					else if(NextwhiteScore[i][j]>=1000000)
						four.addLast(new Point(i,j));
					else if(NextblackScore[i][j]>=100000)
						three.addFirst(new Point(i,j));
					else if(NextwhiteScore[i][j]>=100000)
						three.addFirst(new Point(i,j));
					else if(NextblackScore[i][j]>=1000)
						three.addLast(new Point(i,j));
					else if(NextwhiteScore[i][j]>=1000)
						three.addLast(new Point(i,j));	
					if(player==1)
						if(NextblackScore[i][j]>=10000&&NextblackScore[i][j]<50000)
							four.addLast(new Point(i,j));
					if(player==2)
						if(NextwhiteScore[i][j]>=10000&&NextwhiteScore[i][j]<50000)
							four.addLast(new Point(i,j));						
				}
			}
		}
		if(five.size()>0)
			return five;
		if(four.size()>0)
			return four;
		else
			return three;
	}
	
	public int GetPlayerSpotScore(int AI,Point p)
	{
		if(AI==1)
			return NextwhiteScore[p.getY()][p.getX()];
		else
			return NextblackScore[p.getY()][p.getX()];
	}
	
	public int GetAISpotScore(int AI,Point p)
	{
		if(AI==2)
			return NextwhiteScore[p.getY()][p.getX()];
		else
			return NextblackScore[p.getY()][p.getX()];
	}
	
	public int GetPossibleSpot(int y,int x)
	{
		return Pspot[y][x];
	}
	
	public void addPiece(int x,int y,int color)
	{
		bd.SetSpot(x, y, color);
		spot[y][x]=color;
	}
	
	public void removePiece(int x,int y)
	{
		bd.SetSpot(x, y, 0);
		spot[y][x]=0;
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

