package com.animal.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.member.model.MemberDAO;
import com.member.model.MemberDTO;

public class AnimalDAO {
	Connection con = null;		
	PreparedStatement pstmt = null;	
	ResultSet rs = null;
	String sql = null;

	private static AnimalDAO instance;
	
	private AnimalDAO() {  }  // 기본 생성자
	


	public static AnimalDAO getInstance() {
		
		if(instance == null) {
			instance = new AnimalDAO();
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
	
	public AnimalDTO contentByNum(int no) {
		AnimalDTO dto = new AnimalDTO();
		openConn();
		try {
			sql = "select * from animal where animal_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setAnimal_age(rs.getInt("animal_age"));
				dto.setAnimal_num(no);
				dto.setAnimal_name(rs.getString("animal_name"));
				dto.setAnimal_gender(rs.getString("animal_gender"));
				dto.setAnimal_race(rs.getString("animal_race"));
				dto.setAnimal_type(rs.getString("animal_type"));
				dto.setAnimal_image(rs.getString("animal_image"));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	public int insertAnimal(MemberDTO mdto,AnimalDTO dto, int no) {
		int result = 0,count = 0;
		
		try {
			openConn();
			sql = "select max(animal_num) from animal";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1) + 1;
			}
			
			sql = "insert into animal values(?, ?, ?, ?, ?, ?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setString(2, dto.getAnimal_name());
			pstmt.setInt(3, dto.getAnimal_age());
			pstmt.setString(4, dto.getAnimal_race());
			pstmt.setString(5, dto.getAnimal_gender());
			pstmt.setString(6, dto.getAnimal_type());
			pstmt.setString(7, dto.getAnimal_image());
			pstmt.executeUpdate();
			
			if(no == 1) {
				sql = "update member set member_animal1 = ? where member_num = ?";
			}else if(no == 2) {
				sql = "update member set member_animal2 = ? where member_num = ?";
			}else if(no == 3) {
				sql = "update member set member_animal3 = ? where member_num = ?";
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, mdto.getMember_num());
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
			sql = "update animal set animal_num = animal_num - 1 where animal_num > ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
	
	
	public int delete(int Animal_num, MemberDTO dto, int a) {
		int result = 0;
		try {
			openConn();
			sql = "delete from animal where animal_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Animal_num);
			pstmt.executeUpdate();
			
			if(a == 1) {
				sql = "update member set member_animal1 = ?, member_animal2 = ?, member_animal3 = null where member_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, dto.getMember_animal2());
				pstmt.setInt(2, dto.getMember_animal3());
				pstmt.setInt(3, dto.getMember_num());
				result = pstmt.executeUpdate();
			}else if (a == 2) {
				sql = "update member set member_animal1 = ?, member_animal2 = null where member_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, dto.getMember_animal2());
				pstmt.setInt(2, dto.getMember_num());
				result = pstmt.executeUpdate();
			}else if (a == 4) {
				sql = "update member set member_animal2 = ?, member_animal3 = null where member_num = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, dto.getMember_animal3());
				pstmt.setInt(2, dto.getMember_num());
				result = pstmt.executeUpdate();
			}else {
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
