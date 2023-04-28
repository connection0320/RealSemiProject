package com.member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public MemberDTO contentById(String id) {
		MemberDTO dto = new MemberDTO();
		openConn();
		try {
			sql = "select * from member where member_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setMember_id(rs.getString("member_id"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_age(rs.getInt("member_age"));
				dto.setMember_addr1(rs.getString("member_addr1"));
				dto.setMember_addr2(rs.getString("member_addr2"));
				dto.setMember_addr3(rs.getString("member_addr3"));
				dto.setMember_animal1(rs.getInt("member_animal1"));
				dto.setMember_animal2(rs.getInt("member_animal2"));
				dto.setMember_animal3(rs.getInt("member_animal3"));
				dto.setMember_gender(rs.getString("member_gender"));
				dto.setMember_email(rs.getString("member_email"));
				dto.setMember_name(rs.getString("member_name"));
				dto.setMember_nick(rs.getString("member_nick"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_num(rs.getInt("member_num"));
				dto.setMember_phone(rs.getString("member_phone"));
				dto.setMember_self(rs.getString("member_self"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	public MemberDTO contentByNum(int no) {
		MemberDTO dto = new MemberDTO();
		openConn();
		try {
			sql = "select * from member where member_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setMember_id(rs.getString("member_id"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_age(rs.getInt("member_age"));
				dto.setMember_addr1(rs.getString("member_addr1"));
				dto.setMember_addr2(rs.getString("member_addr2"));
				dto.setMember_addr3(rs.getString("member_addr3"));
				dto.setMember_animal1(rs.getInt("member_animal1"));
				dto.setMember_animal2(rs.getInt("member_animal2"));
				dto.setMember_animal3(rs.getInt("member_animal3"));
				dto.setMember_gender(rs.getString("member_gender"));
				dto.setMember_email(rs.getString("member_email"));
				dto.setMember_name(rs.getString("member_name"));
				dto.setMember_nick(rs.getString("member_nick"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_num(rs.getInt("member_num"));
				dto.setMember_phone(rs.getString("member_phone"));
				dto.setMember_self(rs.getString("member_self"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	public int ModifyMember(MemberDTO dto) {
		int result = 0;
		openConn();
		try {
			sql = "update member set member_age = ?, member_email = ?, member_nick = ?, member_pwd = ?, member_phone = ?, member_self = ? where member_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getMember_age());
			pstmt.setString(2, dto.getMember_email());
			pstmt.setString(3, dto.getMember_nick());
			pstmt.setString(4, dto.getMember_pwd());
			pstmt.setString(5, dto.getMember_phone());
			pstmt.setString(6, dto.getMember_self());
			pstmt.setInt(7, dto.getMember_num());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	}
	
	public void updateSequence(int no) {
		openConn();
		try {
			sql = "update member set member_num = member_num - 1 where member_num > ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
	
	public int deleteMember(int no) {
		int result = 0;
		openConn();
		try {
			sql ="delete from member where member_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
	}
	
	public ArrayList<MemberDTO> getListMatch(String location, int idNum){
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		try {
			openConn();
			sql = "select * from member where member_addr2 = ? and member_num != ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, location);
			pstmt.setInt(2, idNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setMember_id(rs.getString("member_id"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_age(rs.getInt("member_age"));
				dto.setMember_addr1(rs.getString("member_addr1"));
				dto.setMember_addr2(rs.getString("member_addr2"));
				dto.setMember_addr3(rs.getString("member_addr3"));
				dto.setMember_animal1(rs.getInt("member_animal1"));
				dto.setMember_animal2(rs.getInt("member_animal2"));
				dto.setMember_animal3(rs.getInt("member_animal3"));
				dto.setMember_gender(rs.getString("member_age"));
				dto.setMember_email(rs.getString("member_email"));
				dto.setMember_name(rs.getString("member_name"));
				dto.setMember_nick(rs.getString("member_nick"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_num(rs.getInt("member_num"));
				dto.setMember_phone(rs.getString("member_phone"));
				dto.setMember_self(rs.getString("member_self"));
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public MemberDTO findId(String name, String phone) {
		MemberDTO dto = null;
		
		try {
			openConn();
			sql = "select * from member where member_name = ? and member_phone = ?";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setMember_id(rs.getString("member_id"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_age(rs.getInt("member_age"));
				dto.setMember_addr1(rs.getString("member_addr1"));
				dto.setMember_addr2(rs.getString("member_addr2"));
				dto.setMember_addr3(rs.getString("member_addr3"));
				dto.setMember_animal1(rs.getInt("member_animal1"));
				dto.setMember_animal2(rs.getInt("member_animal2"));
				dto.setMember_animal3(rs.getInt("member_animal3"));
				dto.setMember_gender(rs.getString("member_age"));
				dto.setMember_email(rs.getString("member_email"));
				dto.setMember_name(rs.getString("member_name"));
				dto.setMember_nick(rs.getString("member_nick"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_num(rs.getInt("member_num"));
				dto.setMember_phone(rs.getString("member_phone"));
				dto.setMember_self(rs.getString("member_self"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	public MemberDTO findPwd(String name, String id, String email) {
		MemberDTO dto = null;
		
		try {
			openConn();
			sql = "select * from member where member_name = ? and member_id = ? and member_email = ?";			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			pstmt.setString(3, email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setMember_id(rs.getString("member_id"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_age(rs.getInt("member_age"));
				dto.setMember_addr1(rs.getString("member_addr1"));
				dto.setMember_addr2(rs.getString("member_addr2"));
				dto.setMember_addr3(rs.getString("member_addr3"));
				dto.setMember_animal1(rs.getInt("member_animal1"));
				dto.setMember_animal2(rs.getInt("member_animal2"));
				dto.setMember_animal3(rs.getInt("member_animal3"));
				dto.setMember_gender(rs.getString("member_age"));
				dto.setMember_email(rs.getString("member_email"));
				dto.setMember_name(rs.getString("member_name"));
				dto.setMember_nick(rs.getString("member_nick"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_num(rs.getInt("member_num"));
				dto.setMember_phone(rs.getString("member_phone"));
				dto.setMember_self(rs.getString("member_self"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
}
