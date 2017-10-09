package com.wechat.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.wechat.extent.UserLocation;

public class MySQLUtil {
	private static Connection getConn(HttpServletRequest request){
		Connection conn = null;
		String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
		String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
		String username = request.getHeader("BAE_ENV_AK");
		String password = request.getHeader("BAE_ENV_SK");
		String dbName = "";
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 保存用户地理位置
	 * @param request
	 * @param opendId
	 * @param lng
	 * @param lat
	 * @param bd09_lng
	 * @param bd09_lat
	 */
	public static void saveUserLocation(HttpServletRequest request,String opendId,String lng,String lat,String bd09_lng,String bd09_lat){
		String sql = "insert into user_location(open_id,lng,lat,bd09_lng,bd09_lat) values(?,?,?,?,?)";
		try {
			Connection conn = getConn(request);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, opendId);
			ps.setString(2, lng);
			ps.setString(3, lat);
			ps.setString(4, bd09_lng);
			ps.setString(5, bd09_lat);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取用户地理位置
	 */
	public static UserLocation getLastLocation(HttpServletRequest request,String openId){
		UserLocation userLocation = null;
		String sql = "select open_id,lng,lat,bd09_lng,bd09_lat from user_location where open_id=? order by id desc limit 0,1";
		try {
			Connection conn = getConn(request);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, openId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				userLocation = new UserLocation();
				userLocation.setOpenId(openId);
				userLocation.setLng(rs.getString("lng"));
				userLocation.setLat(rs.getString("lat"));
				userLocation.setBd09Lng(rs.getString("bd09_lng"));
				userLocation.setBd09Lat(rs.getString("bd09_lat"));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userLocation;
	}
}
