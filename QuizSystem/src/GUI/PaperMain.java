package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PaperMain extends JFrame
{
	public PaperMain()
	{
		setSize(600,800);
		Container c=getContentPane();
		JPanel p=new JPanel();
		p.setLayout(new GridLayout(3,1));
		p.add(new Single("1+1=","2","3","4","5","A"));
		p.add(new Multiple("1+1<","2","3","4","5","BCD"));
		p.add(new FillBlank("1+1=","2"));
		JScrollPane s=new JScrollPane(p);
		c.add(s);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args )
	{
		new PaperMain();
	}
}
