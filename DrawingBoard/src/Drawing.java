import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;

public class Drawing extends JFrame
{
	private DrawingArea d;
	public Drawing()
	{
		super("Drawing");
		setSize(800,600);
		Container c= getContentPane();
		JPanel p=new JPanel();
		p.setBackground(Color.LIGHT_GRAY);		
		p.setLayout(new GridLayout(8,1));
		d=new DrawingArea();
		JButton b=new JButton("Pen");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.setPen(true);
				d.setEraser(false);
				// TODO Auto-generated method stub
			}
		});
		p.add(b);
		b=new JButton("Line");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.setMode(new LineMode());
				d.setEraser(false);
				d.setPen(false);
				// TODO Auto-generated method stub
				
			}
		});
		p.add(b);
		b=new JButton("Circle");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.setMode(new CircleMode());
				d.setEraser(false);
				d.setPen(false);
				// TODO Auto-generated method stub
			}
		});
		p.add(b);
		b=new JButton("Rect");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.setMode(new RectMode());
				d.setEraser(false);
				d.setPen(false);
				// TODO Auto-generated method stub
				
			}
		});
		p.add(b);
		b=new JButton("Fill");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.setFill(true);
				// TODO Auto-generated method stub
			}
		});
		p.add(b);
		b=new JButton("Frame");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.setFill(false);
				// TODO Auto-generated method stub
			}
		});
		p.add(b);
		b=new JButton("Eraser");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.setEraser(true);
				d.setPen(false);
				// TODO Auto-generated method stub
			}
		});
		p.add(b);
		b=new JButton("Clear");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.clear();
				// TODO Auto-generated method stub	
			}
		});
		p.add(b);
		
		ColorChooser cl=new ColorChooser();
		StrokeWeight s1=new StrokeWeight();
		c.add(s1,BorderLayout.NORTH);
		c.add(cl,BorderLayout.EAST);
		c.add(p,BorderLayout.WEST);
		c.add(d,BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class ColorChooser extends JPanel
	{
		private JScrollBar r,g,b;
		private JPanel p;
		public ColorChooser()
		{
			r=new JScrollBar(JScrollBar.VERTICAL,0,15,0,270);
			g=new JScrollBar(JScrollBar.VERTICAL,0,15,0,270);
			b=new JScrollBar(JScrollBar.VERTICAL,0,15,0,270);
			p=new JPanel();
			p.setBackground(Color.BLACK);
			r.addAdjustmentListener(new ColorListener());
			g.addAdjustmentListener(new ColorListener());
			b.addAdjustmentListener(new ColorListener());
		    setLayout(new GridLayout(1,4));
		    add(p);
			add(r);
			add(g);
			add(b);
		}
		class ColorListener implements AdjustmentListener
		{
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) 
			{
				Color c=new Color(r.getValue(),g.getValue(),b.getValue());
				p.setBackground(c);
				d.setColor(c);
				// TODO Auto-generated method stub		
			}
		}	
	}

	class StrokeWeight extends JPanel
	{
		private JSlider r;
		private DrawingArea p;
		private Rect re;
		public StrokeWeight()
		{
			
			r=new JSlider(SwingConstants.HORIZONTAL,0,50,0);
			p=new DrawingArea();
			p.setBackground(Color.LIGHT_GRAY);
			re=new Rect(Color.LIGHT_GRAY,p.getWidth()/2+p.getWidth(),0,true,10,20);
			r.addChangeListener(new ChangeListener()
			{

				@Override
				public void stateChanged(ChangeEvent e)
				{
					d.setStrokeWeight(r.getValue());	
					Graphics g=getGraphics();
					re.draw(g);
					g.setXORMode(Color.BLACK);
					re=new Rect(Color.LIGHT_GRAY,p.getWidth()/2+p.getWidth()-r.getValue(),0,true,r.getValue()*2,30);
					re.draw(g);				
				}
			
			});
		    setLayout(new GridLayout(1,2));
		    add(r);
			add(p);
		}	
	}
	
	public static void main(String[] args)
	{
		new Drawing();
	}
		
}
