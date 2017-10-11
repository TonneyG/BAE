package com.wechat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("gb2312");
		resp.setCharacterEncoding("gb2312");
		PrintWriter out = resp.getWriter();
		List<HashMap<String,Object>> userList = queryUser(req);
		for(HashMap<String,Object> map : userList){
			out.println(map.get("name")+" "+map.get("age"));
		}
		out.flush();
		out.close();
	}
	
	private static List<HashMap<String,Object>> queryUser(HttpServletRequest request){
		List<HashMap<String,Object>> userList = new ArrayList<HashMap<String,Object>>();
		//从request请求头中取出IP、端口、用户名和密码
		String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
		String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
		String username = request.getHeader("BAE_ENV_AK");
		String password = request.getHeader("BAE_ENV_SK");
		//数据库名称
		String dbName = "";
		String url = String.format("jdbc:mysql://%s:%s/%s", host,port,dbName);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url,username,password);
			String sql = "select name,age from user";
			PreparedStatement ppst = conn.prepareStatement(sql);
			ResultSet rs = ppst.executeQuery();
			while(rs.next()){
				HashMap<String,Object> userMap = new HashMap<String,Object>();
				userMap.put("name",rs.getString("name"));
				userMap.put("age",rs.getInt("age"));
				userList.add(userMap);
			}
			rs.close();
			ppst.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
}
