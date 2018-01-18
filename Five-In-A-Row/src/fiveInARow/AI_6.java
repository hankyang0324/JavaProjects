package fiveInARow;

//a more powerful version
public class AI_6 
{
	private int x,y;
	private int result;//win lose or continue
	private BoardAnalyse ba;
	private MaxMin mm;
	public void play(PiecesData pieces)
	{
		ba=new BoardAnalyse(pieces);
		mm=new MaxMin(ba,((pieces.getColorInt()-1)^1)+1,6);
		Point p=mm.GetPoint();
		x=p.getX();
		y=p.getY();
		result=pieces.addPiece(x,y);
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
