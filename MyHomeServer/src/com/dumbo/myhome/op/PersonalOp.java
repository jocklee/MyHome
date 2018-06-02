package com.dumbo.myhome.op;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dumbo.myhome.db.GetConn;


public class PersonalOp {
	public static String personal_nickname,personal_sex;
	//个人信息  查询
	public String PersonalQuery(String personal_tel) 
	{
		 String str=null;
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select i_name,i_sex from user  where i_user=?");
	ps.setString(1, personal_tel);
	rs=ps.executeQuery();
			if (rs.next())
			{
				personal_nickname=rs.getString(1);
				personal_sex=rs.getString(2);
				System.out.println(personal_nickname+"--"+personal_sex);
				str=personal_nickname+"--"+personal_sex;
				
			}
			else
			{
				str=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	
	//个人信息  修改
	public boolean  PersonalUpdata(String personal_tel,String personal_nickname,String personal_sex) 
	{
		boolean temp = false;
		GetConn getConn=new GetConn();	
		Connection conn=getConn.getConnection();
		Statement stmt=getConn.getStmt();
		 String sql="update user set i_name=?,i_sex=? where i_user=?";
		 PreparedStatement ps;

		try {
			 ps=getConn.getPrepstmt(sql);
	         ps.setString(3, personal_tel);
	         ps.setString(1, personal_nickname);
	         ps.setString(2, personal_sex);

			if (ps.executeUpdate()==1)
			{
				
				temp=true;
			}
			else
			{
				temp=false;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		System.out.println(temp);
		return temp;
	
		}
	}


