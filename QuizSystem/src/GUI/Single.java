package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Single extends JPanel
{
	JLabel question,correct,answer;
	JRadioButton option[];
	String getanswer;
	public Single(String q,String o1,String o2,String o3,String o4,String a)
	{
		getanswer="NULL";
		question=new JLabel(q);
		correct=new JLabel("correct");
		correct.setVisible(false);
		answer=new JLabel("The correct answer is "+a);
		answer.setVisible(false);
		option=new JRadioButton [4];
		option[0]=new JRadioButton("A: "+o1);
		option[1]=new JRadioButton("B: "+o2);
		option[2]=new JRadioButton("C: "+o3);
		option[3]=new JRadioButton("D: "+o4);
		ButtonGroup group=new ButtonGroup();  
		for(int i=0;i<4;i++)
			group.add(option[i]);
        JButton b=new JButton("Submit");
        b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<4;i++)
				{
					if(option[i].isSelected())
					{
						char c=(char)(i+65);
						getanswer=String.valueOf(c);
						break;
					}					
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

