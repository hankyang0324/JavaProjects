import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame
{
	private double num1=0,num2=0;
	private int operator=0;
	private double neg1=1,neg2=1;
	private double dec1=1,dec2=1;
	private double ans=0;
	private boolean numPressed=false;
	private boolean optPressed=false;
	private boolean negPressed=false;
	private boolean dotPressed=false;
	private boolean ansPressed=false;
	private JTextArea t;
	public Calculator()
	{
		super("CALCULATOR");
		setSize(400,500);
		Container c=getContentPane();
		JPanel p=new JPanel();
		p.setBackground(Color.DARK_GRAY);
		Font f=new Font("Arial",Font.PLAIN,48);
		t=new JTextArea(2,0);
		t.setEditable(false);
		t.setFont(f);
		t.setBackground(Color.DARK_GRAY);
		t.setForeground(Color.LIGHT_GRAY);
		p.setLayout((new GridLayout(5,4,3,3)));
		f=new Font("Arial",Font.BOLD,32);
	    JButton j0=new JButton("0");
	    j0.setFont(f);
	    j0.setForeground(Color.ORANGE);
	    j0.addActionListener(new numAction(0));  
	    JButton j1=new JButton("1");
	    j1.setFont(f);
	    j1.setForeground(Color.ORANGE);
	    j1.addActionListener(new numAction(1));	    
	    JButton j2=new JButton("2");
	    j2.setFont(f);
	    j2.setForeground(Color.ORANGE);
	    j2.addActionListener(new numAction(2));
	    JButton j3=new JButton("3");
	    j3.setFont(f);
	    j3.setForeground(Color.ORANGE);
	    j3.addActionListener(new numAction(3));
	    JButton j4=new JButton("4");
	    j4.setFont(f);
	    j4.setForeground(Color.ORANGE);
	    j4.addActionListener(new numAction(4));	    
	    JButton j5=new JButton("5");
	    j5.setFont(f);
	    j5.setForeground(Color.ORANGE);
	    j5.addActionListener(new numAction(5));	    
	    JButton j6=new JButton("6");
	    j6.setFont(f);
	    j6.setForeground(Color.ORANGE);
	    j6.addActionListener(new numAction(6));	    
        JButton j7=new JButton("7");
        j7.setFont(f);
        j7.setForeground(Color.ORANGE);
        j7.addActionListener(new numAction(7));	    
	    JButton j8=new JButton("8");
	    j8.setFont(f);
	    j8.setForeground(Color.ORANGE);
	    j8.addActionListener(new numAction(8));	    
	    JButton j9=new JButton("9");
	    j9.setFont(f);
	    j9.setForeground(Color.ORANGE);
	    j9.addActionListener(new numAction(9));	    
	    JButton jadd=new JButton("+");
	    jadd.setFont(f);
	    jadd.setForeground(Color.GRAY);
	    jadd.addActionListener(new operatorAction(1));	    
	    JButton jsub=new JButton("－");
	    jsub.setFont(f);
	    jsub.setForeground(Color.GRAY);
	    jsub.addActionListener(new operatorAction(2));
	    JButton jmul=new JButton("×");
	    jmul.setFont(f);
	    jmul.setForeground(Color.GRAY);
	    jmul.addActionListener(new operatorAction(3));    
	    JButton jdiv=new JButton("÷");
	    jdiv.setFont(f);
	    jdiv.setForeground(Color.GRAY);
	    jdiv.addActionListener(new operatorAction(4));	    
	    JButton jequ=new JButton("=");
	    jequ.setFont(f);
	    jequ.setForeground(Color.BLACK);
	    jequ.addActionListener(new operatorAction(5));	
	    
	    JButton jcle=new JButton("C");
	    jcle.setFont(f);
	    jcle.setForeground(Color.LIGHT_GRAY);
	    jcle.addActionListener(new ActionListener()
		{
	      	@Override
			public void actionPerformed(ActionEvent e) 
			{
				t.setText("");
				if(operator==0||operator==5)//输出结果后或开始新运算前操作符清零，num1清零待操作
				{
					operator=0;
					num1=0;
					neg1=dec1=1;
				}
				else//运算进行一半时，num2清零待操作
				{
					num2=0;
					neg2=dec2=1;
				}
				numPressed=false;
				negPressed=false;
				dotPressed=false;
				ansPressed=false;
			}      	
		});
	    
	    JButton jacl=new JButton("AC");
	    jacl.setFont(f);
	    jacl.setForeground(Color.RED);
	    jacl.addActionListener(new ActionListener() 
	    {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				t.setText("");
				num1=num2=0;
				neg1=neg2=dec1=dec2=1;
				ans=0;
				operator=0;
				optPressed=false;
				numPressed=false;
				negPressed=false;
				dotPressed=false;
				ansPressed=false;
			}    	
	    });
	    
	    JButton jans=new JButton("Ans");
	    jans.setFont(f);
	    jans.setForeground(Color.BLUE);
	    jans.addActionListener(new ActionListener() 
	    {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(ansPressed==false&&numPressed==false&&dotPressed==false)//重复按该键，按下数字后，按下小数点后无效
				{
					if(operator!=5||negPressed==false)//输出结果并取负后不可操作
					{
						ansPressed=true;
						if(optPressed==true)//按完运算符后按ans，将显示清空，num2=ans
						{
							num2=ans;
							t.setText("");
							optPressed=false;
						}
						if(1<=operator&&operator<=4&&negPressed==true)//按完运算符后按下“-”+“ans”的情况
						{
							num2=ans;
							negPressed=false;
						}
						if(operator==5)//按完等于号后按数字，运算符清零，num1清零
							operator=0;
						if(operator==0)//没按过运算符前num1
							num1=ans;		
						if(ans-(int)ans==0)//整数显示整型
							t.append((int)ans+"");
						else
							t.append((float)ans+"");//这个精度真的是太傻逼了 BigDecimal
					}
				}	
			}
		});
	    
	    JButton jneg=new JButton("-/+");
	    jneg.setFont(f);
	    jneg.setForeground(Color.GRAY);
	    jneg.addActionListener(new ActionListener() 
	    {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(negPressed==false)//重复按下无效
				{
					negPressed=true;
					if(optPressed==true)//按完运算符后按负号，将显示清空，num2清零待输入
					{
						num2=0;
						t.setText("");
						optPressed=false;
					}
					if(operator==5)//按完等于号后按负号，结果取负
					{
						num1=-ans;
						if(num1-(int)num1==0)//整数显示整型
							t.setText(-(int)num1+"");
						else
							t.setText(-(float)num1+"");//这个精度真的是太傻逼了 BigDecimal
					}
					if(operator==0)//没按过运算符前num1
						neg1=-1;
					else//按过运算符后num2
						neg2=-1;
					t.insert("-", 0);
				}				
			}
		});
	    
	    JButton jdot=new JButton(".");
	    jdot.setFont(f);
	    jdot.setForeground(Color.ORANGE);
	    jdot.addActionListener(new ActionListener() 
	    {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(ansPressed==false)//按下ans后不可操作
				{
					if(operator!=5||negPressed==false)//输出结果并取负后不可操作
					{
						if(dotPressed==false)//重复按下无效
						{
							dotPressed=true;
							if(optPressed==true)//按完运算符后按数字，将显示清空，num2清零
							{
								num2=0;
								t.setText("");
								optPressed=false;
							}
							if(operator==5)//按完等于号后按数字，运算符清零，num1清零
							{
								operator=0;
								num1=0;
							}
							if(numPressed==false&&negPressed==false)//"." -> "0."
								t.setText("0");
							if(numPressed==false&&negPressed==true)//"-" -> "-0." 
								t.append("0");
							t.append(".");
						}
					}
				}	
			}
		});
	    
	    p.add(jacl);
	    p.add(jcle);
	    p.add(jneg);
	    p.add(jdiv);
	    p.add(j7);
	    p.add(j8);
	    p.add(j9);
	    p.add(jmul);
	    p.add(j4);
	    p.add(j5);
	    p.add(j6);
	    p.add(jsub);
	    p.add(j1);
	    p.add(j2);
	    p.add(j3);
	    p.add(jadd);
	    p.add(j0);
	    p.add(jdot);    
	    p.add(jans);
	    p.add(jequ);
	    	    
		c.add(p, BorderLayout.CENTER);	
		c.add(t,BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	class numAction implements ActionListener
	{
		private int keyNum;
		public numAction(int n)
		{
			keyNum=n;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(ansPressed==false)//按下ans后不可操作数字
			{
				if(operator!=5||negPressed==false)//输出结果并取负后不可操作数字
				{
					numPressed=true;
					if(optPressed==true)//按完运算符后按数字，将显示清空，num2清零
					{
						num2=0;
						t.setText("");
						optPressed=false;
					}
					if(operator==5)//按完等于号后按数字，运算符清零，num1清零
					{
						operator=0;
						num1=0;
					}
					if(operator==0)//没按过运算符前num1
					{
						num1=num1*10+keyNum;
						if(dotPressed==true)
							dec1*=10;			
					}
					else//按过运算符后num2
					{
						num2=num2*10+keyNum;
						if(dotPressed==true)
							dec2*=10;			
					}
					t.append(keyNum+"");
				}
			}			

		}
	}
	
	class operatorAction implements ActionListener
	{
		private int thisOperator;
		public operatorAction(int o)
		{
			thisOperator=o;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			optPressed=true;
			numPressed=false;
			negPressed=false;
			dotPressed=false;
			ansPressed=false;
			num1*=neg1/dec1;
			num2*=neg2/dec2;
			neg1=neg2=dec1=dec2=1;
			switch(operator)//前一次的运算符
			{
			case 0:break;
			case 1:num1+=num2;break;
			case 2:num1-=num2;break;
			case 3:num1*=num2;break;
			case 4://除以0错误
			{
				if(num2==0)
					break;
				else
					num1/=num2;
				break;
			}
			case 5:break;
			}
			if(operator==4&&num2==0)//除以0错误
			{
				t.setText("ERROR");
				ans=0;
			}
			else
			{
				if(num1-(int)num1==0)//整数显示整型
					t.setText((int)num1+"");
				else
					t.setText((float)num1+"");//这个精度真的是太傻逼了 BigDecimal
			}
			if(thisOperator==5)//输出结果后保存在ans
				ans=num1;
			if(thisOperator==3||thisOperator==4)//按完乘除将num2置1，消除多次按乘除键bug
				num2=1;
			else
				num2=0;//按完加减等于将num2置0，消除多次按加减等于键bug
			operator=thisOperator;
		}		
	}
	
	public static void main(String[] args)
	{
		new Calculator();
	}
}
