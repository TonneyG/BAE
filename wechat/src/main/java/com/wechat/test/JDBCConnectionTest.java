package com.wechat.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCConnectionTest {
	public static void main(String[] args) throws Exception {
		String url = "";
		String user = "";
		String password = "";
		String sql = "";
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			ppst = conn.prepareStatement(sql);
			rs = ppst.executeQuery();
			while(rs.next()){
				rs.getString("name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(ppst != null){
				ppst.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}
}
