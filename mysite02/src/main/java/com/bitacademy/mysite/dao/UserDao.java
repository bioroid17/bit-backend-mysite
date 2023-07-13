package com.bitacademy.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bitacademy.mysite.vo.UserVo;

public class UserDao {
	
	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = getConnection();
			String sql = "insert into user values (null, ?, ?, password(?), ?, now())";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo authUser = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			String sql = "select no, name from user where email=? and password=password(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				authUser = new UserVo();
				authUser.setNo(Integer.parseInt(rs.getString(1)));
				authUser.setName(rs.getString(2));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("Error:" + e);
		}
		
		return authUser;
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		
		Class.forName("org.mariadb.jdbc.Driver");

		String url = "jdbc:mariadb://192.168.0.162:3306/webdb?charset=utf8";
		conn = DriverManager.getConnection(url, "webdb", "webdb");
		
		return conn;
	}
}
