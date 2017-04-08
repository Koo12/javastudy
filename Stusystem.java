package 学生管理系统;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
public class Stusystem extends JFrame implements ActionListener{
	JPanel mb1,mb2;
	JLabel bq1;
	JTextField wbk1;
	JButton an1,an2,an3,an4;
	JTable bg1;	
	JScrollPane gd1;

	Vector ziduan,jilu;
	PreparedStatement ps=null;
	Connection ct=null;
	ResultSet rs=null;

	public static void main(String[] args)
	{
		Stusystem xs=new Stusystem();
	}

	public Stusystem()
	{
		mb1=new JPanel();
		bq1=new JLabel("请输入姓名");
		wbk1=new JTextField(10);
		an1=new JButton("查询");
		an1.addActionListener(this);
		an1.setActionCommand("select");
		mb1.add(bq1); mb1.add(wbk1); mb1.add(an1);

		mb2=new JPanel();
		an2=new JButton("添加");
		an2.addActionListener(this);
		an2.setActionCommand("add");
		an3=new JButton("修改");
		an3.addActionListener(this);
		an3.setActionCommand("update");
		an4=new JButton("删除");
		an4.addActionListener(this);
		an4.setActionCommand("delete");
		mb2.add(an2); mb2.add(an3); mb2.add(an4);

		ziduan=new Vector();
		ziduan.add("编号");
		ziduan.add("姓名");
		ziduan.add("性别");
		ziduan.add("年龄");
		ziduan.add("籍贯");
		ziduan.add("专业");

		jilu=new Vector();

		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=student","sa","gzx254052834843");
			ps=ct.prepareStatement("select * from xuesheng");
			rs=ps.executeQuery();

			while(rs.next())
			{
				Vector hang=new Vector();
				hang.add(rs.getInt(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getInt(4));
				hang.add(rs.getString(5));
				hang.add(rs.getString(6));
				jilu.add(hang);
			}
		}catch(Exception e){}
		finally
		{
			try{
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
			}
			catch(Exception e){}
		}
		bg1=new JTable(jilu,ziduan);//JTable后面的括号里先是记录后是字段
		gd1=new JScrollPane(bg1);

		this.add(gd1);
		this.add(mb1,"North");
		this.add(mb2,"South");

		this.setTitle("学生管理系统");
		this.setSize(800,500);
		this.setLocation(600,200);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("select"))
		{
			String name=this.wbk1.getText().trim();
			String sql;
			Studata stu;
			if(name.equals(""))
			{
				stu=new Studata();
			}
			else
			{
				sql="select * from xuesheng where name='"+name+"'";
				stu=new Studata(sql);
			}	
			bg1.setModel(stu);
		}
		else if(e.getActionCommand().equals("add"))
		{
			Stuadd stu=new Stuadd(this,"添加学生信息",true);
			Studata stu2=new Studata();
			bg1.setModel(stu2);
		}
		else if(e.getActionCommand().equals("update"))
		{
			int ii=this.bg1.getSelectedRow();//记录点击的行
			if(ii==-1)
			{
				JOptionPane.showMessageDialog(this,"请选中要修改的行");//弹出的信息界面
				return;//返回调用的地方
			}
			Studata stu3=new Studata();
			Stuupdate ll=new Stuupdate(this,"修改学生信息",true,stu3,ii);
			
			Studata stu4=new Studata();
			bg1.setModel(stu4);
		}
		else if(e.getActionCommand().equals("delete"))
		{
			int ii=this.bg1.getSelectedRow();//记录点击的行
			if(ii==-1)
			{
				JOptionPane.showMessageDialog(this,"请选中要删除的行");//弹出的信息界面
				return;//返回调用的地方
			}
			Studata stu3=new Studata();
			int st=(int)stu3.getValueAt(ii,0);//ii返回选中的行，0是第0列
			PreparedStatement ps=null;
			Connection ct=null;
			ResultSet rs=null;
			Statement sm=null;
			
			try
			{
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=student","sa","gzx254052834843");
				ps=ct.prepareStatement("delete from xuesheng where number=?");
				ps.setInt(1,st);
				ps.executeQuery();
			}catch(Exception e2){}
			finally
			{
				try{
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
				}
				catch(Exception e3){}
			}
			Studata stu4=new Studata();
			bg1.setModel(stu4);//添加完之后就会在界面上显示出来
		}
	}

}
