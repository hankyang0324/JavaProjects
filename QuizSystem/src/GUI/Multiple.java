package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Multiple extends JPanel
{
	JLabel question,correct,answer;
	JCheckBox option[];
	String getanswer;
	public Multiple(String q,String o1,String o2,String o3,String o4,String a)
	{
		getanswer="NULL";
		question=new JLabel(q);
		correct=new JLabel("correct");
		correct.setVisible(false);
		answer=new JLabel("The correct answer is "+a);
		answer.setVisible(false);
		option=new JCheckBox [4];
		option[0]=new JCheckBox("A: "+o1);
		option[1]=new JCheckBox("B: "+o2);
		option[2]=new JCheckBox("C: "+o3);
		option[3]=new JCheckBox("D: "+o4);
        JButton b=new JButton("Submit");
        b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				StringBuilder b=new StringBuilder();
				for(int i=0;i<4;i++)
				{
					if(option[i].isSelected())
					{
						char a=(char)(i+65);
						b.append(a);
					}
					getanswer=b.toString();
				}
				if(getanswer.equals(a))
				{
					correct.setVisible(true);
					answer.setVisible(false);
				}
				else
				{
					correct.setVisible(false);
					answer.setVisible(true);					
				}
			}});
		setLayout(new GridLayout(8,1));
		add(question);
		for(int i=0;i<4;i++)
			add(option[i]);
		add(b);
		add(correct);
		add(answer);
	}
	public String getAnswer()
	{
		return getanswer;
	}	
}