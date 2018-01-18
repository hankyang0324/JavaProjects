import java.awt.*;
import java.awt.event.*;
import java.awt.image.ColorModel;

import javax.swing.*;

public class ColorChooser extends JPanel
{
	private JScrollBar r,g,b;
	private JPanel p;
	public ColorChooser()
	{
		setSize(100,255);
		r=new JScrollBar(JScrollBar.VERTICAL,0,16,0,255);
		g=new JScrollBar(JScrollBar.VERTICAL,0,16,0,255);
		b=new JScrollBar(JScrollBar.VERTICAL,0,16,0,255);
		p=new JPanel();
		r.addAdjustmentListener(new ColorListener());
		g.addAdjustmentListener(new ColorListener());
		b.addAdjustmentListener(new ColorListener());
	    setLayout(new GridLayout(1,4));
	    add(p);
		add(r);
		add(g);
		add(b);
	}
//	public Color getColor()
//	{
//		return color;		
//	}
	class ColorListener implements AdjustmentListener
	{
		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) 
		{
			Color c=new Color(r.getValue(),g.getValue(),b.getValue());
			p.setBackground(c);
			// TODO Auto-generated method stub		
		}
	}	
}
