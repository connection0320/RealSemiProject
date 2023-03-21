package com.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

		Connection con = null;		
		PreparedStatement pstmt = null;	
		ResultSet rs = null;
		String sql = null;

		private static MemberDAO instance;
		
		private MemberDAO() {  }  // 기본 생성자
		


		public static MemberDAO getInstance() {
			
			if(instance == null) {
				instance = new MemberDAO();
			}
			
			return instance;
		}  
		

		public void openConn() {
			
			try {
				
				Context initCtx = new InitialContext();
				
			
				Context ctx = 
					(Context)initCtx.lookup("java:comp/env");
				
				
				DataSource ds = 
						(DataSource)ctx.lookup("jdbc/myoracle");
				
			
				con = ds.getConnection();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}  
		public void closeConn(ResultSet rs,
				PreparedStatement pstmt, Connection con) {
			
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}  // closeConn() 메서드 end

		public int insertMember(MemberDTO dto) {
			int result = 0, count = 0;
			try {
				openConn();
				sql = "select count(*) from member";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					count = rs.getInt(1);
				}
				
				sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,?,null,null,null,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, count + 1);
				pstmt.setInt(2, dto.getMember_age());
				pstmt.setString(3, dto.getMember_name());
				pstmt.setString(4, dto.getMember_phone());
				pstmt.setString(5, dto.getMember_email());
				pstmt.setString(6, dto.getMember_addr1());
				pstmt.setString(7, dto.getMember_addr2());
				pstmt.setString(8, dto.getMember_addr3());
				pstmt.setString(9, dto.getMember_self());
				pstmt.setString(10, dto.getMember_nick());
				pstmt.setString(11, dto.getMember_gender());
				pstmt.setString(12, dto.getMember_id());
				pstmt.setString(13, dto.getMember_pwd());
				
				result = pstmt.executeUpdate();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
			
			return result;
		}
		
	public int checkId(String id) {
		int result = 0;
		openConn();
		try {
			sql = "select count(*) from member where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	}
	
	public int checkPwd(String id, String pwd) {
		int result = 0;
		String dbPwd = "";
		openConn();
		try {
			sql = "select member_pwd from member where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbPwd = rs.getString("member_pwd");
			}
			
			if(dbPwd.equals(pwd)) {
				result = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
}