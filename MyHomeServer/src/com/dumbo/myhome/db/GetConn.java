package com.dumbo.myhome.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

public class GetConn {
	private Statement stmt;
	private PreparedStatement ps;
	public Connection connection = null;

	public Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			//connection = DriverManager.getConnection("jdbc:mysql://221.214.12.81:3306/myhome", "root", "Loken123");

			
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/myhome", "root", "Loken123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public Statement getStmt() {
		try {
			stmt = connection.createStatement();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return stmt;
	}

	public PreparedStatement getPrepstmt(String sql) {
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return ps;
	}

	public void closeconn(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
