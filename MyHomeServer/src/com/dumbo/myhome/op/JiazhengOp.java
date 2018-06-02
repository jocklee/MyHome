package com.dumbo.myhome.op;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dumbo.myhome.db.GetConn;


public class JiazhengOp {

	
	//±®–ﬁ   ≤Â»Î
	public boolean jiazhengUpdata(String editTel,String editName,String editAddress,String editWork,String editKind,String editMoney) 
	{
		boolean temp = false;
		GetConn getConn=new GetConn();	
		Connection conn=getConn.getConnection();
		Statement stmt=getConn.getStmt();
		 String sql="insert into "+"jiazheng(jz_tel,jz_name,jz_address,jz_work,jz_kind,jz_money)"+
	                "values('"+editTel+"','"+editName+"','"+editAddress+"','"+editWork+"','"+editKind+"','"+editMoney+"')";
		try {

			if (stmt.executeUpdate(sql)==1)
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
		System.out.print(temp);
		return temp;
	
		}
	}


