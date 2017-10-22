package com.wechat.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTest {
	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String url = "jdbc:mysql://localhost:3306/wechat";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,username,password);
			String sql = "select name,age from user";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("name")+","+rs.getString("age"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null != conn){
				conn.close();
			}
		}
	}
}
