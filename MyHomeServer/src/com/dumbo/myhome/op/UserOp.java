package com.dumbo.myhome.op;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dumbo.myhome.db.GetConn;


public class UserOp {
	//µÇÂ½  ²éÑ¯
	public boolean login(String username,String password) 
	{
		boolean b = false;
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from user where i_user=? and i_pwd=?");
			ps.setString(1,username);
			ps.setString(2,password);
			rs=ps.executeQuery();
			if (rs.next())
			{
				b=true;
			}
			else
			{
				b=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	public boolean login(String username)
	{
		boolean temp1=false;
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from user where i_user=?");
			ps.setString(1,username);
			rs=ps.executeQuery();
			if (rs.next())
			{
				temp1=true;
			}
			else
			{
				temp1=false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp1;
	}
	
	//×¢²á ²åÈë
	public boolean reg(String username,String nickname,String password,String password2) 
	{
		boolean temp = false;
		GetConn getConn=new GetConn();	
		Connection conn=getConn.getConnection();
		Statement stmt=getConn.getStmt();
		 String sql="insert into "+"user(i_user,i_name,i_pwd)"+
	                "values('"+username+"','"+nickname+"','"+password+"')";
		
		if(password.equals(password2)){
			System.out.println("yes");
		try {

			if (stmt.executeUpdate(sql)==1)
			{
				System.out.println("kkk");
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
		else {
			
			return temp;
		}
	}

}
