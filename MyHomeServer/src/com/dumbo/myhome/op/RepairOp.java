package com.dumbo.myhome.op;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dumbo.myhome.db.GetConn;


public class RepairOp {
	//报修  查询
	public boolean repairQuery(String editTel,String editName,String editAddress,String editWhy) 
	{
		boolean b = false;
		GetConn getConn=new GetConn();
		ResultSet rs = null;
		Connection conn=getConn.getConnection();
		try {
			PreparedStatement ps=conn.prepareStatement("select * from user where i_user=? and i_pwd=?");
			ps.setString(1,editTel);
			ps.setString(2,editName);
			ps.setString(3, editAddress);
			ps.setString(4, editWhy);
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

	
	//报修   插入
	public boolean repairUpdata(String editTel,String editName,String editAddress,String editWhy) 
	{
		boolean temp = false;
		GetConn getConn=new GetConn();	
		Connection conn=getConn.getConnection();
		Statement stmt=getConn.getStmt();
		 String sql="insert into "+"db_repair(tel,name,address,why)"+
	                "values('"+editTel+"','"+editName+"','"+editAddress+"','"+editWhy+"')";
		// System.out.println("p="+password+"p2="+password2);
		
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
	}


