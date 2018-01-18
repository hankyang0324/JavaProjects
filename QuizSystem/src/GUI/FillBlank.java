package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FillBlank extends JPanel
{
	JLabel question,correct,answer;
	String getanswer;
	FillBlank(String q,String a)
	{
		getanswer="NULL";
		question=new JLabel(q);
		correct=new JLabel("correct");
		correct.setVisible(false);
		answer=new JLabel("The correct answer is "+a);
		answer.setVisible(false);
		JTextArea t=new JTextArea(0,1);
		JButton b=new JButton("Submit");
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getanswer=t.getText();
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
		setLayout(new GridLayout(5,1));
		add(question);
		add(t);
		add(b);
		add(correct);
		add(answer);
	}
	public String getAnswer()
	{
		return getanswer;
	}
}
