package 学生管理系统;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
public class Stuadd extends JDialog implements ActionListener {
	JLabel bq1,bq2,bq3,bq4,bq5,bq6;
	JTextField wbk1,wbk2,wbk3,wbk4,wbk5,wbk6;
	JButton an1,an2;
	JPanel mb1,mb2,mb3,mb4;
	
	public Stuadd(Frame fck,String ckm,Boolean msck)
	   {
		   super(fck,ckm,msck);
		   bq1=new JLabel("               学号      ");
		   bq2=new JLabel("               姓名      ");
		   bq3=new JLabel("               性别     ");
		   bq4=new JLabel("               年龄     ");
		   bq5=new JLabel("               籍贯     ");
		   bq6=new JLabel("               专业     ");
		   
		   wbk1=new JTextField(5);
		   wbk2=new JTextField(5);
		   wbk3=new JTextField(5);
		   wbk4=new JTextField(5);
		   wbk5=new JTextField(5);
		   wbk6=new JTextField(5);
		   
		   an1=new JButton("添加");
		   an1.addActionListener(this);
		   an1.setActionCommand("add2");
		   an2=new JButton("取消");
		   an2.addActionListener(this);
		   an2.setActionCommand("cancel");
		   
		   mb1=new JPanel();
		   mb2=new JPanel();
		   mb3=new JPanel(); 
		   mb4=new JPanel(); 
		   
		   mb1.setLayout(new GridLayout(6,1));
		   mb2.setLayout(new GridLayout(6,1));
		   
		   mb1.add(bq1);  mb1.add(bq2);  mb1.add(bq3);
		   mb1.add(bq4);  mb1.add(bq5);  mb1.add(bq6);
		   
		   mb2.add(wbk1);  mb2.add(wbk2);  mb2.add(wbk3);
		   mb2.add(wbk4);  mb2.add(wbk5);  mb2.add(wbk6);
		   
		   mb3.add(an1);  mb3.add(an2);
		   
		   this.add(mb1,BorderLayout.WEST);
		   this.add(mb2);
		   this.add(mb3,BorderLayout.SOUTH);
		   this.add(mb4,BorderLayout.EAST);
		   
		   this.setSize(450,350);
		   this.setLocation(710,281);
		   this.setResizable(false);
		   //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   this.setVisible(true);
	   }
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("add2"))
		{
			PreparedStatement ps=null;
			Connection ct=null;
			ResultSet rs=null;
			
			try
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=student","sa","gzx254052834843");
				String ss="insert into xuesheng values(?,?,?,?,?,?)";
				ps=ct.prepareStatement(ss);
				ps.setString(1,wbk1.getText());
				ps.setString(2,wbk2.getText());
				ps.setString(3,wbk3.getText());
				ps.setString(4,wbk4.getText());
				ps.setString(5,wbk5.getText());
				ps.setString(6,wbk6.getText());
				rs=ps.executeQuery();
				
				
			}catch(Exception e2){}
			 finally
			    {
			    	try {
			    		if(rs!=null)
						{
							rs.close();
						}
			    		if(ps!=null)
						{
							ps.close();
						}
						if(ct!=null)
						{
							ct.close();
						}
						
					} catch (Exception e3){}		
			    }
			this.dispose();
		}
		if(e.getActionCommand().equals("cancel"))
		{
			this.dispose();
		}
	}
}
