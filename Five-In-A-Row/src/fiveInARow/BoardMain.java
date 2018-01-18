package fiveInARow;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BoardMain extends JFrame
{
	private Drawing d;
	public BoardMain()
	{
		super("Five In A Row");
		setSize(740,660);
		setBackground(Color.WHITE);
		Container c=getContentPane();
		Drawing d=new Drawing();
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(4,1));
		JButton b=new JButton("You First");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.SetMode(1);
				d.ReStart();
			}});
		p.add(b);
		b=new JButton("AI First");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.SetMode(2);
				d.ReStart();
			}});
		p.add(b);
		b=new JButton("PVP");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.SetMode(0);
				d.ReStart();
			}});
		p.add(b);
		b=new JButton("Undo");
		b.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				d.Retract();
			}});
		p.add(b);
		c.add(p,BorderLayout.WEST);
		c.add(d,BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args)
	{
		new BoardMain();		
	}
}
